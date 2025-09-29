import java.util.Scanner;

import java.io.*;

public class GenericsKbAVLAppPart1{
    AVLTree<dataStore> tree= new AVLTree<dataStore>();

    /**creates tree that stores the data in node form. */

    /**load method is to take the file and use each and every line to construct a binary search tree with the nodes having their data being
     * search tree with the nodes having their data being each line.
     * 
    */   
     public GenericsKbAVLAppPart1(){}

    /**load method is to take the file and use each and every line to construct a binary search tree with the nodes having their data being*/
    
    public void load(){
        int lineCount=0;
        try(Scanner file=new Scanner(new File("GenericsKB.txt"))){//to count the number if lines if file does exist.
           while(file.hasNextLine()){
            lineCount++;
            file.nextLine();
           }  
        }

        catch (Exception e) {
          System.out.println("Could not locate Knowledge base.");//if unable to locate file with given file name.
    
        } try(Scanner file=new Scanner(new File("GenericsKB.txt"))){
            while(file.hasNextLine()){
                    String line=file.nextLine();
                    String[] tempArray=line.split("\t",3);//temporary array to hold each element of a linne after splitting each line element by the tab character.
                    dataStore data= new dataStore(tempArray[0], tempArray[1], tempArray[2]);
                    tree.insert(data);             
                }System.out.println("KnowledgeBase loaded successfully.");//prints if tree is correctly constructed.
            }
        catch(Exception e){
            System.out.println("Could not load knowledgeBase.");
        }//prints if tree is not correctly constructed.
        }

     /**
     * searches and gives back terms along with their statement and confidence  if they are found within the  AVL tree.
     * 
     */
     public void Query(){
        int lineCount=0;
        try(Scanner file=new Scanner(new File("query.txt"))){//to count the number if lines if file does exist.
           while(file.hasNextLine()){
            lineCount++;
            file.nextLine();
           }  
        }
        catch (Exception e) {
          System.out.println("Could not load/locate query file.");//if unable to locate file with queries.
    
        } 
    
        try(Scanner file=new Scanner(new File("query.txt"))){
            for(int i=0;i<lineCount;i++){
                String line=file.nextLine();
                line=line.replace("\t", "");
                dataStore other= new dataStore(line);
                if(tree.find(other)!=null){
                    System.out.println(other.getTerm()+ ", was found: "+(tree.find(other).getData()).getStatement()+"(Confidence score: "+(tree.find(other).getData()).getConfidence()+")");//returns this if queried term was found in the tree.
                }
                else{
                    System.out.println("No match found for: "+ other.getTerm() + ".");//returns this if queried term was not found in tree.
                }
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong.");

       } 

    }
     /**main method which is the one the user will interact with.
    */
    public static void main(String [] args){
        GenericsKbAVLAppPart1 obj=  new GenericsKbAVLAppPart1();
         obj.load();
         obj.Query();
        }
    

}
