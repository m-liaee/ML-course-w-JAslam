function margin_f_single_data = get_margin_f_single_data ( model, f_id , x , y) 
    % x & y are for a single data
    
    margin_f_single_data = 0; 
	sum = 0; 
    
    k = size(model,1); 
    
    
    for i = 1:k
        temp_f_id = model(i,1); 
        temp_thresh = model(i,2); 
        temp_alpha = model(i,3); 
        
        
        if f_id  == temp_f_id 

            if x(1,f_id) > temp_thresh
                h_f = 1; 
            else 
                h_f = -1; 
            end

            margin_f_single_data = margin_f_single_data + temp_alpha * h_f; 
        
        end         

        sum = sum + abs(temp_alpha); 
    end 
    
    margin_f_x_single_data = y * margin_f_single_data; 
    
    
    margin_f_single_data = margin_f_x_single_data /sum; 
    
end