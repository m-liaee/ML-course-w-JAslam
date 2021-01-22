import java.awt.Image;
import java.util.ArrayList;


public class test {

	public static void main(String[] args) {
		int[][] image = new int[3][3];
		
		image[0][0] = 0;
		image[0][1] = 12; 
		image[0][2] = 1; 
		
		image[1][0] = 11; 
		image[1][1] = 0; 
		image[1][2] = 2; 
		
		image[2][0] = 1; 
		image[2][1] = 0; 
		image[2][2] = 0;
		
		int [][] black_t = Rectanglee.get_black_table(image);
		
		String s = "";
		for (int i = 0 ; i < 3 ; i++){
			
			for (int j = 0 ; j < 3 ; j++){
				s = s +  black_t[i][j] + ",";
				
			}
			s = s + "\n";
			
		}
		System.out.println(s);

		Rectanglee rec = new Rectanglee(0,0,2,3);
		
		//int black = Rectanglee.get_black(black_t, rec);
		//System.out.println(black );
		
		int black = Rectanglee.get_h_difference(black_t, rec);
		System.out.println(black);
		
	} 
}
