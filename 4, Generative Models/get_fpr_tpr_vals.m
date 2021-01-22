function [fpr_vec , tpr_vec ] = get_fpr_tpr_vals(calc_val, y)

    temp = transpose(calc_val); 
    temp = unique(sort(temp)); 
    temp = [ temp temp(end)+0.1];
    thresh_vec = temp; 
%     display(thresh_vec); 
%     pause; 
    
    fpr_vec = []; 
    tpr_vec = []; 
    
    for tr = thresh_vec

        less_indices = find( calc_val < tr); 
        grt_indices = find( calc_val >= tr); 
        
        pred_vals = []; 
        pred_vals(less_indices) = -1; 
        pred_vals(grt_indices) = 1; 
        pred_vals = transpose(pred_vals); 
        
%        display([calc_val pred_vals]); 
        

        nonspam_indices = find(y == -1); 
        nonspam_size = size(nonspam_indices,1); 
        
        temp = pred_vals(nonspam_indices); 
        false_pos = find(temp == 1); 
        fpr_size = size(false_pos,1); 
        
        fpr_rate = fpr_size/nonspam_size; 
        
        spam_indices = find(y == 1); 
        spam_size = size(spam_indices,1); 
        
        temp = pred_vals(spam_indices); 
        true_pos = find(temp == 1); 
        tpr_size = size(true_pos,1); 
        
        tpr_rate = tpr_size/spam_size; 
        
%         display(fpr_rate); 
%         display(tpr_rate); 
        
        fpr_vec = [fpr_vec; fpr_rate]; 
        tpr_vec = [tpr_vec; tpr_rate]; 
%         pause; 
    end
end 



