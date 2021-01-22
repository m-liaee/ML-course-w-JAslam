%PCA
clc; 
format long
lambda = 0.5;     
threshold = 0; 

train_data = importdata('spam_polluted\train_feature.txt');
train_y = importdata('spam_polluted\train_label.txt'); 

n = size(train_data,1); 

test_data = importdata('spam_polluted\test_feature.txt'); 
test_y = importdata('spam_polluted\test_label.txt'); 
display('reading data is finished.'); 

[w, pc] = princomp(train_data); 

new_train_data = pc(:,1:100); 
new_train_data = [new_train_data train_y]; 

new_test_data = test_data * w;
new_test_data = new_test_data(:,1:100); 
new_test_data = [new_test_data test_y]; 

display('trainning ... '); 
 
[train_acc test_acc] = naive_gaussian(new_train_data, new_test_data, threshold ); 
display([train_acc test_acc]); 
    
    