clc; 
train_data = importdata('spam_polluted\train_feature.txt');
train_y = importdata('spam_polluted\train_label.txt'); 
nonspam_indices = (train_y(:,1) == 0) ; 
train_y(nonspam_indices,1) = -1; 

train_data = [train_data train_y]; 

test_data = importdata('spam_polluted\test_feature.txt'); 
test_y = importdata('spam_polluted\test_label.txt'); 
nonspam_indices = (test_y(:,1) == 0) ; 
test_y(nonspam_indices,1) = -1; 

test_data = [test_data test_y]; 

display('read data'); 

T = 300; 
is_random_decision = true; 

display('creating decision stumps ... ');
dc_stumps = get_decision_stumps(train_data);
save('dc_stumps_polluted_data', 'dc_stumps'); 
load('dc_stumps_polluted_data'); 

display('training ...'); 
model = adaboost( train_data, dc_stumps, is_random_decision, T); 

     % evaluation time ----------------------------------------------------
     train_f_func = 0; 
     test_f_func = 0;
     
     train_size = size(train_data,1); 
     test_size = size(test_data,1); 

     train_initial_dist = ones(1,train_size)/train_size; 
     test_initial_dist = ones(1,test_size)/test_size; 
     
     train_dist = train_initial_dist; 
     
%      fpr_vec = []; 
%      tpr_vec = []; 
          
     for t = 1:T
         partial_model = model(t,:); 
         dc_stump = partial_model([1 2]); 
         alpha = partial_model(3); 
         
         %train performance -----------------------------------------------         
         train_predicted_val = get_prediction(dc_stump, train_data); 
         train_f_func = train_f_func + alpha * train_predicted_val; 
         local_error = get_error(train_dist,train_predicted_val,train_y); 
         train_error = get_error(train_initial_dist, sign(train_f_func),train_y);  
         train_dist = updata_dist(train_dist,alpha, train_predicted_val, train_y);  
          
         %test performance ------------------------------------------------ 
         test_predicted_val = get_prediction(dc_stump, test_data); 
         test_f_func = test_f_func + alpha * test_predicted_val; 
         test_error = get_error(test_initial_dist, sign(test_f_func),test_y); 
         
%          [fpr_vec tpr_vec] = get_fpr_tpr_vals(test_f_func,test_y); 
%          fpr_vec = transpose(fpr_vec); 
%          tpr_vec = transpose(tpr_vec); 
% 
%          test_auc= auc_cal(fpr_vec,tpr_vec);

         format_spec = 'round #%d  local_error:%.4f  train_error:%.4f  test_error:%.4f  test_auc:%.4f \n'; 
         fprintf(format_spec, t, local_error, train_error, test_error, test_auc);

      end    
%      plot(fpr_vec, tpr_vec,'-g');