import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Parser {
	String label_file; 
	String image_file; 
	
	DataInputStream labels; 
	DataInputStream images;
	
	ArrayList<Feature_Values> data_feat_values = new ArrayList<Feature_Values>(); 
	ArrayList<Integer> labels_vals = new ArrayList<Integer>(); 
	
    int numLabels ;
    int numImages ;
    int numRows ;
    int numCols ;
	
	public Parser(String label_f, String image_f) throws IOException{
		label_file = label_f; 
		image_file = image_f; 
		
	    labels = new DataInputStream(new FileInputStream(label_file));
	    images = new DataInputStream(new FileInputStream(image_file));
	    int magicNumber = labels.readInt();
	    if (magicNumber != 2049) {
	      System.err.println("Label file has wrong magic number: " + magicNumber + " (should be 2049)");
	      System.exit(0);
	    }
	    magicNumber = images.readInt();
	    if (magicNumber != 2051) {
	      System.err.println("Image file has wrong magic number: " + magicNumber + " (should be 2051)");
	      System.exit(0);
	    }
	    numLabels = labels.readInt();
	    numImages = images.readInt();
	    numRows = images.readInt();
	    numCols = images.readInt();
	    
	    System.out.println("number of images "+numImages);
	    
	    if (numLabels != numImages) {
	      System.err.println("Image file and label file do not contain the same number of entries.");
	      System.err.println("  Label file contains: " + numLabels);
	      System.err.println("  Image file contains: " + numImages);
	      System.exit(0);
	    }
		
	}

	public void extract_features_labels(ArrayList<Rectanglee> recs) throws IOException{
		
		long start = System.currentTimeMillis();
		 
	    int numLabelsRead = 0;
	    int numImagesRead = 0;
		    
		while (labels.available() > 0 && numLabelsRead < numLabels) {
		    		    
		      int label = labels.readByte();
		      labels_vals.add(label);
		      //System.out.println(label);
		      
		      numLabelsRead++;
		      
		      int[][] image = new int[numCols][numRows];
		      for (int colIdx = 0; colIdx < numCols; colIdx++) {
		        for (int rowIdx = 0; rowIdx < numRows; rowIdx++) {
		          image[colIdx][rowIdx] = images.readUnsignedByte();
		                
		        }  
		      }
		      
		      Feature_Values f_val = Rectanglee.get_images_feature_vals(recs, image);
		      data_feat_values.add(f_val);
		      numImagesRead++;

		     // At this point, 'label' and 'image' agree and you can do whatever you like with them.

		      if (numLabelsRead % 10 == 0) {
		        System.out.print(".");
		      }
		      if ((numLabelsRead % 800) == 0) {
		        System.out.print(" " + numLabelsRead + " / " + numLabels);
		        long end = System.currentTimeMillis();
		        long elapsed = end - start;
		        long minutes = elapsed / (1000 * 60);
		        long seconds = (elapsed / 1000) - (minutes * 60);
		        System.out.println("  " + minutes + " m " + seconds + " s ");
		      }
		    }
		    System.out.println();
		    long end = System.currentTimeMillis();
		    long elapsed = end - start;
		    long minutes = elapsed / (1000 * 60);
		    long seconds = (elapsed / 1000) - (minutes * 60);
		    System.out
		        .println("Read " + numLabelsRead + " samples in " + minutes + " m " + seconds + " s ");
		  }
}

