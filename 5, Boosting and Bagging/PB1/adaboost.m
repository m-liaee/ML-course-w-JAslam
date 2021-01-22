function model = adaboost( train_data, decision_stumps, is_random_decision, T)

[n,m] = size(train_data); 

initial_dist = ones(1,n)/n; 
dist = initial_dist; 

model = []; 
for t = 1:T
        
    if mod(t,50) == 0 
        str = sprintf('adaboost is training: round #%d',t);    
        disp(str); 
    end
     
    if is_random_decision 
        d_stump = get_rand_decision_stump(train_data, decision_stumps, dist); 
    else 
        d_stump = get_opt_decision_stump(train_data, decision_stumps, dist); 
    end
   
     predicted_val = get_prediction(d_stump, train_data);    
     train_y = train_data(:,m);
     
     local_error = get_error(dist,predicted_val,train_y); 
     
     alpha = 1/2 * (log(1-local_error) - log(local_error)); 
     
     dist = updata_dist(dist,alpha, predicted_val, train_y);  
     
     model = [model; [d_stump alpha]]; 

end 

