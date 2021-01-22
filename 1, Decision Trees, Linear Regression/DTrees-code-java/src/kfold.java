import java.util.ArrayList;

public class kfold {

	public static void main(String[] args) {
	
		//read train data
		float[][] data = FileReading.ReadFile("spambase.data");
		System.out.println("\n");
		
		
		int k = 10; 
		float sum_error = 0; 
		
		float level = 7;// number of level of trees
		for (int i = 1 ; i < k+1; i++){
			
			float [][] train = Util.split_train_data(data,i,k);
			float [][] test = Util.split_test_data(data,i,k);
			
			
			//			
			int n = train.length; 
			int m = train[0].length; 
			
			int test_n = test.length;
			int test_m = test[0].length; 
			
			//extract features
			// m-1 because last column is the label
			ArrayList<Feature> features_list = Feature.ExtractFeatures(train, n, m-1);
			//System.out.println("number of feature-threshold" + features_list.size());
			
			//build tree
			boolean isClassification = true; // if not it is a regression problem 
			boolean hasLimitedLevel = true; // if not its terminal node is limited
			
			/**/ //initial labels list
			ArrayList<Float> labels_list = null;
			
			if (isClassification){
				float [] labels = new float[n]; 			
				for (int j = 0 ; j < n ; j++)
					labels[j]= train[j][m-1];
				
				labels_list = Util.FindUniqueVal(labels); 
			}
			
			
			
			Tree t = new Tree(train, features_list, isClassification , hasLimitedLevel , level);
			t.createTree(labels_list);
			
			// find train error 
			float train_error = t.CalcErrorRate(train, n, m);
		
			// find test error
			float test_error = t.CalcErrorRate(test, test_n, test_m);
		
			System.out.println("errors for " + i +" th folder");
			System.out.println("train error rate is: " + train_error + " and test error rate is: " + test_error  );
			System.out.println("");
			sum_error  = sum_error + test_error; 
			
		}
		
		System.out.println("We build tree with no more levels than "+level);
		System.out.println("average test error is : " + sum_error/k);
		
		
		
		
	}	
}
