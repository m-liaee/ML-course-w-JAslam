%format long; 
d = importdata('perceptronData.txt','\t'); 
[n,m] = size(d); 
d = [ones(n,1) d]; 

% % make data positive
pos_d = d; 
neg_indices = find(d(:,m+1) == -1); 
pos_d(neg_indices,:) = -1 * d(neg_indices,:); 

%extract features column and label column
y = pos_d(:,m +1); 
x = pos_d(:,1:m);
%I add ones column befor. 

w_0 = zeros(m,1); % vertical vector

w = w_0; 

neg_indices = []; 
first_round = true; 
iter_counter = 0 ; 

while first_round || size(neg_indices,1) >0
    first_round = false ; 

    mistk_counter = 0 ; 
    for i = 1:n
        if x(i,:) * w <= 0
%            display(i); 
            w = w + transpose(x(i,:)); 
            mistk_counter = mistk_counter + 1; 
        end        
    end
    
    t = x * w; 
   
    neg_indices = find ( t <= 0 ); 
    iter_counter = iter_counter + 1; 
    formatSpec = 'Iteration %d, total mistakes %d'; 
    str = sprintf(formatSpec,iter_counter, mistk_counter);
    disp(str); 
        
end 
display('');
display('row weight vector'); 
display(transpose(w));

display('normalized weight vector'); 
w = w /-w(1); 
display(transpose(w));


% checking correctness
% for i = 1:n 
%     t = d(i,1:m) * w ; 
% 
%     if t <= 0 
%         display(i); 
%             
%     end
% end