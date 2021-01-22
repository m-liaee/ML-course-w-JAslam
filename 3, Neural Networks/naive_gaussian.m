function [false_pos_rate, true_pos_rate] = naive_gaussian(threshold, only_onefold)

    format long
    lambda = 0.5; 
    
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

for k = 1:num_fold
%    disp(k); 
    %partition data
    test_indices = k:10:n; 
    test_data = D(test_indices,:); 
    
    train_data = D; 
    train_data(test_indices,:)=[]; 
    train_size = size(train_data,1);
    
    %------------------------------
    
    %finding parameters
    spam_indices = find(train_data(:,m) == 1); 
    nonspam_indices = find(train_data(:,m) == 0); 
    
    spam_data = train_data(spam_indices,:); 
    nonspam_data = train_data(nonspam_indices,:); 
    
    prob_spam = size(spam_indices,1)/train_size;
    prob_nonspam = size(nonspam_indices,1)/train_size;
    
%    display([prob_spam prob_nonspam]); 

    %for each feature two gaussian(mu , sigma) --> spam , non-spam
    for i = 1 : m-1
        mu_spam(i) = mean(spam_data(:,i));
        sigma_spam(i) = std(spam_data(:,i),1);
        
        if sigma_spam(i)== 0 
%            disp([k i]);
            sigma_spam(i) = std(train_data(:,i),1) * lambda; 
%            disp(sigma_spam(i));
        end
              
        mu_nonspam(i) = mean(nonspam_data(:,i)); 
        sigma_nonspam(i) = std(nonspam_data(:,i),1); 
        
        if sigma_nonspam(i)== 0
%            disp([k i]);
            sigma_nonspam(i) = std(train(:,i),1);
        end        
        
    end
    
%     %predicting train_data
%     disp('train'); 
%     gaussian_prediction(train_data, threshold, k, prob_spam, prob_nonspam, mu_spam, sigma_spam, mu_nonspam, sigma_nonspam,only_onefold); 
%     
    %predicting test data
%    disp('test'); 
    [false_pos_rate, true_pos_rate] = gaussian_prediction(test_data, threshold, k, prob_spam, prob_nonspam, mu_spam, sigma_spam, mu_nonspam, sigma_nonspam,only_onefold); 

    
    

end

