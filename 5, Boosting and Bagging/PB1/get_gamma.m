function gamma = get_gamma(model, feature_id)
    n = size(model,1); 
      
    gamma = 0 ; 
    sum = 0 ; 
    for i = 1:n
        temp_f_id = model(i,1); 
        temp_f_alpha = model(i,3); 
        
        if temp_f_id == feature_id
            gamma = gamma + abs(temp_f_alpha); 
        end
        sum = sum + abs(temp_f_alpha); 
    end 
    
    gamma = gamma/ sum; 
end