import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class FileReading {
	
	double [][] x_data; 
	double [] y_data;
	
	double[][] test_data; 
	double[] test_y;
	
	double[][] train_data; 
	double[] train_y; 
	
	          
	public void split_test_train(){
		
		int n = x_data.length; 
		//int m = x_data[0].length;
		
		int test_size = n / 10 ; 
		int train_size = n - test_size;
		
		train_data = new double [train_size][];
		test_data = new double [test_size][]; 
		
		train_y = new double [train_size]; 
		test_y = new double [test_size];
		 
		int train_ctr = 0 ; 
		int test_ctr = 0 ; 
		
		for (int i = 0 ; i < n ; i++){
			if ( i % 10 == 9 ){
				test_data[test_ctr] = x_data[i];
				test_y [test_ctr] = y_data[i];
				test_ctr++; 
			}else{
				train_data[train_ctr] = x_data[i]; 
				train_y[train_ctr] = y_data[i];
				train_ctr++; 				
			}
		}
		
		
	}
	public static int[] GiveFileSize(String file_name){
		
		int[] size = new int[2];
		
		// read from file
		try {
			FileInputStream fstream;
			fstream = new FileInputStream(file_name);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			String strLine ; 
			//Read File Line By Line
						
			try {
				int i = 0 ; int j = 0 ; 
				while ((strLine = br.readLine()) != null)   {
					
					// avoid reading new line of file 
					strLine = strLine.replaceAll("\\r\\n", " ");
					strLine = strLine.trim(); 
					if (strLine.length() == 0)
						break;
											
					//String [] token =  strLine.split("[ ]+");
					String [] token =  strLine.split("\t");
					if(token.length ==1){
						token = token[0].split(",");
					}
					j = token.length;
					i++;
			
				}
				size[0] = i ; 
				size[1] = j ; 
				System.out.println("size of file is " + i + " * " + j);
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return size; 
	}
	
	
	public void ReadFile(String file_name){
		
		// create matrix
		
		int [] size = FileReading.GiveFileSize(file_name); 
		x_data = new double[size[0]][size[1]-1];
		y_data = new double[size[0]]; 
//		double [][] matrix = new double[size[0]][] ;
//		for (int i = 0 ; i < size[0] ; i++){
//			matrix[i] = new double [size[1]]; 
//		}
	
		// read from file
		try {
			FileInputStream fstream;
			fstream = new FileInputStream(file_name);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			String strLine ; 
			//Read File Line By Line
			try {
				int i = 0 ; 
				while ((strLine = br.readLine()) != null)   {
					
					// check possible errors of reading data from file 
					if ( i == size[0])
						break; 
					
					strLine.replaceAll("\r\n", " "); 
					strLine = strLine.trim();
					if (strLine.length() == 0)
						break; 
					
					String temp[] = strLine.split("\t");
					if (temp.length ==1 ){
						temp = temp[0].split(",");
					}
					if (temp.length != size[1] ) 
						System.err.println("reading file num of cols conflicts");
					int j; 
					for(j = 0 ; j < size[1]-1; j++){
						//matrix[i][j] = Double.parseDouble(temp[j]);
						//System.out.println(i);
						x_data[i][j] = Double.parseDouble(temp[j]);
					}
					y_data[i] = Double.parseDouble(temp[j]);

					i++; 
					
				}

				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//return matrix; 
	}
}
