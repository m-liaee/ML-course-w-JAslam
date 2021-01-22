function [false_pos_rate true_pos_rate] = gaussian_prediction(data, threshold, k, prob_spam, prob_nonspam, mu_spam, sigma_spam, mu_nonspam, sigma_nonspam, only_onefold)
    [n,m] = size(data); 
    spam_indices = find(data(:,m)==1); 
    nonspam_indices = find(data(:,m)==0); 

    predtd_val = []; 
    y = data(:,m);
    for i = 1:n
        log_prob = log(prob_spam) - log(prob_nonspam); 
        for j = 1:m-1
            x_j = data(i,j); 
            prob_f_j_spam = normpdf(x_j,mu_spam(j),sigma_spam(j));
            prob_f_j_nonspam = normpdf(x_j,mu_nonspam(j), sigma_nonspam(j));

            % are my correction accepted?
            if prob_f_j_spam == 0 
                prob_f_j_spam = 0.0000000000001; 
            end
            
            if prob_f_j_nonspam == 0 
                prob_f_j_nonspam = 0.0000000000001; 
            end
            
            log_temp = log( prob_f_j_spam) - log(prob_f_j_nonspam );             
            
             if log_temp == -Inf || log_temp == Inf
                display(prob_f_j_spam); 
                display(prob_f_j_nonspam);
                display([i j]);
                display(x_j);
                display([mu_spam(j) sigma_spam(j)]);
                display([mu_nonspam(j) sigma_nonspam(j)]);
                  display('might be error'); 
                  pause; 
             end


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