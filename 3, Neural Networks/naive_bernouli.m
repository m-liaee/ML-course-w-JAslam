function [false_pos_rate, true_pos_rate] = naive_bernouli(threshold, only_onefold)

    format long
    lambda = 0.5; 
    %threshold = 10 ; 
    D = importdata('spambase.data'); 
    [n,m] = size(D); 

    disp('threshold is: '); 
    disp(threshold); 
    
    if ~ only_onefold
        disp(['[k] '  ' [false positive rate] ' ' [false negative rate] ' ' [overall error rate] ' ' [accuracy rate] ']); 
        display('-'); 
    end
    
    if only_onefold 
        num_fold = 1; 
    else 
        num_fold = 10; 
    end

    for k = 1 : num_fold
%         disp(k); 
        
        %partition data
        test_indices = k:10: n; 
        test_data = D(test_indices,:);  
        
        train_data = D; 
        train_data(test_indices,:) = []; 
        train_size = size(train_data,1); 

        %finding parameters
        spam_indices = find(train_data(:,m) == 1); 
        nonspam_indices = find(train_data(:,m) == 0); 
    
        spam_data = train_data(spam_indices,:); 
        nonspam_data = train_data(nonspam_indices,:); 
    
        prob_spam = size(spam_indices,1)/train_size;
        prob_nonspam = size(nonspam_indices,1)/train_size;
    
        % for each feature
        
        for j = 1:m-1
            mu(j) = mean(train_data(:,j)); 
            
            
            temp1 = find(spam_data(:,j) <= mu(j));             
            %pr(f_j <= mu_j | spam)
            prob_leq_mu_spam(j) = size(temp1,1)/size(spam_data,1);            
            %pr(f_j > mu_j | spam)
            prob_grt_mu_spam(j) = 1-prob_leq_mu_spam(j); 
            

            temp2 = find(nonspam_data(:,j) <= mu(j));             
            %pr(f_j <= mu_j | nonspam)            
            prob_leq_mu_nonspam(j) = size(temp2,2)/ size(nonspam_data,1);            
            %pr(f_j > mu_j |nonspam
            prob_grt_mu_nonspam(j) = 1-prob_leq_mu_nonspam(j); 
            
            %display([ prob_leq_mu_spam(j) prob_grt_mu_spam(j) prob_leq_mu_nonspam(j) prob_grt_mu_nonspam(j) ]);

            %---double check-----------------------------------------------
            %disp(j);
            if prob_leq_mu_spam(j) == 0 
%                 display([k j 1]);
                prob_leq_mu_spam(j) = 1/57; % (size(spam_data,1)+1);
                prob_grt_mu_spam(j) = 1 - prob_leq_mu_spam(j); 
%                 display(prob_leq_mu_spam(j)); 
%                 display(prob_grt_mu_spam(j)); 
            end
            
            if prob_grt_mu_spam(j) == 0 
%                 display(size(spam_data)); 
%                 display(mu(j)); 
                %display(mean(spam_data(:,j)));
%                 display([k j 2]);                
               prob_grt_mu_spam(j) = 1/57; %(size(spam_data,1)+1); 
               prob_leq_mu_spam(j) = 1 - prob_grt_mu_spam(j); 
               
               
%                 display(prob_leq_mu_spam(j)); 
%                 display(prob_grt_mu_spam(j)); 
            end
            
            if prob_leq_mu_nonspam(j) == 0 
%                 display([k j 3]);
               prob_leq_mu_nonspam(j) = 1/57; %(size(nonspam_data,1)+1); 
               prob_grt_mu_nonspam(j)= 1 - prob_leq_mu_nonspam(j);   
%                display(prob_leq_mu_nonspam(j)); 
%                display(prob_grt_mu_nonspam(j)); 
            end
            
           if prob_grt_mu_nonspam(j) == 0 
%                 display([k j 4]);               
              prob_grt_mu_nonspam(j) = 1/57; %(size(nonspam_data,1)+1);               
               prob_leq_mu_nonspam(j) = 1 - prob_grt_mu_nonspam(j);
               
%                display(prob_leq_mu_nonspam(j)); 
%                display(prob_grt_mu_nonspam(j));                
           end
           %--------------------------------------------------------------- 

        end
%        [false_pos_rate, true_pos_rate] = bernouli_prediction(train_data, threshold,k, prob_spam, prob_nonspam, mu, prob_leq_mu_spam, prob_leq_mu_nonspam, only_onefold);        
       [false_pos_rate, true_pos_rate] = bernouli_prediction(test_data, threshold,k, prob_spam, prob_nonspam, mu, prob_leq_mu_spam, prob_leq_mu_nonspam, only_onefold);        
   
    end
end