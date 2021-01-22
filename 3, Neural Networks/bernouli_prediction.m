function [false_pos_rate true_pos_rate] = bernouli_prediction(data, threshold, k, prob_spam, prob_nonspam, mu, prob_leq_mu_spam, prob_leq_mu_nonspam, only_onefold)
    
    [n,m] = size(data); 
    spam_indices = find(data(:,m)==1); 
    nonspam_indices = find(data(:,m)==0);  
    
%     display(size(spam_indices)); 
%     display(size(nonspam_indices)); 
%     pause; 
    
    predtd_val = []; 
    y = data(:,m);
    
%     display(prob_spam); 
%     display(prob_nonspam); 
%     display(log(prob_spam) - log(prob_nonspam)); 
%     pause; 
    for i = 1:n
        log_prob = log(prob_spam) - log(prob_nonspam); 
        
        numerator = 1; 
        denominator = 1; 
        for j = 1:m-1
            x_j = data(i,j); 
            
%             if x_j <= mu(j)
%                 log_temp = log(prob_leq_mu_spam(j)) - log(prob_leq_mu_nonspam(j)); 
%             else
%                 log_temp = log(1 - prob_leq_mu_spam(j)) - log( 1 - prob_leq_mu_nonspam(j)); 
%             end
%             if log_temp == Inf || log_temp == -Inf
%                 display('error');
%                 display([x_j mu(j)  log(prob_leq_mu_spam(j)) log(prob_leq_mu_nonspam(j))]); 
%                 display([i j]); 
%                 pause; 
%             end

%             log_prob = log_prob + log_temp;
            if x_j <= mu(j)
                temp3 =  prob_leq_mu_spam(j); 
                temp4 = prob_leq_mu_nonspam(j); 
            else
                temp3 = 1- prob_leq_mu_spam(j); 
                temp4 = 1- prob_leq_mu_nonspam(j); 
            end
             
            numerator = numerator * temp3; 
            denominator = denominator * temp4; 
                       
        end
        
        if numerator == 0.00000000001 
            disp([i 1]); 
            numerator = 1;             
        end
        if denominator == 0.00000000001 
            disp([i 2]); 
            denominator = 57; 
        end
        
        log_prob = log_prob + log(numerator) - log(denominator); 
%         display(i); 
%         display( log_prob); 
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
    
    temp1 = predtd_val(nonspam_indices); 
    false_pos_vec = find(temp1 == 1);
    
    temp2 = predtd_val(spam_indices); 
    false_neg_vec = find(temp2 == 0);
    
    temp3 = predtd_val(spam_indices); 
    true_pos_vec = find(temp3 == 1); 
        
    true_pos_rate = size(true_pos_vec,1)/size(spam_indices,1);        
    false_pos_rate = size(false_pos_vec,1)/size(nonspam_indices,1);
    false_neg_rate = size(false_neg_vec,1)/size(spam_indices,1);
    overall_err_rate = ( size(false_pos_vec,1) + size(false_neg_vec,1) )/n;
    
    if only_onefold
%        disp([false_pos_rate true_pos_rate]);    
    else
        disp([k false_pos_rate false_neg_rate overall_err_rate acc_rate]); 
    end
    
    

end