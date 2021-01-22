clc; 
data = importdata('spambase.data');
[n,m] = size(data); 
nonspam_indices = (data(:,m) == 0) ; 
data(nonspam_indices,m) = -1; 


c_vec = [2 5 10 15 20 30 50]; 
k = 10 ; 
init_pct = 5; 
final_pct = 50; 
is_random_train = false; 
T = 10; 

for i = 1%:k

     str = sprintf('folder #%d :',i);    
     disp(str); 
     
     % spliting train and test data of each fold --------------------------     
     test_indices = i:10:n; 
     test_data = data(test_indices,:); 
     
     train_data = data; 
     train_data(test_indices,:) = [];         
     train_size = size(train_data,1); 
     
     % train time ---------------------------------------------------------

     for c = c_vec(1)
%          is_random_pick = true; 
%          model_random = active_learning(train_data, test_data, init_pct, c, final_pct, T, is_random_pick ); % 
%          disp(model_random); 

         is_random_pick = false; 
          model_active = active_learning(train_data, test_data, init_pct, c, final_pct, T, is_random_pick ); 
          disp(model_active); 

          
     end
    
    
end

