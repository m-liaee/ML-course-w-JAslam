import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.crypto.Data;


public class AdaBoost {

	ArrayList<Feature> features; 
	ArrayList<Label> labels; 
	
	public AdaBoost(ArrayList<Feature> fs, ArrayList<Label> labs){
		features = fs; 
		labels = labs; 
	}
		
	public ArrayList<Model> Train( ArrayList<DataPoint> train_data, ArrayList<Decision_Stump> dc_stumps, 
							boolean is_radnom_decision, int T){
		
		ArrayList<Model> models = new ArrayList<Model>();
		ArrayList<Character> data_labels = DataPoint.getLabels(train_data);
		
		double[] dist = Util.GetUniformDistribution(train_data.size()); 
		
		for (int t = 0 ; t < T; t++){
			
			
			Decision_Stump single_dc ; 
			// selecting decision stump 
			if (is_radnom_decision){
				single_dc = get_rand_decision_stump(dc_stumps); 
			}else{
				single_dc = get_opt_decision_stump(train_data, dc_stumps,dist); 
			}
			// predicting using decision stump 
			ArrayList<Character> predicted_labels = getPrediction(single_dc, train_data);
			
			// calcing error 
			double local_error = getError(dist, predicted_labels, data_labels);
			
			// calcing alpha 
			double alpha = (double)1/2 * (Math.log(1-local_error) - Math.log(local_error));
			
			// updating data distribution
			dist = Update_dist(dist, alpha, predicted_labels, data_labels);
			
			// updating the model 
			Model m = new Model(); 
			m.alpha = alpha; 
			m.decision_stump = single_dc; 
			models.add(m);
			
			//System.out.println("#" + t + " " + local_error + " " + single_dc);
		}
		
		return models; 
	} 
	
	public static double[] Update_dist(double[] dist, double alpha, ArrayList<Character> pred_labels, 
				ArrayList<Character> real_labels){
		int n = dist.length;  
		double[] new_dist = new double[n];
		
		double normalized_val = 0 ; 
		for (int i = 0 ; i < n ; i++){
			int sign = (pred_labels.get(i) == real_labels.get(i))? 1:-1; 
			double exponent = -1 * alpha * sign ;
			new_dist[i] =  dist[i] * (Math.pow(Math.E, exponent));
			normalized_val = normalized_val + new_dist[i]; 
		}
		
		for (int i = 0 ; i<n ; i++){
			new_dist[i] = new_dist[i]/normalized_val; 
		}
			
		return new_dist; 	
	}

	public static Decision_Stump get_rand_decision_stump(ArrayList<Decision_Stump> dc_stumps){
		int size = dc_stumps.size(); 
		Random randomGenerator = new Random();
		int rand_index = randomGenerator.nextInt(size); 
		return dc_stumps.get(rand_index) ; 
		
	}
	
	public static double getError(double[] distribution, ArrayList<Character> predicted_labels, 
																		ArrayList<Character> real_labels){
		int len = distribution.length; 
		double sum_error = 0; 
		
		for (int i = 0 ; i < len ; i++){
			int not_same = (predicted_labels.get(i) != real_labels.get(i)) ? 1 : 0; 
			sum_error = sum_error +  distribution[i] * not_same; 
		}
		
		return sum_error; 
	}
	
	public ArrayList<Character> getPrediction(Decision_Stump dc_stump, ArrayList<DataPoint> data){
		ArrayList<Character> predicted_labels; 
		int f_id = dc_stump.feature_id;
		Feature f = features.get(f_id); 
		
		if(f.feature_type == 'c')
			predicted_labels = PredictBasedOn_Cont_DC (dc_stump,data); 
		else{//f.feature_type == 'd'
			predicted_labels = PredictBasedOn_Disc_DC(dc_stump, data);
		}
		return predicted_labels; 
	} 
	
	public ArrayList<Character> PredictBasedOn_Cont_DC (Decision_Stump dc_stump, ArrayList<DataPoint> data){
		ArrayList<Character> predicted_labels = new ArrayList<Character>();
		
		int f_id = dc_stump.feature_id;
		Feature f = features.get(f_id);
		double threshold = Double.parseDouble(dc_stump.threshold_val); 
		
		int len = data.size(); 
		for (int i = 0 ; i < len ; i++){
			String s = data.get(i).f_values.get(f_id).value;
			double val;
			
			if (s.charAt(0) == '?'){
				val = threshold; 
			}else{
				val = Double.parseDouble(s);
			}
			
			char c; 
			if (val < threshold){
				 c = labels.get(0).label_val; 
			}else {
				 c = labels.get(1).label_val; 
			}
			predicted_labels.add(c); 
			
		}
		
		return predicted_labels; 
	}
	
	public ArrayList<Character> PredictBasedOn_Disc_DC (Decision_Stump dc_stump, ArrayList<DataPoint> data){
		ArrayList<Character> predicted_labels = new ArrayList<Character>();
		
		int f_id = dc_stump.feature_id;
		Feature f = features.get(f_id);
		char threshold = dc_stump.threshold_val.charAt(0); 
		
		int len = data.size(); 
		for (int i = 0 ; i < len ; i++){
			String s = data.get(i).f_values.get(f_id).value;
			char val = s.charAt(0);
			
			if (val == '?')
				val = threshold; 
			
			char c ; 
			if (val == threshold){
				 c = labels.get(0).label_val; 
			}else {
				 c = labels.get(1).label_val; 
			}
			predicted_labels.add(c); 
			
		}		
		
		return predicted_labels; 
	}
	
	public Decision_Stump get_opt_decision_stump(ArrayList<DataPoint> data, ArrayList<Decision_Stump> dc_stumps,
																		double[] dist){
		int len = dc_stumps.size(); 
		ArrayList<Character> real_labels = DataPoint.getLabels(data);
		
		double max_val = 0; 
		int max_index = 0; 
		
		for (int i = 0 ; i < len ; i++){
			Decision_Stump dc = dc_stumps.get(i);
			ArrayList<Character> pred_labels = getPrediction(dc, data);
			double error = getError(dist, pred_labels, real_labels);
			
			double val = Math.abs(error - 0.5);
			
			if (val >= max_val ){
				max_val = val; 
				max_index = i; 
			}
		}
		
		return dc_stumps.get(max_index); 
	}

	public ArrayList<Double> cal_f_function(ArrayList<Double> f_values,
								double alpha, ArrayList<Character> predicted_labels) {
		int len = predicted_labels.size(); 
		if (f_values == null){
			// initialize with zero 
			f_values = new ArrayList<Double>(); 
			
			for (int i = 0 ; i < len ; i++){
				f_values.add(0.0); 
			}
		}
		ArrayList<Double> new_f_values = new ArrayList<Double>(); 
		
		for (int i = 0 ; i < len ; i++){
			double temp = f_values.get(i); 
			int sign = (predicted_labels.get(i) == labels.get(0).label_val ) ? -1 : 1; 
			
			temp = temp + alpha * sign; 
			new_f_values.add(temp); 
		}
			
		return new_f_values; 
	}
	
	ArrayList<Character> PredictBy_f_function(ArrayList<Double> f_values){
		ArrayList<Character> predicted_labels = new ArrayList<Character>(); 
		int len = f_values.size(); 
		
		for (int i = 0 ; i < len ; i++){
			double f_val = f_values.get(i);
			if ( f_val < 0){
				predicted_labels.add(labels.get(0).label_val);
			}else{
				predicted_labels.add(labels.get(1).label_val);
			}
				
		}
		return predicted_labels; 
	}
}
