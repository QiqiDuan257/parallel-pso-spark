function [cf_params] = set_cf_params(func_name, func_dim, func_bounds, func_init_bounds, func_opt_x, func_opt_y)
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
% Set Parameters for the Continuous Function (i.e., cf) Optimized.
%
% -------------------
% || INPUT  ||   <---
% -------------------
%   func_name    <--- string, function name
%   func_dim     <--- int, function dimension (default: 10)
%   func_bounds  <--- matrix(2, func_dim), search bounds during optimization
%       the first row is the search lower bound
%       the second row is the search upper bound (must be larger than lower bound)
%       (default: 10.0 * [-ones(1, func_dim); ones(1, func_dim)])
%   func_init_bounds <--- matrix(2, func_dim), initial search bounds
%       the first row is the initial search lower bound
%       the second row is the initial search upper bound (must be larger than lower bound)
%       (default: func_bounds)
%   func_opt_x   <--- optimal value (i.e., x) for the function
%       (default: Inf * ones(1, func_dim))
%   func_opt_y   <--- optimal function value (i.e., y) for the function
%       (default: Inf)
%
% -------------------
% || OUTPUT ||   --->
% -------------------
%   cf_params    ---> struct, continuous function parameters
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
switch nargin
    case 6
        check_cf_params_func_dim(func_dim);
        check_cf_params_func_bounds(func_bounds, func_dim);
        check_cf_params_func_init_bounds(func_init_bounds, func_dim);
    case 5
        check_cf_params_func_dim(func_dim);
        check_cf_params_func_bounds(func_bounds, func_dim);
        check_cf_params_func_init_bounds(func_init_bounds, func_dim);
        func_opt_y = Inf;
    case 4
        check_cf_params_func_dim(func_dim);
        check_cf_params_func_bounds(func_bounds, func_dim);
        check_cf_params_func_init_bounds(func_init_bounds, func_dim);
        func_opt_x = Inf * ones(1, func_dim);
        func_opt_y = Inf;
    case 3
        check_cf_params_func_dim(func_dim);
        check_cf_params_func_bounds(func_bounds, func_dim);
        func_init_bounds = func_bounds;
        func_opt_x = Inf * ones(1, func_dim);
        func_opt_y = Inf;
    case 2
        check_cf_params_func_dim(func_dim);
        func_bounds = 10.0 * [-ones(1, func_dim); ones(1, func_dim)];
        func_init_bounds = func_bounds;
        func_opt_x = Inf * ones(1, func_dim);
        func_opt_y = Inf;
    case 1
        func_dim = 10;
        func_bounds = 10.0 * [-ones(1, func_dim); ones(1, func_dim)];
        func_init_bounds = func_bounds;
        func_opt_x = Inf * ones(1, func_dim);
        func_opt_y = Inf;
    otherwise
        error('Please set the *func_name* parameter.');
end

if ~ischar(func_name)
    error('Please correctly set the *func_name* parameter ---> ' + ...
        'it must be a char type.');
end

[func_bounds] = update_cf_params_func_bounds(func_bounds, func_dim);
[func_init_bounds] = update_cf_params_func_bounds(func_init_bounds, func_dim);

func_opt_obj = 'min'; % optimization objective (min vs. max)

cf_params = struct(...
    'func_name', func_name, ...
    'func_opt_obj', func_opt_obj, ...
    'func_dim', func_dim, ...
    'func_bounds', func_bounds, ...
    'func_init_bounds', func_init_bounds, ...
    'func_opt_x', func_opt_x, ...
    'func_opt_y', func_opt_y);
end
