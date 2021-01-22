import java.util.ArrayList;


public class PB1_main_image {

	public static void main(String[] args) throws Exception {

		int percent = 20; 
		int num_rectangle = 100;
		int num_features = 2 * num_rectangle;
		
		int num_classes = 10; 
		
		int train_size_20percnet = 11996 ; 
		int test_size = 10000; 
		
		
		/**
		String image_f = "train-images.idx3-ubyte"; 
		String label_f = "train-labels.idx1-ubyte";
		
		String image_f_test = "test-images.idx3-ubyte"; 
		String label_f_test = "test-labels.idx1-ubyte";

		ArrayList<Rectanglee> recs = Rectanglee.getRandomRectangles(num_rectangle);
		System.out.println(num_rectangle + " Rectangles are sampled.");
		
		Parser p = new Parser(label_f, image_f);
		p.extract_features_labels(recs);

		Parser p_test = new Parser(label_f_test, image_f_test);
		p_test.extract_features_labels(recs);
		
		ArrayList<Integer> indices = Util.getIndices_somePercent(p.labels_vals, percent, num_classes);
		ArrayList<Double> train_labels = Util.getSubArray(indices, p.labels_vals); 
		ArrayList<Feature_Values> train_f_vals = Util.getElements(indices, p.data_feat_values);
		
		double [][] train_data = Util.getMatrix(train_f_vals, num_features);
		double [] train_y = Util.getArray(train_labels);
		
		double [][] test_data = Util.getMatrix(p_test.data_feat_values, num_features);
		double [] test_y = Util.getArray(p_test.labels_vals);
		
		double [] mean = new double [num_features]; 
		double [] sigma = new double [num_features];
		
		train_data = Util.get_z_score_normalize(train_data, false, mean, sigma);
		test_data = Util.get_z_score_normalize(test_data, true, mean, sigma);

		System.out.println(train_y.length + " " + train_data.length);
		System.out.println(test_y.length + " " + test_data.length);

		
		FileWriting fw = new FileWriting(); 
		
		fw.WriteDataToFile("20_percent_train_data.txt", train_data); 
		fw.WriteDataToFile("20_percent_train_y.txt", train_y); 
		
		fw.WriteDataToFile("test_data.txt", test_data);
		fw.WriteDataToFile("test_y.txt", test_y);
		/**/
		
		
		/**/
		double [][] train_data = FileWriting.ReadDataFromFile("20_percent_train_data.txt", train_size_20percnet , num_features);
		double [] train_y = FileWriting.ReadDataFromFile("20_percent_train_y.txt", train_size_20percnet);
		
		double [][] test_data = FileWriting.ReadDataFromFile("test_data.txt", test_size, num_features);
		double [] test_y = FileWriting.ReadDataFromFile("test_y.txt", test_size);
		
		System.out.println(train_data.length + " " + train_y.length);
		System.out.println(test_data.length + " " + test_y.length);
		
		//----------------------------------------
		double acc_rate; 
		/**
		System.out.println("Euclidian Distance");
		KNN knn_image_euclidian = new KNN(KNN.Euclidian, 1, 10, train_data, train_y);
		
		acc_rate = knn_image_euclidian.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 1,  " + acc_rate);
		
		knn_image_euclidian.k_nn = 3;
		acc_rate = knn_image_euclidian.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 3,  " + acc_rate);
		
		knn_image_euclidian.k_nn = 7; 
		acc_rate = knn_image_euclidian.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 7,  " + acc_rate);
		/**/
		//----------------------------------------
		/**/
		System.out.println("Cosine Distance");
		KNN knn_image_cosine = new KNN(KNN.Cosine, 1, 10, train_data, train_y);
		
		acc_rate = knn_image_cosine.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 1,  " + acc_rate);
		
		knn_image_cosine.k_nn = 3; 
		acc_rate = knn_image_cosine.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 3,  " + acc_rate);
		
		knn_image_cosine.k_nn = 7; 
		acc_rate = knn_image_cosine.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 7,  " + acc_rate);

		/**/
		//----------------------------------------
		/**/
		System.out.println("Gaussian Kernel");
		KNN knn_image_gaussian = new KNN(KNN.Gaussian, 1, 10, train_data, train_y);
		
		acc_rate = knn_image_gaussian.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 1,  " + acc_rate);
		
		knn_image_gaussian.k_nn = 3; 
		acc_rate = knn_image_gaussian.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 3,  " + acc_rate);
		
		knn_image_gaussian.k_nn = 7; 
		acc_rate = knn_image_gaussian.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 7,  " + acc_rate);
		
		/**/
		//-------------------------------------------

		/**/
		System.out.println("Polynomial degree 2 Kernel");
		KNN knn_image_poly2 = new KNN(KNN.Poly_2, 1, 10, train_data, train_y);
		
		acc_rate = knn_image_poly2.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 1,  " + acc_rate);
		
		knn_image_gaussian.k_nn = 3; 
		acc_rate = knn_image_poly2.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 3,  " + acc_rate);
		
		knn_image_gaussian.k_nn = 7; 
		acc_rate = knn_image_poly2.get_accuracy(test_data, test_y);
		System.out.println("accuracy rate for k = 7,  " + acc_rate);
		/**/
	}
}
