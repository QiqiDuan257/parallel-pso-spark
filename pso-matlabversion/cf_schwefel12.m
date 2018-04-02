function y = cf_schwefel12(X)
[pop_size, fun_dim] = size(X);
y = zeros(pop_size, 1);
for fd_ind = 1 : fun_dim
    y = y + (sum(X(:, 1 : fd_ind), 2)) .^ 2;
end
end
