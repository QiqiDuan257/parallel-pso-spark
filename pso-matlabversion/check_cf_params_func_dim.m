function check_cf_params_func_dim(func_dim)
if ~isscalar(func_dim) || ~isnumeric(func_dim) || func_dim <= 0
    error('Please correctly set the *func_dim* parameter -> ' + ...
        'it must be an integer type and be larger than 0.');
end
end
