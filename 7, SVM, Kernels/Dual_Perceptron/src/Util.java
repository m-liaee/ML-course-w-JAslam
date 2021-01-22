import java.util.Random;


public class Util {
	
	
	
	public static void initial_array(double [] arr, double init_val){
		int n = arr.length; 
		
		for (int i = 0 ; i < n ; i++){
			arr[i] = init_val; 
		}
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
	
	public static void print(double [] a){
		int len = a.length;
		String s = ""; 
		for (int i = 0 ; i < len; i++)
				s = s + ","+a[i]; 
		System.out.println(s);
	}
	
	public static double [][] add_column_one(double[][] data){
		int row = data.length; 
		int col = data[0].length; 
		
		double [][] new_data = new double[row][col+1]; 
		
		for (int i = 0 ; i < row; i++){
			new_data[i][0] = 1; 
			for (int j = 1 ; j < col+1; j++){
				new_data[i][j] = data[i][j-1];
			}
			
		}
		
		return new_data; 
	}
}
