function model = active_learning(whole_data, test_data, init_pct, round_pct, final_pct , T , is_random_pick)
    
    model = []; 
    % settings ------------------------------------------------------------
    whole_size = size(whole_data,1);
    init_train_size = ceil(init_pct /100 * whole_size); 
    round_train_size = ceil(round_pct /100 * whole_size); 
    final_train_size = ceil(final_pct /100 * whole_size); 
    
    format_spec = '%d , %d , %d, %d '; 
    str =  sprintf(format_spec, whole_size, init_train_size, round_train_size, final_train_size);
    %disp(str); 
    
    % first train episode
    new_train_indices = pick_at_random(whole_data, init_train_size); 
    remained_data = remove_data(whole_data, new_train_indices); 
    new_train = whole_data(new_train_indices,:); 
    current_train = new_train; 
     
    model = active_learning_episode(current_train, T); 

     initial_dist = ones(1,init_train_size)/init_train_size; 
 
     current_pct = init_pct;      
     % other train episodes------------------------------------------------ 
     while current_pct < final_pct
       % display(current_pct); 
        
        % train of each episode-------------------------------------------- 
        if is_random_pick
            new_train_indices = pick_at_random(remained_data,round_train_size); 
        else
            new_train_indices = pick_closest(model, remained_data, round_train_size); 
        end
        current_train = [current_train; remained_data(new_train_indices,:) ]; 
        remainded_data = remove_data(remained_data,new_train_indices); 
        
        model = active_learning_episode(current_train,T);
        current_pct = current_pct + round_pct; 
        
        % evaluation of each episode---------------------------------------
        m = size(whole_data,2); 
        
        %episode_error
        f_func = get_f_func(model, current_train);
        current_train_size = size(current_train,1);
        dist = ones(1,current_train_size)/current_train_size;
        current_y = current_train(:,m); 
        episode_error = get_error(dist, sign(f_func),current_y); 

        
        %train_error
        f_func = get_f_func(model, whole_data);
        dist = ones(1,whole_size)/whole_size;
        whole_y = whole_data(:,m); 
        train_error = get_error(dist, sign(f_func),whole_y); 

        
        %test_error
        f_func = get_f_func(model, test_data);
        test_size = size(test_data,1); 
        dist = ones(1,test_size)/test_size;
        test_y = test_data(:,m); 
        test_error = get_error(dist, sign(f_func),test_y); 

        
        
        format_spec = ' current train percent:%d, episode error:%.4f , train error:%.4f , test error:%.4f, %.4f '; 
        str =  sprintf(format_spec, current_pct, episode_error, train_error, test_error);
        disp(str); 
        
    end
    
end