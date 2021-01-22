import java.beans.FeatureDescriptor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Parser {
	
	ArrayList<Feature> features; 
	ArrayList<Label> labels; 
	
	ArrayList<DataPoint> datapoints;
	int data_size; 
	
	public Parser(String conf_addr, String data_addr){
		features = new ArrayList<Feature>(); 
		labels = new ArrayList<Label>();
		datapoints = new ArrayList<DataPoint>();
		
		ParseConfig(conf_addr);
		ParseData(data_addr); 
	}
	
	public void ParseData(String data_addr){
		
		try {
			FileInputStream fstream;
			fstream = new FileInputStream(data_addr);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			int counter = 0 ; 
			while (counter < data_size){
				try {
					String strLine = br.readLine();
					DataPoint dp = ExtractDataPoint(strLine, counter); 
					datapoints.add(dp);
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
				counter ++; 
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		
	}
	
	private DataPoint ExtractDataPoint(String strLine, int index) {
		strLine = ExtractExtraSpaces(strLine); 			 
		strLine = strLine.trim(); 
		String[] temp = strLine.split("[ ]+");
		
		DataPoint dp = new DataPoint();
		int len = temp.length;
		
		for (int i = 0 ; i < len-1; i++){
			Feature_Val f_val = new Feature_Val();
			f_val.featur_id = i+1; 
				
			f_val.value = temp[i]; 
			dp.f_values.add(f_val); 
			
		}
		dp.lb_val = temp[len-1].charAt(0); 
		
		
		return dp; 
	}

	public void ParseConfig(String conf_addr){
		try {
			FileInputStream fstream;
			fstream = new FileInputStream(conf_addr);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			String strLine ; 
			//Read File Line By Line				
			try {
				strLine = br.readLine();
				strLine = ExtractExtraSpaces(strLine); 			 
				strLine = strLine.trim(); 
				//System.out.println(strLine);
				String[] temp = strLine.split("[ ]+");
			
				this.data_size = Integer.parseInt(temp[0]); 				
				int num_features = Integer.parseInt(temp[1].trim()) + Integer.parseInt(temp[2].trim()); 
					
				int counter = 0 ; 
								
				while (counter < num_features)   {
					
					strLine = br.readLine(); 
					strLine = ExtractExtraSpaces(strLine); 
					
					Feature f = ExtractFeature(strLine, counter); 
					features.add(f); 				
					counter ++; 
				}
				
				strLine = br.readLine();
				strLine = ExtractExtraSpaces(strLine); 
				ExtractLabels(strLine); 
				
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void ExtractLabels(String strLine){
		
		strLine = strLine.trim(); 
		String [] token =  strLine.split("[ ]+");
		
		int num_labels = Integer.parseInt(token[0]);
		for (int i = 1; i <= num_labels ; i++){
			Label lab = new Label(i-1, token[i].charAt(0)); // ids start from 0
			labels.add(lab);
		}
		
	}
	public Feature ExtractFeature(String strLine, int id){
		Feature f = new Feature(); 
		f.feature_id = id; 
		
		strLine = strLine.trim(); 
		String [] token =  strLine.split("[ ]+");
		
		//System.out.println(token[0]);
		
		int temp_val = Integer.parseInt(token[0].trim()); 
		
		if (temp_val > 0)
			f.feature_type = 'd'; 
		else
			f.feature_type = 'c';
		
		int len = token.length; 
		for (int i = 1 ; i < len; i++){
			token[i] = token[i].trim(); 
			f.values_set.add(token[i]); 
		}

		return f; 
	}
	
	public String ExtractExtraSpaces(String strLine){
		strLine = strLine.replaceAll("\t", " ");
		strLine = strLine.replaceAll("\n", " ");
		strLine = strLine.replaceAll("\r", " ");
		return strLine; 
	} 
}

   