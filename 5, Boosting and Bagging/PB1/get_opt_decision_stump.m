function dc_stump = get_opt_decision_stump(data, decision_stumps, dist)

    len = size( decision_stumps,1); 
    
    m = size(data,2); 
    y = data(:,m);
    
    max_val = 0 ; 
    max_index = 0; 
    
    for i = 1:len 
        
        dc_temp = decision_stumps(i,:); 
        predicted_val = get_prediction(dc_temp,data); 
         
        
        error = get_error(dist,predicted_val,y); 
        
        val = abs(0.5 - error); 
        if val > max_val
            max_val = val; 
            max_index = i; 
            
        end
        
    end 
    
    dc_stump = decision_stumps(max_index,:); 
end