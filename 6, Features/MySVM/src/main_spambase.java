
public class main_spambase {

	public static void main(String[] args){
		
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
		
		//train_data = Util.getNormalizedData(train_data, false, minn, maxx); 
		//test_data = Util.getNormalizedData(test_data, true, minn, maxx);
		
		train_data = Util.get_z_score_normalize(train_data, false, mean, sigma); 
		test_data = Util.get_z_score_normalize(test_data, true, mean, sigma); 
		
		//System.out.println(train_data.length + " " + train_data[0].length + " " + train_y.length);
		//System.out.println(test_data.length + " " + test_data[0].length + " " + test_y.length);
		
		SVM_Param p = new SVM_Param();
		p.setLinearParam(0.01, 0.95, 0.01);
		//p.setLinearParam(100, 0., 0.01);
		
		Platt_SVM platt_svm = new Platt_SVM(p, train_data, train_y);		
		platt_svm.train_routine();
		
		double train_accuracy = platt_svm.test_routine(train_data, train_y);
		System.out.println("train accuracy rate: " + train_accuracy);
		double test_accuracy = platt_svm.test_routine(test_data, test_y);
		System.out.println("test accuracy rate: " + test_accuracy);
		
	}
	

}
