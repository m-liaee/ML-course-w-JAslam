function acc_rate = gaussian_prediction(data, threshold, prob_spam, prob_nonspam, mu_spam, sigma_spam, mu_nonspam, sigma_nonspam)
    [n,m] = size(data); 
    
    spam_indices = find(data(:,m)==1); 
    nonspam_indices = find(data(:,m)==0); 
    y = data(:,m);
    
    predtd_val = []; 

    for i = 1:n
        log_prob = log(prob_spam) - log(prob_nonspam); 
       
        for j = 1:m-1
            x_j = data(i,j); 
            
                prob_f_j_spam = normpdf(x_j,mu_spam(j),sigma_spam(j));
                prob_f_j_nonspam = normpdf(x_j,mu_nonspam(j), sigma_nonspam(j));
         
                if prob_f_j_spam == 0 
                    prob_f_j_spam = 0.0000000001; 
                    prob_f_j_spam = 1/m; 
%                     disp([i j]); 
%                     disp('zero prob spam'); 
                    
                end
            
                if prob_f_j_nonspam == 0 
                    prob_f_j_nonspam = 0.0000000001; 
                    prob_f_j_nonspam = 1/m; 
%                     disp([i j]);                     
%                      disp('zero prob nonspam'); 
                end
                    
                log_temp = log( prob_f_j_spam / prob_f_j_nonspam );
                
                log_prob = log_prob + log_temp; 
            
        end
        
        if log_prob > threshold
            predtd_val(i) = 1; 
        else
            predtd_val(i) = 0; 
        end

         
    end

    predtd_val = transpose(predtd_val);
    accurate_indices = find(y == predtd_val); 
    num_accurate = size(accurate_indices,1);
    acc_rate = num_accurate / n;
    
end


