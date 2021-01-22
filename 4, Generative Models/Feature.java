import java.util.ArrayList;


public class Feature {

	int feature_id; 
	char feature_type; // c = continues, d = discrete 
	ArrayList<String> values_set; 
	
	public Feature() {
		values_set = new ArrayList<String>(); 		
	}
	
	public String toString(){
		String s; 
		s = "feature id: " + feature_id + ", feature type: " + feature_type + ", values set: "; 
		
		for (int i = 0 ; i < values_set.size(); i++){
			s = s +(values_set.get(i) + " "); 
		}
		s = s + "\n"; 
		return s; 
	}
}
