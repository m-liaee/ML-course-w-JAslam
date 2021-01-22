import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class FileReading {
	
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
											
					String [] token =  strLine.split("[ ]+");
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
	
	public static double[][] ReadFile(String file_name){
		
		// create matrix
		
		int [] size = FileReading.GiveFileSize(file_name); 
		double [][] matrix = new double[size[0]][] ;
		for (int i = 0 ; i < size[0] ; i++){
			matrix[i] = new double [size[1]]; 
		}
	
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
					
					String temp[] = strLine.split("[ ]+");
					if (temp.length ==1 ){
						temp = temp[0].split(",");
					}
					if (temp.length != size[1] ) 
						System.err.println("reading file num of cols conflicts");
					
					for(int j = 0 ; j < size[1]; j++){
						matrix[i][j] = Double.parseDouble(temp[j]);							
					}
					
					i++; 
					
				}

				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return matrix; 
	}
}
