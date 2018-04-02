function opt_res = CLPSO(cf_params, algo_params)
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
% Comprehensive Learning Particle Swarm Optimizer (i.e., CLPSO).
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
%   1. Liang J J, Qin A K, Suganthan P N, et al.
%      Comprehensive Learning Particle Swarm Optimizer for Global Optimization of Multimodal Functions.
%      IEEE Transactions on Evolutionary Computation (IEEE TEVC), 2006, 10(3): 281-295.
%      http://ieeexplore.ieee.org/abstract/document/1637688/
%   2. Source Code Published by the Authors (Suganthan P N):
%      http://web.mysites.ntu.edu.sg/epnsugan/PublicSite/Shared%20Documents/Codes/2006-IEEE-TEC-CLPSO.zip
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
refreshing_gap = algo_params.algo_refreshing_gap;
learning_rate = algo_params.algo_learning_rate;
weights = algo_params.algo_weights;

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
V_ub = 0.2 * (X_ub - X_lb); V_lb = -V_ub;
V = V_lb + (V_ub - V_lb) .* rand(pop_size, func_dim);
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
% initialize learn probability and exemplar learned for each particle
learn_prob = 0.05 + 0.45 * ...
    (exp(10 * (((1 : pop_size) - 1) / (pop_size - 1))) - 1) / (exp(10) - 1);
learn_X_pb = X_pb;
refreshing_gaps = zeros(pop_size, 1);

%% iteratively update the population
while fe_num < fe_max % asynchronously update
    w = weights(fix(fe_num / pop_size)); % inertia weight
    for pi = 1 : pop_size % pop_ind
        % allow the particle to learn from the exemplar until the particle
        % stops improving for a certain number of generations
        if refreshing_gaps(pi) >= refreshing_gap
            refreshing_gaps(pi) = 0;
            learn_flag = false; % flag to learn to at least other particle
            for fd_ind = 1 : func_dim
                if rand < learn_prob(pi)
                    a_or_b = randperm(pop_size, 2); % tournament selection
                    if y_pb(a_or_b(2)) < y_pb(a_or_b(1))
                        learn_X_pb(pi, fd_ind) = X_pb(a_or_b(2), fd_ind);
                    else
                        learn_X_pb(pi, fd_ind) = X_pb(a_or_b(1), fd_ind);
                    end
                    learn_flag = true;
                else
                    learn_X_pb(pi, fd_ind) = X_pb(pi, fd_ind);
                end
            end
            % make sure to learn to at least other particle for one random dimension
            if ~learn_flag
                fd_ind = randi(func_dim);
                a_or_b = randperm(pop_size, 2);
                if a_or_b(1) == pi
                    exemplar_ind = a_or_b(2);
                else
                    exemplar_ind = a_or_b(1);
                end
                learn_X_pb(pi, fd_ind) = X_pb(exemplar_ind, fd_ind);
            end
        end
        % update and limit V
        V(pi, :) = w * V(pi, :) + learning_rate * ...
            rand(1, func_dim) .* (learn_X_pb(pi, :) - X(pi, :));
        V(pi, :) = min(V_ub(pi, :), max(V_lb(pi, :), V(pi, :)));
        % update and limit X
        X(pi, :) = X(pi, :) + V(pi, :);
        % different from the original paper to avoid inefficient loop
        X(pi, :) = min(X_ub(pi, :), max(X_lb(pi, :), X(pi, :)));
        fe_runtime_start = tic;
        y(pi) = feval(func_name, X(pi, :));
        fe_runtime = fe_runtime + toc(fe_runtime_start);
        fe_num = fe_num + 1;
        % update personally and globally best X and y
        if y(pi) < y_pb(pi)
            X_pb(pi, :) = X(pi, :); y_pb(pi) = y(pi);
            refreshing_gaps(pi) = 0;
            if y_pb(pi) < opt_y % update globally best X and y
                opt_y = y_pb(pi); opt_x = X_pb(pi, :);
            end
        else
            refreshing_gaps(pi) = refreshing_gaps(pi) + 1;
        end
        if rem(fe_num, pop_size) == 0 % opt_res.fe_cvgc_itv == pop_size
            fe_cvgc(fix(fe_num / pop_size)) = opt_y;
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
