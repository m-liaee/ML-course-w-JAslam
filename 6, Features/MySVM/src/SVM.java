//public class SVM {
//	
//	SVM_Param svm_parameter;
//	double[] alphas;
//	
//	public SVM(SVM_Param p){
//		svm_parameter = p; 
//	}
//
//	public SVM_Model train(double[][] train_data){
//		int n = train_data.length; 
//		alphas = new double [n];
//		Util.initial_array(alphas, 0);
//		
//		
//		boolean converged = false; 
//		
//		while ( !converged ){
//			double[] two_alphas_indices = choose_best_2_alphas(); 
//			//double[] new_alphas= optimize_w(); 
//			
//			//converged = is_converged(new_alphas, alphas , svm_parameter.epsilon); 
//			
//		}
//		
//		
//		return null; 
//	}
//	
//	
//	
//	public  boolean is_converged(double[] alphas1, double[] alphas2,
//			double epsilon) {
//		int len = alphas1.length; 
//		
//		double maxx = 0; 
//		for (int i = 0 ; i < len ; i++){
//			double temp = alphas1[i] - alphas2[i];
//			temp = Math.abs(temp);
//			if (temp > maxx){
//				maxx = temp ; 
//			}
//		}
//		
//		if (maxx < epsilon)
//			return true;
//		
//		return false;
//	}
//
//	public int predict( SVM_Model model, double[] single_data_f){
//		return 0; 
//	}
//	
//	public double [] choose_best_2_alphas(){
//		return null; 
//	}
//}
