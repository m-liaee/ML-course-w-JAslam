import java.beans.FeatureDescriptor;


public class Platt_SVM {

	SVM_Param svm_p ; 
	int num_train_example;
	int num_features; 
	double[] alphas;  
	
	double[][] train_data; 
	double [] train_y; 
	
	double tolerance = 0.95;
	double b = 0;
	
	double[] w;
	
	int num_iteration = 0; 
	
	double [][] kernel_table; 
	
	
	public Platt_SVM(SVM_Param p , double [][] data_x, double[] data_y){
		
		svm_p = p; 
		train_data = data_x;
		train_y = data_y;
		
		num_train_example = data_y.length; 
		num_features = data_x[0].length; 
		
		tolerance = p.gamma; 
		
		w = new double[num_features];
		Util.initial_array(w, 0);
	}
	
	public double accuracy_routine(double[][] test_data, double[] test_y){
		int len = test_data.length; 
		
		double sum = 0; 
		for (int i = 0 ; i < len; i++){
			double pred_val = svm_predict(test_data[i]); 
			if (pred_val == test_y[i]){
				sum++; 
			}
		}
		
		return sum / len; 
	}
	
	public double [] train_routine(){
		
		kernel_table = new double[num_train_example][num_train_example]; 
		alphas = new double[num_train_example];
		Util.initial_array(alphas, 0);
		num_iteration = 0; 
		
		main_routine(); 
		kernel_table = null; 
		System.gc(); 
		return alphas; 
	}
	
	public void main_routine(){ 
		
		int num_changed = 0 ; 
		boolean examineAll = true; 
		
		while( num_changed > 0 || examineAll ){
			if (num_iteration > 30)
				return ; 
			num_changed = 0 ; 
			if ( examineAll ){
				
				int[] rand_index = Util.randomRange(0, num_train_example);
				for (int i = 0 ; i < num_train_example ; i++){
					int rand_i = rand_index[i];
					num_changed += examine_example(rand_i);
//					if ( i % 100 == 0)
//						System.out.println("first: num of changes " + num_changed);
				}
				System.out.println("first : iter #" + num_iteration++ + " num of changes "+num_changed);
				//System.out.println("first: num of changes " + num_changed);
			}else {
				
				int[] rand_index = Util.randomRange(0, num_train_example);
				for (int i = 0 ; i < num_train_example ; i++){
					
					int rand_i = rand_index[i];
					if ( alphas[rand_i] > 0 && alphas[rand_i] < svm_p.C ){
						num_changed += examine_example(rand_i);
						
//						if ( i % 100 == 0)
//							System.out.println("second: num of changes " + num_changed);
					}
					
				}
				
				System.out.println("second : iter #" + num_iteration++ + " num of changes "+num_changed);
				//System.out.println("second num of changes " + num_changed);
			}
			
			if ( examineAll ){
				//System.out.println("we are here");
				examineAll = false;
				
			}else if (num_changed == 0){
					examineAll = true; 
				
				
			}
//			System.out.println(examineAll);
//			System.out.println(num_changed);
//			System.exit(0); 
		}
		
	}
	
	
	public int examine_example (int data_index){
		
		int index2 = data_index; 
		double y2 = train_y[index2];
		double alpha2 = alphas[data_index];
		
		double pred_val2 = svm_output(index2);//// to be checked 
		double e2 = pred_val2 - y2; 
		
		double r2 = e2 * y2;
		
		if ( (r2 < - tolerance && alpha2 < svm_p.C) || ( r2 > tolerance && alpha2 > 0) ){
			
			if ( Util.get_num_between_value(alphas, 0, svm_p.C) > 1){
				int index1 = second_heuristic(index2 , e2); 
				if ( take_step(index1, index2) )
					return 1; 
			}
			
			int len = num_train_example;
			int[] rand_index = Util.randomRange(0, len); 
			for (int i = 0 ; i < len ; i++){
				
				int index1 = rand_index[i];
				if (alphas[index1] > 0 && alphas[index1] < svm_p.C){
					if(take_step(index1, index2)){
						return 1;
					}
				}
			}
			
			len = num_train_example;
			rand_index = Util.randomRange(0, len);
			for (int i = 0 ; i <  len ; i++){
				int index1 = rand_index[i]; 
				if( take_step(index1, index2)){
					return 1; 
				}
			}
			
		}
		
		return 0 ; 
	}
	
