clc; 
data = importdata('spambase.data');
[n,m] = size(data); 
nonspam_indices = (data(:,m) == 0) ; 
data(nonspam_indices,m) = -1; 

k = 10 ; 

% choosing decision stumps is random or by optimum
is_random_decision = false; 
T = 10; 

for i = 1%:k

     str = sprintf('folder #%d :',i);    
     disp(str); 
     
     % spliting train and test data of each fold --------------------------
     test_indices = i:10:n; 
     test_data = data(test_indices,:); 
     
     train_data = data; 
     train_data(test_indices,:) = [];         
     
     % training time ------------------------------------------------------
     dc_stumps = get_decision_stumps(train_data); 
     display('decision stumps are just created.'); 
         
     % model is T * 3 vector , first column is feature num, second is
     % feature threshhold , third is alpha

     model = adaboost( train_data, dc_stumps, is_random_decision, T); 
     
%      for t = 1:T
%          display(model(t,:)); 
%      end
     
     % evaluation time ----------------------------------------------------
     train_f_func = 0; 
     test_f_func = 0;
     
     train_size = size(train_data,1); 
     test_size = size(test_data,1); 

     train_initial_dist = ones(1,train_size)/train_size; 
     test_initial_dist = ones(1,test_size)/test_size; 
     
     train_dist = train_initial_dist; 
     
     train_y = train_data(:,m); 
     test_y = test_data(:,m); 
 
     fpr_vec = []; 
     tpr_vec = []; 
          
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
         
         [fpr_vec tpr_vec] = get_fpr_tpr_vals(test_f_func,test_y); 
         fpr_vec = transpose(fpr_vec); 
         tpr_vec = transpose(tpr_vec); 

         test_auc= auc_cal(fpr_vec,tpr_vec);

         format_spec = 'round #%d  local_error:%.4f  train_error:%.4f  test_error:%.4f  test_auc:%.4f \n'; 
         fprintf(format_spec, t, local_error, train_error, test_error, test_auc);

      end    
     plot(fpr_vec, tpr_vec,'-g'); 
end
    