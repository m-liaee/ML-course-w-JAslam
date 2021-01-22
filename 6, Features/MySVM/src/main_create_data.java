
public class main_create_data {
	public static void main(String[] args) {
		
		int train_size = 5996; 
		int test_size = 10000;
		int num_features = 200; 
		
		FileWriting fw = new FileWriting();
		double [][] train_data = fw.ReadDataFromFile("10_percent_train_data.txt", train_size, num_features);
		double [] train_y = fw.ReadDataFromFile("10_percnet_train_y.txt", train_size);
		System.out.println("trian_data is read.");
		
		double [][] test_data = fw.ReadDataFromFile("test_data.txt", test_size, num_features);
		double [] test_y = fw.ReadDataFromFile("test_y.txt", test_size);
		System.out.println("test_data is read.");
		
		fw.WriteToFile_light_format("svm_light_train.txt", train_data, train_y); 
		fw.WriteToFile_light_format("svm_light_test.txt", test_data, test_y); 
		System.out.println("data is written to svm_light format.");
	}
}
