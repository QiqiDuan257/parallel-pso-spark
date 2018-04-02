% NOTE that the original paper here refer to:
%    Liang J J, Qin A K, Suganthan P N, et al.
%    Comprehensive Learning Particle Swarm Optimizer for Global Optimization of Multimodal Functions.
%    IEEE Transactions on Evolutionary Computation (IEEE TEVC), 2006, 10(3): 281-295.
%    http://ieeexplore.ieee.org/abstract/document/1637688/
% rather than:
%    Shi Y, Eberhart R.
%    A Modified Particle Swarm Optimizer.
%    IEEE World Congress on Computational Intelligence, 1998, 69-73.
%    http://ieeexplore.ieee.org/document/699146/

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_sphere', 10, 100, [-100 50]);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_PSO();
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (7.96e-51 & 3.56e-50) [from the original paper]
% vs. [similar]
% (2.84e-47 & 1.55e-46) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_sphere', 30, 100, [-100 50]);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_PSO(200000, 40);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (9.78e-30 & 2.50e-29) [from the original paper]
% vs. [similar]
% (1.78e-31 & 5.80e-31) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_rosenbrock', 10, 2.048);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_PSO();
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (3.08e+00 & 7.69e-01) [from the original paper]
% vs. [similar]
% (3.97e+00 & 6.93e-01) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_rosenbrock', 30, 2.048);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_PSO(200000, 40);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (2.93e+01 & 2.51e+01) [from the original paper]
% vs. [similar]
% (2.32e+01 & 1.70e+00) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_griewank', 10, 600, [-600, 200]);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_PSO();
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (9.69e-02 & 5.01e-02) [from the original paper]
% vs. [similar]
% (9.00e-02 & 4.14e-02) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_griewank', 30, 600, [-600, 200]);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_PSO(200000, 40);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (8.13e-03 & 7.16e-03) [from the original paper]
% vs. [similar]
% (1.91e-02 & 2.39e-02) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_rastrigin', 10, 5.12, [-5.12, 2.0]);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_PSO();
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (5.82e+00 & 2.96e+00) [from the original paper]
% vs. [similar]
% (6.04e+00 & 6.55e+00) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_rastrigin', 30, 5.12, [-5.12, 2.0]);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_PSO(200000, 40);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (2.90e+01 & 7.70e+00) [from the original paper]
% vs. [similar]
% (3.09e+01 & 1.09e+01) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_schwefel12', 10000, 10.0);
test_params = set_test_params(5, true, 20180308);
algo_params = set_algo_params_PSO(500, 100);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% * func_name: cf_schwefel12 + test_num: 5 + func_dim: 10000
% * algo_name: PSO + algo_fe_max: 500
% test  1: opt_y = +2.7581e+07 || runtime = 1.03e+02 || fe_runtime = 1.03e+02 || fe_num = 500 <- opt_x [+1.01e+00 ... +1.62e-01]
% test  2: opt_y = +2.2641e+07 || runtime = 1.03e+02 || fe_runtime = 1.02e+02 || fe_num = 500 <- opt_x [+1.28e+00 ... -7.52e+00]
% test  3: opt_y = +2.9283e+07 || runtime = 1.03e+02 || fe_runtime = 1.03e+02 || fe_num = 500 <- opt_x [+4.56e+00 ... +3.72e+00]
% test  4: opt_y = +3.2331e+07 || runtime = 1.03e+02 || fe_runtime = 1.03e+02 || fe_num = 500 <- opt_x [-4.97e+00 ... -5.16e+00]
% test  5: opt_y = +2.0490e+07 || runtime = 1.03e+02 || fe_runtime = 1.02e+02 || fe_num = 500 <- opt_x [+1.99e+00 ... +4.21e+00]
% $------- Summary -------$:
% * func_name: cf_schwefel12 + test_num: 5 + func_dim: 10000
% * algo_name: PSO + algo_fe_max: 500
%    opt_y      --- Mean & Std: 2.65e+07  &  4.85e+06
%    runtime    --- Mean & Std: 1.03e+02  &  2.54e-01
%    fe_runtime --- Mean & Std: 1.03e+02  &  2.30e-01
%    fe_ratio   --- Mean & Std:   99.79%  &     0.00%
%    fe_num     --- Mean & Std: 5.00e+02  &  0.00e+00
% Total run time: 514.625851 seconds.
