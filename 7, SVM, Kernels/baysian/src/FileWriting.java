import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class FileWriting {
	
	public FileWriting(){
		
	}
	
	public static void WriteDataToFile(String file_addr, double[][] data){
		
		PrintWriter writer;
		try {
			writer = new PrintWriter(file_addr, "UTF-8");
			int len = data.length; 
			int num_col = data[0].length; 
			for(int i = 0 ; i < len ; i++){
				String s = "" + data[i][0];
				
				for (int j = 1 ; j < num_col ; j++){ 
					s = s + " " + data[i][j]; 
				}
				writer.println(s);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void WriteDataToFile(String file_addr, double[] column_arr){
		int len = column_arr.length;
		
		PrintWriter writer; 
		
		try{
			writer = new PrintWriter(file_addr, "UTF-8"); 
			for (int i = 0 ; i < len; i++){
				String s = "" + column_arr[i];
				writer.println(s); 
			}
			writer.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static double[][] ReadDataFromFile(String file_addr, int num_row, int num_col){
		double[][] data = new double[num_row][num_col];  
		try {
			FileInputStream fstream;
			fstream = new FileInputStream(file_addr);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			String strLine;
			int line_cntr = 0; 
			try {
				while ((strLine = br.readLine()) != null){
					strLine = strLine.trim();
					String[] tokens = strLine.split(" ");
					for (int j = 0 ; j < num_col ; j++){
						data[line_cntr][j] = Double.parseDouble(tokens[j]); 
						
					}
					line_cntr ++; 
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
			
		return data;  
	}
	
	public static double[] ReadDataFromFile(String file_addr, int num_row){
		
		double [] arr = new double[num_row]; 

		try {
			FileInputStream fstream;
			fstream = new FileInputStream(file_addr);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			String strLine;
			int line_cntr = 0; 
			try {
				while ((strLine = br.readLine()) != null){
					strLine = strLine.trim();
					String[] tokens = strLine.split(" ");
					double temp = Double.parseDouble(tokens[0]);
					arr[line_cntr] = temp; 
					line_cntr ++; 
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		
		return arr; 
	}

	public void WriteToFile_light_format(String file_addr, double[][] data, double[] y){
		
		int num_row = data.length; 
		int num_col = data[0].length; 
		
		PrintWriter writer; 
		
		try{
			writer = new PrintWriter(file_addr, "UTF-8"); 
			for (int i = 0 ; i < num_row; i++){
				int target = (int) y[i];
				if (target == 0)
					target = 10; 
				String s = target + " "; 
				
				for (int j = 0 ; j < num_col; j++){
					int f_id = j+1; 
					s = s + f_id +":"+data[i][j] + " "; 
				}
				writer.println(s);
			}
			writer.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	} 
}
