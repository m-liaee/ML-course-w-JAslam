import java.util.ArrayList;

public class Feature {
	
	float threshold; 
	int feature_index; 
	
	public static ArrayList<Feature> ExtractFeatures(float[][] data , int size_n, int size_m){
		
		ArrayList<Feature> features_list = new ArrayList<Feature>();
		
		// iterate on each column of data to extract features_threshold
		for (int i = 0 ; i < size_m ; i++){
			float [] tmp = new float[size_n];
			
			for (int j = 0 ; j < size_n ; j++){
				tmp [j] = data[j][i]; 
			}
			
			ArrayList<Float> uniq_vals = Util.FindUniqueVal(tmp);
			int uniq_val_length = uniq_vals.size(); 
			//System.out.println("dimension " + i + " has " + uniq_val_length + " different unique values");
			
			for (int k = 0 ; k < uniq_val_length -1 ; k++){
				
				 float num = (uniq_vals.get(k) + uniq_vals.get(k+1))/2;			    	     
			     Feature f = new Feature(i,num); 
			     features_list.add(f); 
			}
						
		}
		
		return features_list; 
			 
		
	}
	
	public Feature(int f_index, float tr) {
		feature_index = f_index; 
		threshold = tr;		
	}
}
