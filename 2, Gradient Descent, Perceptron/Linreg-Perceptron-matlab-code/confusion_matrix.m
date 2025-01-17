function confusion_matrix(real_d, linreg_pre, lingd_pre, logsc_pre, thresh) 

true_val = find (real_d == 1); 
neg_val = find(real_d == 0); 

disp('confusion matrix format');
disp('|TN FP|');
disp('|FN TP|');

%--------------------------------------------------------------------------
% linear regression 

t = linreg_pre(true_val); 
TP = size(find(t==1),1); 
FN = size(find(t==0),1); 

t = linreg_pre(neg_val); 
FP = size(find(t==1),1); 
TN = size(find(t==0),1); 

conf_linreg = [TN FP; FN TP];
display(conf_linreg); 

%--------------------------------------------------------------------------
% linear regression with gredient descent

pos_indices = (lingd_pre > thresh); 
neg_indices = (lingd_pre <= thresh); 

lingd_pre(pos_indices) = 1; 
lingd_pre(neg_indices) = 0; 

t = lingd_pre(true_val); 
TP = size(find(t==1),1); 
FN = size(find(t==0),1); 

t = lingd_pre(neg_val); 
FP = size(find(t==1),1); 
TN = size(find(t==0),1); 

conf_lingd =  [TN FP; FN TP]; 
display(conf_lingd);

%--------------------------------------------------------------------------
% logistic regression

pos_indices = (logsc_pre > thresh); 
neg_indices = (logsc_pre <= thresh);

logsc_pre(pos_indices) = 1; 
logsc_pre(neg_indices) = 0; 

t = logsc_pre(true_val); 
TP = size(find(t==1),1); 
FN = size(find(t==0),1); 

t = logsc_pre(neg_val); 
FP = size(find(t==1),1); 
TN = size(find(t==0),1);

conf_lgscreg = [TN FP; FN TP]; 
display(conf_lgscreg); 
%}
end 