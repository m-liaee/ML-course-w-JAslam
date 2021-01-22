
     ini_percent = 5;  
     sum_percent = ini_percent; 
     round_percent = c; 
     final_percent = 50; 

     initial_train_size = ceil(ini_percent /100 * train_size); 
     round_size = ceil( round_percent /100 * train_size); 

     % initial step
        
      model = []; 
       
      rand_indices = randsample(train_size,initial_train_size); 
      current_train = train_data(rand_indices,:); 
      remain_train = train_data; 
      remain_train(rand_indices,:) = []; 
 
      dc_stumps = get_decision_stumps(current_train); 
      model = active_learning(model, current_train, dc_stumps, T); 

      % next episode  
      
%      while sum_percent < final_percent
%          
%          if ~is_random
%              new_train = get_closest_points(model, remain_train,round_size); 
%          else
%              new_train = get_random_points(remain_train, round_size); 
%          end
%          current_train = merge(new_train, current_train); 
%          remain_train = remove(remain_train,new_train); 
%          
%          dc_stumps = get_decision_stumps(current_train); 
%          model = active_learning(model,current_data,dc_stumps,T);
%          
%          sum_percent = sum_percent + round_percent; 
%      end