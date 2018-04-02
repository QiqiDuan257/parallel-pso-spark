function opt_res = ALCPSO(cf_params, algo_params)
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
% Particle Swarm Optimizer With an Aging Leader and Challengers (i.e., ALCPSO).
%
% -------------------
% || INPUT  ||   <---
% -------------------
%   cf_params    <--- struct, parameters for the continuous function (i.e., cf) optimized
%   algo_params  <--- struct, parameters for the optimization algorithm (i.e., algo) selected
%
% -------------------
% || OUTPUT ||   --->
% -------------------
%   opt_res      ---> struct, optimization results
%
% -------------------
% ||   REFERENCE   ||
% -------------------
%   1. Chen W N, Zhang J, Lin Y, et al.
%      Particle Swarm Optimization with an Aging Leader and Challengers.
%      IEEE Transactions on Evolutionary Computation (IEEE TEVC), 2013, 17(2): 241-258.
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
runtime_start = tic; % start to timing

%% initialize parameters
% generate uncontrollable randomness
RandStream.setGlobalStream(RandStream('mt19937ar', 'Seed', 'shuffle'));

opt_res = struct(...
    'opt_y', [], ...      % the optimal function value found by the algorithm
    'opt_x', [], ...      % the optimal value found by the algorithm
    'fe_runtime', [], ... % the total runtime of the function evaluations (i.e., fe) part
    'fe_num', [], ...     % the total number of fe
    'fe_cvgc_itv', [], ...% the convergence curve interval of fe
    'fe_cvgc', [], ...    % the convergence curve of fe
    'runtime', []);       % the total runtime of the algorithm

% simplify the naming of local variables
func_dim = cf_params.func_dim;
func_name = str2func(cf_params.func_name);

pop_size = algo_params.algo_pop_size;
fe_max = algo_params.algo_fe_max;
challenging_times_max = algo_params.challenging_times_max;
leader_lifespan_max = algo_params.leader_lifespan_max;

fe_cvgc = Inf * ones(1, ceil(fe_max / pop_size));

%% initialize the population (i.e., X):
% initialize search lower and upper bounds during search
X_lb = repmat(cf_params.func_bounds(1, :), pop_size, 1); % lower bounds
X_ub = repmat(cf_params.func_bounds(2, :), pop_size, 1); % upper bounds
% initialize search lower and upper bounds at the initialization stage
init_X_lb = repmat(cf_params.func_init_bounds(1, :), pop_size, 1);
init_X_ub = repmat(cf_params.func_init_bounds(2, :), pop_size, 1);
X = init_X_lb + (init_X_ub - init_X_lb) .* ...
    rand(RandStream('mt19937ar', 'Seed', algo_params.algo_init_seed), ...
    func_dim, pop_size)';
% initialize velocities (i.e., V)
V_ub = 0.5 * (X_ub - X_lb); V_lb = -V_ub;
V = zeros(pop_size, func_dim);
% initialize function values (i.e., y)
fe_runtime_start = tic; % timing for fe
y = feval(func_name, X);
fe_runtime = toc(fe_runtime_start);
fe_num = pop_size;
% initialize personally best X and y
X_pb = X; y_pb = y;
% initialize globally best X and y
[opt_y, min_ind] = min(y_pb);
opt_x = X_pb(min_ind, :);
fe_cvgc(1 : 2) = [y(1) opt_y];
% initialize leader
leader = opt_x; leader_y = opt_y;
leader_age = 0;
leader_lifespan = leader_lifespan_max;

