function acc_rate = bucket_prediction(data, threshold, num_bins, prob_spam, prob_nonspam, bucket_thresh, bucket_prob_spam, bucket_prob_nonspam)
    [n,m] = size(data); 
    y = data(:,m); 
    
    for i = 1:n
         log_prob = log(prob_spam) - log(prob_nonspam); 

         numerator = 1;          
         denominator = 1; 
         
         for j = 1:m-1
             x_ij = data(i,j); 
             if ( ~ isnan(x_ij) )
             
                 which_bin = 0; 
                 for q = 1:num_bins
                     low_thresh = bucket_thresh(q,j,1);
                     high_thresh = bucket_thresh(q,j,2); 

                     if x_ij < high_thresh && x_ij >= low_thresh
                         which_bin = q; 
                         break; 
                     end
                 end

                 if which_bin ==0              
                     display('theres is an error'); 
                     display([i j]);                  
                     display(x_ij); 
                     display(low_thresh); 
                     display(high_thresh); 

                     pause; 
                 end

                 numerator = numerator * bucket_prob_spam(q,j); 
                 denominator = denominator * bucket_prob_nonspam(q,j);
             end
             
         end
         % i did not smoothing 
         if numerator == 0 
             
             display(i);
             pause; 
             numerator = 1; 
             denominator = 57; 
         end
         
         if denominator == 0 
             display(i); 
             numerator = 57; 
             denominator = 1; 
             pause; 
         end
         
         log_prob = log_prob + log(numerator) - log(denominator);
         
         if log_prob > threshold
             predtd_val(i) = 1; 
         else
             predtd_val(i) = 0; 
         end       
         
%         display([log_prob y(i) predtd_val(i)]);
         
    end
  
    predtd_val = transpose(predtd_val);
    accurate_indices = find(y == predtd_val); 
    num_accurate = size(accurate_indices,1); 
    acc_rate = num_accurate / n;   
    
end