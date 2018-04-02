%% Run Time Varying with Function Dimensions on Schwefel12
fun_dims = 10 .^ (1 : 5);
PSOMatlab_run_time_mean    = [4.63e-02 2.80e-02 8.90e-01 7.30e+01 9.13e+03] / (60 * 60);
PSOSpark_run_time_mean     = [2.67e-01 1.51e-01 2.60e-01 1.77e+00 4.07e+01] / (60 * 60);
CLPSOMatlab_run_time_mean  = [5.28e-02 1.38e-01 3.40e+00 1.40e+02 8.59e+03] / (60 * 60);
CLPSOSpark_run_time_mean   = [2.86e-01 1.42e-01 2.18e-01 1.44e+00 3.77e+01] / (60 * 60);
ALCPSOMatlab_run_time_mean = [7.36e-02 8.26e-02 1.29e+00 7.13e+01 7.33e+03] / (60 * 60);
ALCPSOSpark_run_time_mean  = [2.74e-01 1.40e-01 2.56e-01 1.63e+00 3.98e+01] / (60 * 60);
PSOScala_run_time_mean     = [4.23e-02 5.61e-02 1.34e+00 1.38e+02 1.97e+04] / (60 * 60);

figure(1);
loglog(fun_dims, PSOMatlab_run_time_mean, '-s', 'LineWidth', 2); hold on;
loglog(fun_dims, PSOSpark_run_time_mean, '-s', 'LineWidth', 2); hold on;
loglog(fun_dims, CLPSOMatlab_run_time_mean, '-s', 'LineWidth', 2); hold on;
loglog(fun_dims, CLPSOSpark_run_time_mean, '-s', 'LineWidth', 2); hold on;
loglog(fun_dims, ALCPSOMatlab_run_time_mean, '-s', 'LineWidth', 2); hold on;
loglog(fun_dims, ALCPSOSpark_run_time_mean, '-s', 'LineWidth', 2); hold on;
loglog(fun_dims, PSOScala_run_time_mean, '-s', 'LineWidth', 2); hold on;
title('Run Time Varying with Function Dimensions on Schwefel12');
xlabel('Function dimension')
ylabel('Average run time (hours)');
legend('Matlab-PSO', 'Spark-PSO', 'Matlab-CLPSO', 'Spark-CLPSO', 'Matlab-ALCPSO', 'Spark-ALCPSO', 'Scala-PSO', 'Location', 'northwest');

figure(2);
semilogx(fun_dims, PSOMatlab_run_time_mean, '-s', 'LineWidth', 2); hold on;
semilogx(fun_dims, PSOSpark_run_time_mean, '-s', 'LineWidth', 2); hold on;
semilogx(fun_dims, CLPSOMatlab_run_time_mean, '-s', 'LineWidth', 2); hold on;
semilogx(fun_dims, CLPSOSpark_run_time_mean, '-s', 'LineWidth', 2); hold on;
semilogx(fun_dims, ALCPSOMatlab_run_time_mean, '-s', 'LineWidth', 2); hold on;
semilogx(fun_dims, ALCPSOSpark_run_time_mean, '-s', 'LineWidth', 2); hold on;
semilogx(fun_dims, PSOScala_run_time_mean, '-s', 'LineWidth', 2); hold on;
title('Run Time Varying with Function Dimensions on Schwefel12');
xlabel('Function dimension')
ylabel('Average run time (hours)');
legend('Matlab-PSO', 'Spark-PSO', 'Matlab-CLPSO', 'Spark-CLPSO', 'Matlab-ALCPSO', 'Spark-ALCPSO', 'Scala-PSO', 'Location', 'northwest');

