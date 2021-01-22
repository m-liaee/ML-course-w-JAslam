format long
D = importdata('spambase.data');

[n, m] = size(D); 
% m = 58 
% spam db has 57 features

train_acc_rate_vec = []; 
test_acc_rate_vec = []; 

len = ceil(n / 10); 
for k = 1:10
    
    % find indices for k-fold partitions
    min_index = (k-1) * len + 1; 
    max_index = k * len; 
    
    if max_index > n
        max_index = n ; 
    end
    
    %split test and train
    test_indices = min_index : max_index; 
    test_data = D(test_indices,:); 
    
    train_data = D; 
    train_data(test_indices,:) = []; 
     
    %split spam and non-spam
    spam_indices = find(train_data(:,m)==1); 
    spams = train_data(spam_indices,:); 
  
    nonspam_indices = find(train_data(:,m)==0); 
    nonspams = train_data(nonspam_indices,:); 

    %calculating mean and sigma for spam and nonspam
    mean_spam = mean(spams); 
    mean_spam(m) = []; 
    mean_nonspam = mean(nonspams); 
    mean_nonspam(m) = []; 
    
    sigma =  zeros(m-1);

    train_size = size(train_data,1);     
 
    for i = 1 : train_size
        y_i = train_data(i,m); 
        x_i = train_data(i,(1:m-1)); 
        
        if ( y_i ==1 )
            vector = x_i - mean_spam; 
        else 
            vector = x_i - mean_nonspam; 
        end
        temp = transpose(vector) * vector; 
        
        sigma = sigma + transpose(vector) * vector; 
    end
    sigma = (1/train_size) * sigma;
    
    % predicting  train
    prob_spam = mvnpdf(train_data(:,1:m-1), mean_spam, sigma); 
    prob_nonspam = mvnpdf(train_data(:,1:m-1), mean_nonspam, sigma);
    
    predicted_val = prob_spam > prob_nonspam;
    
    y = train_data(:,m); 
    
    % accuracy of train
    t = find(y == predicted_val); 
    num_acc = size(t,1); 
    acc_rate = num_acc/train_size;
    train_acc_rate_vec = [train_acc_rate_vec; acc_rate]; 

    
    % predicting test
    prob_spam = mvnpdf(test_data(:,1:m-1), mean_spam, sigma); 
    prob_nonspam = mvnpdf(test_data(:,1:m-1), mean_nonspam, sigma); 
    
    predicted_val = prob_spam > prob_nonspam ; 
    y = test_data(:,m); 

    % accuracy of test
    t = find(y == predicted_val); 
    num_acc = size(t,1); 
    test_size = size(test_data,1); 
    acc_rate = num_acc/test_size;
    test_acc_rate_vec = [test_acc_rate_vec; acc_rate];

    
end 

temp = [train_acc_rate_vec test_acc_rate_vec]; 
display(temp);
display(mean(temp));

