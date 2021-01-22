import java.util.ArrayList;


public class Util {
	
	
	public static ArrayList<Float> FindUniqueVal(float array[]){
		
		float[] tmp; 		
		tmp = SortVal(array);
		int size = tmp.length; 
		
		ArrayList<Float> uniq_val = new ArrayList<Float>();
		
		uniq_val.add(tmp[0]); 
		for (int i = 1 ; i < size ; i++){
			if (tmp[i] - tmp[i-1] > 0)
				uniq_val.add(tmp[i]); 
				
		}		
		return uniq_val; 
		
		
	}
	

	public static float[] SortVal(float array[]){
		
		float[] tmp = new float [array.length]; 
		
		for (int i = 0 ; i < array.length ; i++){
			tmp[i] = array[i]; 			
		}
		
		//simple sort 
		for (int i = 0 ; i < tmp.length -1 ; i++){
			for (int j = i +1 ; j < tmp.length ; j++){
				
				//swap
				if ( tmp[i] > tmp[j]){
					float t ; 
					t = tmp[j]; 
					tmp[j] = tmp[i]; 
					tmp[i] = t; 
				}
					
			}
		}
		
		
		return tmp; 
	}

	// checked
	public static int FindMaxByIndex(ArrayList<Integer> list) {
		int len = list.size(); 
		int max = 0 ; 
		int max_index = 0; 
		
		for (int i = 0 ; i < len ; i++){
			int temp = list.get(i); 
			if (temp > max){
				max = temp; 
				max_index = i; 
			}
		}
		return max_index;
	}
	
	//checked
	public static ArrayList<Integer> Counter (float[] data, ArrayList<Float> labels){
		ArrayList<Integer> counter = new ArrayList<Integer>(); 
		
		int lb_len = labels.size();  
		for (int i = 0 ; i < lb_len ; i++){
			
			int c = 0 ; 
			
			int d_len = data.length ; 
			for (int j = 0 ; j < d_len; j++){
				if ( data[j] == labels.get(i) )
					c++; 
			}
			
			counter.add(c); 
		}
		
		return counter; 
	}
	
	//checked
	public static float FindMeanValue (float[] d){
		float sum = 0 ; 
		for (int i = 0 ; i < d.length; i++){
			sum = sum + d[i]; 
			
		}
		if (d.length == 0) 
			return 0 ; 
		return (sum/(float)d.length); 
		
	}

	//checked
	public static float FindSSE(float[] d, float mean) {
		float sum = 0 ;  
		
		for (int i = 0 ; i < d.length; i++){
			float base = d[i] - mean ; 
			sum =  sum +  (float) Math.pow(base, 2) ; 
		}
		return sum;
	}


	public static float[][] split_test_data(float[][] data, int i ,  int k) {

		int n = data.length;
		
		int size_of_each_folder = (int) Math.ceil( (float) n/k );  
		
		int min_index = (i-1)* size_of_each_folder; 
		int max_index = (i)*size_of_each_folder -1;
		
		if (max_index >= n)
			max_index = n-1; 
		
		int size = max_index - min_index +1; 
		
		float[][] test = new float [size][];
		for (int j = 0 ; j < size; j++){
			test[j] = data[j + min_index]; 
		}
		
		return test; 
		
	}
	
	public static float[][] split_train_data(float[][] data, int i ,  int k) {

		int n = data.length;
		
		int size_of_each_folder = (int) Math.ceil( (float) n/k );  
		
		int min_index = (i-1)* size_of_each_folder; 
		int max_index = (i)*size_of_each_folder -1;
		
		if (max_index >= n)
			max_index = n-1; 
		
		int size = max_index - min_index +1; 
		
		float[][] train = new float [n - size][];
		
		int counter = 0 ; 
		for (int j = 0 ; j < n; j++){
			if ( j>= min_index && j <= max_index )
				continue; 
			
			train[counter] = data[j];
			counter++; 
		}
		
		return train; 
	}
	

	
}
