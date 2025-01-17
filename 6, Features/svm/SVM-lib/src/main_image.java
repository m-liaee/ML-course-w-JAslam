import java.util.ArrayList;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;


public class main_image {
	public static void main(String[] args) throws Exception {
		
		int num_rectangle = 100; 
		ArrayList<Rectanglee> recs = Rectanglee.getRandomRectangles(num_rectangle);
		System.out.println(num_rectangle + " Rectangles are sampled.");
		
		System.out.println("Haar features are created.");

		int num_features = 2 * num_rectangle; 
		int num_labels = 10; 
		
		String image_f = "train-images.idx3-ubyte"; 
		String label_f = "train-labels.idx1-ubyte";
				
		Parser p = new Parser(label_f, image_f);
		p.extract_features_labels(recs);
		
		int percent = 10; 
		ArrayList<Integer> indices = Util.getIndices_somePercent(p.labels_vals, percent);
		ArrayList<Integer> train_labels = Util.getSubArray(indices, p.labels_vals); 
		ArrayList<Feature_Values> train_f_vals = Util.getElements(indices, p.data_feat_values);

		System.out.println("train data is extracted.");
					
		double [][] train_data = Util.getMatrix(train_f_vals, num_features);
		double [] train_y = Util.getArray(train_labels);
		
		double [] min = new double[num_features]; 
		double [] max = new double[num_features]; 
		double [][] normal_train_data = Util.getNormalizedData(train_data, false,  min , max);
		System.out.println("train data is normalized.");
				
		svm_model model = train_func(normal_train_data, train_y);
		double train_acc_rate = accuracy_func(model, normal_train_data, train_y);
		System.out.println(train_acc_rate);
		
		String image_f_test = "test-images.idx3-ubyte"; 
		String label_f_test = "test-labels.idx1-ubyte";
		
		Parser p_test = new Parser(label_f_test, image_f_test);
		p_test.extract_features_labels(recs);
		
		double [][] test_data = Util.getMatrix(p_test.data_feat_values, num_features); 
		double[] test_y = Util.getArray(p.labels_vals);
		
		
		double [][] normal_test_data = Util.getNormalizedData(test_data, true, min, max);
		double test_acc_rate = accuracy_func(model, normal_test_data, test_y);
		System.out.println(test_acc_rate);
		System.out.println(train_acc_rate);
		
	}
	
	public static double accuracy_func(svm_model model, double[][] data, double [] data_y){
		double num_acc = 0 ; 
		int n = data.length; 
		
		
		for (int i = 0 ; i < n ; i++){
			int pred_label = evaluate_func(model, data[i], data_y[i]);
			if (pred_label == data_y[i])
				num_acc ++ ; 
		}
		
		return (num_acc/n);
	}
	
	public static svm_model train_func(double[][] data, double [] data_y){
		
		svm_problem prob = new svm_problem();
		int dataCount = data.length;
		
		prob.y = new double[dataCount];
	    prob.l = dataCount;
	    prob.x = new svm_node[dataCount][];     

	    for (int i = 0; i < dataCount; i++){   
	    	
	        double[] features = data[i];
	        prob.x[i] = new svm_node[features.length];
	        for (int j = 0; j < features.length ; j++){
	            svm_node node = new svm_node();
	            node.index = j;
	            node.value = features[j];
	            prob.x[i][j] = node;
	        }           
	        prob.y[i] = data_y[i];
	    } 
	    
	    svm_parameter param = new svm_parameter();
	    
	    param.svm_type = svm_parameter.C_SVC;
	    param.kernel_type = svm_parameter.LINEAR;
	    //param.probability = 1;
        //param.nu = 0.5;
	    param.cache_size = 20000;
	    param.gamma = 0.1;
	    param.C = 25;
	    param.eps = 0.001;
	    
	    //param.degree = 2;
	    //param.coef0
	    
	    svm_model model = svm.svm_train(prob, param);
	    
	    return model; 
	}
	
	public static int evaluate_func(svm_model model , double[] features, double data_y ) {
		  svm_node[] nodes = new svm_node[features.length];
		  for (int i = 0; i < features.length; i++){
		        svm_node node = new svm_node();
		        node.index = i;
		        node.value = features[i];

		        nodes[i] = node;
		    }

	    int totalClasses = 10;       
	    int[] labels = new int[totalClasses];
	    svm.svm_get_labels(model,labels);

	    double[] prob_estimates = new double[totalClasses];
	    double v = svm.svm_predict_probability(model, nodes, prob_estimates);

	    for (int i = 0; i < totalClasses; i++){
	        System.out.print("(" + labels[i] + ":" + prob_estimates[i] + ")");
	    }
	    System.out.println("(Actual:" + data_y + " Prediction:" + v + ")");            

	    return (int)v;
	}
	
	
}
