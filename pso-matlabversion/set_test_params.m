function [test_params] = set_test_params(test_num, test_log, test_seed)
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
% Set Parameters for the Test.
%
% -------------------
% || INPUT  ||   <---
% -------------------
%   test_num     <--- int, the total number of tests (default: 30)
%   test_log     <--- boolean, the flag to print the log (default: true)
%   test_seed    <--- int, the random seed to initialize the population (default: now)
%
% -------------------
% || OUTPUT ||   --->
% -------------------
%   test_params  ---> struct, test parameters
% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% %
switch nargin
    case 3
    case 2
        test_seed = str2double(datestr(now, 'yyyymmdd')); % now == today
    case 1
        test_log = true;
        test_seed = str2double(datestr(now, 'yyyymmdd'));
    otherwise
        test_num = 30;
        test_log = true;
        test_seed = str2double(datestr(now, 'yyyymmdd'));
end

if ~isnumeric(test_num)
    error('Please correctly set the *test_num* parameter -> ' + ...
        'it must be numeric.');
end
if ~islogical(test_log)
    error('Please correctly set the *test_log* parameter -> ' + ...
        'it must be logical.');
end
if ~isnumeric(test_seed)
    error('Please correctly set the *test_seed* parameter -> ' + ...
        'it must be numeric.');
end

% For *reproducibility*, it's needed to set the random seed to initialize the population
% for each test. It means that when plotting the convergence curve figure, at least
% the same starting point could be obtained for different optimization algorithms
% on each test. Note that below set different random seeds to initialize the population
% for different tests.
test_seeds = test_seed + (2 * (0 : (test_num - 1)));

test_params = struct(...
    'test_num', test_num, ...
    'test_log', test_log, ...
    'test_seed', test_seed, ...
    'test_seeds', test_seeds);
end
