


public class test {

	public static void main(String[] args) {
		double [][] data = new double [3][2]; 
		
		data[0][0] = 1 ;
		data[0][1] = 2; 
		data[1][1] = 3; 
		data[1][0] = 4; 
		data[2][1] = 5; 
		data[2][0] = 9; 
		
		//data = Util.getNormalizedData(data); 
		
		for (int i = 0 ; i < 3 ; i++){
			for (int j = 0 ; j < 2 ; j++){
				System.out.println(data[i][j]);
				
			}
		}
		
	}
}
