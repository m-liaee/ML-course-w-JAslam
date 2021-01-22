function indices = pick_closest(model, remained_data, round_train_size)
    T = size(model,1); 
    
    f_func = 0; 
    for t = 1:T
        partial_model = model(t,:); 
        dc_stump = partial_model([1 2]); 
        alpha = partial_model(3); 
        
        predicted_val = get_prediction(dc_stump, remained_data); 
        f_func = f_func + alpha * predicted_val; 
        
    end

    abs_f_value = abs(f_func); 
    temp = sort(abs_f_value); 
    val = temp(round_train_size); 
    
    indices = find(abs_f_value < val); 
    
    if size(indices) > round_train_size
        indices(1:round_train_size); 
    end
end