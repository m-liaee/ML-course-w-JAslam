import java.io.Console;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;


public class AdaBoost {
	public AdaBoost(){
		
	}
	
	public static Model Train(double[][] data_val, int[] alias_y, ArrayList<Decision_Stump>  dcs, boolean is_random_decision, int T){
		Model m = new Model(); 
		
		double[] distribution = Util.GetUniformDistribution(alias_y.length);
		
		for (int i = 0 ; i < T; i++){

			//System.out.println("i #" + i);
			Decision_Stump dc_stump; 
			if(is_random_decision){
				dc_stump = get_rand_decision_stump(dcs);
			}else{
				dc_stump = get_opt_decision_stump(data_val, alias_y, dcs, distribution); 
			}			
			
		     int [] predicted_val = Predict_Basedon_DC(data_val, dc_stump);
		     double local_error = Util.getError(distribution, predicted_val, alias_y);
		     double eps = (1-local_error)/local_error; 
		     double alpha = (double)1/2 * Math.log( eps);
		     distribution = Update_dist(distribution, alpha, predicted_val, alias_y);
		     
		     m.alphas.add(alpha);
		     m.dcs.add(dc_stump);
		     
		     double train_error = AdaBoost.GetError(m, 0, data_val, alias_y);
		     //System.out.println("round #" + i + " dc " + dc_stump.feature_id + ", local error " + local_error + ", train_err "+train_error );
		     if (i % 50 == 49)
		    	 System.out.println(" round #" + i);
		     	   
		}
		
		return m; 
	}
	
	
	public static Decision_Stump get_rand_decision_stump(ArrayList<Decision_Stump> dcs){
		int len = dcs.size(); 
		
		Random randomGenerator = new Random();
		int rand_index = randomGenerator.nextInt(len);
		
		return dcs.get(rand_index);
	}
	
	public static Decision_Stump get_opt_decision_stump(double[][] data_val, int[] y , 
			ArrayList<Decision_Stump>dcs, double[] distribution ){
		
		int len = dcs.size(); 
		double error = 0; 
		
		double max_val = 0 ; 
		int max_index = 0; 
		
		for (int i = 0 ; i < len; i++){
			
			Decision_Stump dc_temp = dcs.get(i);
			int [] predicted_val = Predict_Basedon_DC(data_val, dc_temp) ; 
			error = Util.getError(distribution, predicted_val, y);
			
			double val = Math.abs(error - 0.5);

			if(val >= max_val){
				max_val = val ;
				max_index = i; 
			}		
			
		}
		return dcs.get(max_index); 
	}

	public static int Predict_Basedon_DC(double[] single_data_val, Decision_Stump dc) {
		int f_id = dc.feature_id; 
		double thresh = dc.threshold;
		
		int pred_val; 
		
		double temp = single_data_val[f_id];
		if (temp < thresh)
				pred_val = 0 ; 
			else
				pred_val = 1; 

		return pred_val; 
	}
	
	public static int[] Predict_Basedon_DC(double[][] data_val, Decision_Stump dc) {
		int len = data_val.length;
		int [] predicted_val = new int[len];
		
		int f_id = dc.feature_id; 
		double thresh = dc.threshold; 
		
		for(int i = 0 ; i < len ; i++){
			double temp = data_val[i][f_id];
			
				if (temp < thresh){
					predicted_val[i] = 0 ;
			 
				}else{
					predicted_val[i] = 1;
			 
				}

		}		
		
		return predicted_val;
	}
	
	public static double[] Update_dist(double[] dist, double alpha, int[] pred_vals, int[] real_y){
	
		int n = dist.length;  
		double[] new_dist = new double[n];
		
		double normalized_val = 0 ; 
		for (int i = 0 ; i < n ; i++){
			int sign = (pred_vals[i] == real_y[i])? 1:-1; 
			double exponent = -1 * alpha * sign ;
			new_dist[i] =  dist[i] * (Math.pow(Math.E, exponent));
			normalized_val = normalized_val + new_dist[i]; 
		}
		
		for (int i = 0 ; i<n ; i++){
			new_dist[i] = new_dist[i]/normalized_val; 
		}
			
		return new_dist;
		
	}
	
	public static int Predict_BasedonModel(Model m, double threshold, double[] single_data_point){
		int len = m.dcs.size(); 
		
		double sum = 0; 
		for(int i = 0 ; i < len; i++){
			Decision_Stump dc = m.dcs.get(i);
			int sign = (Predict_Basedon_DC(single_data_point, dc ) == 0 )? -1 :1;
			//System.out.println(sign);
			sum = sum + m.alphas.get(i) * sign; 
		}
		
		
		if (sum < threshold)
			return 0; 
		else	
			return 1; 
			
	}
	
	public static double GetError(Model m, double threshold, double[][] data_vals , int[] y_vals){
		int len = y_vals.length; 
		
		int sum = 0;  
		
		for(int i = 0 ; i < len; i++){
			int pred_val = Predict_BasedonModel(m, threshold, data_vals[i]);
			int count = (pred_val != y_vals[i])?1:0; 
			sum = sum + count; 
		}
		//System.out.println(sum);
		return sum/(double)len;
	}

}