%% Run Time Varying with Number of Function Evaluations on Schwefel12
fun_dims = [1000 2000 3000 4000 5000];
PSOMatlab_run_time_mean    = [1.85e+04 3.74e+04 5.62e+04 7.48e+04 9.36e+04] / (60 * 60);
PSOSpark_run_time_mean     = [8.12e+01 1.62e+02 3.04e+02 3.23e+02 4.28e+02] / (60 * 60);
CLPSOMatlab_run_time_mean  = [1.53e+04 3.04e+04 4.49e+04 5.63e+04 6.96e+04] / (60 * 60);
CLPSOSpark_run_time_mean   = [7.76e+01 1.85e+02 3.12e+02 4.39e+02 4.79e+02] / (60 * 60);
ALCPSOMatlab_run_time_mean = [1.42e+04 2.89e+04 4.41e+04 5.86e+04 7.45e+04] / (60 * 60);
ALCPSOSpark_run_time_mean =  [7.94e+01 2.21e+02 3.42e+02 4.16e+02 3.93e+02] / (60 * 60);
figure(1);
plot(fun_dims, PSOMatlab_run_time_mean, '-s', 'LineWidth', 2); hold on;
plot(fun_dims, PSOSpark_run_time_mean, '-s', 'LineWidth', 2); hold on;
plot(fun_dims, CLPSOMatlab_run_time_mean, '-s', 'LineWidth', 2); hold on;
plot(fun_dims, CLPSOSpark_run_time_mean, '-s', 'LineWidth', 2); hold on;
plot(fun_dims, ALCPSOMatlab_run_time_mean, '-s', 'LineWidth', 2);
plot(fun_dims, ALCPSOSpark_run_time_mean, '-s', 'LineWidth', 2);
title('Average Run Time on Schwefel12');
xlabel('Number of function evaluations')
ylabel('Average run time (hours)');
xticks(fun_dims);
legend('Matlab-PSO', 'Spark-PSO', 'Matlab-CLPSO', 'Spark-CLPSO', 'Matlab-ALCPSO', 'Spark-ALCPSO', 'Location', 'northwest');
figure(2);
PSO_speedup = PSOMatlab_run_time_mean ./ PSOSpark_run_time_mean;
CLPSO_speedup = CLPSOMatlab_run_time_mean ./ CLPSOSpark_run_time_mean;
ALCPSO_speedup = ALCPSOMatlab_run_time_mean ./ ALCPSOSpark_run_time_mean;
plot(fun_dims, PSO_speedup, '-s', 'LineWidth', 2); hold on;
plot(fun_dims, CLPSO_speedup, '-s', 'LineWidth', 2); hold on;
plot(fun_dims, ALCPSO_speedup, '-s', 'LineWidth', 2); hold on;
title('Average Speedup on Schwefel12');
xlabel('Number of function evaluations')
ylabel('Average speedup');
xticks(fun_dims);
legend('PSO', 'CLPSO', 'ALCPSO', 'Location', 'northwest');
ylim([0 300]);

%% Run Time Varying with Number of Function Evaluations on Schwefel12
PSOSpark_1000 = [...
    8.33e+01  8.16e+01  8.07e+01  8.07e+01  8.12e+01  8.08e+01  8.09e+01  8.15e+01  8.09e+01  8.13e+01 ...
    8.12e+01  8.14e+01  8.08e+01  8.12e+01  8.08e+01  8.06e+01  8.10e+01  8.09e+01  8.15e+01  8.14e+01 ...
    8.10e+01  8.18e+01  8.03e+01  8.10e+01  8.09e+01  8.21e+01  8.16e+01  8.15e+01  8.10e+01  8.13e+01] / (60 * 60);
PSOSpark_2000 = [...
    1.61e+02  1.61e+02  1.61e+02  1.62e+02  1.62e+02  1.63e+02  1.63e+02  1.60e+02  1.63e+02  1.61e+02 ...
    1.61e+02  1.62e+02  1.62e+02  1.61e+02  1.62e+02  1.61e+02  1.63e+02  1.62e+02  1.61e+02  1.61e+02 ...
    1.61e+02  1.62e+02  1.62e+02  1.61e+02  1.62e+02  1.62e+02  1.61e+02  1.61e+02  1.62e+02  1.61e+02] / (60 * 60);
