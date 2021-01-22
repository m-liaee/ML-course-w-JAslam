
le = 0.1 ; %learning rate
stop_cr = 0.1; 

y = zeros(8); 
y = y + eye(8); 

x = y; 
x = [ones(8,1) x]; 

%initial_weights

w_1 = zeros(3,9); % why is 9 and not 8, becuase w_0
w_2 = zeros(8,4); % why is 4 and not 3, becuase w_0
 
% p_1 = zeros(3,9); 
% p_2 = zeros(8,4); 
% s_1 = zeros(3,1); 
% s_2 = zeros(8,1); 
% o_1 = zeros(3,1); 
% o_2 = zeros(8,1); 

%{%
counter = 1; 
while counter < 5000

        
        %fix an input point with index k
        for k = 1:8
 
            %feed_forward
            % input(k,:) is an array of size 1*9

            p_1 = w_1 .* [x(k,:) ; x(k,:) ; x(k,:)]; 
            s_1 = w_1 * transpose(x(k,:)); 
            o_1 = sigmf(s_1,[1 0]); 
            internal_input = [1; o_1]; %internal input is now 4 * 1 

            t = transpose(internal_input);  % t is a 1*4 array
            p_2 = w_2 .* [t; t; t; t; t; t; t; t;];
            s_2 = w_2 * internal_input; 
            o_2 = sigmf(s_2,[1 0]); 

            e = o_2 - transpose(y(k,:)); 
            E = sum(e .* e); % element-wise production 
            % E is Sum Squared Error

                          
                
            %back_propagation
 
             t_y = transpose(y(k,:)); % t_y is 8 * 1 
 
             delta_o_2 = 2 * ( o_2 - t_y); 
             delta_s_2 = delta_o_2 .* o_2 .* ( ones(8,1)- o_2); 
             delta_p_2 = [ delta_s_2 delta_s_2 delta_s_2 delta_s_2 ]; 
             
             %t_o_1 = transpose(o_1); 
             t_o_1 = transpose(internal_input); 
             delta_w_2 = delta_p_2 .* [t_o_1; t_o_1; t_o_1; t_o_1; t_o_1; t_o_1; t_o_1; t_o_1]; % should be double check

             %delta_o_1 = transpose(sum(delta_p_2));  %which line is true?
             delta_o_1 = transpose(sum(delta_p_2 .* w_2));  

             delta_o_1 = delta_o_1(2:4,:); % becuase the first element is related to w0 dummy one
              
             delta_s_1 = delta_o_1 .* o_1 .* (ones(3,1) - o_1); 
             delta_p_1 = [ delta_s_1 delta_s_1 delta_s_1 delta_s_1 delta_s_1 delta_s_1 delta_s_1 delta_s_1 delta_s_1]; 
             inpt = x(k,:); 
             delta_w_1 = delta_p_1 .* [inpt; inpt; inpt]; % 3 * 9 
          
             
             % update w_1 and w_2
% 
             w_1 = w_1 - delta_w_1 * le; 
             w_2 = w_2 - delta_w_2 * le; 
             
             counter = counter + 1; 

             if counter == 4000
                le = 0.01;

             elseif counter == 2000
                le = 0.02;

             elseif counter == 1000
                le = 0.05; 

             end

        end
         
          
          if E <= stop_cr
              break; 
          end
end

display(o_2); 
display(counter); 