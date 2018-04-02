clear; clc;

%% Test Correctness Based on Samples
X1 = zeros(3, 7);
disp(cf_griewank(X1)'); % 0     0     0

X2 = ones(5, 3);
disp(cf_griewank(X2)'); % 0.6566    0.6566    0.6566    0.6566    0.6566

X3 = -ones(7, 3);
disp(cf_griewank(X3)'); % 0.6566    0.6566    0.6566    0.6566    0.6566    0.6566    0.6566

X4 = [0 0 0 0 0; ...
    1 1 1 1 1; ...
    -1 -1 -1 -1 -1; ...
    1 -1 1 -1 1; ...
    1 2 3 4 5; ...
    1 -2 3 -4 5; ...
    5 4 3 2 0; ...
    1.1 1.2 1.3 1.4 -1.5];
disp(cf_griewank(X4)');
% 0    0.7289    0.7289    0.7289    1.0172    1.0172    0.9901    0.8708

%% Test Time Complexity Based on Samples
fun_dims = 10 .^ (0 : 6); % fd
test_num = 30;
fprintf(sprintf('fun_dim  :  runtime  \n'));
fprintf(sprintf('---------|-----------\n'));
for fd_ind = 1 : length(fun_dims)
    X = rand(100, fun_dims(fd_ind));
    runtime_start = tic;
    for test_ind = 1 : test_num
        y = cf_griewank(X);
    end
    runtime = toc(runtime_start) / test_num; % avg
    fprintf(sprintf('%07.2e : %07.4e\n', fun_dims(fd_ind), runtime));
end

%     fun_dim  :  runtime
%     ---------|-----------
%     1.00e+00 : 1.7551e-04
%     1.00e+01 : 1.5682e-04
%     1.00e+02 : 1.0012e-04
%     1.00e+03 : 6.7664e-04
%     1.00e+04 : 1.1972e-02
%     1.00e+05 : 1.2191e-01
%     1.00e+06 : 1.0667e+00
