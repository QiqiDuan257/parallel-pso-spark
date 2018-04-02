clear; clc;

%% Test Correctness Based on Samples
X1 = zeros(3, 7);
disp(cf_sphere(X1)'); % 0     0     0

X2 = ones(5, 3);
disp(cf_sphere(X2)'); % 3     3     3     3     3

X3 = -ones(7, 3);
disp(cf_sphere(X3)'); % 3     3     3     3     3     3     3

X4 = [0 0 0 0 0; ...
    1 1 1 1 1; ...
    -1 -1 -1 -1 -1; ...
    1 -1 1 -1 1; ...
    1 2 3 4 5; ...
    1 -2 3 -4 5; ...
    5 4 3 2 0; ...
    1.1 1.2 1.3 1.4 -1.5];
disp(cf_sphere(X4)');
% 0    5.0000    5.0000    5.0000   55.0000   55.0000   54.0000    8.5500

%% Test Time Complexity Based on Samples
fun_dims = 10 .^ (0 : 6); % fd
test_num = 30;
fprintf(sprintf('fun_dim  :  runtime  \n'));
fprintf(sprintf('---------|-----------\n'));
for fd_ind = 1 : length(fun_dims)
    X = rand(100, fun_dims(fd_ind));
    runtime_start = tic;
    for test_ind = 1 : test_num
        y = cf_sphere(X);
    end
    runtime = toc(runtime_start) / test_num; % avg
    fprintf(sprintf('%07.2e : %07.4e\n', fun_dims(fd_ind), runtime));
end

%     fun_dim  :  runtime
%     ---------|-----------
%     1.00e+00 : 2.3996e-05
%     1.00e+01 : 2.8231e-06
%     1.00e+02 : 1.4586e-05
%     1.00e+03 : 1.0127e-04
%     1.00e+04 : 4.3189e-03
%     1.00e+05 : 3.0009e-02
%     1.00e+06 : 3.0678e-01
