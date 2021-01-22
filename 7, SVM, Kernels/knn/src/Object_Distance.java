
public class Object_Distance {
	int train_object_id; 
	double distance; 
	
	public Object_Distance(int obj_id , double dis){
		train_object_id = obj_id; 
		distance = dis; 
	}
	
	@Override
	public String toString() {
	
		String s = train_object_id + " : " + distance; 
		return s;
	}
}
