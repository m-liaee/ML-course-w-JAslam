
public class PB2_B_main_spambase {

	public static void main(String[] args) {
		FileReading fr = new FileReading(); 
		fr.ReadFile("spambase.data");
		fr.split_test_train();
		
		double [][] train_data = fr.train_data; 
		double [] train_y = fr.train_y; 
				
		double [][] test_data = fr.test_data; 
		double [] test_y = fr.test_y; 
		
		int m = train_data[0].length; 
		double [] mean = new double [m]; 
		double [] sigma = new double [m];
		
		train_data = Util.get_z_score_normalize(train_data, false, mean, sigma); 
		test_data = Util.get_z_score_normalize(test_data, true, mean, sigma);
		
		Baysian b; 
		int n_classes = 2; 
		int k_type; 
		
		k_type = Kernel.Gaussian; 
		b = new Baysian(train_data, train_y, n_classes, k_type); 
		double acc_rate = b.get_accuracy(test_data, test_y);
		
		System.out.println( "accuracy rate for kernel gaussian is :" + acc_rate );
		
	}
	
	
}
