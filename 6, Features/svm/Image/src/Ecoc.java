import java.util.ArrayList;
import java.util.Random;


public class Ecoc {
	
	int[][] func_table;
	int num_functions; 
	int num_lables;
	int distance; 
	
	ArrayList<Model> models; // for each function there is one model
		
	public Ecoc(int distance, int num_functions, int num_labels) {
		this.num_functions = num_functions; 
		this.num_lables = num_labels; 
		this.distance = distance; 
		CreateFuncTable(distance, num_functions, num_labels);
		//Util.print(func_table);
	}
	
	public ArrayList<Model> Train(double[][] data_val, int[] data_y, ArrayList<Decision_Stump> dcs, boolean is_random_decision, int T){
		models = new ArrayList<Model>(); 
				
		for (int j = 0 ; j < num_functions; j++){
			System.out.println("function # " +j);
			
			int [] func_j = Util.getTableColumn_j(func_table, j);
			int [] alias_y = getAliasY(data_y,func_j);
			
			System.out.println("function #"+j+" is training.");
			
			Model m = AdaBoost.Train(data_val, alias_y, dcs, is_random_decision, T); 			
			models.add(m);
			
			System.out.println("function #"+j+"'s training is finished.");
//			System.out.println(m);
			
			
 
		}
		return models; 
		
	}

	public void Test(double[][] data_val, int[] data_y , ArrayList<Model> models){
		int len = data_val.length;

		int sum = 0 ; 
		for (int i = 0 ; i < len ; i++){
			
			int label = PredictLabel(data_val[i], models);
			sum = sum + ((label == data_y[i])? 1:0); 
						
		}
		double accuracy_rate = (double) sum / len; 
		System.out.println("accuracy rate: " + accuracy_rate + ", error rate: " + (1-accuracy_rate));
	}
	
	public int PredictLabel (double[] single_data_point, ArrayList<Model> models){
		int[] code = new int [num_functions];
		for (int i = 0 ; i < num_functions; i++){
			Model m = models.get(i);
			code [i] = AdaBoost.Predict_BasedonModel(m, 0, single_data_point);
			
		}

		int min_dist = this.num_functions; 
		int min_index = 0; 
		
		for( int i = 0 ; i < this.num_lables; i++){
			int [] class_i = Util.getTableRow_i(func_table, i);
			int temp_dist = Util.getDistance(code, class_i );
			//System.out.println("distance "+i+": "+temp_dist);
			if (temp_dist <= min_dist){
				min_dist = temp_dist; 
				min_index = i; 
			}
				
		}
		//Util.print(code);		
		//System.out.println("min dist" + min_dist + " min index" + min_index) ;
		return min_index; 
	}
	
	public void CreateFuncTable(int distance, int num_functions, int num_labels){
		func_table = Util.getTableWithHamingDistance(distance, num_labels, num_functions);		
	}
	
	public static int [] getAliasY(int[] data_y, int[] function){
		
		// length of functions are the number of classes here is 10
		
		int len = data_y.length; 
		
		int [] alias_y = new int[len];
		
		for (int i = 0 ; i < len ; i++){
			//System.out.println(data_y[i] + " : ");
			alias_y[i] = function[data_y[i]]; 
		}
		
		return alias_y; 
	}
	
	
}