%% iteratively update the population
while fe_num < fe_max % synchronously update
    indicator_glp = false; % good leading power
    indicator_flp = false; y_pb_bak = y_pb; % fair leading power
    indicator_plp = false; % poor leading power
    for pi = 1 : pop_size
        % update and limit V
        V(pi, :) = 0.4  * V(pi, :) + ...
            2.0 * rand(1, func_dim) .* (X_pb(pi, :) - X(pi, :)) + ...
            2.0 * rand(1, func_dim) .* (leader - X(pi, :));
        V(pi, :) = min(V_ub(pi, :), max(V_lb(pi, :), V(pi, :)));
        % update and limit X
        X(pi, :) = X(pi, :) + V(pi, :);
        X(pi, :) = min(X_ub(pi, :), max(X_lb(pi, :), X(pi, :)));
        fe_runtime_start = tic;
        y = feval(func_name, X(pi, :));
        fe_runtime = fe_runtime + toc(fe_runtime_start);
        fe_num = fe_num + 1;
        if y < leader_y
            leader_y = y; leader = X(pi, :);
            indicator_plp = true;
        end
        % update individually-best X and y
        if y < y_pb(pi)
            X_pb(pi, :) = X(pi, :); y_pb(pi) = y;
            % update globally-best X and y
            if y < opt_y
                opt_y = y; opt_x = X(pi, :);
                indicator_glp = true;
            end
        end
        if rem(fe_num, pop_size) == 0 % opt_res.fe_cvgc_itv == pop_size
            fe_cvgc(fix(fe_num / pop_size)) = opt_y;
        end
    end
    if sum(y_pb) < sum(y_pb_bak)
        indicator_flp = true;
    end
    % control the lifespan of the leader
    leader_age = leader_age + 1;
    if indicator_glp
        leader_lifespan = leader_lifespan + 2;
    elseif indicator_flp
        leader_lifespan = leader_lifespan + 1;
    elseif indicator_plp
    else % no leading power
        leader_lifespan = leader_lifespan - 1;
    end
    if leader_age >= leader_lifespan % generate and test the challenger
        flag_same = true; % the challenger is the same as the leader
        challenger = leader;
        for fd_ind = 1 : func_dim
            if rand < (1 / func_dim)
                challenger(1, fd_ind) = unifrnd(X_lb(1, fd_ind), X_ub(1, fd_ind));
                flag_same = false;
            end
        end
        % ensure that the challenger is different from the leader
        if flag_same
            challenger(randi(func_dim)) = unifrnd(X_lb(1, fd_ind), X_ub(1, fd_ind));
        end
        X_bak = X; V_bak = V;
        fe_runtime_start = tic; % timing for fe
        challenger_y = feval(func_name, challenger);
        fe_runtime = fe_runtime + toc(fe_runtime_start);
        fe_num = fe_num + 1;
        if challenger_y < opt_y
            opt_y = challenger_y; opt_x = challenger;
        end
        if rem(fe_num, pop_size) == 0
            fe_cvgc(fix(fe_num / pop_size)) = opt_y;
        end
        flag_improve = false;
        for try_ind = 1 : challenging_times_max
            for pi = 1 : pop_size
                % update and limit V
                V(pi, :) = 0.4  * V(pi, :) + ...
                    2.0 * rand(1, func_dim) .* (X_pb(pi, :) - X(pi, :)) + ...
                    2.0 * rand(1, func_dim) .* (challenger - X(pi, :));
                V(pi, :) = min(V_ub(pi, :), max(V_lb(pi, :), V(pi, :)));
                % update and limit X
                X(pi, :) = X(pi, :) + V(pi, :);
                X(pi, :) = min(X_ub(pi, :), max(X_lb(pi, :), X(pi, :)));
                fe_runtime_start = tic;
                y = feval(func_name, X(pi, :));
                fe_runtime = fe_runtime + toc(fe_runtime_start);
                fe_num = fe_num + 1;
                % update individually-best X and y
                if y < y_pb(pi)
                    y_pb(pi) = y; X_pb(pi, :) = X(pi, :);
                    flag_improve = true;
                    % update globally-best X and y
                    if y < opt_y
                        opt_y = y; opt_x = X(pi, :);
                    end
                end
                if y < challenger_y
                    challenger_y = y; challenger = X(pi, :);
                end
                if rem(fe_num, pop_size) == 0 % opt_res.fe_cvgc_itv == pop_size
                    fe_cvgc(fix(fe_num / pop_size)) = opt_y;
                end
            end
            if flag_improve
                leader = challenger; leader_y = challenger_y;
                leader_age = 0;
                leader_lifespan = leader_lifespan_max;
                break;
            end
        end
        if ~flag_improve
            X = X_bak; V = V_bak;
            leader_age = leader_lifespan - 1;
        end
    end
end

%% return final optimization results
opt_res.opt_y = opt_y;
opt_res.opt_x = opt_x;
opt_res.fe_runtime = fe_runtime;
opt_res.fe_num = fe_num;
opt_res.fe_cvgc_itv = pop_size;
opt_res.fe_cvgc = fe_cvgc;
opt_res.runtime = toc(runtime_start);
end
