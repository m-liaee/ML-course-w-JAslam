
public class main_A {

	public static void main(String[] args) {
		
		FileReading fr = new FileReading();
		fr.ReadFile("perceptronData.txt");
		
		double[][] data = fr.x_data;
		data = Util.add_column_one(data);
		double[] y = fr.y_data; 
		
		int kernel_type; 
		DualPerceptron dp; 
		
		kernel_type = Kernel.Linear; 
		dp = new DualPerceptron(kernel_type); 
		dp.train(data,y);
		
		
		System.out.println("linear resutlt");
		
		for (int i = 0 ; i < y.length; i++){
			double pred_val = dp.predict(data[i]);
			double out_val = dp.output(data[i]);
			System.out.println(i + ":  "+ y[i] + " " + pred_val + " " + out_val);
		}
		 
		
	}
}
