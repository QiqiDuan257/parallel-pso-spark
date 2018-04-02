function [algo_params] = set_algo_params_ALCPSO(algo_fe_max, algo_pop_size)
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
% Set Parameters for the Optimization Algorithm (i.e., ALCPSO) Selected.
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
        if ~isnumeric(algo_fe_max) || algo_fe_max <= 0
            error('Please correctly set the *algo_fe_max* parameter -> ' + ...
                'it must be numeric and larger than 0.');
        end
        if ~isnumeric(algo_pop_size) || algo_pop_size <= 0
            error('Please correctly set the *algo_pop_size* parameter -> ' + ...
                'it must be numeric and larger than 0.');
        end
    case 1
        if ~isnumeric(algo_fe_max) || algo_fe_max <= 0
            error('Please correctly set the *algo_fe_max* parameter -> ' + ...
                'it must be numeric and larger than 0.');
        end
        algo_pop_size = 10;
    otherwise
        algo_fe_max = 30000;
        algo_pop_size = 10;
end

algo_name = 'ALCPSO';
algo_iter_max = ceil(algo_fe_max / algo_pop_size); % the maximum of iterations
challenging_times_max = 2; % the maximum of challenging times on each iteration
leader_lifespan_max = 60; % the maximum of the lifespan of the leader
algo_init_seed = [];  % the random seed to initialize the population

algo_params = struct(...
    'algo_name', algo_name, ...
    'algo_fe_max', algo_fe_max, ...
    'algo_pop_size', algo_pop_size, ...
    'algo_iter_max', algo_iter_max, ...
    'challenging_times_max', challenging_times_max, ...
    'leader_lifespan_max', leader_lifespan_max, ...
    'algo_init_seed', algo_init_seed);
end
