%Neural Network
le = 0.2; 
final_step = 1000; 

D = eye(8); 

w_1 = zeros(8,3);
w_2 = zeros(3,8); 

w_1 = rand(8,3); 
w_2 = rand(3,8); 


theta_1 = zeros(1,3); 
theta_2 = zeros(1,8);

theta_1 = rand(1,3);
theta_2 = rand(1,8); 

% E_1 ; 
% E_2 ; 
% I_input;
% O_input;
% I_internal;
% O_internal;
% I_output;
% O_output;

counter = 0 ; 

while counter < final_step
    
    for i =1:8
        
        x = D(i,:);

        %feed forward
        %input layer-------------------------------------------------------
        I_input = x; 
        O_input = I_input;         
        %internal layer----------------------------------------------------
        for j = 1:3
            sum = 0 ; 
            for k = 1:8 
                sum = sum + w_1(k,j) * O_input(k);
            end
            I_internal(j) = sum + theta_1(j);
            O_internal(j) = sigmf(I_internal(j),[1 0]);
        end
        %output layer------------------------------------------------------
        for j = 1:8
            sum = 0 ; 
            for k = 1:3
                sum = sum + w_2(k,j) * O_internal(k) ;
            end
            I_output(j) = sum + theta_2(j);
            O_output(j) = sigmf(I_output(j),[1 0]);
        end
        display(w_1); 
        display(w_2); 
        %back propagate
        %------------------------------------------------------------------ 
        for j = 1:8
           Err_2(j) = O_output(j) * ( 1 - O_output(j)) * ( x(j)- O_output(j)); 
        end
        
        for j = 1:3
            Err_out = 0 ; 
            for k = 1:8 
                Err_out = Err_out + w_2(j,k) * Err_2(k); 
            end
            Err_1(j) = O_internal(j) * (1- O_internal(j)) * Err_out ; 
        end 
        
        for k = 1:3 
            for j = 1:8
                delta = le * Err_2(j)* O_internal(k);
                w_2(k,j) = w_2(k,j) + delta;
            end
        end
        
        for k = 1:8
            for j = 1:3
                delta = le * Err_1(j) * O_input(k); 
                w_1(k,j) = w_1(k,j) + delta; 
            end
        end
        
        for k = 1:8
               delta = le * Err_2(k);
               theta_2(k) = theta_2(k) + delta; 
        end
        
        for k = 1:3
            delta = le * Err_1(k); 
            theta_1(k) = theta_1(k) + delta; 
        end

    end
    counter = counter + 1; 
    if (counter == 800)
%         le = le/2 ; 
%     elseif(counter == 800)    
%         le = le/2 ; 
    end
    disp(counter); 
      
 end
%{%
for i =1:8
        x = D(i,:);
        %feed forward
        %input layer
        I_input = x; 
        O_input = I_input; 
        
        %internal layer
        for j = 1:3
            sum = 0 ; 
            for k = 1:8 
                sum = sum + O_input(k) * w_1(k,j);
            end
            I_internal(j) = sum + theta_1(j);
            O_internal(j) = sigmf(I_internal(j),[1 0]);
        end
        %output layer
        for j = 1:8
            sum = 0 ; 
            for k = 1:3
                sum = sum + O_internal(k) * w_2(k,j);
            end
            I_output(j) = sum + theta_2(j);
            O_output(j) = sigmf(I_output(j),[1 0]);
        end
        
        disp(i); 
        disp(O_output); 
end
%}