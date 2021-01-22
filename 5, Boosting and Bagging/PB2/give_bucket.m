function bucket = give_bucket(num_bins, data)

    epsilon = 0.00001; 
    [n,m] = size(data); 
    max_val = max(data(:,1:m-1)) + epsilon;
    min_val = min(data(:,1:m-1)); 
    overall_mean = mean(data(:,1:m-1));     
    
    bucket = zeros(num_bins, m-1 ,2); 
    if num_bins ==4       
        
        spam_indices = find(data(:,m)==1); 
        nonspam_indices = find(data(:,m)==0);

        spam_mean_val = mean(data(spam_indices,1:m-1)); 
        nonspam_mean_val = mean(data(nonspam_indices,1:m-1)); 

        low_mean_val = min(spam_mean_val,nonspam_mean_val ); 
        high_mean_val = max(spam_mean_val, nonspam_mean_val); 
             
        bucket(:,:,1) = [min_val; low_mean_val; overall_mean; high_mean_val]; 
        bucket(:,:,2) = [low_mean_val; overall_mean; high_mean_val; max_val]; 
        
    elseif num_bins == 9 
        
        interval_len  = max_val - min_val; 
        t = interval_len /9; 
        
        bucket(:,:,1) = [min_val; min_val + t; min_val + 2*t; min_val + 3*t; min_val + 4*t; min_val + 5*t; min_val + 6*t; min_val + 7*t;  min_val + 8*t ];
        bucket(:,:,2) = [min_val + t; min_val + 2*t; min_val + 3*t; min_val + 4*t; min_val + 5*t; min_val + 6*t; min_val + 7*t;  min_val + 8*t ; max_val; ];
        
    elseif num_bins == 2 
        
        bucket(:,:,1) = [min_val; overall_mean];
        bucket(:,:,2) = [overall_mean; max_val];
    else
        display('not implemented yet :D'); 
    end
end