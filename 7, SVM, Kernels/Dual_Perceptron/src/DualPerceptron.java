
public class DualPerceptron {

	
	double [] m_arr; 
	double [] w; 
	
	int train_size; 
	double [][] train_data; 
	double [] train_y; 
	
	int kernel_type; 
	
	
	public DualPerceptron(int k_type) {
		kernel_type = k_type; 
	}
	
	public void train(double[][] data , double [] data_y){
		train_size = data.length; 
		
		train_data = data; 
		train_y = data_y; 
		
		int num_features = data[0].length; 
		w = new double [num_features];
		Util.initial_array(w, 0);
		
		m_arr = new double [train_size];
		Util.initial_array(m_arr, 0); 
		// we don't need initialize it with zero
		
		main_train_routine();
		

	}
	
	public void main_train_routine(){
		
		boolean first_round = true;
		int itr_cntr = 0 ; 
		int num_mistake = 0; 
		
		while ( first_round || num_mistake > 0 ){
			first_round = false; 
			num_mistake = 0 ;
			
			for (int i = 0 ; i < train_size; i++){
				
				double out_i = output(train_data[i]); 
				
				if ( out_i * train_y[i] <= 0 ){
					update_w (train_y[i], train_data[i]);
					m_arr[i] = m_arr[i] + train_y[i];
					
					num_mistake ++; 
				}				
			}
			
			
			itr_cntr ++;
			double acc_rate = get_accuracy(train_data, train_y);
			System.out.println("Iteration #" + itr_cntr
						+ ", total mistakes " + num_mistake + ", accuracy: " + acc_rate);
			//Util.print(w);
			//System.exit(0);
			
			if (itr_cntr > 100 )
				return; 
		}
		
	}
	
	public double output (double[] z){
		
		int len = train_size;
		double sum = 0; 
		
		for (int i = 0 ; i < len ; i++){
			sum = sum + m_arr[i] * Kernel.get_kernel(z, train_data[i], kernel_type); 
		}
		
		return sum; 
	}
	
	public double predict (double[] z ){
		
		double out = output(z);
		double sign = (out > 0)? 1 : -1; 
		return sign; 
	}
	
	public double get_accuracy(double[][] data , double[] data_y){
		int len = data_y.length; 
		
		double sum = 0; 
		
		for (int i = 0 ; i < len; i++){
			double pred_val = predict(data[i]);
			
			if (pred_val == data_y[i])
				sum ++; 
		}
		
		return sum / len; 
	}
	
	
		
	public void update_w(double y_i , double[] x_i){
		int len = w.length; 
		
		for (int j = 0 ; j < len; j++){
			w[j] = w[j] + y_i * x_i[j];
		}
	}
	
	public double get_accuracy_with_w(double[][] data , double[] data_y){
		int len = data_y.length; 
		
		double sum = 0; 
		
		for (int i = 0 ; i < len; i++){
			double pred_val = predict_with_w(data[i]);
			
			if (pred_val == data_y[i])
				sum ++; 
		}
		
		return sum / len; 
	}
	
	public double predict_with_w(double[] z){
		int len = w.length; 
		double sum = 0; 
		
		for (int i = 0 ; i < len; i++){
			sum = sum + w[i] * z[i]; 
		}
		
		if (sum > 0)
			return 1; 
		else return -1; 
	}
}
