function y = cf_rastrigin(X)
% [pop_size, fun_dim] = size(X);
y = sum(X .^ 2 - 10.0 * cos(2.0 * pi * X) + 10, 2);
end
