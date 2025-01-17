
%data % n * m 
train_data = importdata('housing_train.txt'); 

[n,m] = size(train_data);


y = train_data(:,m); 
x = train_data(:,1:m-1); 
i = ones(n,1); 
x = [i ,x ]; 

temp = inv( transpose(x) * x) * transpose(x); 

w = temp * y; 

display('coeficients of line, w is: '),
display(w); 

diff_arr = (x * w) - y ; 

sum_square_error =  transpose(diff_arr) * diff_arr; 
mean_error = sum_square_error / n; 
RMS = sqrt(mean_error); 

display('RMS of training data is :'),
display( RMS);

%---------------------------------------

test_data = importdata('housing_test.txt'); 
[n,m] = size(test_data);


x = test_data(:,1:m-1);
i = ones(n,1); 
x = [i ,x ]; 
y = test_data(:,m); 

diff_arr = (x * w) - y ; 

sum_square_error =  transpose(diff_arr) * diff_arr; 
mean_error = sum_square_error / n; 
RMS = sqrt(mean_error); 

display('RMS of test data is :'),
display( RMS);

