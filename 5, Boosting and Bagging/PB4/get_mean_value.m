function mean = get_mean_value(vertical_arr)
    
    n = size(vertical_arr,1); 
%     display(n); 
    sum = 0 ; 
    counter = 0 ; 
    
    for i = 1:n
        temp = vertical_arr(i); 
        if ~ isnan(temp)
%             display(temp); 
            
            counter = counter + 1; 
            sum = sum + temp; 
        end
    end
    
    mean = sum / counter; 
end