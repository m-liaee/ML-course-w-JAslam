import java.util.ArrayList;
import java.util.Random;


public class Util {
	
	static Random randomGenerator = new Random();
	
	public static double [] getArray(ArrayList<Integer> list){
		double [] arr = new double [list.size()];
		
		for (int i = 0 ; i < list.size() ; i ++){
			arr[i] = list.get(i);
		}
		return arr; 
	}
	
	public static double[][] getMatrix(ArrayList<Feature_Values> data_f_vals, int num_features){
		int row = data_f_vals.size(); 
		int col = num_features; 
		
		double[][] matrix = new double[row][col]; 
		
		for (int i = 0 ; i < row; i++){
			Feature_Values single_data_f_vals = data_f_vals.get(i);
			for (int j = 0 ; j < col ; j++){
				int f_j_val = single_data_f_vals.values.get(j);
				matrix[i][j] = f_j_val; 
			}
		}
		
		return matrix ; 
	}
	public static double [][] getNormalizedData(double[][] data, boolean use_vector,  double[] minn, double[] maxx){
		int n = data.length; 
		int m = data[0].length; 
		
		double [][] normal_data = new double [n][m]; 
		
		if (use_vector){
			for (int j = 0 ; j < m;  j++){
				for (int i = 0 ; i < n ; i++){
					normal_data[i][j] = (data[i][j] - minn[j])/maxx[j];
				}
				
			}
			
		}else{
		
			for (int j = 0 ; j < m ; j++){
				
				double [] temp = getTableColumn_j(data, j);
				double max_val = getMaxVal(temp); 
				double min_val = getMinVal(temp);
				
				minn[j] = min_val; 
				maxx[j] = max_val; 
				
				for (int i = 0 ; i < n ; i++){
					
					normal_data[i][j] = (data[i][j] - min_val) / max_val; 
				}
			}
		}
		return normal_data; 
		
	}
	
	public static int get_random_even_number(int begin_interval, int end_interval){
		int interval_len = end_interval - begin_interval; 
		int temp = interval_len / 2;
		
		int off_set = randomGenerator.nextInt(temp+1);
		 

		int next_int = begin_interval + off_set * 2;  
		
		//System.err.println(next_int);
		return next_int ; 
			
	}
	
	public static int get_random_number (int max_num){
		return randomGenerator.nextInt(max_num); 
	}

	
	public static double getMinVal(double[] data) {
		int len = data.length; 
		
		double min_val = data[0]; 
		
		for (int i = 0 ; i < len ; i++){
			if (data[i] < min_val)
				min_val = data[i];
		}
		
		return min_val;
	}


	public static double getMaxVal(double[] data) {
		int len = data.length; 
		
		double max_val = 0 ; 
		for (int i = 0 ; i < len ; i++){
			if (data[i] > max_val )
				max_val = data[i];
		}
		return max_val;
	}


	public static double[] getTableColumn_j(double[][] table, int j ){
		int row_num = table.length; 
		int col_num = table[0].length; 
		
		if ( j>= col_num){
			System.err.println("function Util.getTableColumn_j: j exceeds table cols");
			return null; 
		}
		
		double[] col_arr = new double[row_num];
		for(int i = 0 ; i < row_num; i++){
			col_arr[i] = table[i][j];
		}
		
		return col_arr; 
	}
	
	public static double[] getTableRow_i(double[][]table, int i){
		int row_num = table.length; 
		int col_num = table[0].length;
		
		if (i>= row_num ){
			System.err.println("function Util.getTableRow_i: i exceeds table rows");
			return null; 
		}
		
		double[] row_arr = new double[col_num]; 
		for (int k = 0 ; k < col_num; k++){
			row_arr[k] = table[i][k];
		}
		return row_arr; 
	}
	
	public static ArrayList<Integer> getIndices_somePercent(ArrayList<Integer> labels, int percent){
		int size = labels.size(); 
		int [] counter = new int [10];
		
		for (int i = 0 ; i < 10 ; i++){
			counter[i] = 0 ; 
			for (int k = 0 ; k < size; k++){
				int label = labels.get(k); 
				if (label == i){
					counter[i]++;
				}
			}
			
			//System.out.println(counter[i]);
		}
		
		ArrayList<Integer> indices = new ArrayList<Integer>(); 
		
		for (int i = 0 ; i < 10 ; i++){
			int num_pick_i = counter[i] * percent /100;
			
			for (int k = 0 ; k <size; k++){
				if (labels.get(k) == i && num_pick_i >0){
					indices.add(k);
					num_pick_i --; 
				}
			}
		}
		
		return indices; 
	}
	
	public static ArrayList<Feature_Values> getElements(ArrayList<Integer> indices, ArrayList<Feature_Values> data_f_vals){
		ArrayList<Feature_Values> sub_data = new ArrayList<Feature_Values>(); 
		
		int size = indices.size(); 
		for(int i = 0 ; i < size; i++){
			int index = indices.get(i);
			Feature_Values single_data_f_val = data_f_vals.get(index);
			sub_data.add(single_data_f_val);
		}
		
		return sub_data; 
	}
	
	public static ArrayList<Integer> getSubArray(ArrayList<Integer> indices, ArrayList<Integer> data_labels){
		ArrayList<Integer> sub_arr = new ArrayList<Integer>(); 
		
		int size = indices.size(); 
		for(int i = 0 ; i < size; i++){
			int index = indices.get(i);
			int label = data_labels.get(index);
			sub_arr.add(label);
		}
		
		return sub_arr; 
	}

	public static void split_test_train(double[][] whole_data,
			double[][] train_data, double[][] test_data) {
		
		int n = whole_data.length; 
		
		int test_counter = 0;
		int train_counter = 0;
		
		for (int i = 0 ; i < n ; i++){
			if ( i % 10 == 9 ){
				test_data[test_counter] = whole_data[i];
				test_counter++; 
			}else{
				train_data[train_counter] = whole_data[i];
				train_counter++; 
			}
		}
		
	}
}
