function func_bounds = update_cf_params_func_bounds(func_bounds, func_dim)
if isscalar(func_bounds)
    func_bounds = func_bounds * ...
        [-ones(1, func_dim); ...
        ones(1, func_dim)];
else
    [row, col] = size(func_bounds);
    if row == 1 && col == 2
        tmp_func_bounds = Inf * ones(2, func_dim);
        tmp_func_bounds(1, :) = func_bounds(1) * ones(1, func_dim);
        tmp_func_bounds(2, :) = func_bounds(2) * ones(1, func_dim);
        func_bounds = tmp_func_bounds;
    end
end
end
