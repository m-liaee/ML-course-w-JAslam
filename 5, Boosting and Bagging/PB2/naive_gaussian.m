function [train_acc test_acc] = naive_gaussian(train_data, test_data, threshold)

    lambda = 0.5; 
 
    [train_size, m] = size(train_data);
    
    %------------------------------    
    display('finding parameters'); 
    spam_indices = find(train_data(:,m) == 1); 
    nonspam_indices = find(train_data(:,m) == 0); 
    
    spam_data = train_data(spam_indices,:); 
    nonspam_data = train_data(nonspam_indices,:); 
    
    prob_spam = size(spam_indices,1)/train_size;
    prob_nonspam = size(nonspam_indices,1)/train_size;
    
    
    
%     display([prob_spam prob_nonspam]); 
 
    %for each feature two gaussian(mu , sigma) --> spam , non-spam
    for i = 1:m-1
        
        whole_sigma = std(train_data(:,i),1); 
%         display(whole_sigma); 
        
        temp = spam_data(:,i); 
        mu_spam(i) = mean(temp);
        sigma_spam(i) = std(temp,1);
              
        temp = nonspam_data(:,i); 
        mu_nonspam(i) = mean(temp); 
        sigma_nonspam(i) = std(temp,1); 
        
        if (sigma_nonspam(i) < 0.1)
            sigma_nonspam(i) = lambda * sigma_nonspam(i)+ (1-lambda) * whole_sigma; 
        end
        
        if (sigma_spam(i) < 0.1)
            sigma_spam(i) = lambda * sigma_spam(i)+ (1-lambda) * whole_sigma; 
        end

%      disp([i mu_spam(i) sigma_spam(i) mu_nonspam(i) sigma_nonspam(i)]);
              
        if sigma_spam(i)== 0 
            temp = train_data(:,i); 
            disp('yeah'); 
            sigma_spam(i) = std(temp,1) * lambda; 
        end
        
        if sigma_nonspam(i)== 0
            temp = train_data(:,i); 
            disp('yeah');             
            sigma_nonspam(i) = std(temp,1) * lambda;
        end        
        
    end
      
      %predicting train_data
      train_acc = gaussian_prediction(train_data, threshold, prob_spam, prob_nonspam, mu_spam, sigma_spam, mu_nonspam, sigma_nonspam); 

      
      %predicting test data
      test_acc = gaussian_prediction(test_data, threshold, prob_spam, prob_nonspam, mu_spam, sigma_spam, mu_nonspam, sigma_nonspam); 
    



