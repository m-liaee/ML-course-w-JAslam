function weights = logistic_gd(x, y, le , final_step)
[n,m] = size(x); 
w_0 = zeros(m,1); 

weights = w_0; 

counter = 0; 
%E = Inf; 
%while E <     

while counter < final_step
    for i = 1 :n
        hw = x(i,:) * weights; 
        hw = sigmf(hw,[1 0]);
        next_w = weights - le * (hw- y(i)) * hw * (1-hw) * transpose (x(i,:)); 
        weights = next_w; 
       
   end

    counter = counter +1; 

    %{
    temp = x * weights ; 
    e = (temp - y); 
    E = sum (e .*e); 
    display(E); 
    %}


end
end