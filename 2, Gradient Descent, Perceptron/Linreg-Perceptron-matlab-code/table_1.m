%housing 

%initial setting
le = 0.05; %learning rate or lambda
final_step = 5000; 

data = importdata('housing_train.txt'); 
[n,m] = size(data); 

%--------------------------------------------------------------------------
% we need to normalize data 
% it is really important!!!
% shift and scale

% splitting and normalizing data
x = data(:,1:m-1);
minn = min(x);
for i=1:n
    x(i,:) = x(i,:) - minn; 
end

maxx = max(x);
for i=1:n
    x(i,:) = x(i,:) ./ maxx; 
end
x = [ones(n,1) x]; 
y = data(:,m); 

%--------------------------------------------------------------------------
% w is a wight vector of size m
w_1 = linear_regression(x,y); 
w_2 = linear_gd(x,y,le,final_step); 

%logistic gredient descent is meaningless for a regression problem

%{%
display('Train'); 

% linear regression
pre = x * w_1; % predicted value 
e = y - pre; 
s = sum(e .* e); 
mse = s/n; % mean square error
format_desc = 'Train MSE for linear regression: %f'; 
t = sprintf(format_desc, mse); 
disp(t); 

% linear regression with gradient descent 
pre = x * w_2; 
e = y - pre; 
s = sum(e .* e); 
mse = s/n; 
format_desc = 'Train MSE for linear regression with gradient descent: %f'; 
t = sprintf(format_desc, mse); 
disp(t); 

%-------------------------------------------------------------------------- 
data = importdata('housing_test.txt'); 
[n,m] = size(data); 

%split and normalization
y = data(:,m); 
x = data(:,1:m-1); 
for i=1:n
    x(i,:) = x(i,:) - minn; 
end 

for i=1:n
    x(i,:) = x(i,:) ./ maxx; 
end
x = [ones(n,1) x]; 

%--------------------------------------------------------------------------
display('Test');

% linear regression
pre = x * w_1; 
e = y - pre; 
s = sum(e .* e); 
mse = s/n; 
format_desc = 'Test MSE for linear regression: %f'; 
t = sprintf(format_desc, mse); 
disp(t); 

% linear regression with gradient descent 
pre = x * w_2; 
e = y - pre; 
s = sum(e .* e); 
mse = s/n; 
format_desc = 'Test MSE for linear regression with gradient descent: %f'; 
t = sprintf(format_desc, mse); 
disp(t); 

%}