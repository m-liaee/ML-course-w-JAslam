%data % n * m 
d = importdata('spambase.data'); 

[n,m] = size(d);

k = 10 ; 
size_each_folder = ceil(n/k); 

train_error_vec = []; 
test_error_vec = []; 

for j = 1:k
    
    display('in jth section, j is : '); 
    display(j); 
    
    x = [] ; y = [] ; 
    data = d; 
        
    min_index = (j-1) * size_each_folder + 1; 
    max_index =  j * size_each_folder ; 
    
    if max_index > n 
        max_index = n; 
    end
    
    % split train and data set
    test_data = data(min_index:max_index, :); 
    display(    size(test_data));
    
     data( min_index:max_index, : ) = []; 
     train_data = data; 
     display(size(train_data));
    
     
     [n_tr,m_tr] = size(train_data);
  
     y = train_data(:,m_tr); 
     x = train_data(:,1:m_tr-1); 
     i = ones(n_tr,1); 
     x = [i ,x ]; 

      temp = inv( transpose(x) * x) * transpose(x); 
  
      w = temp * y; 
  
% % %    display('coeficients of line, w is: '),
% % %    display(w); 
 
     diff_arr = (x * w) - y ; 
 
     sum_square_error =  transpose(diff_arr) * diff_arr; 
     mean_error = sum_square_error / n; 
     RMS = sqrt(mean_error); 
 
     display('RMS of training data is :'),
     display( RMS);
     train_error_vec = [train_error_vec RMS]; 
    
% %     ----------------------------------------------------------
% %     
      [n_te,m_te] = size(test_data);
  
      x = test_data(:,1:m_te-1);
      i = ones(n_te,1); 
      x = [i ,x ]; 
      y = test_data(:,m_te); 
      
  
      diff_arr = (x * w) - y ; 
      
      sum_square_error =  transpose(diff_arr) * diff_arr; 
 
      mean_error = sum_square_error / n_te; 
      RMS = sqrt(mean_error); 
 
     display('RMS of test data is :');
     display( RMS);
     
     test_error_vec = [test_error_vec RMS]; 
     

end

display('average for train RMS error'); 
display(mean(train_error_vec)); 

display('average for test RMS error'); 
display(mean(test_error_vec)); 