PSOSpark_3000 = [...
    2.42e+02  2.42e+02  2.42e+02  8.65e+02  2.40e+02  2.41e+02  2.41e+02  2.40e+02  2.40e+02  8.69e+02 ...
    2.40e+02  2.42e+02  8.52e+02  2.42e+02  2.43e+02  2.41e+02  2.42e+02  2.43e+02  2.41e+02  2.41e+02 ...
    2.44e+02  2.43e+02  2.42e+02  2.43e+02  2.44e+02  2.43e+02  2.42e+02  2.44e+02  2.42e+02  2.47e+02] / (60 * 60);
PSOSpark_4000 = [...
    3.27e+02  3.25e+02  3.25e+02  3.26e+02  3.24e+02  3.24e+02  3.26e+02  3.24e+02  3.25e+02  3.24e+02 ...
    3.27e+02  3.24e+02  3.25e+02  3.25e+02  3.24e+02  3.22e+02  3.21e+02  3.22e+02  3.21e+02  3.21e+02 ...
    3.22e+02  3.21e+02  3.22e+02  3.25e+02  3.21e+02  3.21e+02  3.22e+02  3.20e+02  3.21e+02  3.22e+02] / (60 * 60);
PSOSpark_5000 = [...
    4.01e+02  3.99e+02  3.99e+02  4.00e+02  4.04e+02  4.00e+02  1.03e+03  3.97e+02  4.01e+02  4.03e+02 ...
    4.05e+02  4.03e+02  4.04e+02  4.02e+02  4.02e+02  4.03e+02  4.04e+02  4.04e+02  4.02e+02  3.99e+02 ...
    4.02e+02  4.02e+02  4.02e+02  5.64e+02  4.04e+02  4.01e+02  3.99e+02  4.00e+02  4.02e+02  3.99e+02] / (60 * 60);

CLPSOSpark_1000 = [...
    7.98e+01  7.93e+01  7.76e+01  7.67e+01  7.67e+01  7.69e+01  7.75e+01  7.84e+01  7.75e+01  7.77e+01 ...
    7.79e+01  7.86e+01  7.79e+01  7.79e+01  7.76e+01  7.71e+01  7.73e+01  7.76e+01  7.68e+01  7.76e+01 ...
    7.70e+01  7.71e+01  7.68e+01  7.71e+01  7.75e+01  7.84e+01  7.76e+01  7.80e+01  7.75e+01  7.74e+01] / (60 * 60);
CLPSOSpark_2000 = [...
    1.63e+02  7.88e+02  1.63e+02  1.62e+02  1.62e+02  1.63e+02  1.64e+02  1.65e+02  1.66e+02  1.64e+02 ...
    1.66e+02  1.65e+02  1.66e+02  1.66e+02  1.65e+02  1.65e+02  1.65e+02  1.65e+02  1.65e+02  1.63e+02 ...
    1.66e+02  1.64e+02  1.63e+02  1.65e+02  1.66e+02  1.63e+02  1.65e+02  1.67e+02  1.65e+02  1.66e+02] / (60 * 60);
CLPSOSpark_3000 = [...
    2.52e+02  2.51e+02  2.53e+02  2.51e+02  2.49e+02  2.47e+02  2.49e+02  2.51e+02  2.53e+02  2.51e+02 ...
    2.49e+02  2.49e+02  2.47e+02  2.48e+02  2.47e+02  2.47e+02  2.49e+02  8.81e+02  2.54e+02  2.50e+02 ...
    2.51e+02  2.51e+02  2.52e+02  8.76e+02  2.47e+02  2.47e+02  2.46e+02  2.47e+02  2.47e+02  8.67e+02] / (60 * 60);
CLPSOSpark_4000 = [...
    3.31e+02  3.34e+02  3.34e+02  3.30e+02  9.59e+02  3.34e+02  3.33e+02  3.36e+02  3.34e+02  9.65e+02 ...
    3.36e+02  3.38e+02  3.38e+02  9.55e+02  3.32e+02  3.33e+02  3.32e+02  3.32e+02  9.62e+02  3.37e+02 ...
    3.38e+02  3.36e+02  3.36e+02  9.65e+02  3.33e+02  3.34e+02  3.33e+02  3.36e+02  3.39e+02  3.39e+02] / (60 * 60);
