import libsvm.*; 

public class main_spambase {

	public static void main(String[] args) {
				
		double [][] whole_data = FileReading.ReadFile("spambase.data");
		
		int n = whole_data.length; 
		
		int test_size = n /10 ; 
		int train_size = n - test_size; 
		
		double [][] train_data = new double [train_size][];
		double [][] test_data = new double [test_size][];
				
		Util.split_test_train(whole_data, train_data, test_data);
		
		int m = train_data[0].length; 
		double [] minn = new double [m]; 
		double [] maxx = new double [m];
		
		double [][] normal_train_data = Util.getNormalizedData(train_data,false, minn, maxx); 
		
		svm_model model = train_func(normal_train_data);
		System.out.println("\n\ntraining is just finished.");
		
		double train_acc_rate = accuracy_func(model, normal_train_data); 
		System.out.println("train accuracy rate " + train_acc_rate);
		
		double [][] normal_test_data = Util.getNormalizedData(test_data, true, minn, maxx);
		double test_acc_rate = accuracy_func(model, normal_test_data); 
		System.out.println("test accuracy rate " + test_acc_rate);
		System.out.println("train accuracy rate " + train_acc_rate);
		/**/
		
	}
		 
	public static double accuracy_func(svm_model model, double[][] data){
		double num_acc = 0 ; 
		int n = data.length; 
		int m = data[0].length; 
		
		for (int i = 0 ; i < n ; i++){
			int pred_label = evaluate_func(model, data[i]);
			if (pred_label == data[i][m-1])
				num_acc ++ ; 
		}
		
		return (num_acc/n);
	}
	
	public static int evaluate_func(svm_model model , double[] features) {
		  svm_node[] nodes = new svm_node[features.length-1];
		  for (int i = 0; i < features.length-1; i++){
		        svm_node node = new svm_node();
		        node.index = i;
		        node.value = features[i];

		        nodes[i] = node;
		    }

	    int totalClasses = 2;       
	    int[] labels = new int[totalClasses];
	    svm.svm_get_labels(model,labels);

	    double[] prob_estimates = new double[totalClasses];
	    double v = svm.svm_predict_probability(model, nodes, prob_estimates);

	    for (int i = 0; i < totalClasses; i++){
	        System.out.print("(" + labels[i] + ":" + prob_estimates[i] + ")");
	    }
	    System.out.println("(Actual:" + features[features.length-1] + " Prediction:" + v + ")");            

	    return (int)v;
	}
	public static svm_model train_func(double[][] data){
		
		svm_problem prob = new svm_problem();
		int dataCount = data.length;
		
		prob.y = new double[dataCount];
	    prob.l = dataCount;
	    prob.x = new svm_node[dataCount][];     

	    for (int i = 0; i < dataCount; i++){   
	    	
	        double[] features = data[i];
	        prob.x[i] = new svm_node[features.length-1];
	        for (int j = 0; j < features.length -1 ; j++){
	            svm_node node = new svm_node();
	            node.index = j;
	            node.value = features[j];
	            prob.x[i][j] = node;
	        }           
	        prob.y[i] = features[features.length-1];
	    } 
	    
	    svm_parameter param = new svm_parameter();
	    
	    param.svm_type = svm_parameter.C_SVC;
	    param.kernel_type = svm_parameter.RBF;
	    //param.probability = 1;
        //param.nu = 0.5;
	    param.cache_size = 20000;
	    param.gamma = 0.01;
	    param.C = 5;
	    param.eps = 0.001;  
	    //param.degree = 3;
	    //param.coef0
	    
	    svm_model model = svm.svm_train(prob, param);
	    
	    return model; 
	}
}

