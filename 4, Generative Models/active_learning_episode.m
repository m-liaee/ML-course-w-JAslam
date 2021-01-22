function model = active_learning_episode(train_data, T)
    dc_stumps = get_decision_stumps(train_data); 
    model = adaboost( train_data, dc_stumps, false, T);
end