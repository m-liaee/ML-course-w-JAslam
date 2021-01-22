import java.util.ArrayList;


public class Model {
	ArrayList<Decision_Stump> dcs; 
	ArrayList<Double> alphas;
	
	
	public Model() {
		dcs = new ArrayList<Decision_Stump>(); 
		alphas = new ArrayList<Double>(); 
	 
	}
	
	
	public String toString() {
		int len = dcs.size(); 
		String s = "";
		for (int i = 0 ; i < len ; i++){
			s = s + "dcs " + dcs.get(i).feature_id + ", " +
					", "+ dcs.get(i).threshold + ", "+alphas.get(i)+"\n"; 
			
		}
		return s; 
	}
}
