%%
clear; clc; total_run_time_start = tic;
fun_dims = 10 .^ (1 : 6); % 7 leads to out of memory
cf_list = {'cf_sphere', 'cf_rosenbrock', 'cf_rastrigin', 'cf_griewank'};
for cf_ind = 1 : length(cf_list)
    fprintf('**************************************************\n');
    cf = cf_list{cf_ind};
    for fd_ind = 1 : length(fun_dims)
        fun_dim = fun_dims(fd_ind);
        run_time_start = tic;
        cf_params = set_cf_params(cf, fun_dim, 10.0);
        test_params = set_test_params(3);
        algo_params = set_algo_params_PSO(500, 100);
        opt_res = run_algo(cf_params, test_params, algo_params);
        save_data(cf_params, test_params, algo_params, opt_res, 'PPSN2018--TEST001');
        fprintf(sprintf('Total run time: %f seconds.\n\n\n', toc(run_time_start)));
    end
end
fprintf(sprintf('\nRun Time: %f seconds.\n', toc(total_run_time_start)));
