function new_dist = updata_dist(dist,alpha, predicted_val, y)


    n = size(y,1); 
    for i = 1:n 
        exponent = -1 * alpha * y(i) * predicted_val(i) ; 
        new_dist(i) = dist(i) * exp(exponent); 
    end

    normalized_fact = sum(new_dist); 
    new_dist = new_dist / normalized_fact; 

end