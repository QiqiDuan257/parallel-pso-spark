function check_cf_params_func_bounds(func_bounds, func_dim)
if ~isnumeric(func_bounds)
    error('Please correctly set the *func_bounds* parameter -> ' + ...
        'it must be a numeric type.');
end
[row, col] = size(func_bounds);
if row == 1 && col == 1
    if func_bounds <= 0
        error('Please correctly set the *func_bounds* parameter -> ' + ...
            'when *func_bounds* is a scalar, it must be larger than 0.');
    end
elseif row == 1 && col == 2
    if func_bounds(1) >= func_bounds(2)
        error('Please correctly set the *func_bounds* parameter -> ' + ...
            'when *func_bounds* is a 1x2 matrix, lower bounds must be less than upper bounds.');
    end
elseif row == 2 && col > 0
    if col ~= func_dim
        error('Please correctly set the *func_bounds* parameter -> ' + ...
            'its column dimension must be equal to the *func_dim* parameter.');
    end
    if any(func_bounds(1, :) >= func_bounds(2, :))
        error('Please correctly set the *func_bounds* parameter -> ' + ...
            'lower bounds must be less than upper bounds.');
    end
else
    error('Please correctly set the *func_bounds* parameter.');
end
end
