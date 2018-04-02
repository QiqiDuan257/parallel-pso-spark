function opt_res = run_algo(cf_params, test_params, algo_params)
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
% Run the Optimization Algorithm.
%
% -------------------
% || INPUT  ||   <---
% -------------------
%   cf_params    <--- struct, parameters for the continuous function optimized
%   test_params  <--- struct, parameters for all tests
%   algo_params  <--- struct, parameters for the optimization algorithm selected
%
% -------------------
% || OUTPUT ||   --->
% -------------------
%   opt_res      ---> struct, optimization results
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
% simplify the naming of local variables
func_name = cf_params.func_name;
func_dim = cf_params.func_dim;
test_num = test_params.test_num;
algo_name = algo_params.algo_name;
algo_fe_max = algo_params.algo_fe_max;

% save final optimization results
opt_res = cell(1, test_num);
opt_ys = zeros(1, test_num);
fe_runtimes = zeros(1, test_num);
fe_num = zeros(1, test_num);
runtimes = zeros(1, test_num);

fprintf(sprintf('* func_name: %s + test_num: %d + func_dim: %d\n', ...
    func_name, test_num, func_dim));
fprintf(sprintf('* algo_name: %s + algo_fe_max: %d\n', ...
    algo_name, algo_fe_max));

log_info = ['test %2d: opt_y = %+7.4e || runtime = %7.2e ' ...
    '|| fe_runtime = %7.2e || fe_num = %d <- '...
    'opt_x [%+7.2e ... %+7.2e]\n'];

for ti = 1 : test_num % test_ind
    algo_params.algo_init_seed = test_params.test_seeds(ti);
    opt_res{ti} = feval(str2func(algo_name), cf_params, algo_params);
    opt_ys(ti) = opt_res{ti}.opt_y;
    runtimes(ti) = opt_res{ti}.runtime;
    fe_runtimes(ti) = opt_res{ti}.fe_runtime;
    fe_num(ti) = opt_res{ti}.fe_num;
    if test_params.test_log
        fprintf(log_info, ti, ...
            opt_res{ti}.opt_y, opt_res{ti}.runtime, ...
            opt_res{ti}.fe_runtime, opt_res{ti}.fe_num, ...
            opt_res{ti}.opt_x(1), opt_res{ti}.opt_x(end));
    end
end

fprintf('$------- Summary -------$:\n');
fe_ratio = 100.0 * (fe_runtimes / runtimes); % percentage
fprintf(sprintf('* func_name: %s + test_num: %d + func_dim: %d\n', ...
    func_name, test_num, func_dim));
fprintf(sprintf('* algo_name: %s + algo_fe_max: %d\n', ...
    algo_name, algo_fe_max));
fprintf('   opt_y      --- Mean & Std: %7.2e  &  %7.2e\n', mean(opt_ys), std(opt_ys));
fprintf('   runtime    --- Mean & Std: %7.2e  &  %7.2e\n', mean(runtimes), std(runtimes));
fprintf('   fe_runtime --- Mean & Std: %7.2e  &  %7.2e\n', mean(fe_runtimes), std(fe_runtimes));
fprintf('   fe_ratio   --- Mean & Std: %7.2f%%  &  %7.2f%%\n', mean(fe_ratio), std(fe_ratio));
fprintf('   fe_num     --- Mean & Std: %7.2e  &  %7.2e\n', mean(fe_num), std(fe_num));
end
