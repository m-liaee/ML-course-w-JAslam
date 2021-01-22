

public class Util {

	public static int get_max_by_index(double [] arr){
		int len = arr.length; 
		
		double max = 0 ; 
		int max_index = 0; 
		for (int i = 0 ; i < len; i++){
			if (arr[i] > max){
				max = arr[i]; 
				max_index = i ; 
			}
				
		}
		
		return max_index; 
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

}
