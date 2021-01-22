function e = get_error (dist, predicted_val , y)

    wrong_indices = find(predicted_val ~= y); 
    
    wrong_probs = dist(wrong_indices); 
    e = sum(wrong_probs); 
end