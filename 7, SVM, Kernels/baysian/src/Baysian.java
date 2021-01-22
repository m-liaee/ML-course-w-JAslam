
public class Baysian {
	
	int num_classes;
	
	double [][] train_data; 
	double [] train_y; 
	
	int kernel_type; 
	
	double[] prob_c;  
	
	public Baysian(double[][] data, double[] data_y, int n_classes, int k_type){
		train_data = data; 
		train_y = data_y;
		
		num_classes = n_classes;
		
		kernel_type = k_type; 
		
		initialize_prob_c(); 
	}
	
	
	public void initialize_prob_c(){
		prob_c = new double [num_classes];
		
		int len = train_y.length; 
		for (int i = 0 ; i < len ; i++){
			int label = (int) train_y[i];
			prob_c[label] ++; 
						
		}
		
		for (int k = 0 ; k < num_classes; k++){
			prob_c[k] = prob_c[k]/len; 
		}
	}
	
	public double predict(double[] z){
		
		double [] prob_c_z = new double [num_classes]; 
		
		for (int i = 0 ; i < num_classes ; i++){
			prob_c_z[i] =  get_condition_prob_Ci_z(i, z) ; 
		}
		
		int c_w_high_prob = Util.get_max_by_index(prob_c_z);
		return c_w_high_prob; 
	} 

	
	public double get_condition_prob_Ci_z (int class_index , double [] z ){
		
		double prob_c = this.prob_c[class_index]; 
		double prob_z_c = get_condition_prob_z_Ci(class_index, z);
		
		double prob_z; ///??
		
		double prob_c_z = prob_c * prob_z_c; // / prob_z;
		
		return prob_c_z; 
	} 
	
	public double get_condition_prob_z_Ci (int class_index, double[] z){
		int len = train_y.length; 
		
		int m_c = 0;
		double sum = 0; 
		
		for (int i = 0 ; i < len ; i++){
			if (train_y[i] == class_index){
				sum = sum + Kernel.get_kernel(z, train_data[i], kernel_type);
				m_c ++; 
			}
		}
		
		return sum / m_c; 
	}
	
	public double get_accuracy(double[][] test_data, double[] test_y ){
		int len = test_y.length; 
		double sum = 0 ;
		
		for (int i = 0 ; i < len; i++){
			double pred_val = predict(test_data[i]);
			
			if ( pred_val == test_y[i])
				sum++; 
			
			if ( i % 50 == 49)
				System.out.println(i +  " : " + sum);
		}
		
		return sum /len; 
	}
}