	public int second_heuristic(int index2 , double e2){
		int len = alphas.length;
	
		int index_1 = 0; 
		double max_diff = 0; 
		
		for (int i = 0 ; i < len; i++){
			
			if ( i != index2){
				double temp_y = train_y[i];
				double temp_pred_val = svm_output(i);
				double e1 = temp_pred_val - temp_y; 
				
				double temp_diff = Math.abs(e1 - e2);
				
				if (temp_diff > max_diff){
					max_diff = temp_diff; 
					index_1 = i; 
				}				
			}			
		}
		
		return index_1; 
	}
	
	public double svm_output(int data_index){
		double[] x = train_data[data_index]; 
		return svm_output(x); 
	}
	
	public double svm_output(double[] x){
		/**
		int len = alphas.length;
				
		double sum = 0 ; 
		for (int i = 0 ; i < len; i++){
			if (alphas[i] != 0){
//				if (alphas[i] < 0 )
//					System.out.println("alpha is an error");
				sum = sum + train_y[i] * alphas[i] * dot_product(x, train_data[i]) ; 
			}
		}
		**/
		
		//assuming that kernel is linear
		
		int len = num_features;
		double sum = 0 ; 
		for (int j = 0 ; j < len ; j++){
			sum = sum + w[j]*x[j]; 
		}
		
		sum = sum - b; 
		return sum; 
	}
	
	public double svm_predict(double [] x){
		double temp_output = svm_output(x);
		
		if (temp_output > 0){
			return 1; 
		}else return -1; 
	} 
	
	public boolean take_step(int index1 , int index2){
				
		if (index1 == index2)
			return false; 
		
		double alpha1 = alphas[index1];
		double alpha2 = alphas[index2];
		
		double y1 = train_y[index1];
		double y2 = train_y[index2];
		
		double a1; 
		double a2; 
		
		double e1 = svm_output(index1) - y1;
		double e2 = svm_output(index2) - y2; 
		double s = y1 * y2;
		
		double H = compute_H(y1, y2, alpha1, alpha2);
		double L = compute_L(y1, y2, alpha1, alpha2);
		
		if ( H == L )
			return false;
		
		double k11 = kernel(index1, index1);
		double k12 = kernel(index1, index2); 
		double k22 = kernel(index2, index2);
		
		double eta = k11 + k22 - 2 * k12;
		 
		if (eta > 0){
			
			a2 = alpha2 + y2 * (e1 - e2)/eta;
			
			if ( a2 < L ){
				a2 = L; 
			}else{
				if ( a2 > H){
					a2 = H; 
				}
			}
		}else{
			System.out.println("negetive eta");
			double L_obj = get_obj_function(L, index1, index2, alpha1, alpha2, e1, e2, s);
			double H_obj = get_obj_function(H, index1, index2, alpha1, alpha2, e1, e2, s);
						
			if (L_obj < H_obj - svm_p.epsilon )
				a2 = L;
			else{
				if (L_obj > H_obj - svm_p.epsilon){
					a2 = H; 
				}else{
					a2 = alpha2; 
				}
			}
				
		}
		
		if ( Math.abs(a2 - alpha2) < svm_p.epsilon * (a2 + alpha2 + svm_p.epsilon) )
			return false; 
		
		a1 = alpha1 + s * (alpha2 - a2);
		
		update_threshold(index1, index2, e1, e2 , a1, a2);
		if (svm_p.kernel_type == SVM_Param.LINEAR)
			update_weight(index1, index2, a1, a2);
		
		//update_error_cache();
		
		alphas[index1] = a1; 
		alphas[index2] = a2; 
		
		return true; 
	}
	
	
	
	
	public double kernel (int index1, int index2){
		
		if ( kernel_table[index1][index2] != 0 )
			return kernel_table[index1][index2]; 
		
		double [] x1 = train_data[index1]; 
		double [] x2 = train_data[index2];
		
		double k =  kernel(x1,x2);
		kernel_table[index1][index2] = k; 
		kernel_table[index2][index1] = k; 
		return k; 
		
	}
	
