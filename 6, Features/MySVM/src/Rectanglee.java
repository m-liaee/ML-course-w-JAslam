import java.util.ArrayList;

public class Rectanglee {
	
	int top_left_x; 
	int top_left_y; 
	int height; 
	int width; 	
	
	public Rectanglee(){
		
	}
	
	public Rectanglee(int x, int y , int w , int h){
		top_left_x = x; 
		top_left_y = y; 
		
		width = w; 
		height = h; 
	}
	
	public static ArrayList<Rectanglee> getRandomRectangles(int sample_num){
		ArrayList<Rectanglee> recs = new ArrayList<Rectanglee>(); 
		
		for (int i = 0 ; i < sample_num; i++){
			Rectanglee rec = Rectanglee.getRandom_Rectanglee();
			recs.add(rec); 
		}
		
		return recs; 
	}
	
	public static Rectanglee getRandom_Rectanglee(){
		int width = Util.get_random_even_number(6,28); 
		int height = 170/width; 
		
		int max_h_offset = 28 - width; 
		int max_v_offset = 28 - height;
		
		Rectanglee rec = new Rectanglee(); 
		
		rec.width = width; 
		rec.height = height; 
		
		
		int h_offset = Util.get_random_number(max_h_offset+1); 
		int v_offset = Util.get_random_number(max_v_offset+1);
		
		rec.top_left_x = h_offset; 
		rec.top_left_y = v_offset;
		
		return rec; 
		
	}
	
	public String toString() {
		String s;
		s = "x: " + top_left_x + ", y:" + top_left_y + ", h:" + height + ", w:" + width + "\n";  
		return s; 
	}

	public static Feature_Values get_images_feature_vals( ArrayList<Rectanglee> recs, int[][] image) {

		int[][] black_table = get_black_table(image); 
		int len = recs.size(); 
		
		Feature_Values single_image_f_vals = new Feature_Values(); 
		for (int i = 0 ; i < len ; i++){
			Rectanglee rec = recs.get(i);
			int v_diff = Rectanglee.get_v_difference(black_table, rec);
			int h_diff = Rectanglee.get_h_difference(black_table, rec);
			
			single_image_f_vals.values.add(v_diff);
			single_image_f_vals.values.add(h_diff);
		}
		
		return single_image_f_vals; 
	}
	
	public static int[][] get_black_table(int [][] image){
		
		int row = image.length; 
		int col = image[0].length; 
				
		int[][] black_table = new int[row][col];
		
		// first row
		black_table[0][0] = (image[0][0] >0)? 1:0; 
		for(int j = 1; j < col ; j++){			
			black_table[0][j] = black_table[0][j-1] + ((image[0][j] >0)? 1:0);    
		}
		
		// other rows 
		for(int i = 1 ; i < row; i++){
			black_table[i][0] = black_table[i-1][0] + ((image[i][0]>0)? 1:0);
			
			for (int j = 1 ; j < col; j++){
				black_table[i][j] = black_table[i-1][j] + black_table[i][j-1] - black_table[i-1][j-1]; 
				black_table[i][j] = black_table[i][j] + ((image[i][j] > 0)? 1:0);
			}
		}
		
		return black_table; 
		
	}
	
	
	
	public static int get_h_difference(int[][] black_table, Rectanglee rec){
		
		int x = rec.top_left_x; 
		int y = rec.top_left_y; 
		
		int w = rec.width; 
		int h = rec.height; 
		
		Rectanglee rec_top = new Rectanglee(x,y,w/2, h ); 
		Rectanglee rec_bottom = new Rectanglee(x+w/2, y, w/2, h); 
		
		int black_top = Rectanglee.get_black(black_table, rec_top);
		int black_bottom = Rectanglee.get_black(black_table, rec_bottom);
		
		return black_top - black_bottom; 
		
		
	}
	
	public static int get_v_difference(int [][] black_table, Rectanglee rec){
		
		int x = rec.top_left_x; 
		int y = rec.top_left_y; 
		
		int w = rec.width; 
		int h = rec.height; 
		
		Rectanglee rec_left = new Rectanglee(x,y,w, h/2 ); 
		Rectanglee rec_right = new Rectanglee(x, y+h/2, w, h/2); 
		
		int black_left = Rectanglee.get_black(black_table, rec_left);
		int black_right = Rectanglee.get_black(black_table, rec_right);
		
		return black_left - black_right; 
				
	}
	
	public static int get_black(int[][] black_table, Rectanglee rec){
		int x_1, y_1; 
		int x_2, y_2;
		
		x_1 = rec.top_left_x; 
		y_1 = rec.top_left_y; 
		
		
		
		x_2 = x_1 + rec.width; 
		y_2 = y_1 + rec.height;
		
		if (x_1 == 0 && y_1 == 0 ){
			return black_table[x_2 -1][y_2-1];
		}
		
		if ( x_1 == 0 ){
			return black_table[x_2 - 1][y_2 -1 ] - black_table[x_2-1][y_1-1]; 
		}
		
		if ( y_1 == 0 ){
			return black_table[x_2 -1][y_2 -1] - black_table[x_1-1][y_2-1]; 
		}
		
		int num_black = black_table[x_2 - 1][y_2 -1] - black_table[x_2 - 1][y_1 - 1] - black_table[x_1 - 1][y_2 - 1] + black_table[x_1 - 1][y_1 - 1]; 
		return num_black; 
	}
	
	
}
