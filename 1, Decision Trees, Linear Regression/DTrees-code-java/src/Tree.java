import java.util.ArrayList;

import org.omg.CORBA.CTX_RESTRICT_SCOPE;


public class Tree {
	
	public Node root;
	public ArrayList<Feature> features_list;
	ArrayList<Float> labels_list; 
	
	private boolean is_classification;
	private boolean has_limited_level; 
	private float value ;  
	
	float[][] data; 
	
	public Tree(float[][] d, ArrayList<Feature> features,
			boolean isClassif, boolean hasLmtLevel, float val) {
		
			this.data =d; 
			this.features_list = features;
			this.is_classification = isClassif;
			this.has_limited_level = hasLmtLevel;
			this.value = val ; 
			
			this.root = null; // root is setup in createTree		
	}

	//checked
	public void setLabelsList(ArrayList<Float> lb_list){
		this.labels_list = lb_list; 
	}
	
	//checked
	public void createTree(ArrayList<Float> lb_list) {
		
			setLabelsList(lb_list); 
		
			ArrayList<Integer> data_indices = new ArrayList<Integer>();
			for ( int i = 0 ; i < data.length ; i++){
				data_indices.add(i); 
			}

			root = new Node(null, this.data, data_indices);
						
			BuildUpTree(root); 
			
	} 
	
	private void BuildUpTree(Node n) {

		if (has_limited_level ){

			if (n.getLevel() >= value){// since we want to stop splitting nodes from here any more. 
				
				//n.setIsLeaf(true);
				
				float label_value = n.CalcLabel_Value(this.is_classification , this.labels_list);
				
				if(this.is_classification){
					n.setLabel((int) label_value); 
				}else{
					n.setValue(label_value); 
				}
			
				return; 
			}
		}else{ // if number of terminal point is reason for stop.  
			float percent = n.getPercentOfPoints();  
			if( percent <= value){ // since we want to stop splitting nodes from here any more. 
				
				//n.setIsLeaf(true);
				
				float label_value = n.CalcLabel_Value(this.is_classification , this.labels_list);
				
				if(this.is_classification){
					n.setLabel((int) label_value); 
				}else{
					n.setValue(label_value); 
				}
				
				return; 
			}
		}
		
		Feature f = FindBestFeature(n);
		
		if (f == null){
			//n.setIsLeaf(true); this 
			float label_value = n.CalcLabel_Value(this.is_classification , this.labels_list);
			
			if(this.is_classification){
				n.setLabel((int) label_value); 
			}else{
				n.setValue(label_value); 
			}
		
			return; 
		}
		
		n.SplitNode(f);
		
		BuildUpTree(n.left_child); 
		BuildUpTree(n.right_child); 

	}
	
	// checked
	public Feature FindBestFeature(Node n) {
		if (is_classification)
			return FindBestFeatureInClassification(n); 
		else 
			return FindBestFeatureInEvaluation(n); 
			  
	}
	//checked
	public Feature FindBestFeatureInEvaluation(Node n) {
		int size = features_list.size();
		
		float max_delta_err = 0; 
		int index = 0; 
		
		for (int i = 0; i < size; i++) {
			Feature f = features_list.get(i); 
			float delta_err = FindDecreaseErr(n,f); 
			
			if (delta_err > max_delta_err){
				max_delta_err = delta_err; 
				index = i; 
			}
		}
		
		if (max_delta_err >0)
			return features_list.get(index); 
		else 
			return null;
	}

	//checked
	public float FindDecreaseErr(Node n, Feature f){
		float err = FindSSE(n); 
		
		ArrayList<Integer>left_indices = new ArrayList<Integer>();
		ArrayList<Integer>right_indices = new ArrayList<Integer>(); 
		
		int len = n.getNumPoints(); // or n.data_indices.size()
		for ( int i = 0 ; i < len ; i++){
			int index = n.data_indices.get(i); 
			if ( data[index][f.feature_index] <= f.threshold ){
				left_indices.add(index); 
			} else {
				right_indices.add(index); 
			}
				
		}
		
		Node l = new Node(null, n.data, left_indices); 
		Node r = new Node(null, n.data, right_indices); 
		
		float p_left = (float)l.getNumPoints()/n.getNumPoints(); 
		float err_left = FindSSE(l);
		
		float p_right = (float)r.getNumPoints()/n.getNumPoints(); 
		float err_right = FindSSE(r); 
		
		float delta_err = err - (err_left * p_left + err_right * p_right);
		return delta_err; 
	}
	
