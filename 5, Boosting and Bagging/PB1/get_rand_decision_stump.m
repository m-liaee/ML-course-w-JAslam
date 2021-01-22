function dc_stump = get_rand_decision_stump(data, decision_stumps, dist)
    
    n = size(decision_stumps,1); 
    index = randsample(n,1); 
    dc_stump = decision_stumps(index,:); 

end
