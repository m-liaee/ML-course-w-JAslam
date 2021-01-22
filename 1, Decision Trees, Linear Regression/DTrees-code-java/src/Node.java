import java.util.ArrayList;


public class Node {
	
/**/
	
 	private int level;  	
	
	boolean is_leaf;
	int label; // if classification 
	float value; // if regression
		
	//array of terminal node;
	ArrayList<Integer> data_indices; 
	float[][] data; 
	
	Feature split_criteria;
	
	Node parent; 
	Node left_child; 
	Node right_child; 
	  
	
	public Node() {
		parent = null; 
		left_child = null; 
		right_child = null; 
	}

	// checked
	public Node(Node p, float[][] d, ArrayList<Integer> d_indices ){
	
		this();
		this.is_leaf = false; 
		this.parent = p; 
		
		this.data_indices = d_indices;
		this.data = d; 
				
		if (this.parent == null)
			setLevel(0); 
		else 
			setLevel(this.parent.getLevel()+1); 
		
	}

	//checked
	public void setLevel(int i) {
		this.level = i;
	}
	
	//checked
	public int getLevel() {
	 	return this.level; 
	}
	
	//checked
	public int getNumPoints() {
		return data_indices.size(); 
		
	}
	
	//checked
	public float getPercentOfPoints(){
		int x = getNumPoints(); 
		int y = data.length;
		
		return (float)x/y * 100; 
		
	}
	
	//checked
	public void setIsLeaf(boolean b){
		this.is_leaf = b; 
	}
	
	//checked
	public boolean CheckIsLeaf(){
		return this.is_leaf; 
	}
	
	
	// checked
	public void setSplit_criteria(Feature f) {
		this.split_criteria = f;
	}

	//checked
	public void SplitNode( Feature f) {
		
			this.setSplit_criteria(f); 
			int len = getNumPoints(); 
			
			ArrayList<Integer> left_points_indices = new ArrayList<Integer>(); 
			ArrayList<Integer> right_points_indices = new ArrayList<Integer>(); 
			
			for (int i = 0 ; i < len ; i++){
				int index = data_indices.get(i); 
				float val = data[index][f.feature_index];
				if ( val <= f.threshold ){
					left_points_indices.add(index); 
				}else {
					right_points_indices.add(index); 
				}
			}
			
			this.left_child = new Node( this, this.data, left_points_indices); 
			
			
			this.right_child = new Node( this, this.data, right_points_indices); 
			 
	}


	//checked
	public float CalcLabel_Value(boolean is_classification , ArrayList<Float> lb_list) {
		
		int len = this.data_indices.size();
		int value_index = this.data[0].length -1; // -1 is so important			
		
		float [] d = new float [len]; 
		for(int i = 0 ; i < len ; i++){
			int index = this.data_indices.get(i); 
			d[i] =  this.data[index][value_index]; 
		}
		
		if (is_classification){
			
			ArrayList<Integer> labels_counter = Util.Counter(d, lb_list); 
			int index = Util.FindMaxByIndex(labels_counter); // assign majority label as label
			return lb_list.get(index); 
			
		}else { // if is evaluation , regression, find mean value
			
			return Util.FindMeanValue(d); 
			
		}
		
	}

	/**/
	public void setValue(float label_value) {
		this.setIsLeaf(true); // error nashe ?
		this.value = label_value; 
		
	}
		
	public void setLabel(float label_value){
		
		this.setIsLeaf(true); 
		this.label = (int) label_value; 
	}
	/**/

	
}
