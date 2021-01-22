function weights = linear_regression(x,y)
    temp = inv( transpose(x) * x) * transpose(x); 
    weights = temp * y; 
    %weights = weights/ -weights(1); 
end