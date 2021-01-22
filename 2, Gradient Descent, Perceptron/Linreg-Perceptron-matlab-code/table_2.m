load('weights'); 

threshold = 0.1:0.1:0.9; 


%linear regression
disp('---------------------------------');
train_acc_lr = [];
test_acc_lr = [];
for thresh = threshold
   
 %train
 temp = x_train * w_1; 
 zero_indices = find(temp < thresh); 

 train_pre_1 = ones(n_train,1);
 train_pre_1(zero_indices)=0; 
 acc_indices = find(train_pre_1 - y_train ==0); 
 acc_rate = size(acc_indices,1)/n_train; 

 train_acc_lr = [train_acc_lr acc_rate];
 
 %test
 temp = x_test * w_1; 
 zero_indices = find(temp < thresh); 

 test_pre_1 = ones(n_test,1);
 test_pre_1(zero_indices)=0; 
 acc_indices = find(test_pre_1 - y_test ==0); 
 acc_rate = size(acc_indices,1)/n_test; 

 test_acc_lr = [test_acc_lr acc_rate];
end
display('Linear regression result:');
disp('threshold vector');
disp(threshold);
disp('train and test accuracy rate for each threshold 0.1 - 0.9');
disp([ train_acc_lr; test_acc_lr]);

%linear regression using gredient descent
disp('---------------------------------');
train_acc_lrgd = [];
test_acc_lrgd = [];

for thresh = threshold
   
 %train
 temp = x_train * w_2; 
 zero_indices = find(temp < thresh); 

 train_pre_2 = ones(n_train,1);
 train_pre_2(zero_indices)=0; 
 acc_indices = find(train_pre_2 - y_train ==0); 
 acc_rate = size(acc_indices,1)/n_train; 

 train_acc_lrgd = [train_acc_lrgd acc_rate];
 
 %test
 temp = x_test * w_2; 
 zero_indices = find(temp < thresh); 

 test_pre_2 = ones(n_test,1);
 test_pre_2(zero_indices)=0; 
 acc_indices = find(test_pre_2 - y_test ==0); 
 acc_rate = size(acc_indices,1)/n_test; 

 test_acc_lrgd = [test_acc_lrgd acc_rate];
end
disp('Linear regression with gredient descent result:');
disp('threshold vector'); 
disp(threshold);
disp('train and test accuracy rate for each threshold 0.1 - 0.9');
disp([train_acc_lrgd; test_acc_lrgd]);

%logistic regression using gredient descent
disp('---------------------------------');
train_acc_logd = [];
test_acc_logd = [];

for thresh = threshold
   
 %train
 temp = x_train * w_3; 
 temp = sigmf(temp , [1 0]);
 zero_indices = find(temp < thresh); 

 train_pre_3 = ones(n_train,1);
 train_pre_3(zero_indices)=0; 
 acc_indices = find(train_pre_3 - y_train ==0); 
 acc_rate = size(acc_indices,1)/n_train; 

 train_acc_logd = [train_acc_logd acc_rate];
 
 %test
 temp = x_test * w_3; 
 temp = sigmf(temp , [1 0]);
 zero_indices = find(temp < thresh); 

 test_pre_3 = ones(n_test,1);
 test_pre_3(zero_indices)=0; 
 acc_indices = find(test_pre_3 - y_test ==0); 
 acc_rate = size(acc_indices,1)/n_test; 

 test_acc_logd = [test_acc_logd acc_rate];
end
disp('Logistic regression with gredient descent result:');
disp('threshold vector'); 
disp(threshold);
disp('train and test accuracy rate for each threshold 0.1 - 0.9');
disp([train_acc_logd; test_acc_logd]);

disp('-----------------------------------------------');
best_thresh = 0.4; 
disp('Train');
confusion_matrix(y_train,train_pre_1,train_pre_2,train_pre_3,best_thresh);
disp('Test');
confusion_matrix(y_test,test_pre_1,test_pre_2,test_pre_3,best_thresh);
