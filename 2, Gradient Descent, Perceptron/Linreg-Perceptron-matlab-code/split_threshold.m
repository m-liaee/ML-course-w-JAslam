function thrs_vector = split_threshold(pre); 
    thrs_vector = zeros(1,10); 
    for i = pre
        index = ceil(i * 10) ; 
        if (index > 10 ) 
            index = 10 ; 
        elseif index<1 
            index = 1;
        end
        thrs_vector(index) = thrs_vector(index)+1; 
    end
end