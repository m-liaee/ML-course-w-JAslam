import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


public class Util {
	
	static Random randomGenerator = new Random();
	
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
	
	public static double[] GetUniformDistribution(int n) {
		double [] dist = new double[n];
		
		for (int i = 0 ; i < n ; i++){
			dist[i] = (double)1/n; 
		}
		return dist;
	}
	
	public static double getError(double[] distribution, int[] predicted_val, int[] y ){
		int len = distribution.length; 
		
		double sum = 0; 
		for (int i = 0 ; i < len ; i++){
			if( predicted_val[i] != y[i]){
				sum = sum + distribution[i];
			}
		}
		
		return sum; 
	}
	
	
	public static ArrayList<Double> FindUniqueVal(double array[]){
		
		double[] tmp; 		
		tmp = SortVal(array);
		int size = tmp.length; 
		
		ArrayList<Double> uniq_val = new ArrayList<Double>();
		
		uniq_val.add(tmp[0]); 
		for (int i = 1 ; i < size ; i++){
			if (tmp[i] - tmp[i-1] > 0)
				uniq_val.add(tmp[i]); 
				
		}		
		return uniq_val; 
		
		
	}
	
	
	public static double[] SortVal(double array[]){
		
		double[] tmp = new double [array.length]; 
		
		for (int i = 0 ; i < array.length ; i++){
			tmp[i] = array[i]; 			
		}
		
		//simple sort 
		for (int i = 0 ; i < tmp.length -1 ; i++){
			for (int j = i +1 ; j < tmp.length ; j++){
				
				//swap
				if ( tmp[i] > tmp[j]){
					double t ; 
					t = tmp[j]; 
					tmp[j] = tmp[i]; 
					tmp[i] = t; 
				}
					
			}
		}
		
		
		return tmp; 
	}
	
	public static void print(int [] a){
		int len = a.length;
		String s = ""; 
		for (int i = 0 ; i < len; i++)
				s = s + ","+a[i]; 
		System.out.println(s);
	}
	
	public static void print(int [][] a ){
		int row = a.length; 
		int col = a[0].length; 
		
		
		for (int i = 0 ; i < row ; i++){
			String s = ""; 
			for (int j = 0 ; j < col ; j++)
				s = s + "," + a[i][j];
			System.out.println(s);
		}
	}
	
	public static int[][] getTableWithHamingDistance(int distance, int num_row, int num_col){
		int [][] haming_table  = new int [num_row][];
		
		for (int i = 0 ; i < num_row ; i++){
			haming_table[i] = new int [num_col]; 
		}
		
		Random randomGenerator = new Random();
		int max_int = (int)Math.pow(2, num_col); 
		 
		for (int i = 0 ; i < num_row; i++){
			boolean generated = false;
			while (! generated){	    
				int random_int = randomGenerator.nextInt(max_int);
				int [] temp = getBinaryArray(random_int, num_col);
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
	
	public static int[] getBinaryArray(int n, int length){
		int [] arr = new int [length]; 
		for (int i = length-1 ; i >= 0 ; i--){
			arr[i] = n % 2;
			n = n/2; 
		}
		return arr; 
	}
	
	public static boolean hasSufficientDistance(int distance, int [][] table, int[] temp, int j){
		 
		for (int i = 0 ; i < j; i++){
			int d = getDistance(table[i], temp);
			if (d < distance)
				return false; 
		}
		
		return true; 
	}
	
	public static int getDistance(int a[], int b[]){
		int sum = 0; 
		
		int len = a.length; 
		
		for (int i = 0 ; i < len ; i++){
			if (a[i] != b[i])
				sum++; 
		}
		
		return sum; 
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
	
	public static int [] getArray(ArrayList<Integer> list){
		int [] arr = new int [list.size()];
		
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
}
