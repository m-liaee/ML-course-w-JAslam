function weights = linear_gd(x, y , le , final_step)

[n,m] = size(x); 
w_0 = zeros(m,1); 

weights = w_0; 

counter = 0; 
%E = Inf; 
%while E <     

while counter < final_step
    for i = 1:n 
        hw = x(i,:) * weights; 
        next_w = weights - le * (hw- y(i)) * transpose (x(i,:)); 
        weights = next_w; 
        
        %{
        for j = 1:m
            next_w(j) = weights(j) + le *( hw - y(i)) * x(i,j) ; 
        end
        %}
    end

    counter = counter +1; 
    if counter == 1000
        le = le /2; 
    elseif counter == 2000
        le = le /2; 
    elseif counter == 4000
        le = le /2;         
    end
    %{
    temp = x * weights ; 
    e = (temp - y); 
    E = sum (e .*e); 
    display(E); 
    %}
    
end


end