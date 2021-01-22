
public class SVM_Param {
	
	public static final int LINEAR = 0;
	public static final int POLY = 1;
	public static final int RBF = 2;
	
	int kernel_type; 
	int degree; 
	double coeff; 
	double C; 
	
	double gamma; 
	double epsilon; 
	
	public void setLinearParam(double c, double g, double eps){
		kernel_type = SVM_Param.LINEAR; 
		
		this.C = c; 
		this.gamma = g; 
		this.epsilon = eps; 
		
	}
	
	public void setPolyParam(double c, double g, double eps, int d ){
		kernel_type = SVM_Param.POLY; 
		
		this.C = c; 
		this.gamma = g; 
		this.epsilon = eps; 
		this.degree = d; 
	}
	
	public void setRBFParam(double c, double g, double eps){
		kernel_type = SVM_Param.RBF; 
		
		this.C = c; 
		this.gamma = g; 
		this.epsilon = eps; 
	}
}
