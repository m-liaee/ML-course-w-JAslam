function stump_arr = get_decision_stumps(data)

    eps = 0.1; 
    [n,m] = size(data); 

    stump_arr = []; 
    for j = 1 : m-1
        feature_j_values = data(:,j); 
        feature_j_values = unique(feature_j_values);
        feature_j_values = sort(feature_j_values); 
        
%         display(j); 
%         display(size(feature_j_values));
        
        len = size(feature_j_values,1); 

        min_val = min(feature_j_values) - eps;        
        next_entry = [j min_val]; 
        stump_arr = [stump_arr; next_entry];        
        
        for k = 1:len-1
            temp = feature_j_values(k) + feature_j_values(k+1);  
            temp = temp /2; 
            next_entry = [j temp]; 
            stump_arr = [stump_arr; next_entry]; 
            
        end     
       
       max_val = max(feature_j_values) + eps; 
       next_entry = [j max_val]; 
       stump_arr = [stump_arr; next_entry];        
       
    end
end
