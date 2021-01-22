import java.util.ArrayList;

public class Label {

	int label_id; 
	char label_val; 
	
	public Label(int id, char val) {
		label_id = id; 
		label_val = val; 
	}
	
	public String toString(){
		String str; 

		str = "label id:" + label_id + ", label value: " + label_val; 
		
		return str; 
	}

	
}