	public double kernel (double[] x1, double[] x2){
		switch (svm_p.kernel_type) {
		
			case SVM_Param.LINEAR: 
				return dot_product(x1, x2);
				
			case SVM_Param.POLY: 
				System.out.println("kernel is not linear");
				double base = svm_p.gamma * dot_product(x1, x2) + svm_p.coeff; 
				double exponent = svm_p.degree; 
				return Math.pow(base, exponent);
				
			case SVM_Param.RBF:
				System.out.println("kernel is not linear");
				return rbf_value(x1, x2); 
				
			default:
			System.out.println("error not correct kernel");
			return 0; 
		}
	}
	
	public double rbf_value (double[] x1, double[] x2){
		int len = x1.length; 
		double sum = 0 ; 
		for (int i = 0 ; i < len ; i++){
			double d = (x1[i] - x2[i]);
			sum += d * d; 
		}
		
		
		return Math.exp(-svm_p.gamma*sum);
	}
	public double dot_product(double[] x1, double[] x2){
		
		double sum = 0 ; 
		for (int j = 0 ; j < num_features ; j++){
			sum = sum + (x1[j] * x2[j]); 
		}
		
		return sum; 
	}
	
	
	public void update_threshold(int index1, int index2 , double E1, double E2, 
														double new_alpha1, double clip_alpha2){
		double y1 = train_y[index1];
		double y2 = train_y[index2];
		
		double alpha1 = alphas[index1]; 
		double alpha2 = alphas[index2];
		
		double b1 = E1 + y1 * (new_alpha1 - alpha1) * kernel(index1, index1) + y2 * (clip_alpha2 - alpha2) * kernel(index1, index2) + b; 
		double b2 = E2 + y1 * (new_alpha1 - alpha1) * kernel(index1, index2) + y2 * (clip_alpha2 - alpha2) * kernel(index2, index2) + b;
		
		if (new_alpha1 > 0 && new_alpha1 < svm_p.C )
			b = b1; 
		else if (clip_alpha2 > 0 && clip_alpha2 < svm_p.C){
			b = b2; 
		}else{
			b = (b1 + b2)/2;
		}
	}
	
	public double compute_H(double y1, double y2, double alpha1, double alpha2){
		double H; 
		if (y1 == y2){
			H = Math.min(svm_p.C, alpha1 + alpha2);
		}else{
			H = Math.min(svm_p.C, svm_p.C + alpha2 - alpha1);
		}
		return H; 
		
	}
	
	public double compute_L(double y1, double y2, double alpha1, double alpha2){
		double L; 
		if (y1 == y2){
			L = Math.max(0, alpha1 + alpha2 - svm_p.C); 
		}else{
			L = Math.max(0, alpha2 - alpha1);
		}
		return L; 

	}
	

	
	public double get_obj_function(double input , int index1, int index2, double alpha1, double alpha2, 
											double E1, double E2, double s){
		double y1 = train_y[index1];
		double y2 = train_y[index2];	
				
		double f1 = y1 * (E1+b) - alpha1 * kernel(index1, index1) - s * alpha2 * kernel(index1, index2);
		double f2 = y2 * (E2+b) - s * alpha1 * kernel(index1, index2) - alpha2 * kernel(index2, index2);
		
		double input1 = alpha1 + s * (alpha2 - input);
		
		
		double obj_val = input1 * f1 + input * f2; 
		obj_val = obj_val + 1/2 * input1 * input1 * kernel(index1, index1); 
		obj_val = obj_val + 1/2 * input * input * kernel(index2, index2);
		obj_val = obj_val + s * input * input1 * kernel(index1, index2); 
		return obj_val; 
	}
	
	public void  update_weight(int index1, int index2 , double a1, double a2){
		 
		
		double[] w_new = new double [num_features];
		double y1 = train_y[index1];
		double y2 = train_y[index2];
		
		double alpha1 = alphas[index1]; 
		double alpha2 = alphas[index2];
		
		double[] x1 = train_data[index1];
		double[] x2 = train_data[index2];
		
		 
		Util.initial_array(w_new, 0); 
		
		for (int j = 0 ; j < num_features ; j++){
				w_new[j] = w[j] + y1 * (a1 - alpha1) * x1[j] + y2 * (a2 -alpha2) * x2[j]; 
		}
		
		for (int j = 0; j < num_features ; j++){
			w[j] = w_new[j];
			
		}
	}

	public double test_routine(double[][] test_data, double[] test_y) {
		
		
		return accuracy_routine(test_data, test_y);
		
	}
}
