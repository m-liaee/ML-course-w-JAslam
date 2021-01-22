function new_arr = remove_nan_values( arr)
    
    n = size(arr,1); 
    
    nan_indices = isnan(arr); 
    
    arr(nan_indices) = []; 
    new_arr = arr; 
end