CLPSOSpark_5000 = [...
    4.25e+02  4.24e+02  4.23e+02  4.24e+02  4.27e+02  4.24e+02  4.25e+02  4.23e+02  4.21e+02  4.18e+02 ...
    4.22e+02  4.19e+02  4.20e+02  4.18e+02  4.16e+02  1.04e+03  4.19e+02  4.20e+02  4.22e+02  8.51e+02 ...
    4.26e+02  4.40e+02  1.04e+03  4.25e+02  4.26e+02  4.26e+02  4.24e+02  4.22e+02  4.24e+02  4.27e+02] / (60 * 60);

ALCPSOSpark_1000 = [...
    8.20e+01  7.97e+01  7.84e+01  7.91e+01  7.89e+01  7.87e+01  7.88e+01  7.95e+01  7.89e+01  7.92e+01 ...
    7.89e+01  7.99e+01  7.93e+01  8.04e+01  8.01e+01  8.01e+01  7.94e+01  8.03e+01  7.92e+01  7.95e+01 ...
    7.95e+01  7.95e+01  7.93e+01  7.86e+01  7.96e+01  7.90e+01  7.90e+01  7.93e+01  7.94e+01  7.93e+01] / (60 * 60);
ALCPSOSpark_2000 = [...
    1.59e+02  1.59e+02  1.58e+02  1.59e+02  1.59e+02  7.85e+02  1.58e+02  1.57e+02  1.59e+02  1.59e+02 ...
    1.58e+02  1.58e+02  1.58e+02  1.58e+02  1.59e+02  7.82e+02  1.58e+02  1.57e+02  1.56e+02  1.60e+02 ...
    1.59e+02  1.58e+02  1.58e+02  1.59e+02  1.59e+02  7.82e+02  1.59e+02  1.59e+02  1.59e+02  1.58e+02] / (60 * 60);
ALCPSOSpark_3000 = [...
    2.42e+02  2.37e+02  2.39e+02  8.69e+02  2.39e+02  2.37e+02  2.38e+02  2.39e+02  2.36e+02  8.57e+02 ...
    2.37e+02  2.37e+02  2.38e+02  2.36e+02  2.38e+02  8.67e+02  2.37e+02  2.37e+02  2.37e+02  2.37e+02 ...
    2.37e+02  2.39e+02  8.63e+02  2.38e+02  2.36e+02  2.37e+02  2.37e+02  2.37e+02  8.68e+02  2.38e+02] / (60 * 60);
ALCPSOSpark_4000 = [...
    3.15e+02  3.14e+02  3.20e+02  4.37e+02  3.14e+02  3.17e+02  3.14e+02  3.15e+02  9.34e+02  3.15e+02 ...
    3.18e+02  3.15e+02  3.18e+02  9.42e+02  3.17e+02  3.16e+02  3.14e+02  3.16e+02  6.54e+02  3.20e+02 ...
    3.22e+02  3.22e+02  9.43e+02  3.21e+02  3.18e+02  3.18e+02  3.19e+02  9.42e+02  3.21e+02  3.19e+02] / (60 * 60);
ALCPSOSpark_5000 = [...
    3.93e+02  3.94e+02  3.91e+02  3.92e+02  3.93e+02  3.92e+02  3.92e+02  3.96e+02  3.93e+02  3.95e+02 ...
    3.93e+02  3.92e+02  3.93e+02  3.93e+02  3.91e+02  3.94e+02  3.95e+02  3.96e+02  3.93e+02  3.94e+02 ...
    3.92e+02  3.94e+02  3.96e+02  3.96e+02  3.91e+02  3.99e+02  3.90e+02  3.90e+02  3.93e+02  3.96e+02] / (60 * 60);

