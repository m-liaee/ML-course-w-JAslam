function area = auc_cal(x,y)
n = size(x,2); 

sum = 0 ; 
for i = 1: n-1
    sum = sum + ( x(i)-x(i+1) ) * ( y(i) + y(i+1) ); 
end
area = sum /2 ; 
end 