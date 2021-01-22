import java.util.ArrayList;


public class DataPoint {

	char lb_val; 
	ArrayList<Feature_Val> f_values; 
	
	public DataPoint(){
		f_values = new ArrayList<Feature_Val>(); 
	}

	public String toString(){
		String str; 
		str = "label "+ lb_val +" , feature values: "; 
		
		for (int i = 0; i < f_values.size(); i++) {
			str = str + f_values.get(i).value +",";  
		}
		return str; 
	}
	
	public static ArrayList<Character> getLabels(ArrayList<DataPoint> data){
		ArrayList<Character> data_labels = new ArrayList<Character>(); 
		
		int len = data.size(); 
		for (int i = 0; i < len; i++) {
			char c = data.get(i).lb_val;
			data_labels.add(c);
		}
		return data_labels; 
	} 
}
