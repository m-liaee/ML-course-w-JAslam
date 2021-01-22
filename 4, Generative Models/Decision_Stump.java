import java.util.ArrayList;


public class Decision_Stump {
	int feature_id; 
	String threshold_val; 
	
	public static ArrayList<Decision_Stump> GetDecisionStumps(ArrayList<DataPoint> data, ArrayList<Feature> features ){
		ArrayList<Decision_Stump> dc_stumps = new ArrayList<Decision_Stump>(); 
		
		int fs_len = features.size();
		
		for (int i = 0 ; i < fs_len ; i++){
			Feature f = features.get(i); 
			ArrayList<Decision_Stump> temp_dc_stumps; 
			
			if (f.feature_type == 'c'){ 
				 temp_dc_stumps = GetCont_DCStumps_Feature(data,f);
				dc_stumps.addAll(temp_dc_stumps);
			}else{//f.feature_type == 'd'

				temp_dc_stumps = GetDisc_DCStumps_Feature(data,f); 
				dc_stumps.addAll(temp_dc_stumps);	
			}
		}
		
		
		
		return dc_stumps; 
	}
	
	public static ArrayList<Decision_Stump> GetDisc_DCStumps_Feature(ArrayList<DataPoint> data, Feature f){
		int data_size = data.size(); 
		int f_id = f.feature_id;
		
		ArrayList<Decision_Stump> dc_stump_f = new ArrayList<Decision_Stump>();
		
		int len = f.values_set.size(); 
		for (int i = 0 ; i < len ; i++){
			Decision_Stump single_dc = new Decision_Stump(); 
			single_dc.feature_id = f_id; 
			single_dc.threshold_val = f.values_set.get(i);
			dc_stump_f.add(single_dc);
		}
		
		return dc_stump_f; 
	}
	
	public static ArrayList<Decision_Stump> GetCont_DCStumps_Feature(ArrayList<DataPoint> data, Feature f){
		int counter = 0 ; 
		int data_size = data.size(); 
		int f_id = f.feature_id; 
		
		double[] arr = new double[data_size]; 
		
		for (int i = 0 ; i < data_size ; i++){
			DataPoint single_data = data.get(i);
			String s = single_data.f_values.get(f_id).value;

			if ( s.charAt(0) == '?'){
				counter ++;
				//System.out.println(i);
				arr[i] = 0;
			}else{
				arr[i] = Double.parseDouble(s);
				
			}
		}
		
		arr = Util.SortVal(arr);
		ArrayList<Double> temp_arr = Util.FindUniqueVal(arr);
		
				
		double epsilon = 0.1; 
		
		ArrayList<Decision_Stump> dc_stump_f = new ArrayList<Decision_Stump>(); 
		
		Decision_Stump single_dc = new Decision_Stump(); 
		single_dc.feature_id = f_id; 
		single_dc.threshold_val = (temp_arr.get(0) - epsilon) + ""; 
		
		dc_stump_f.add(single_dc);
		
		for (int i = 0 ; i < temp_arr.size()-1 ; i++){
			double mid_val = (temp_arr.get(i) + temp_arr.get(i+1))/2;
			
			single_dc = new Decision_Stump();			
			single_dc.feature_id = f_id; 
			single_dc.threshold_val = mid_val + ""; 
			
			dc_stump_f.add(single_dc);
		}
		
		single_dc = new Decision_Stump(); 
		single_dc.feature_id = f_id;
		single_dc.threshold_val = (temp_arr.get(temp_arr.size()-1)+ epsilon) + ""; 
		
		dc_stump_f.add(single_dc);		
		//System.out.println(counter);
		return dc_stump_f; 	
	}
	
	public String toString() {
		String s = "feature id " + feature_id + ", threshold " + threshold_val + "\n";   
		return s;
	}
}
