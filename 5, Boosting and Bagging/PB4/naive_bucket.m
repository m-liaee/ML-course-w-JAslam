%function [false_pos_rate, true_pos_rate] = naive_bucket(threshold,only_onefold, num_bins)
clc;
num_bins = 2; 
threshold = 0; 
  
train_data = importdata('20_percent_missing_train.txt'); 
train_size = size(train_data,1); 
test_data = importdata('20_percent_missing_test.txt');       
D = [train_data; test_data];
bucket_thresh = give_bucket(num_bins, D); % bucket is num_bins * m-1 * 2 
  
%------------------------------------------------------------------------
%     bucket_thresh = give_bucket(num_bins, train_data);
    
    spam_indices = find(train_data(:,m)==1);
    nonspam_indices = find(train_data(:,m)==0);
    
    spam_data = train_data(spam_indices,:); 
    nonspam_data = train_data(nonspam_indices,:); 

    spam_size = size(spam_data,1); 
    nonspam_size = size(nonspam_data,1); 

    prob_spam = spam_size /train_size; 
    prob_nonspam = nonspam_size / train_size; 
   %-----------------------------------------------------------------------
   
    bucket_counter_spam = bucket_counter(spam_data,num_bins, bucket_thresh); 
    bucket_counter_nonspam = bucket_counter(nonspam_data,num_bins, bucket_thresh); 
    
%    smoothing_const = 0; 
    smoothing_const = num_bins; 
    
    bucket_prob_spam = bucket_counter_spam / (spam_size + smoothing_const); 
    bucket_prob_nonspam = bucket_counter_nonspam / (nonspam_size + smoothing_const); 

      train_acc_rate = bucket_prediction(train_data, threshold, num_bins, prob_spam, prob_nonspam, bucket_thresh, bucket_prob_spam, bucket_prob_nonspam ); 
      display(train_acc_rate);
      test_acc_rate = bucket_prediction(test_data, threshold,  num_bins, prob_spam, prob_nonspam, bucket_thresh, bucket_prob_spam, bucket_prob_nonspam ); 
      display(test_acc_rate);   
