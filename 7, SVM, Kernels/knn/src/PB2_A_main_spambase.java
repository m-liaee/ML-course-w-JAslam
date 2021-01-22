
public class PB2_A_main_spambase {

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
		
		System.out.println(train_data.length + " : " + train_y.length);
		System.out.println(test_data.length + " : " + test_y.length);
		
		int num_labels = 2; 
		
		KNN knn; 
		double r; // radious  
		double accuracy_rate; 
		
		
		r = 0.5; 
		knn= new KNN(KNN.Euclidian, r, num_labels, train_data, train_y);
		accuracy_rate = knn.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for r = " + r + ",  "+ accuracy_rate);
		
		r = 1; 
		knn= new KNN(KNN.Euclidian, r, num_labels, train_data, train_y);
		accuracy_rate = knn.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for r = " + r + ",  "+ accuracy_rate);
		
		r = 3; 
		knn= new KNN(KNN.Euclidian, r, num_labels, train_data, train_y);
		accuracy_rate = knn.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for r = " + r + ",  "+ accuracy_rate);
		
		r = 5; 
		knn= new KNN(KNN.Euclidian, r, num_labels, train_data, train_y);
		accuracy_rate = knn.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for r = " + r + ",  "+ accuracy_rate);
	}
	
}
