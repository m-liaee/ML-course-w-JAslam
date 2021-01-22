import java.util.Comparator;


public class Distance_Comprator implements Comparator<Object_Distance>{

	@Override
	public int compare(Object_Distance o1, Object_Distance o2) {
		if ( o1.distance > o2.distance )
			return 1; 
		else if ( o1.distance < o2.distance )
			return -1; 
		else 
			return 0;
	}

	
}
