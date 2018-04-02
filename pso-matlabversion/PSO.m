function opt_res = PSO(cf_params, algo_params)
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
% Particle Swarm Optimizer (i.e., PSO).
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
%   1. Shi Y, Eberhart R.
%      A Modified Particle Swarm Optimizer.
%      IEEE World Congress on Computational Intelligence, 1998, 69-73.
%      http://ieeexplore.ieee.org/document/699146/
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
V = unifrnd(V_lb, V_ub);
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

%% iteratively update the population
while fe_num < fe_max % synchronously update
    w = weights(fix(fe_num / pop_size)); % inertia weight
    % update and limit the velocities
    V = w * V ...
        + 2.0 * rand(pop_size, func_dim) .* (X_pb - X) ...
        + 2.0 * rand(pop_size, func_dim) .* (repmat(opt_x, pop_size, 1) - X);
    V = min(V_ub, max(V_lb, V));
    % update, limit, and evaluate the positions
    X = X + V;
    X = min(X_ub, max(X_lb, X));
    fe_runtime_start = tic;
    y = feval(func_name, X);
    fe_runtime = fe_runtime + toc(fe_runtime_start);
    fe_num = fe_num + pop_size;
    % update the individually-best positions and function values
    X_pb_ind = y < y_pb;
    X_pb(X_pb_ind, :) = X(X_pb_ind, :);
    y_pb(X_pb_ind) = y(X_pb_ind);
    % update the globally-best positions and function values
    [y_curr_min, min_ind] = min(y_pb);
    if y_curr_min < opt_y
        opt_y = y_curr_min;
        opt_x = X(min_ind, :);
    end
    if rem(fe_num, pop_size) == 0 % opt_res.fe_cvgc_itv == pop_size
        fe_cvgc(fix(fe_num / pop_size)) = opt_y;
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
