%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_sphere', 10, 100, [-100 50]);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_CLPSO();
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (5.15e-29 & 2.16e-28) [from the original paper]
% vs. [similar]
% (4.12e-27 & 1.17e-26) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_sphere', 30, 100, [-100 50]);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_CLPSO(200000, 40);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (4.46e-14 & 1.73e-14) [from the original paper]
% vs. [similar]
% (2.16e-14 & 8.81e-15) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_rosenbrock', 10, 2.048);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_CLPSO();
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (2.46e+00 & 1.70e+00) [from the original paper]
% vs. [similar]
% (2.83e+00 & 1.87e+00) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_rosenbrock', 30, 2.048);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_CLPSO(200000, 40);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (2.10e+01 & 2.98e+00) [from the original paper]
% vs. [similar]
% (2.12e+01 & 2.01e+00) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_griewank', 10, 600, [-600, 200]);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_CLPSO();
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (4.56e-03 & 4.81e-03) [from the original paper]
% vs. [similar]
% (6.85e-03 & 6.85e-03) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_griewank', 30, 600, [-600, 200]);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_CLPSO(200000, 40);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (3.14e-10 & 4.64e-10) [from the original paper]
% vs. [similar]
% (1.42e-09 & 1.98e-09) [from this code]

%% **
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_rastrigin', 10, 5.12, [-5.12, 2.0]);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_CLPSO();
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (0.00e-00 & 0.00e-00) [from the original paper]
% vs. [similar] (NOTE that the instability on this function **)
% (3.32e-02 & 1.82e-01) [from this code]

%% **
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_rastrigin', 30, 5.12, [-5.12, 2.0]);
test_params = set_test_params(30, true, 20180308);
algo_params = set_algo_params_CLPSO(200000, 40);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (4.85e-10 & 3.63e-10) [from the original paper]
% vs. [similar]
% (4.97e-05 & 4.30e-05) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_schwefel12', 10000, 10.0);
test_params = set_test_params(5, true, 20180308);
algo_params = set_algo_params_CLPSO(500, 100);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% * func_name: cf_schwefel12 + test_num: 5 + func_dim: 10000
% * algo_name: CLPSO + algo_fe_max: 500
% test  1: opt_y = +1.0759e+08 || runtime = 8.56e+01 || fe_runtime = 8.52e+01 || fe_num = 500 <- opt_x [+5.09e+00 ... +9.89e+00]
% test  2: opt_y = +9.5894e+07 || runtime = 8.64e+01 || fe_runtime = 8.61e+01 || fe_num = 500 <- opt_x [+4.33e-01 ... -7.23e+00]
% test  3: opt_y = +1.0482e+08 || runtime = 8.73e+01 || fe_runtime = 8.70e+01 || fe_num = 500 <- opt_x [-8.21e+00 ... +2.57e+00]
% test  4: opt_y = +7.8993e+07 || runtime = 8.67e+01 || fe_runtime = 8.64e+01 || fe_num = 500 <- opt_x [-5.71e+00 ... -8.21e+00]
% test  5: opt_y = +9.3976e+07 || runtime = 8.56e+01 || fe_runtime = 8.53e+01 || fe_num = 500 <- opt_x [-1.76e+00 ... -6.42e+00]
% $------- Summary -------$:
% * func_name: cf_schwefel12 + test_num: 5 + func_dim: 10000
% * algo_name: CLPSO + algo_fe_max: 500
%    opt_y      --- Mean & Std: 9.63e+07  &  1.12e+07
%    runtime    --- Mean & Std: 8.63e+01  &  7.39e-01
%    fe_runtime --- Mean & Std: 8.60e+01  &  7.44e-01
%    fe_ratio   --- Mean & Std:   99.62%  &     0.00%
%    fe_num     --- Mean & Std: 5.00e+02  &  0.00e+00
% Total run time: 431.648730 seconds.
