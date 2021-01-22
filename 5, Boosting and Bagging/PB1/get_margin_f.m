function margin_f = get_margin_f( model, f_id , x, y)

    n = size(y,1); 
    margin_f = 0 ; 
    
    for i = 1:n 
        margin_f = margin_f + get_margin_f_single_data(model, f_id, x(i,:), y(i)); 
    end
     
    
end