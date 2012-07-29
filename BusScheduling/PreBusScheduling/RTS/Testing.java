/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
import java.io.*;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Testing {

   public void testingGo(){

      
      System.out.println("Testing Function just started");
      Writer outputResult = null;
      File fileGolden = new File("F:\\eclipse\\MohanWorkspace\\rts_se_Project\\BusLog\\busGolden.doc");
      File fileUser = new File("F:\\eclipse\\MohanWorkspace\\rts_se_Project\\BusLog\\busUserlog.doc");
      File fileResult = new File("F:\\eclipse\\MohanWorkspace\\rts_se_Project\\BusLog\\busResult.doc");
     

     try{
         
     String userLine = null;
     String goldenLine = null;

     FileReader userFileReader = new FileReader(fileUser);
     BufferedReader inUserFile = new BufferedReader(userFileReader);
     try{
     outputResult = new BufferedWriter(new FileWriter(fileResult,true));
     }catch(Exception e1){System.out.println("error while writing to the file");}

     userLine = inUserFile.readLine();
     
          while(userLine != null){

                      System.out.println("Userline to be checked is: "+userLine);
                      outputResult.write("Requested Path is: " + userLine+ "\n");
                      
/*
                      try {
						outputResult.write("Requested Path" + userLine+ "\n");
                      } catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					   }

                      try {
						outputResult.close();
					  } catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					  }
*/
                      FileReader goldenFileReader = new FileReader(fileGolden);
                      BufferedReader inGoldenFile = new BufferedReader(goldenFileReader);
                      goldenLine = inGoldenFile.readLine();

                      while(goldenLine !=null){

                               if(userLine.equals(goldenLine)){
                               System.out.println("Golden Line matching is: "+ goldenLine);

                               goldenLine = inGoldenFile.readLine();
                               userLine = inUserFile.readLine();

                               System.out.println("Userline path is: "+userLine);
                               System.out.println("goldenLine path is: "+goldenLine);

                               outputResult.write("Golden Path: " + goldenLine+ "\n");
                               outputResult.write("Path Calculated: " + userLine+ "\n");

                               //System.out.println("Golden Line is: "+ goldenLine);
                                    if(userLine.equals(goldenLine)){
                                     System.out.println("Result is Success");
                                     outputResult.write("Result is Success"+ "\n\n");
                                     break;
                                    }
                                    else{
                                    System.out.println("Result is Failure");
                                    outputResult.write("Result is Failure"+ "\n\n");
                                    break;
                                    }
                               }
                               else{
                               goldenLine = inGoldenFile.readLine();
                               }
                         
                       }

               userLine = inUserFile.readLine();

               }
                      try {
						outputResult.close();
					  } catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					  }

     }
     catch(FileNotFoundException e){
				System.out.println("Make sure that User file is present");
			}
      catch(IOException e){
				System.out.println("IOException Occured");
			}


   }
}

