import java.util.ArrayList;

import javax.xml.crypto.Data;


public class main {
	
	public static void main(String[] args) {
		
		String conf_addr = "crx.config"; 
		String data_addr = "crx.data";
		
		int T = 100; 
		boolean is_random_decision = false; 
		
		Parser p = new Parser(conf_addr,data_addr);
		ArrayList<Feature> features = p.features; 
		ArrayList<DataPoint> data = p.datapoints;
		
		int folder_i = 0; // folders are from 0 to 9 
		
		ArrayList<Integer> test_indices = get_folder_i_indices(folder_i,data.size()); 
		ArrayList<DataPoint> test_data = Remove_Data_byIndex(data, test_indices);
		ArrayList<DataPoint> train_data = data;
		
		System.out.println(test_data.size());
		
		ArrayList<Decision_Stump> dc_stumps = Decision_Stump.GetDecisionStumps(train_data, features);
		System.out.println("Decision stumps are created.");
		
		System.out.println("Training is just started with " + T + " rounds.");
		AdaBoost ada = new AdaBoost(p.features, p.labels); 
		ArrayList<Model> models = ada.Train(train_data, dc_stumps, is_random_decision, T);
		
		System.out.println("Training is just finished.");
				
		// performance evaluation
		System.out.println("Performance:");
		
		ArrayList<Double> train_f_values = null;
		ArrayList<Double> test_f_values = null;
		
		double[] train_init_dist = Util.GetUniformDistribution(train_data.size());
		double[] test_init_dist = Util.GetUniformDistribution(test_data.size());
		
		ArrayList<Character> train_real_labels = DataPoint.getLabels(train_data);
		ArrayList<Character> test_real_labels = DataPoint.getLabels(test_data);
		
		
		for (int i = 0 ; i < T ; i++){
			Model m = models.get(i);
			Decision_Stump dc = m.decision_stump; 
			double alpha = m.alpha; 
					
		/**/
		// train
	         ArrayList<Character> train_predicted_labels = ada.getPrediction(dc, train_data); // each round prediction
	         double local_error = ada.getError(train_init_dist, train_predicted_labels, train_real_labels); 
	         
	         train_f_values = ada.cal_f_function(train_f_values,alpha, train_predicted_labels);
	         
	         ArrayList<Character> train_pred_labels = ada.PredictBy_f_function(train_f_values); // prediction so far
	         double train_err = ada.getError(train_init_dist, train_pred_labels, train_real_labels); 
	         
	     // test 
	         ArrayList<Character> test_predicted_labels = ada.getPrediction(dc, test_data); // each round predictoin
	         
	         test_f_values = ada.cal_f_function(test_f_values,alpha, test_predicted_labels);
	         
	         ArrayList<Character> test_pred_labels = ada.PredictBy_f_function(test_f_values);// prediction so far
	        System.out.println(m.decision_stump);
	         System.out.println(test_real_labels.get(0) + "  " +test_pred_labels.get(0));
	         double test_err = ada.getError(test_init_dist, test_pred_labels, test_real_labels);
	          
//	         /**/ 
	         
	         System.out.println("round #"+i+"  "+ local_error + "   " +train_err + "   " + test_err);
		}		
	}
	
		
	public static ArrayList<DataPoint> Remove_Data_byIndex(ArrayList<DataPoint> data, ArrayList<Integer> indices){
		
		ArrayList<DataPoint> removed_data = new ArrayList<DataPoint>(); 
		int len = indices.size(); 
		for (int i = len -1 ; i >= 0 ; i--){
			int index = indices.get(i);
			
			removed_data.add(data.remove(index));
		}
		
		return removed_data; 
		
	}
	
	public static ArrayList<Integer> get_folder_i_indices(int folder_i,int size){
		ArrayList<Integer> indices = new ArrayList<Integer>(); 
		
		int index = folder_i ; 
		while ( index < size){
			indices.add(index);			
			index = index + 10; 
		}
		
		return indices; 
	}

}
