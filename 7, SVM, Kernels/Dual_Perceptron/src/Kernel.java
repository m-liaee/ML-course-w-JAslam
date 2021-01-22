
public class Kernel {
	
	final static int Euclidian = 1; 
	final static int Gaussian = 2; 
	final static int Cosine = 3; 
	final static int Poly_2 = 4; 
	final static int Linear = 5; 
	
	int kernel_type;
	
	public static double get_kernel(double x[] , double []y, int kernel_type){
		if (kernel_type == Kernel.Gaussian){
			return get_gaussian_kernel(x, y);
		}else if (kernel_type == Kernel.Poly_2){
			return get_poly2_kernel(x, y);
		}else if (kernel_type == Kernel.Linear){
			//System.out.println("here");
			return get_linear_kernel(x,y);
		}else{
			System.out.println(" a problem in get_kernel.");
			return 0; 
		}
			
	}
	
	private static double get_linear_kernel(double[] x, double[] y) {
		return get_dot_product(x, y);
	}

	public static double get_gaussian_kernel(double[] x, double[] y){
		int len = x.length; 
		double sigma = 0.95;
		
		double sum = 0; 
		
		for (int i = 0 ; i < len ; i++){
			double temp = Math.pow((x[i] - y[i]), 2);
			sum = sum + temp ; 
			
		}
		sum = - sum ; 
		
		double sigma_2 = sigma * sigma; 
		double g_value = Math.exp(sum/2 * sigma_2);	
		
		return g_value; 
	}
	
	public static double get_poly2_kernel(double[] x, double[] y){
		int len = x.length; 
		double c = 0; 
		double d = 2;  
		double dot_xy = get_dot_product(x, y);
		double base = dot_xy + c; 
		
		return base * base; // since d is 2 
	}

	public static double get_dot_product(double [] x, double[] y){
		double sum = 0 ; 
		int len = x.length; 
		
		for(int i = 0 ; i < len ; i++){
			sum = sum + x[i] * y[i];
		}
		
		return sum; 
	}
	
}
