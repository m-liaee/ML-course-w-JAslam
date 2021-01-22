import java.util.ArrayList;
import java.util.PriorityQueue;


public class KNN {
	
	final static int Euclidian = 1; 
	final static int Gaussian = 2; 
	final static int Cosine = 3; 
	final static int Poly_2 = 4; 
	
	int kernel_type; 
	int k_nn; 
	double radious;
	int num_classes; 
	
	double [][] train_data; 
	double [] train_y;
	
	boolean fixed_window; 
	
	public KNN(int k_type, int k, int num_labels, double[][] data, double[] y){
		
		kernel_type = k_type; 
		num_classes = num_labels;
		
		k_nn = k; 
		
		train_data = data; 
		train_y = y; 
		
		fixed_window = false; 
	}
	
	
	public KNN(int k_type, double r, int num_labels, double[][] data, double[] y){
		
		kernel_type = k_type; 
		num_classes = num_labels;
		
		radious = r; 
		
		train_data = data; 
		train_y = y; 
		
		fixed_window = true; 
	}
	
	
	public double predict_w_fixed_window(double[] x){
				
		ArrayList<Double> neighbor_labels= new ArrayList<Double>();  
		
		int len = train_y.length; 
		for (int i = 0 ; i < len ; i++){
			double d = get_distance(x, train_data[i]);
			
			if ( d < radious){
				neighbor_labels.add(train_y[i]); 
			}
		}
		
		return take_majority(neighbor_labels); 
		
	}
	
	public double predict_w_fixed_nbr(double[] x){
		int len = train_y.length; 
		
		PriorityQueue<Object_Distance> pqueue = new PriorityQueue<Object_Distance>(1,new Distance_Comprator());  
		
		for (int i = 0 ; i < len ; i++){
			double d = get_distance(x, train_data[i]);
			Object_Distance od = new Object_Distance(i,d);
			pqueue.add(od);
		}
		
		ArrayList<Double> k_neighbor_labels= new ArrayList<Double>();
		
		for (int i = 0 ; i < k_nn ; i++){
			Object_Distance o = pqueue.remove();
			int index = o.train_object_id ; 
			double lable = train_y[index];
			k_neighbor_labels.add(lable);
			
		}
		
		return take_majority(k_neighbor_labels); 
		
	}
	
	public double predict_class(double[] x){
		if (fixed_window){
			//System.out.println("f");
			return predict_w_fixed_window(x);
		}else {
			//System.out.println("nf");
			return predict_w_fixed_nbr(x); 
		}
	}
	
	public double take_majority(ArrayList<Double> k_ngh_labels){
		int labels_counter [] = new int [num_classes];
		
		int len = k_ngh_labels.size();
		
		for (int i = len-1 ; i >= 0 ; i--){
			double temp =  k_ngh_labels.remove(i);
			int class_label = (int) temp ; 
			labels_counter[class_label] ++; 
			
		}
		
		return (double)Util.get_max_by_index(labels_counter);
	}
	public double get_accuracy(double[][] test_data, double[] test_y){
		
		double sum = 0;
		int len = test_y.length;
		
		for (int i = 0 ; i < len; i++){
			if (i % 500 == 499){
				System.out.println(i + " : " + sum);
			}
			double pred_val = predict_class(test_data[i]);
			if (test_y[i] == pred_val){
				sum ++; 
			}
		}
		
		return sum /len;  
		
	}
	
	public double get_distance(double[] x, double y[]){
		
		switch (kernel_type) {

			case KNN.Euclidian:
				return get_eclidian_distance(x, y);
			case KNN.Gaussian: 
				return get_gaussian_distance(x, y);
			case KNN.Cosine:
				return get_cosine_distance(x, y);
			case KNN.Poly_2: 
				return get_poly_2_distance(x, y);
					
			default:
				return get_eclidian_distance(x, y);

		}
		
	}
	
	public double get_eclidian_distance(double[] x, double y[]){
		double sum = 0 ; 
		
		int len = x.length; 
		
		for (int i = 0 ; i < len ; i++){
			double temp = x[i] - y[i];
			temp = temp * temp; 
			sum = sum + temp; 
		}
		
		double sqrt = Math.sqrt(sum);
		return sqrt; 
	}
	
	public double get_gaussian_distance(double[] x, double y[]){
		int len = x.length; 
		double sum = 0; 
		double sigma = 1.00; 
		
		for (int i = 0 ; i < len ; i++){
			double temp = Math.pow((x[i] - y[i]), 2);
			sum = sum + temp ; 
			
		}
		sum = - sum ; 
		
		double sigma_2 = sigma * sigma; 
		double g_value = Math.exp(sum/2 * sigma_2);
		g_value = - g_value; // to work for distance
		return g_value ; 
	}
	
	public static double get_cosine_distance(double[] x, double y[]){
		double dot_x_y = get_dot_product(x, y); 
		double dot_x_x = get_dot_product(x, x);
		double dot_y_y = get_dot_product(y, y);
		
		double cosine_x_y = dot_x_y / (Math.sqrt(dot_x_x) * Math.sqrt(dot_y_y));
				
		return  Math.acos(cosine_x_y); 
	}
	
	public double get_poly_2_distance(double[] x, double y[]){
		double d = 2; 
		double c = 0; 
		
		double dot_xy = get_dot_product(x, y);
		double base = dot_xy + c; 
		
		//double kernel_val = Math.pow(base, d);
		double kernel_val = base * base; // since d is 2 
		double distance = -1 * kernel_val;
		return distance; 
		
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
