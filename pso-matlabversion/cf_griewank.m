function y = cf_griewank(X)
[pop_size, fun_dim] = size(X);
y = sum(X .^ 2, 2) / 4000.0 - ...
    prod(cos(X ./ sqrt(repmat(1 : fun_dim, pop_size, 1))), 2) + 1;
end
