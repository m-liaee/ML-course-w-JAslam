function pred_vals = get_prediction(dc_stump, data)
    f_index = dc_stump(1); 
    f_thresh = dc_stump(2); 
    
    f_vals = data(:,f_index); 
    
    less_indices = find(f_vals < f_thresh); 
    grt_indices = find(f_vals >= f_thresh); 
    
    pred_vals(less_indices) = -1; 
    pred_vals(grt_indices) = 1; 
    pred_vals = transpose(pred_vals); 
    
end