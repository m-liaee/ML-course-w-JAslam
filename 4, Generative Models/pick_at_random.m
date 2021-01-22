function indices = pick_at_random(data, sample_num)
    n = size(data,1); 
    
    indices = randsample(n,sample_num); 
end