import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main {
	
	public static void main (String[] args){

		//read train data
		float [][] train_data = FileReading.ReadFile("housing_train.txt");
		
		//size of data matrix 
		int n = train_data.length; 
		int m = train_data[0].length; 

		//extract features
		// m-1 because last column is the label
		ArrayList<Feature> features_list = Feature.ExtractFeatures(train_data, n, m-1);
		//System.out.println("number of feature-threshold" + features_list.size());
		
		//read test data
		float [][] test_data = FileReading.ReadFile("housing_test.txt"); 
		int test_n = test_data.length; 
		int test_m = test_data[0].length; 
		
		System.out.println("\n");
		
		//build tree
		boolean isClassification = false; // if not it is a regression problem 
		boolean hasLimitedLevel = true; // if not its terminal node is limited
		
		/**/ //initial labels list
		ArrayList<Float> labels_list = null;
		
		if (isClassification){
			float [] labels = new float[n]; 			
			for (int i = 0 ; i < n ; i++)
				labels[i]= train_data[i][m-1];
			
			labels_list = Util.FindUniqueVal(labels); 
		}
		/**/
		
		int max_level = 12; 
		for (int k = 0 ; k < max_level ; k++){
			
			float value = (float) k; 
			Tree t = new Tree(train_data, features_list, isClassification , hasLimitedLevel , value);
		
			// if problem is evaluation and is regression Tree, labels list is null
			t.createTree(labels_list);
				
	
			// find train error 
			float train_error = t.CalcErrorRate(train_data, n, m);
		
			// find test error
			float test_error = t.CalcErrorRate(test_data, test_n, test_m);
			
			System.out.println("When the number of level is at most: " + (int)value + " : ");
			System.out.println("train error rate (RMS) is: " + train_error + " and test error rate (RMS) is: " + test_error  );
			System.out.println("");
			
		 /**/
		}
		 
	}
	
	
		

		

	

}