	// checked
	public float FindSSE (Node n){
		
		int len = n.getNumPoints();// or n.data_indices.size(); 
		int m = data[0].length; 
		
		float [] d = new float[len]; 
		
		for (int i = 0; i < len; i++) {
			int index = n.data_indices.get(i); 
			d[i] = data[index][m-1];  
		}
		
		float mean = Util.FindMeanValue(d); 
		
		float sum_square_err = Util.FindSSE(d,mean);
		
		return sum_square_err; 
	}
	
	// checked
	public Feature FindBestFeatureInClassification(Node n){
		int size = features_list.size();
		
		float max_gain = 0 ;
		int f_max_index = 0 ; 
		
		for(int i = 0 ; i < size; i++){
			Feature f = features_list.get(i); 
			float info_gain = FindInfoGain(n,f);
			
			if (info_gain > max_gain){
				max_gain = info_gain; 
				f_max_index = i; 
			}
							
		}
		
		if (max_gain > 0 ){
			return features_list.get(f_max_index);
		}else{
			return null;
		}
	}
	
	//checked
	public float FindInfoGain(Node n , Feature f) {

		float h = FindEntropy(n); // H(Y)
				
		ArrayList<Integer>left_indices = new ArrayList<Integer>();
		ArrayList<Integer>right_indices = new ArrayList<Integer>(); 
		
		int len = n.getNumPoints(); 
		for ( int i = 0 ; i < len ; i++){
			int index = n.data_indices.get(i); 
			if ( data[index][f.feature_index] <= f.threshold ){
				left_indices.add(index); 
			} else {
				right_indices.add(index); 
			}
				
		}
		
		Node l = new Node(null, n.data, left_indices); 
		Node r = new Node(null, n.data, right_indices); 
		
		int num_left_points = left_indices.size(); 
		float p_left = (float)num_left_points / n.getNumPoints(); 
		float h_f_left = FindEntropy(l) * p_left; // H(Y|f<=t) * p_l
		
		int num_right_points = right_indices.size();
		float p_right = (float)num_right_points/n.getNumPoints(); 
		float h_f_right = FindEntropy(r) * p_right; // H(Y|f>t) * p_r
		

		return h - (h_f_left + h_f_right); 
	}
	
	//checked
	public float FindEntropy(Node n){
		
		int num_points = n.getNumPoints() ;
		
		float[] d = new float[num_points];  
				 
		int m = data[0].length; 
		for (int i = 0 ; i < num_points ; i++ ){
			d[i] = data[n.data_indices.get(i)][m -1]; // this m-1 or m has potential for error  
		}
		
		ArrayList<Integer> cntr = Util.Counter(d, labels_list); 
		
		float entropy = 0 ; 
		int lb_list_len = labels_list.size();
		
		for (int i = 0 ; i < lb_list_len ; i++){
			float p = (float) cntr.get(i) / num_points ; // probability of each label
		
			if ( p > 0 ){
				float lg =  ((float) Math.log(1/p) / (float)Math.log( 2.0) );
		
				entropy = entropy + p * lg ;
			}
			
		}
		
		return entropy; 
	}
		
	public float CalcErrorRate(float[][] d , int n , int m){
		
		float sum = 0 ; 
		
		for (int i = 0 ; i < n ; i++){
			float prdc_val = this.FindPredictedValue(d[i]); 
			float primary_val = d[i][m -1]; 
			
			if(is_classification){
				if (primary_val != prdc_val ){
					sum = sum + 1; // number of erros  
				}
				
			}else {
					sum = sum + (float)( Math.pow(primary_val - prdc_val, 2)); 
			}			
		}
		
		if ( is_classification ){
			float error = (float)sum / n;
			//System.out.println("error rate in classification problme is : " + error);
			return error; 
		}else {
			 
			float rms = (float)Math.sqrt((float)sum/n); 
			//System.out.println("error rate (RMS) in evaluation problme is : " + rms);
			return rms; 
		}
	}

	private float FindPredictedValue(float[] data_point) {
		
		Node n = this.root; 
		
		while ( ! n.is_leaf ){ // means while n is not leaf
			
			Feature f = n.split_criteria;
						
			if (data_point[f.feature_index] <= f.threshold ){
				n = n.left_child; 
				
			} else {
				n = n.right_child;
			}
			
		}
		
		if (n.is_leaf){
			if (is_classification){
				return n.label; 
			}else{
				return n.value; 
			}
			
		}else {
			System.err.println("The must be a Bug");
			return 0;
		}
		
	}
}
