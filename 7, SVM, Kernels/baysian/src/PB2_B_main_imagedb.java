
public class PB2_B_main_imagedb {
	
	public static void main(String[] args) {
		
		int percent = 20; 
		int num_rectangle = 100;
		int num_features = 2 * num_rectangle;
		
		int num_classes = 10; 
		
		int train_size_20percnet = 11996 ; 
		int test_size = 10000; 
		
		
		double [][] train_data = FileWriting.ReadDataFromFile("20_percent_train_data.txt", train_size_20percnet , num_features);
		double [] train_y = FileWriting.ReadDataFromFile("20_percent_train_y.txt", train_size_20percnet);
		
		double [][] test_data = FileWriting.ReadDataFromFile("test_data.txt", test_size, num_features);
		double [] test_y = FileWriting.ReadDataFromFile("test_y.txt", test_size);
		
		System.out.println(train_data.length + " " + train_y.length);
		System.out.println(test_data.length + " " + test_y.length);
		
		
		double acc_rate; 
		int kernal_type; 
		Baysian b; 
		

		// -------------- poly2 kernel ---------------------------
		kernal_type = Kernel.Poly_2; 
		b = new Baysian(train_data, train_y, num_classes, kernal_type);
		acc_rate = b.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for polynomial w degree 2 kernel " + acc_rate);
		
		// -------------- gaussian kernel ---------------------------
		kernal_type = Kernel.Gaussian; 
		b = new Baysian(train_data, train_y, num_classes, kernal_type);
		acc_rate = b.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for gaussian kernel " + acc_rate);


		
		
		
		
		
	}

}
