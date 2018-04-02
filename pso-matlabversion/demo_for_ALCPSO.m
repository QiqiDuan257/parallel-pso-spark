%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_sphere', 30, 100);
test_params = set_test_params();
algo_params = set_algo_params_ALCPSO(200000, 20);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (1.68e-161 & 8.21e-161) [from the original paper]
% vs. [similar]
% (4.92e-161 & 1.93e-160) [from this code]

%% ***
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_rosenbrock', 30, 10);
test_params = set_test_params();
algo_params = set_algo_params_ALCPSO(200000, 20);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (7.61e+00 & 6.66e+00) [from the original paper]
% vs. [different] (NOTE that the instability on this function ***)
% (3.29e+01 & 6.23e+01) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_griewank', 30, 600);
test_params = set_test_params();
algo_params = set_algo_params_ALCPSO(200000, 20);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (1.22e-02 & 1.58e-02) [from the original paper]
% vs. [similar]
% (1.85e-02 & 2.15e-02) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_rastrigin', 30, 5.12);
test_params = set_test_params();
algo_params = set_algo_params_ALCPSO(200000, 20);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

% (2.53e-14 & 1.38e-14) [from the original paper]
% vs. [similar]
% (1.46e-14 & 5.93e-15) [from this code]

%%
clear; clc; run_time_start = tic;
cf_params = set_cf_params('cf_schwefel12', 10000, 10.0);
test_params = set_test_params(5);
algo_params = set_algo_params_ALCPSO(500, 100);
opt_res = run_algo(cf_params, test_params, algo_params);
save_data(cf_params, test_params, algo_params, opt_res);
fprintf(sprintf('Total run time: %f seconds.\n', toc(run_time_start)));

%     * func_name: cf_schwefel12 + test_num: 5 + func_dim: 10000
%     * algo_name: ALCPSO + algo_fe_max: 500
%     test  1: opt_y = +5.3333e+07 || runtime = 8.77e+01 || fe_runtime = 8.73e+01 || fe_num = 500 <- opt_x [+2.85e+00 ... +1.31e+00]
%     test  2: opt_y = +3.4429e+07 || runtime = 8.68e+01 || fe_runtime = 8.65e+01 || fe_num = 500 <- opt_x [+3.93e-01 ... +2.62e+00]
%     test  3: opt_y = +4.0165e+07 || runtime = 8.69e+01 || fe_runtime = 8.66e+01 || fe_num = 500 <- opt_x [-6.17e+00 ... +2.11e+00]
%     test  4: opt_y = +3.1984e+07 || runtime = 8.71e+01 || fe_runtime = 8.67e+01 || fe_num = 500 <- opt_x [+5.10e+00 ... +3.71e+00]
%     test  5: opt_y = +4.5687e+07 || runtime = 8.62e+01 || fe_runtime = 8.58e+01 || fe_num = 500 <- opt_x [-1.00e+01 ... -3.82e+00]
%     $------- Summary -------$:
%     * func_name: cf_schwefel12 + test_num: 5 + func_dim: 10000
%     * algo_name: ALCPSO + algo_fe_max: 500
%        opt_y      --- Mean & Std: 4.11e+07  &  8.65e+06
%        runtime    --- Mean & Std: 8.69e+01  &  5.48e-01
%        fe_runtime --- Mean & Std: 8.66e+01  &  5.44e-01
%        fe_ratio   --- Mean & Std:   99.60%  &     0.00%
%        fe_num     --- Mean & Std: 5.00e+02  &  0.00e+00
%     Total run time: 434.756788 seconds.

%%
clear; clc;
load('./ALCPSO--20180308/cf_sphere-30-ALCPSO-200000.mat');
fe_cvgc = opt_res{1}.fe_cvgc;
figure(1);
plot(1 : length(fe_cvgc), fe_cvgc);
ax = gca; % get(ax)
title('ALCPSO on Sphere');
xlabel('Iteration Number')
ylabel('Function Value');
ax.YScale = 'log'; % set y-axis to logarithmic scaling
ax.FontSize = 12;
