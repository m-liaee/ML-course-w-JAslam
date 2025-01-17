import java.util.ArrayList;


public class main_image {
	
	public static void main(String[] args) throws Exception {
		
		/**/
		
		int percent = 10; 
		int num_classes = 10;
		
		int distance = 12;
		int num_functions = 25; 
		
		int num_rectangle = 100;
		int num_features = 2 * num_rectangle;
		
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
		
		double [] mean = new double [num_features]; 
		double [] sigma = new double [num_features];
				
		double [][] train_data = Util.getMatrix(train_f_vals, num_features);
		double [] train_y = Util.getArray(train_labels);
		
		train_data = Util.get_z_score_normalize(train_data, false, mean, sigma);

		double [][] test_data = Util.getMatrix(p_test.data_feat_values, num_features);
		double [] test_y = Util.getArray(p_test.labels_vals );
		test_data = Util.get_z_score_normalize(test_data, true, mean, sigma);

		System.out.println(train_y.length + " " + train_data.length);
		System.out.println(test_y.length + " " + test_data.length);
		
		FileWriting fw = new FileWriting(); 
		
		fw.WriteDataToFile("10_percent_train_data.txt", train_data); 
		fw.WriteDataToFile("10_percnet_train_y.txt", train_y); 
		
		fw.WriteDataToFile("test_data.txt", test_data);
		fw.WriteDataToFile("test_y.txt", test_y);
		/**/
		
		int train_size = 5996; 
		int test_size = 10000; 
		
		FileWriting fw = new FileWriting();
		double [][] train_data = fw.ReadDataFromFile("10_percent_train_data.txt", train_size, num_features);
		double [] train_y = fw.ReadDataFromFile("10_percnet_train_y.txt", train_size);
		
		double [][] test_data = fw.ReadDataFromFile("test_data.txt", test_size, num_features);
		double [] test_y = fw.ReadDataFromFile("test_y.txt", test_size);
		
		System.out.println("reading data is finished.");
		System.out.println(train_y.length + " " + train_data.length);
		System.out.println(test_y.length + " " + test_data.length);
		
		/**/
		SVM_Param param = new SVM_Param(); 
		param.setLinearParam(0.25, 0.001, 0.001);		
		
		Ecoc ecoc = new Ecoc( distance, num_functions, num_classes, param);
		ArrayList<Platt_SVM> models = ecoc.Train(train_data, train_y);
		ecoc.Test(train_data, train_y, models);
		ecoc.Test(test_data, test_y, models); 
		/**/
	}
}
