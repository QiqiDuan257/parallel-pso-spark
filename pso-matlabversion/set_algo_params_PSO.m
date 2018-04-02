function [algo_params] = set_algo_params_PSO(algo_fe_max, algo_pop_size)
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
% Set Parameters for the Optimization Algorithm (i.e., PSO) Selected.
%
% -------------------
% || INPUT  ||   <---
% -------------------
%   algo_fe_max  <--- int, the maximum of function evaluations (default: 30000)
%   algo_pop_size<--- int, the population size (default: 10)
%
% -------------------
% || OUTPUT ||   --->
% -------------------
%   algo_params  ---> struct, algorithm parameters
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
switch nargin
    case 2
    case 1
        algo_pop_size = 10;
    otherwise
        algo_fe_max = 30000;
        algo_pop_size = 10;
end

if ~isnumeric(algo_fe_max) || algo_fe_max <= 0
    error('Please correctly set the *algo_fe_max* parameter -> ' + ...
        'it must be numeric and larger than 0.');
end
if ~isnumeric(algo_pop_size) || algo_pop_size <= 0
    error('Please correctly set the *algo_pop_size* parameter -> ' + ...
        'it must be numeric and larger than 0.');
end

algo_name = 'PSO';
algo_iter_max = ceil(algo_fe_max / algo_pop_size); % the maximum of iterations
algo_weight_bounds = [0.9, 0.4]; % inertia weights linearly decreased during optimization
algo_weights = linspace(algo_weight_bounds(1), algo_weight_bounds(2), algo_iter_max);
algo_init_seed = [];  % the random seed to initialize the population

algo_params = struct(...
    'algo_name', algo_name, ...
    'algo_fe_max', algo_fe_max, ...
    'algo_pop_size', algo_pop_size, ...
    'algo_iter_max', algo_iter_max, ...
    'algo_weights', algo_weights, ...
    'algo_init_seed', algo_init_seed);
end
