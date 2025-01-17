import java.util.ArrayList;
import java.util.Random;


public class Util {
	
	static Random randomGenerator = new Random();

	public static double [][] getNormalizedData(double[][] data, boolean use_vector,  double[] minn, double[] maxx){
		int n = data.length; 
		int m = data[0].length; 
		
		double [][] normal_data = new double [n][m]; 
		
		if (use_vector){
			for (int j = 0 ; j < m;  j++){
				for (int i = 0 ; i < n ; i++){
					
					normal_data[i][j] = (data[i][j] - minn[j])/maxx[j];
//					if ( i == 0 && j == 1){
//						System.out.println("hey " + minn[j]);
//					}
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
	
	public static double [][] get_z_score_normalize(double[][] data, boolean use_vector,  double[] mean, double[] sigma){
		
		int n = data.length; 
		int m = data[0].length; 
		
		double [][] normal_data = new double [n][m]; 
		
		if (use_vector){
			for (int j = 0 ; j < m ; j++){
				
				for (int i = 0 ; i < n ; i++){
					normal_data[i][j] = (data[i][j] - mean[j])/sigma[j];
				}
			}
		}else{
			
			for (int j = 0 ; j < m ; j++){
				double[] col_j = Util.getTableColumn_j(data, j); 
				mean[j] = Util.get_mean_value(col_j); 
				sigma[j] = Util.get_sigma(col_j);
				
				for (int i = 0 ; i < n ; i++){
					normal_data[i][j] = (data[i][j] - mean[j])/sigma[j];
				}
			}
		}
		
		return normal_data; 
		
	}
	
	public static int[] getTableColumn_j(int[][] table, int j ){
		int row_num = table.length; 
		int col_num = table[0].length; 
		
		if ( j>= col_num){
			System.err.println("function Util.getTableColumn_j: j exceeds table cols");
			return null; 
		}
		
		int[] col_arr = new int[row_num];
		for(int i = 0 ; i < row_num; i++){
			col_arr[i] = table[i][j];
		}
		
		return col_arr; 
	}
	
	public static int[] getTableRow_i(int[][]table, int i){
		int row_num = table.length; 
		int col_num = table[0].length;
		
		if (i>= row_num ){
			System.err.println("function Util.getTableRow_i: i exceeds table rows");
			return null; 
		}
		
		int[] row_arr = new int[col_num]; 
		for (int k = 0 ; k < col_num; k++){
			row_arr[k] = table[i][k];
		}
		return row_arr; 
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
	
	public static void initial_array(double [] arr, double init_val){
		int n = arr.length; 
		
		for (int i = 0 ; i < n ; i++){
			arr[i] = init_val; 
		}
	}
	
	public static int get_num_between_value (double[] arr , double min_val , double max_val){
		int len = arr.length; 
		int cntr = 0 ; 
		
		for(int i = 0 ; i < len; i++){
			if ( arr[i] > min_val && arr[i] < max_val){
				cntr++; 
			}
		}
		return cntr; 
	}
	
	public static int get_random_number (int max_num){
		return randomGenerator.nextInt(max_num); 
	}
	
	public static int[] randomRange(int start, int end) {
        Random rgen = new Random();  // Random number generator
        int size = end-start;
        int[] array = new int[size];

        for(int i=0; i< size; i++){
            array[i] = start+i;
        }

        for (int i=0; i<array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
        return array;
    }
	
	public static double get_mean_value (double arr[]){
		int len = arr.length; 
		double sum = 0; 
		
		for(int i = 0 ; i < len; i++){
			sum = sum + arr[i]; 
		}
		
		return sum / len;
	}
	
	public static double get_sigma(double arr[]){
		
		int len = arr.length; 
		double mean = get_mean_value(arr);
		
		double var = 0; 
		for (int i = 0 ; i < len ; i++){
			double temp = arr[i] - mean;
			temp = temp * temp ; 
			
			var = var + temp; 
		}
		
		var = var / len; 
		return Math.sqrt(var); 
	}
	
	public static int get_random_even_number(int begin_interval, int end_interval){
		int interval_len = end_interval - begin_interval; 
		int temp = interval_len / 2;
		
		int off_set = randomGenerator.nextInt(temp+1);
		 

		int next_int = begin_interval + off_set * 2;  
		
		//System.err.println(next_int);
		return next_int ; 
			
	}
	 
	public static ArrayList<Integer> getIndices_somePercent(ArrayList<Double> labels, int percent , int num_classes){
		int size = labels.size(); 
		int [] counter = new int [num_classes];
		
		for (int i = 0 ; i < num_classes ; i++){
			counter[i] = 0 ; 
			for (int k = 0 ; k < size; k++){
				double label = labels.get(k); 
				if (label == i){
					counter[i]++;
				}
			}
			
			//System.out.println(counter[i]);
		}
		
		ArrayList<Integer> indices = new ArrayList<Integer>(); 
		
		for (int i = 0 ; i < num_classes ; i++){
			int num_pick_i = counter[i] * percent /100;
			
			for (int k = 0 ; k <size; k++){
				if (labels.get(k) == i && num_pick_i > 0){
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
	
	public static ArrayList<Double> getSubArray(ArrayList<Integer> indices, ArrayList<Double> data_labels){
		ArrayList<Double> sub_arr = new ArrayList<Double>(); 
		
		int size = indices.size(); 
		for(int i = 0 ; i < size; i++){
			int index = indices.get(i);
			double label = data_labels.get(index);
			sub_arr.add(label);
		}
		
		return sub_arr; 
	}
	
	public static double [] getArray(ArrayList<Double> list){
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
	
	public static double[][] getTableWithHamingDistance(int distance, int num_row, int num_col){
		double [][] haming_table  = new double [num_row][];
		
		for (int i = 0 ; i < num_row ; i++){
			haming_table[i] = new double [num_col]; 
		}
		
		Random randomGenerator = new Random();
		int max_int = (int)Math.pow(2, num_col); 
		 
		for (int i = 0 ; i < num_row; i++){
			boolean generated = false;
			while (! generated){	    
				int random_int = randomGenerator.nextInt(max_int);
				double [] temp = getBinaryArray(random_int, num_col);
				boolean new_entry_is_good = hasSufficientDistance(distance, haming_table, temp, i);
				if (new_entry_is_good){
					generated = true;
					haming_table[i] = temp; 
				}
			}
			
			System.out.println("Haming row# "+i+" is created." );			
		}
		
		return haming_table; 
	}
	
	public static double[] getBinaryArray(int n, int length){
		double [] arr = new double [length]; 
		for (int i = length-1 ; i >= 0 ; i--){
			arr[i] = n % 2;
			n = n/2; 
		}
		return arr; 
	}
	
	public static boolean hasSufficientDistance(int distance, double [][] table, double[] temp, int j){
		 
		for (int i = 0 ; i < j; i++){
			int d = getDistance(table[i], temp);
			if (d < distance)
				return false; 
		}
		
		return true; 
	}
	
	public static int getDistance(double a[], double b[]){
		int sum = 0; 
		
		int len = a.length; 
		
		for (int i = 0 ; i < len ; i++){
			if (a[i] != b[i])
				sum++; 
		}
		
		return sum; 
	}
	
	public static void print(double [] a){
		int len = a.length;
		String s = ""; 
		for (int i = 0 ; i < len; i++)
				s = s + ","+a[i]; 
		System.out.println(s);
	}
	
	public static void print(double [][] a ){
		int row = a.length; 
		int col = a[0].length; 
		
		
		for (int i = 0 ; i < row ; i++){
			String s = ""; 
			for (int j = 0 ; j < col ; j++)
				s = s + "," + a[i][j];
			System.out.println(s);
		}
	}
}
