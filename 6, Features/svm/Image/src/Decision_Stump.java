import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class Decision_Stump {
	int feature_id; 
	double threshold; 

	
		
	public Decision_Stump(int f_id, double thresh) {
			feature_id = f_id; 
			threshold = thresh; 
		
	}
	
	public String toString() {
		String s = "feature id: " + feature_id + " threshold: "+threshold;
		return s; 
	}
	
	public static ArrayList<Decision_Stump> getDecisionStumps(ArrayList<Feature_Values> data_f_vals, int feature_num  ){
		
		//int diff = 2; // get 0.1 of the decision stumps
			
		ArrayList<Decision_Stump> dcs = new ArrayList<Decision_Stump>();
		int data_size = data_f_vals.size(); 
		
		for (int j = 0 ; j < feature_num ; j++){
			double[] f_j_possible_vals = new double[data_size]; 
	
			for (int k = 0 ; k < data_size ; k++){
				Feature_Values single_data_f_vals = data_f_vals.get(k); 
				int temp_j = single_data_f_vals.values.get(j);
				f_j_possible_vals[k] = temp_j;
			}
			
			f_j_possible_vals = Util.SortVal(f_j_possible_vals);
			ArrayList<Double> unique_vals_j = Util.FindUniqueVal(f_j_possible_vals);
			
			for (int k = 0 ; k < unique_vals_j.size() - 1 ; k++){
				double thresh = unique_vals_j.get(k) + unique_vals_j.get(k+1);
				thresh = thresh /2; 
				Decision_Stump dc = new Decision_Stump(j, thresh);
				dcs.add(dc);
			}
			
		}
		return dcs; 
		
	}
	
	public static void WriteDCstumpsToFile(String file_addr, ArrayList<Decision_Stump> dcs){
		
		PrintWriter writer;
		try {
			writer = new PrintWriter(file_addr, "UTF-8");
			int len = dcs.size(); 
			for(int i = 0 ; i < len ; i++){
				String s = dcs.get(i).feature_id+":"+dcs.get(i).threshold; 
				writer.println(s); 
			}
			writer.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		
		
		
	}
	
	public static ArrayList<Decision_Stump> ReadDCstumpsFromFile(String file_addr){
		ArrayList<Decision_Stump> dcs = new ArrayList<Decision_Stump>(); 
		try {
			FileInputStream fstream;
			fstream = new FileInputStream(file_addr);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			String strLine; 
			try {
				while ((strLine = br.readLine()) != null){
					strLine = strLine.trim();
					String[] tokens = strLine.split(":");
					int f_id = Integer.parseInt(tokens[0]); 
					double thresh = Double.parseDouble(tokens[1]);					
					Decision_Stump dc = new Decision_Stump(f_id,thresh); 
					dcs.add(dc);
					
				}
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
			
			
		
	
		
		return dcs; 
	}
	
	public static double getProbLessThresh(double [][] data, int f_id, double thresh){
		int len = data.length; 
		int count_has_value = 0;  
		int count_less_thresh = 0; 
		
		for (int i = 0 ; i < len; i++){
			if (data[i][f_id] != 0){
				count_has_value++; 
				if (data[i][f_id] < thresh)
					count_less_thresh++; 
			}
		}
		return count_less_thresh/((double) count_has_value );
	}
}
