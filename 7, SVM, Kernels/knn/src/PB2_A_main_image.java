
public class PB2_A_main_image {
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
		
		
		KNN knn_image_cosine; 
		double acc_rate;
		double r; 
		
		System.out.println("Cosine Distance");		
		
		r = 0.2; 
		knn_image_cosine = new KNN(KNN.Cosine, r, num_classes, train_data, train_y);		
		acc_rate = knn_image_cosine.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for r = " + r +" ,  " + acc_rate);
		
		
		r = 0.5; 
		knn_image_cosine = new KNN(KNN.Cosine, r, num_classes, train_data, train_y);		
		acc_rate = knn_image_cosine.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for r = " + r +" ,  " + acc_rate);
		
		r = 1; 
		knn_image_cosine = new KNN(KNN.Cosine, r, num_classes, train_data, train_y);		
		acc_rate = knn_image_cosine.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for r = " + r +" ,  " + acc_rate);
		
		r = 1.2; 
		knn_image_cosine = new KNN(KNN.Cosine, r, num_classes, train_data, train_y);		
		acc_rate = knn_image_cosine.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for r = " + r +" ,  " + acc_rate);
		
		r = 1.57; 
		knn_image_cosine = new KNN(KNN.Cosine, r, num_classes, train_data, train_y);		
		acc_rate = knn_image_cosine.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for r = " + r +" ,  " + acc_rate);
	}
}
