load('weights'); 

threshold = 0.0:0.02:1.02; 
%threshold = [-Inf threshold Inf];

spam_indices = find(y_train==1);
nonspam_indices = find(y_train==0);


%linear regression with gredient descent
temp = x_train * w_2; 
temp(find(temp<0),1) = 0 ; 
temp(find(temp>1)) = 1; 
tpr_vec = []; 
fpr_vec = [];

for t = threshold
    %train
    zero_indices = find(temp < t);    
    train_pre_1 = ones(n_train,1);
    train_pre_1(zero_indices,1) = 0; 
       
    sub = train_pre_1(spam_indices,1); 
    TP = size(find(sub==1),1);
    tpr = TP / size(spam_indices,1);
    tpr_vec = [tpr_vec tpr];
    
     sub = train_pre_1(nonspam_indices,1);
     FP = size(find(sub==1),1);
     fpr = FP / size(nonspam_indices,1);
     fpr_vec = [fpr_vec fpr];
        
end
plot(fpr_vec, tpr_vec,'-r'),hold, 
auc_1 = auc_cal(fpr_vec , tpr_vec); 
display(auc_1);


%logistic regression with gredient descent
temp = x_train *w_3; 
temp = sigmf(temp,[1 0]);
tpr_vec = []; 
fpr_vec = [];

for t = threshold
    %train
    zero_indices = find(temp < t);    

    train_pre_2 = ones(n_train,1);
    train_pre_2(zero_indices,1) = 0; 
       
    sub = train_pre_2(spam_indices,1); 
    TP = size(find(sub==1),1);
    tpr = TP / size(spam_indices,1);
    tpr_vec = [tpr_vec tpr];

     sub = train_pre_2(nonspam_indices,1);
     FP = size(find(sub==1),1);
     fpr = FP / size(nonspam_indices,1);
     fpr_vec = [fpr_vec fpr];
    
end

plot(fpr_vec, tpr_vec,'-b'); 
auc_2 = auc_cal(fpr_vec , tpr_vec); 
display(auc_2);