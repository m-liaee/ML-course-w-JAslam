function em_algo(dim)

    if dim == 2 
        D = importdata('2gaussian.txt'); 
    else
        D = importdata('3gaussian.txt'); 
    end
     
    D = D(1:2000,:); 

    Max_step = 500; 
    delta = 0.000000001; 
    
    [n,m] = size(D); 
    %display([n, m]);
    
    %Initialize------------------------------------------------------------    
    mu = rand(dim,2); 
    
    if (dim == 2)
        x = rand(1);
        omega = [x,1-x]; % sum of omega should be one
    else
        x = rand(1,2)/3; 
        z = 1 - (x(1) + x(2)); 
        omega = [x(1) x(2) z];
    end

    
    sigma = rand(2,2,dim); % should positive symmetric
    sigma(:,:,1) = [0.5 0.1 ; 0.1 0.5]; 
    sigma(:,:,2) = [0.6 0.2 ; 0.2 0.6]; 
    if (dim == 3)
        sigma(:,:,3) = [0.7 0.3; 0.3 0.4]; 
    end
    
    %--------
    L_0 = 0 ; 
    for i=1:n
        temp = 0; 
        y_i = D(i,:); 
        
        for j=1:dim
            temp = temp + mvnpdf(y_i, mu(j,:), sigma(:,:,j)); 
        end
        L_0 = L_0 + log(temp); 
    end
    L_0 = L_0 /n ; 
    L = L_0; 
%    display(L_0); 
    
    %----------------------------------------------------------------------
     counter = 1; 
     stop = false; 
     while ~stop && counter < Max_step
         
        %E step

        for i=1:n
            y_i = D(i,:); 
            for j=1:dim
                prob = mvnpdf(y_i, mu(j,:), sigma(:,:,j)); 
                numerator = omega(j) * prob; 
                gamma(i,j)= numerator; 
            end
            denominator = sum(gamma(i,:)); 
            gamma(i,:) = gamma(i,:)/denominator; 
             
        end
%       display(gamma); 
 
        for j = 1:dim
            nn(j)= sum(gamma(:,j)); 
        end
%        display(nn); 
       
        % can nn elements bigger than 1?
         
        %M step------------------------------------------------------------
        omega = nn/n; 
        %display(omega);     
        
        for j = 1:dim
            summ = [0 0] ; 
            for i = 1:n
                y_i = D(i,:); 
                summ = summ + gamma(i,j) * y_i; 
            end
            mu(j,:)= summ/nn(j); 
        end
        
        for j = 1:dim
            summ = [0 0 ; 0 0]; 
            for i = 1:n
                y_i = D(i,:); 
                e = y_i - mu(j,:); 
                
                temp = transpose(e) * e; 
                summ = summ + gamma(i,j) * temp; 
            end
            sigma(:,:,j) = summ /nn(j); 

        end
        %L step------------------------------------------------------------
                
        L_next = 0; 
        for i = 1:n
            y_i = D(i,:); 
            temp = 0; 
            for j = 1:dim
                temp = temp + omega(j) * mvnpdf(y_i, mu(j,:), sigma(:,:,j)); 
            end
            L_next = L_next + log(temp); 
        end
        L_next = L_next/n; 
%        display(L_next); 
%        display(L); 
        if L_next - L < delta; 
            stop = true; 
        end
        
        L = L_next;
        display(counter); 
        counter = counter + 1; 
    end
    
    display(omega); 
    display(mu); 
    display(sigma); 
end