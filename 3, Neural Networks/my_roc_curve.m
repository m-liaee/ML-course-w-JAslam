% my_roc_curve

%threshold = [-1000:100:-150 -100:20:-20 -15:2:15 20:20:100 150:100:1000]; 
threshold = [-100 -20:2:-2 -1:0.1:1 2:2:20 100]; 

% fpr_vec = []; 
% tpr_vec = []; 
% 
% for t = threshold
%     [f, tr] = naive_gaussian(t,true);
%     fpr_vec = [fpr_vec f]; 
%     tpr_vec = [tpr_vec tr]; 
% end
% 
% display(fpr_vec); 
% display(tpr_vec); 
% 
% plot(fpr_vec, tpr_vec,'-r'),hold; 
% area= auc_cal(fpr_vec,tpr_vec);
% display(area); 
%---------------------------------------------
% fpr_vec = []; 
% tpr_vec = []; 
% 
% for t = threshold
%     [f, tr] = naive_bernouli(t,true);
%     fpr_vec = [fpr_vec f]; 
%     tpr_vec = [tpr_vec tr]; 
% end
% 
% display(fpr_vec); 
% display(tpr_vec); 
% 
% plot(fpr_vec, tpr_vec,'-b'); 
% area= auc_cal(fpr_vec,tpr_vec);
% display(area); 
%---------------------------------------------
fpr_vec = []; 
tpr_vec = []; 

for t = threshold
    display(t); 
    [f, tr] = naive_bucket(t,true,4);% last is the number of bins 
    fpr_vec = [fpr_vec f]; 
    tpr_vec = [tpr_vec tr]; 
end

% display(fpr_vec); 
% display(tpr_vec); 

plot(fpr_vec, tpr_vec,'-g'); 
area= auc_cal(fpr_vec,tpr_vec);
display(area); 