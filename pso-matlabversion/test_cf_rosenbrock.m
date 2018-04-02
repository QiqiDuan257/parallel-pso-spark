clear; clc;

%% Test Correctness Based on Samples
X1 = zeros(3, 7);
disp(cf_rosenbrock(X1)'); % 6     6     6

X2 = ones(5, 3);
disp(cf_rosenbrock(X2)'); % 0     0     0     0     0

X3 = -ones(7, 3);
disp(cf_rosenbrock(X3)'); % 808   808   808   808   808   808   808

X4 = [0 0 0 0 0; ...
    1 1 1 1 1; ...
    -1 -1 -1 -1 -1; ...
    1 -1 1 -1 1; ...
    1 2 3 4 5; ...
    1 -2 3 -4 5; ...
    5 4 3 2 0];
disp(cf_rosenbrock(X4)');
% 4           0        1616         808       14814       30038       67530

%% Test Time Complexity Based on Samples
fun_dims = 10 .^ (0 : 6); % fd
test_num = 30;
fprintf(sprintf('fun_dim  :  runtime  \n'));
fprintf(sprintf('---------|-----------\n'));
for fd_ind = 1 : length(fun_dims)
    X = rand(100, fun_dims(fd_ind));
    runtime_start = tic;
    for test_ind = 1 : test_num
        y = cf_rosenbrock(X);
    end
    runtime = toc(runtime_start) / test_num; % avg
    fprintf(sprintf('%07.2e : %07.4e\n', fun_dims(fd_ind), runtime));
end

%     fun_dim  :  runtime
%     ---------|-----------
%     1.00e+00 : 2.0972e-04
%     1.00e+01 : 6.4332e-05
%     1.00e+02 : 1.6430e-04
%     1.00e+03 : 7.4972e-04
%     1.00e+04 : 1.7165e-02
%     1.00e+05 : 1.5970e-01
%     1.00e+06 : 1.4869e+00
