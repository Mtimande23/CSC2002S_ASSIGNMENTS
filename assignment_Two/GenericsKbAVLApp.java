import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.*;
/**
 * This program is used to load the GenericsKB.txt file into an AVL tree and then search for terms in the tree based on queries from a file.
 * It also generates subsets of the original data for testing purposes.
 * 
 * @author Themba Shongwe
 * @date 26 March 2025
 */
public class GenericsKbAVLApp{
    AVLTree<dataStore> tree= new AVLTree<dataStore>();
    

    /**creates tree that stores the data in node form. */
    /**load method is to take the file and use each and every line to construct an AVL tree with the nodes having their data being
     * search tree with the nodes having their data being each line.
    */public GenericsKbAVLApp(){}

    public void load(){
        try (PrintWriter person = new PrintWriter(new FileWriter("resulter.txt", true))) {//to print the  column headings for the operations result file.
            person.println("size,insetCom,searchCom");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        ArrayList<Integer> inArray = new ArrayList<>();
        ArrayList<Integer> seArray = new ArrayList<>();

        int[] subSize={5,15,50,150,500,1500,5000,15000,30000,50000};//n sizes for the subdatasets.

        ArrayList<String> genericsArray = new ArrayList<>();
        int lineCounter=0;
        try (Scanner scanner = new Scanner(new File("GenericsKB.txt"))) {
            while(scanner.hasNextLine()){
                lineCounter++;
                genericsArray.add(scanner.nextLine());  
            } 
        }catch (Exception e) {
        }

        for(int i :subSize){////for each n size in the subSize array.
            int numLines=i;
            tree.clear();//clears the tree for each subdataset of n size.
            tree.opCountInsert=0;//clears the tree for each subdataset of n size.
            tree.opCountSearch=0;//clears the tree for each subdataset of n size.


            System.out.println("Subdataset of size: "+numLines+" is being loaded.");
            System.out.println("----------------------------------------------------------------------------------");
            for(int j=1;j<=10;j++){////for each subdataset of n size, 10 subfiles will be created.
                
              
                String File="SubFile"+j+".txt";
            Collections.shuffle(genericsArray);//shuffle the lines randomly 
            List<String> subFile = genericsArray.subList(0, numLines);
            try (PrintWriter writer = new PrintWriter(new FileWriter(File))) {
                for (String shuffledLine : subFile) {
                    writer.println(shuffledLine);
                }

                try(Scanner file=new Scanner(new File(File))){
                while(file.hasNextLine()){
                        String line=file.nextLine();
                        String[] tempArray=line.split("\t",3);//temporary array to hold each element of a linne after splitting each line element by the tab character.
                        dataStore data= new dataStore(tempArray[0], tempArray[1], tempArray[2]);
                        tree.insert(data);
                    }System.out.println("KnowledgeBase loaded successfully.");//prints if tree is correctly constructed.

                }catch(Exception e){
                        System.out.println("Could not load knowledgeBase.");//prints if tree is not correctly constructed.
                    }
            try (PrintWriter results = new PrintWriter(new FileWriter("queryResults.txt", true))) {

                try(Scanner file=new Scanner(new File("GenericsKB-queries.txt"))){//to count the number if lines if file does exist.
                    while(file.hasNextLine()){
                        String line=file.nextLine();
                        line=line.replace("\t", "");
                        dataStore other= new dataStore(line);
                        if(tree.find(other)!=null){
                            results.println(other.getTerm()+ ", was found: "+(tree.find(other).data).getStatement()+"(Confidence score: "+(tree.find(other).data).getConfidence()+")");//returns this if queried term was found in the tree.
                        }
                        else{
                            results.println("No match found for: "+ other.getTerm() + ".");//returns this if queried term was not found in tree.
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("Something went wrong.");
                }
            }catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());

               
                
            }catch (Exception e) {
                System.out.println("Error writing to file: " + e.getMessage());
        }
    }catch(Exception f){
    }System.out.println("----------------------------------------------------------------------------------");

    //inArray.add(tree.opCountInsert);//adds the number of operations for each insertion to the array.    
    // seArray.add(tree.opCountSearch);//adds the number of operations for each search to the array.

    try (PrintWriter user = new PrintWriter(new FileWriter("resulter.txt", true))) {
        user.println(i+","+tree.opCountInsert+","+tree.opCountSearch);
          
    }
    catch(Exception ex) {
        System.out.println("Something went wrong.");
    }
            }
}
    }

    /**main method which will be used to automate the program.
    */
    public static void main(String[] args){
        GenericsKbAVLApp obj = new GenericsKbAVLApp();
        obj.load();
        
}
}