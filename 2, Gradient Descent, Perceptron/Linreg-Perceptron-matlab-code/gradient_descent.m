function final_w = gradient_descent(data , w_0, lambda)

    [n, m] = size(data); 
    
    y = data(:,m); 

    x = data(:,(1:m-1));     
    x = [ones(n,1) x ]; 
    
    display(y); 
    display(x)
    
    %gradient_stochastic 
    w = w_0; 
    
    while x > 0
        for i = 1:n
            new_w ; 
            for j = 1:m 
                
                new_w(j) = w(j) - lambda * ( w * transpose(x(t))- y(i) ) * x(i,j); 
            end 
            w = new_w; 
            
        end
    end
    
    final_w = w; 
end 