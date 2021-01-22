function sorted_arr = ascending_sort(matrix, col_index)
    n = size(matrix, 1); 
    
    for i = 1:n-1
        for j = i + 1: n
            if matrix(i,col_index) < matrix(j,col_index)
               temp = matrix(i,:); 
               matrix(i,:) = matrix(j,:); 
               matrix(j,:) = temp; 
            end
            
        end
    end 
    sorted_arr = matrix; 
end