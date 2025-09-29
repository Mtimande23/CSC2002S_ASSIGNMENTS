import java.lang.Comparable;

/**Progarm that creates dataType to be used for nodes in the AVL App
*@author Themba Shongwe
*16 March 2025.
 */
public class dataStore implements Comparable<dataStore>{
    String term;
    String statement;
    String score;
    /**constructor to create dataType objects which will make it easier to access the term,statement and confidence score.
   * @param term the term.
   * @param statement statement accociated with term.
   * @param score how true is the statement.
  */
    public dataStore(String term,String statement,String score){ 
        this.term=term;
        this.statement=statement;
        this.score=score;
    }
    /**to possibly be used to fins searched term(option 3) 
     *@param term the term.
    */
    public dataStore(String term){
        this.term=term;
    }
    /**to possibly be used to fins searched term(option 4)
     * @param term the term.
     * @param statement statement accociated with term.
     */
    public dataStore(String term,String statement){
        this.term=term;
        this.statement=statement;
    }

    /**getter for statement 
       *  @return statement
      */
    public String getStatement(){
        return this.statement;
    }
    /**getter for term
       *  @return term
       */
    public String getTerm(){
        return this.term;
    }
    /**getter for confidence score
       * @return score
       */
    public String getConfidence(){
        return this.score;
    }
    /**
     * to compare statements 
     * @param other given data
     * @return 0 if equal,-1 if less than, 1 if greater than
     */
    public int compareStatement(dataStore other) {
        
        return this.getStatement().compareTo(other.getStatement());
        
    }
    /**
     * to compare terms
     * @param other given data
     * @return 0 if equal,-1 if less than, 1 if greater than
     */
    @Override
    public int compareTo(dataStore other) {
        
        return this.getTerm().compareTo(other.getTerm());
        
    }

}
