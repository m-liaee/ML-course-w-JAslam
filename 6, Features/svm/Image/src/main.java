import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class main {
	public static void main(String[] args) throws IOException {
		
		String image_f = "train-images.idx3-ubyte"; 
		String label_f = "train-labels.idx1-ubyte";
		
		String image_f_test = "test-images.idx3-ubyte"; 
		String label_f_test = "test-labels.idx1-ubyte";
		
		
		int num_rectangle = 100; 
		ArrayList<Rectanglee> recs = Rectanglee.getRandomRectangles(num_rectangle);
		System.out.println(num_rectangle + " Rectangles are sampled.");
		
		/**/
		Parser p = new Parser(label_f, image_f);
		p.extract_features_labels(recs);
		
		Parser p_test = new Parser(label_f_test, image_f_test);
		p_test.extract_features_labels(recs);
		
		int percent = 10; 
		ArrayList<Integer> indices = Util.getIndices_somePercent(p.labels_vals, percent);
		ArrayList<Integer> train_labels = Util.getSubArray(indices, p.labels_vals); 
		ArrayList<Feature_Values> train_f_vals = Util.getElements(indices, p.data_feat_values); 
		
		//System.out.println(train_labels.size() + " "+train_f_vals.size() );
		/**/
		/**/
		int num_features = 2 * num_rectangle; 	
		String dcs_file = "dc_stumps.txt"; 
		ArrayList<Decision_Stump> dcs = Decision_Stump.getDecisionStumps(train_f_vals,num_features);
		Decision_Stump.WriteDCstumpsToFile(dcs_file, dcs); 
		//ArrayList<Decision_Stump> dcs = Decision_Stump.ReadDCstumpsFromFile(dcs_file);
		System.out.println("Decision Stumps are created.");
		
		int T = 100; 
		boolean is_random_decision = false;
		
		int num_labels = 10; 
		int num_functions = 50; 
		int distance = 16; 
		
		double [][] train_data = Util.getMatrix(train_f_vals, num_features);
		int [] train_y = Util.getArray(train_labels);
		
		Ecoc ecoc = new Ecoc(distance, num_functions, num_labels);
		ArrayList<Model> models = ecoc.Train(train_data, train_y, dcs, is_random_decision, T);

		System.out.println("train performance");
		ecoc.Test(train_data, train_y, models);
		
		double [][] test_data = Util.getMatrix(p_test.data_feat_values, num_features);
		int [] test_y = Util.getArray(p_test.labels_vals );
		System.out.println("test performance");
		ecoc.Test(test_data, test_y, models);
		
		/**/
	}

}
