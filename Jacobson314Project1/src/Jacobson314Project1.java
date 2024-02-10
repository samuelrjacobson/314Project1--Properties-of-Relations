/*
Sam Jacobson
COSC 314 Program 1
2023-03-17

This program reads a matrix from text file and determines whether it is reflexive,
antireflexive, symmetric, or antisymmetric and calculates the transitive closure.
It outputs this information to a file.

*/
import java.io.*;
import java.util.*;

public class Jacobson314Project1 {
    
    public static void main(String[] args) {
         Scanner scnr = new Scanner(System.in);
         
         //get matrix filename
         System.out.print("Enter a filename with type extension: ");
         String matrixFile = scnr.nextLine();
                  
         final int NUM_ROWS_AND_COLS = 10;
         int [][] matrix = new int[NUM_ROWS_AND_COLS][NUM_ROWS_AND_COLS];
         
         //read data from file
         try {
        	 FileInputStream fiStream = new FileInputStream(matrixFile);
        	 Scanner fileReader = new Scanner(fiStream);

             //store matrix as 2D array
        	 for(int i = 0; i < NUM_ROWS_AND_COLS; i++){
        		 for(int j = 0; j < NUM_ROWS_AND_COLS; j++){
        			 matrix[i][j] = fileReader.nextInt();
        		 }
        	 }
        	 fileReader.close();
        	 
        	 // open file for writing contents.
 			 File fout = new File(matrixFile + "_output.txt");
 			 FileOutputStream fostream = new FileOutputStream(fout);
 			 BufferedWriter toOutFile = new BufferedWriter(new OutputStreamWriter(fostream));
 			
 			 //print qualities
 	         
 	         toOutFile.write("Reflexive - ");
 	         if (reflexive(matrix, NUM_ROWS_AND_COLS)) toOutFile.write("yes\nAntireflexive - no\n");
 	         else {
 	        	 toOutFile.write("no\n");
 	        	 toOutFile.write("Antireflexive - ");
 	        	 if (antiReflexive(matrix, NUM_ROWS_AND_COLS)) toOutFile.write("yes\n");
 	        	 else toOutFile.write("no\n");
 	         }
 	         
 	         toOutFile.write("Symmetric - ");
 	         if (symmetric(matrix, NUM_ROWS_AND_COLS)) toOutFile.write("yes\n");
 	         else toOutFile.write("no\n");
 	         
 	         toOutFile.write("Antisymmetric - ");
	         if (antiSymmetric(matrix, NUM_ROWS_AND_COLS)) toOutFile.write("yes\n");
	         else toOutFile.write("no\n");
 	         
	         toOutFile.write("Transitive closure:\n");
 	         transitiveClosure(matrix, NUM_ROWS_AND_COLS);
 	         for(int x = 0; x < NUM_ROWS_AND_COLS; x++){
 	        	 for(int y = 0; y < NUM_ROWS_AND_COLS; y++){
 	        		toOutFile.write(matrix[x][y] + " ");
 	             }
 	        	toOutFile.write("\n");
 	         }
 	         System.out.println("Summary information awaits you in file " + matrixFile + "_output.txt");
 	         
 	         //close files
 	         fileReader.close();
 	         toOutFile.close();
         }
    	 catch (FileNotFoundException e) {
    		 e.printStackTrace();
    	 }
         catch (IOException e) {
        	 e.printStackTrace();
         }

    }
    //determines whether matrix is reflexive--if every diagonal value is 1
    public static boolean reflexive(int[][] matrix, int NUM_ROWS_AND_COLS) {
         for(int x = 0; x < NUM_ROWS_AND_COLS; x++){
            for(int y = 0; y < NUM_ROWS_AND_COLS; y++){
               if(matrix[x][x] != 1) return false;
            }
         }
         return true;
    }
    //determines whether matrix is reflexive--if every diagonal value is 0
    public static boolean antiReflexive(int[][] matrix, int NUM_ROWS_AND_COLS) {
         for(int x = 0; x < NUM_ROWS_AND_COLS; x++){
            for(int y = 0; y < NUM_ROWS_AND_COLS; y++){
               if(matrix[x][x] != 0) return false;
            }
         }
         return true;
    }
    //determines whether matrix is symmetric--if every value xRy = yRx
    public static boolean symmetric(int[][] matrix, int NUM_ROWS_AND_COLS) {
         for(int x = 0; x < NUM_ROWS_AND_COLS; x++){
            for(int y = x + 1; y < NUM_ROWS_AND_COLS; y++){
               if(matrix[x][y] != matrix[y][x]) return false;
            }
         }
         return true;
    }
    //determines whether matrix is antisymmetric--if for every value x != y, xRy and yRx are not both true
    public static boolean antiSymmetric(int[][] matrix, int NUM_ROWS_AND_COLS) {
         for(int x = 0; x < NUM_ROWS_AND_COLS; x++){
            for(int y = x + 1; y < NUM_ROWS_AND_COLS; y++){
               if(x != y && matrix[x][y] == 1 && matrix[y][x] == 1) return false;
            }
         }
         return true;
    }
    //calculates the transitive closure of the matrix
    public static void transitiveClosure(int[][] matrix, int NUM_ROWS_AND_COLS){
    	//outer:
    	for(int x = 0; x < NUM_ROWS_AND_COLS; x++){
        	for(int y = 0; y < NUM_ROWS_AND_COLS; y++){
        		if(matrix[x][y] == 1){
        			for(int z = 0; z < NUM_ROWS_AND_COLS; z++){
        				if(matrix[y][z] == 1 && matrix[x][z] == 0){
        					
        					//if (x, y) and (x, z) are 1 and (x, z) is 0, set (x, z) to 1
        					matrix[x][z] = 1;
        		 	        
        					//repeat process until there are no such x, y, z
        					transitiveClosure(matrix, NUM_ROWS_AND_COLS);
        					//break outer;
        				}
        			}
                }
            }
        }
    }
}