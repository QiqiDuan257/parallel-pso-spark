clear; clc;

%% Test Correctness Based on Samples
X1 = zeros(4, 13);
disp(cf_schwefel12(X1)'); % 0     0     0     0

X2 = ones(5, 6);
disp(cf_schwefel12(X2)'); % 91    91    91    91    91

X3 = -ones(2, 6);
disp(cf_schwefel12(X3)'); % 91    91

X4 = [0 0 0 0 0; ...
    1 1 1 1 1; ...
    -1 -1 -1 -1 -1; ...
    1 -1 1 -1 1; ...
    1 2 3 4 5; ...
    1 -2 3 -4 5; ...
    5 4 3 2 0; ...
    -5 4 3 2 -1];
disp(cf_schwefel12(X4)');
% 0    55    55     3   371    19   642    55

%% Test Time Complexity Based on Samples
fun_dims = 10 .^ (0 : 4); % fd
test_num = 5;
fprintf(sprintf('fun_dim  :  runtime  \n'));
fprintf(sprintf('---------|-----------\n'));
for fd_ind = 1 : length(fun_dims)
    X = rand(100, fun_dims(fd_ind));
    runtime_start = tic;
    for test_ind = 1 : test_num
        y = cf_schwefel12(X);
    end
    runtime = toc(runtime_start) / test_num; % avg
    fprintf(sprintf('%07.2e : %07.4e\n', fun_dims(fd_ind), runtime));
end

%     fun_dim  :  runtime
%     ---------|-----------
%     1.00e+00 : 1.1453e-04
%     1.00e+01 : 7.2245e-05
%     1.00e+02 : 8.7945e-04
%     1.00e+03 : 8.1859e-02
%     1.00e+04 : 2.0403e+01