fun_dims = [1000 2000 3000 4000 5000];
subplot(3, 1, 1);
boxplot([PSOSpark_1000' PSOSpark_2000' PSOSpark_3000' PSOSpark_4000' PSOSpark_5000'], ...
    'labels', {'1000', '2000', '3000', '4000', '5000'});
title('Run Time Varying with Number of Function Evaluations for Spark-PSO');
subplot(3, 1, 2);
boxplot([CLPSOSpark_1000' CLPSOSpark_2000' CLPSOSpark_3000' CLPSOSpark_4000' CLPSOSpark_5000'], ...
    'labels', {'1000', '2000', '3000', '4000', '5000'});
title('Run Time Varying with Number of Function Evaluations for Spark-CLPSO');
ylabel('Average run time (hours)');
subplot(3, 1, 3);
boxplot([ALCPSOSpark_1000' ALCPSOSpark_2000' ALCPSOSpark_3000' ALCPSOSpark_4000' ALCPSOSpark_5000'], ...
    'labels', {'1000', '2000', '3000', '4000', '5000'});
title('Run Time Varying with Number of Function Evaluations for Spark-ALCPSO');
xlabel('Number of function evaluations')

%% Proportion of Function Evaluations Varying with Function Dimension on Sphere
cf_sphere_PSOMatlab    = [2.21 1.18 3.27 5.00 4.92 4.75];
cf_sphere_CLPSOMatlab  = [6.48 11.54 11.18 14.66 9.53 9.17];
cf_sphere_ALCPSOMatlab = [4.86 8.92 10.41 11.05 8.21 7.86 5.97];
semilogx(10 .^ (1 : length(cf_sphere_PSOMatlab)), cf_sphere_PSOMatlab, '-s', 'LineWidth', 2);
hold on;
semilogx(10 .^ (1 : length(cf_sphere_CLPSOMatlab)), cf_sphere_CLPSOMatlab, '-s', 'LineWidth', 2);
hold on;
semilogx(10 .^ (1 : length(cf_sphere_ALCPSOMatlab)), cf_sphere_ALCPSOMatlab, '-s', 'LineWidth', 2);
ylim([0 100]);
title('Proportions of Function Evaluations Varying with Dimensions on Sphere');
xlabel('Function dimension')
ylabel('Proportion of Function Evaluations (%)');
legend('PSO', 'CLPSO', 'ALCPSO', 'Location', 'northwest');

%% Proportion of Function Evaluations Varying with Function Dimension on Rosenbrock
cf_rosenbrock_PSOMatlab    = [14.25 6.02 15.68 22.09 30.26 30.62];
cf_rosenbrock_CLPSOMatlab  = [36.91 22.39 24.02 33.75 30.40 29.66];
cf_rosenbrock_ALCPSOMatlab = [50.06 21.87 23.98 27.03 33.41 24.28 30.01];
semilogx(10 .^ (1 : length(cf_rosenbrock_PSOMatlab)), cf_rosenbrock_PSOMatlab, '-s', 'LineWidth', 2);
hold on;
semilogx(10 .^ (1 : length(cf_rosenbrock_CLPSOMatlab)), cf_rosenbrock_CLPSOMatlab, '-s', 'LineWidth', 2);
hold on;
semilogx(10 .^ (1 : length(cf_rosenbrock_ALCPSOMatlab)), cf_rosenbrock_ALCPSOMatlab, '-s', 'LineWidth', 2);
ylim([0 100]);
title('Proportions of Function Evaluations Varying with Dimensions on Rosenbrock');
xlabel('Function dimension')
ylabel('Proportion of Function Evaluations (%)');
legend('PSO', 'CLPSO', 'ALCPSO', 'Location', 'northwest');

%% Proportion of Function Evaluations Varying with Function Dimension on Rastrigin
cf_rastrigin_PSOMatlab    = [22.42 3.85 10.18 12.83 13.66 10.74];
cf_rastrigin_CLPSOMatlab  = [23.09 19.21 30.66 20.21 15.78 13.90];
cf_rastrigin_ALCPSOMatlab = [53.21 29.28 26.37 13.66 12.82 12.23 7.49];
semilogx(10 .^ (1 : length(cf_rastrigin_PSOMatlab)), cf_rastrigin_PSOMatlab, '-s', 'LineWidth', 2);
hold on;
semilogx(10 .^ (1 : length(cf_rastrigin_CLPSOMatlab)), cf_rastrigin_CLPSOMatlab, '-s', 'LineWidth', 2);
hold on;
semilogx(10 .^ (1 : length(cf_rastrigin_ALCPSOMatlab)), cf_rastrigin_ALCPSOMatlab, '-s', 'LineWidth', 2);
ylim([0 100]);
title('Proportions of Function Evaluations Varying with Dimensions on Rastrigin');
xlabel('Function dimension')
ylabel('Proportion of Function Evaluations (%)');
legend('PSO', 'CLPSO', 'ALCPSO', 'Location', 'northwest');

%% Proportion of Function Evaluations Varying with Function Dimension on Griewank
cf_griewank_PSOMatlab     = [16.58 5.31 9.90 12.13 12.86 11.73];
cf_griewank_CLPSOMatlab   = [30.99 24.26 37.61 27.07 17.45 16.90];
cf_griewank_ALCPSOMatlab  = [44.02 33.52 33.62 19.13 19.37 13.47 16.61];
semilogx(10 .^ (1 : length(cf_griewank_PSOMatlab)), cf_griewank_PSOMatlab, '-s', 'LineWidth', 2);
hold on;
semilogx(10 .^ (1 : length(cf_griewank_CLPSOMatlab)), cf_griewank_CLPSOMatlab, '-s', 'LineWidth', 2);
hold on;
semilogx(10 .^ (1 : length(cf_griewank_ALCPSOMatlab)), cf_griewank_ALCPSOMatlab, '-s', 'LineWidth', 2);
ylim([0 100]);
title('Proportions of Function Evaluations Varying with Dimensions on Griewank');
xlabel('Function dimension')
ylabel('Proportion of Function Evaluations (%)');
legend('PSO', 'CLPSO', 'ALCPSO', 'Location', 'northwest');

%% Proportion of Function Evaluations Varying with Function Dimension on Schwefel12
cf_schwefel12_PSOMatlab    = [4.44 26.39 96.75 99.79 99.98];
cf_schwefel12_CLPSOMatlab  = [13.11 74.85 97.26 99.60 99.97];
cf_schwefel12_ALCPSOMatlab = [16.04 77.25 97.18 99.55 99.97];
semilogx(10 .^ (1 : length(cf_schwefel12_PSOMatlab)), cf_schwefel12_PSOMatlab, '-s', 'LineWidth', 2);
hold on;
semilogx(10 .^ (1 : length(cf_schwefel12_CLPSOMatlab)), cf_schwefel12_CLPSOMatlab, '-s', 'LineWidth', 2);
hold on;
semilogx(10 .^ (1 : length(cf_schwefel12_ALCPSOMatlab)), cf_schwefel12_ALCPSOMatlab, '-s', 'LineWidth', 2);
ylim([0 100]);
title('Proportions of Function Evaluations Varying with Dimensions on Schwefel12');
xlabel('Function dimension')
ylabel('Proportion of Function Evaluations (%)');
legend('PSO', 'CLPSO', 'ALCPSO', 'Location', 'northwest');

%%
parallel_level = 100;
cf_schwefel12_PSOMatlab = 0.01 * [4.44 26.39 96.75 99.79 99.98];
speedup_PSOMatlab = 1 ./ ((1 - cf_schwefel12_PSOMatlab) + (cf_schwefel12_PSOMatlab) / parallel_level);
cf_schwefel12_CLPSOMatlab = 0.01 * [13.11 74.85 97.26 99.60 99.97];
speedup_CLPSOMatlab = 1 ./ ((1 - cf_schwefel12_CLPSOMatlab) + (cf_schwefel12_CLPSOMatlab) / parallel_level);
cf_schwefel12_ALCPSOMatlab = 0.01 * [16.04 77.25 97.18 99.55 99.97];
speedup_ALCPSOMatlab = 1 ./ ((1 - cf_schwefel12_ALCPSOMatlab) + (cf_schwefel12_ALCPSOMatlab) / parallel_level);
semilogx(10 .^ (1 : length(speedup_PSOMatlab)), speedup_PSOMatlab, '-s', 'LineWidth', 2);
hold on;
semilogx(10 .^ (1 : length(speedup_CLPSOMatlab)), speedup_CLPSOMatlab, '-s', 'LineWidth', 2);
hold on;
semilogx(10 .^ (1 : length(speedup_ALCPSOMatlab)), speedup_ALCPSOMatlab, '-s', 'LineWidth', 2);
title('Theoretical Speedup Varying with Dimension on Schwefel12');
xlabel('Function dimension')
ylabel('Theoretical speedup');
legend('PSO', 'CLPSO', 'ALCPSO', 'Location', 'northwest');

%%
% f = [0.5 0.05, 0.005, 0.0005 0.00005]; % serial portion
f = [0.5 0.4 0.3 0.2 0.1];
p = 2 .^ (1 : 10); % number of processors
figure(1);
for i = 1 : length(f)
    speedup = 1 ./ (f(i)+ (1 - f(i)) ./ p);
    plot(p, speedup, '-.', 'LineWidth', 3);
    hold on;
end
title('Amdahl''s Law');
xlim([p(1) p(end)])
xtick_labels = cell(1, length(p));
for i = 1 : length(p)
    xtick_labels{1, i} = int2str(p(i));
end
xticklabels(xtick_labels);
xlabel('Number of cores');
ylabel('Speedup');
f_legend = [];
for i = 1 : length(f)
    f_legend = cat(1, f_legend, sprintf('%7.e %%', 100 * f(i))); % percentage
end
legend(f_legend, 'Location', 'northwest');

%% Run Time on Other Four Functions
% PSOMatlab + PSOSpark + CLPSOMatlab + CLPSOSpark + ALCPSOMatlab + ALCPSOSpark + PSOScala
cfSphere_run_time_mean     = [1.59e+00 1.16e+01 1.71e+00 8.53e+00 2.24e+00 9.87e+00 2.04e+01];
cfRosenbrock_run_time_mean = [2.14e+00 1.20e+01 2.26e+00 8.30e+00 3.10e+00 1.01e+01 2.21e+01];
cfRastrigin_run_time_mean  = [1.74e+00 1.20e+01 1.87e+00 8.53e+00 2.39e+00 1.03e+01 2.34e+01];
cfGriewank_run_time_mean  =  [1.72e+00 1.18e+01 1.90e+00 8.55e+00 2.59e+00 1.02e+01 2.42e+01];
figure(1);
bar(cfSphere_run_time_mean);
title('Run Time on Sphere');
xlabel('Algorithm name');
ylabel('Average run time (seconds)');
xticklabels({'Matlab-PSO', 'Spark-PSO', 'Matlab-CLPSO', 'Spark-CLPSO', 'Matlab-ALCPSO', 'Spark-ALCPSO', 'Scala-PSO'});
set(gca, 'XTickLabelRotation', 30);
figure(2);
bar(cfRosenbrock_run_time_mean);
title('Run Time on Rosenbrock');
xlabel('Algorithm name');
ylabel('Average run time (seconds)');
xticklabels({'Matlab-PSO', 'Spark-PSO', 'Matlab-CLPSO', 'Spark-CLPSO', 'Matlab-ALCPSO', 'Spark-ALCPSO', 'Scala-PSO'});
set(gca, 'XTickLabelRotation', 30);
figure(3);
bar(cfRastrigin_run_time_mean);
title('Run Time on Rastrigin');
xlabel('Algorithm name');
ylabel('Average run time (seconds)');
xticklabels({'Matlab-PSO', 'Spark-PSO', 'Matlab-CLPSO', 'Spark-CLPSO', 'Matlab-ALCPSO', 'Spark-ALCPSO', 'Scala-PSO'});
set(gca, 'XTickLabelRotation', 30);
figure(4);
bar(cfGriewank_run_time_mean);
title('Run Time on Griewank');
xlabel('Algorithm name');
ylabel('Average run time (seconds)');
xticklabels({'Matlab-PSO', 'Spark-PSO', 'Matlab-CLPSO', 'Spark-CLPSO', 'Matlab-ALCPSO', 'Spark-ALCPSO', 'Scala-PSO'});
set(gca, 'XTickLabelRotation', 30);
