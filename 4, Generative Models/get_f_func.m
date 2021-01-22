function f_func = get_f_func(model, data)
    T = size(model,1); 
    f_func = 0; 
    for t = 1:T
        partial_model = model(t,:); 
        dc_stump = partial_model([1 2]); 
        alpha = partial_model(3); 
                
        predicted_val = get_prediction(dc_stump, data); 
        f_func = f_func + alpha * predicted_val;
        
    end
end