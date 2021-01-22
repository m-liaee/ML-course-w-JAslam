function [false_pos_rate true_pos_rate] = bucket_prediction(data, threshold, k, num_bins, prob_spam, prob_nonspam, bucket_thresh, bucket_prob_spam, bucket_prob_nonspam, only_onefold )
    [n,m] = size(data); 
    y = data(:,m); 
    
    for i = 1:n
         log_prob = log(prob_spam) - log(prob_nonspam); 

         numerator = 1;          
         denominator = 1; 
         
         for j = 1:m-1
             x_ij = data(i,j); 
             
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
    accuracy_rate = num_accurate / n; 
%    display(accuracy_rate); 
    
    spam_indices = find(y==1); 
    nonspam_indices = find(y==0); 
    
    temp1 = predtd_val(nonspam_indices); 
    false_pos = find(temp1 == 1); 
    false_pos_rate = size(false_pos,1)/size(nonspam_indices,1); 
%    display(false_pos_rate); 
    
    temp2 = predtd_val(spam_indices); 
    false_neg = find(temp2 == 0); 
    false_neg_rate = size(false_neg,1)/size(spam_indices,1); 
%    display(false_neg_rate); 
    
    overall_err_rate = ( size(false_pos,1) + size(false_neg,1) )/n;
 %   display(overall_err_rate); 
    
    if only_onefold
%        disp([false_pos_rate true_pos_rate]);    
    else
        disp([k false_pos_rate false_neg_rate overall_err_rate accuracy_rate]); 
    end
    temp3 = predtd_val(spam_indices); 
    true_pos = find(temp3 == 1); 
    true_pos_rate = size(true_pos,1)/size(spam_indices,1) ; 
end