clc; 
data = importdata('spambase.data');
[n,m] = size(data); 
nonspam_indices = (data(:,m) == 0); 
data(nonspam_indices,m) = -1; 

num_folder = 10 ; 

% choosing decision stumps is random or by optimum
is_random_decision = true; 
T = 300; 

features_score = zeros(m-1,2); 
features_score(:,1) = (1:m-1); 

for i = 1:num_folder

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
  
    % feature analysis ----------------------------------------------------
    margin = 0 ;
    f_partial_score = zeros(m-1,1); 
    for k = 1:m-1
         f_id = k;  
         gamma_f = get_gamma(model, f_id); 
         margin_f = get_margin_f(model, f_id, train_data(:,1:m-1), train_data(:,m)); 
         f_partial_score(k) = gamma_f * margin_f;               
         margin = margin + f_partial_score(k); 

    end     
     
    f_partial_score = f_partial_score / margin; 
    features_score(:,2) = features_score(:,2) + f_partial_score; 
  
end
    
sorted_vals = ascending_sort(features_score,2); 
disp(sorted_vals(1:20,:)); 