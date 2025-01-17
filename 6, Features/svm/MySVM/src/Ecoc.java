import java.util.ArrayList;
import java.util.Random;


public class Ecoc {
	
	double[][] func_table;
	int num_functions; 
	int num_lables;
	int distance; 
	SVM_Param svm_param; 
	
	ArrayList<Platt_SVM> models; // for each function there is one model
		
	public Ecoc(int distance, int num_functions, int num_labels, SVM_Param p) {
		 
		svm_param = p; 
		this.num_functions = num_functions; 
		this.num_lables = num_labels; 
		this.distance = distance; 
		CreateFuncTable(distance, num_functions, num_labels);
		//Util.print(func_table);
	}
	
	public ArrayList<Platt_SVM> Train(double[][] data_val, double[] data_y ){
		models = new ArrayList<Platt_SVM>();  
				
		for (int j = 0 ; j < num_functions; j++){
			
			System.out.println("function # " +j);
			
			double [] func_j = Util.getTableColumn_j(func_table, j);
			double [] alias_y = getAliasY(data_y,func_j);
			alias_y = get_neg_pos_1(alias_y); 
			
			System.out.println("function #"+j+" is training.");
			
			Platt_SVM plat_svm = new Platt_SVM(svm_param, data_val, alias_y);
			plat_svm.train_routine();
			double accuracy_rate = plat_svm.accuracy_routine(data_val, alias_y); 
					
			System.out.println("function #"+j+"'s training is finished with accuracy rate: " + accuracy_rate);
	
			models.add(plat_svm);
			//System.exit(0); 
		}
		return models; 
		
	}

	public void Test(double[][] data_val, double[] data_y , ArrayList<Platt_SVM> models){
		
		int len = data_val.length;

		int sum = 0 ; 
		for (int i = 0 ; i < len ; i++){
			
			int label = PredictLabel(data_val[i], models);
			sum = sum + ((label == data_y[i])? 1:0); 
						
		}
		double accuracy_rate = (double) sum / len; 
		System.out.println("accuracy rate: " + accuracy_rate + ", error rate: " + (1-accuracy_rate));
	}
	
	public int PredictLabel (double[] single_data_point, ArrayList<Platt_SVM> models){
		
		double[] code = new double [num_functions];
		for (int i = 0 ; i < num_functions; i++){
			Platt_SVM m = models.get(i);
			 double temp_pred = m.svm_predict(single_data_point);
			 code [i] = (temp_pred == 1)? 1:0; 
		}

		double min_dist = this.num_functions; 
		int min_index = 0; 
		
		for( int i = 0 ; i < this.num_lables; i++){
			double [] class_i = Util.getTableRow_i(func_table, i);
			double temp_dist = Util.getDistance(code, class_i );
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
	
	public static double [] getAliasY(double[] data_y, double[] function){
		
		// length of functions are the number of classes here is 10
		
		int len = data_y.length; 
		
		double [] alias_y = new double[len];
		
		for (int i = 0 ; i < len ; i++){
			//System.out.println(data_y[i] + " : ");
			int index = (int) data_y[i];
			alias_y[i] = function[index]; 
		}
		
		return alias_y; 
	}
	
	public static double [] get_neg_pos_1(double[] data_y ){
		int len = data_y.length; 
		
		double [] arr = new double [len]; 
		for (int i = 0 ; i < len ; i++){
			arr[i] = (data_y[i] == 1)? 1:-1; 
		}
		
		return arr; 
	}
	
}
