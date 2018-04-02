clear; clc; total_run_time_start = tic;
fes = [1000 2000 3000 4000 5000];
cf_list = {'cf_sphere', 'cf_rosenbrock', 'cf_rastrigin', 'cf_griewank'};
for cf_ind = 1 : length(cf_list)
    fprintf('**************************************************\n');
    cf = cf_list{cf_ind};
    for fe_ind = 1 : length(fes)
        fe = fes(fe_ind);
        run_time_start = tic;
        cf_params = set_cf_params(cf, 10 ^ 6, 10.0);
        test_params = set_test_params(3);
        algo_params = set_algo_params_CLPSO(fe, 100);
        opt_res = run_algo(cf_params, test_params, algo_params);
        save_data(cf_params, test_params, algo_params, opt_res, 'PPSN2018--TEST001');
        fprintf(sprintf('Total run time: %f seconds.\n\n\n', toc(run_time_start)));
    end
end
fprintf(sprintf('\nRun Time: %f seconds.\n', toc(total_run_time_start)));
