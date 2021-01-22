function counter = bucket_counter(data,num_bins, bucket_thresh)
    [n,m] = size(data); 
    
    %counter = zeros(num_bins,m-1);
    counter = ones(num_bins,m-1);
    for i = 1:n 
        for j = 1:m-1
            x_ij = data(i,j); 
            for q = 1:num_bins
                low_thresh = bucket_thresh(q,j,1); 
                high_thresh = bucket_thresh(q,j,2);
                
                if x_ij < high_thresh && x_ij >= low_thresh
                    counter(q,j) = counter(q,j) + 1; 
                end
            end
        end 
    end 
end