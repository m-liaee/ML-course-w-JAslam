%spambase

%setting
le = 0.1; 
final_step = 1000; 

data = importdata('spambase.data'); 
[n,m] = size(data);

% randomly choose train and test data--------------------------------------
random_indices = randperm(n); 

test_size = ( ceil(n /10)); 
test_indices = random_indices(1:test_size);
train_indices = random_indices(test_size+1:n);

train_data = data(train_indices,:); 
test_data = data(test_indices,:); 

n_train = size(train_data,1); 
n_test = size(test_data,1); 

%split data----------------------------------------------------------------
y_train = train_data(:,m);
y_test = test_data(:,m); 

x_train = train_data(:,1:m-1); 
x_test = test_data(:,1:m-1); 

%normalize data------------------------------------------------------------ 
minn = min(x_train); 
for i=1:n_train
    x_train(i,:) = x_train(i,:)-minn; 
end
maxx = max(x_train); 
for i=1:n_train 
    x_train(i,:) = x_train(i,:) ./ maxx; 
end
x_train = [ones(n_train,1) x_train]; 


for i = 1:n_test
    x_test(i,:) = x_test(i,:)-minn; 
end
for i = 1:n_test
    x_test(i,:) = x_test(i,:) ./ maxx; 
end
x_test = [ones(n_test,1) x_test]; 

save('weights','x_train');
save('weights','y_train');
save('weights','x_test');
save('weights','y_test');
save('weights','m');
save('weights','n_test');
save('weights','n_train');


%train---------------------------------------------------------------------
display('Train'); 
w_1 = linear_regression(x_train,y_train); 
w_2 = linear_gd(x_train,y_train,le,final_step); 
w_3 = logistic_gd(x_train,y_train,le,final_step);

save('weights', 'w_1'); 
save('weights', 'w_2'); 
save('weights', 'w_3'); 

display('Finished calculating weights')
%}