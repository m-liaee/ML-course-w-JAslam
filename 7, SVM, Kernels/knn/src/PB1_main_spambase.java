
public class PB1_main_spambase {
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
		
		KNN knn = new KNN(KNN.Euclidian, 1, 2, train_data, train_y);
		double accuracy_rate = knn.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 1,  " + accuracy_rate);
		
		knn.k_nn = 3;
		accuracy_rate = knn.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 3,  " + accuracy_rate);
		
		knn.k_nn = 7;
		accuracy_rate = knn.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 7,  " + accuracy_rate);
	}
}
