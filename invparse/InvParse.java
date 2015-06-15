/*
 * 
 * is created in Launcher.java
 */

package invparse;

import java.util.*;
import java.io.*;


public class InvParse {
    private String inputname;
    private String outputname;
    private List<String> filter;
        
    public InvParse(String input, String output){
        this.inputname = input;
        this.outputname = output;
    }
    
    public String getInput(){
            return inputname;
    }
    
    public String getOutput(){
        return outputname;
    }
    
    public void setFilter(List<String> newTokens){
        this.filter = new ArrayList<String>();
        for(int i = 0; i < newTokens.size(); i++){
            filter.add(newTokens.get(i).toUpperCase());
        }
    }
    public void setInput(String input){
        this.inputname = input;
    }
    
    public void setOutput(String output){
        this.outputname = output;
    } 
    
    public void run() throws FileNotFoundException{
 
        String token = new String();
        
        int i = 0;
        int skuPos = 0, quaPos = 0;
        
        ArrayList<String> sku = new ArrayList<String>();        //First line of input is 
        Scanner input = new Scanner(new File(inputname));       //read and constant
        String strFirstLine = input.nextLine();                 //variables collected
        String[] tokens = strFirstLine.split(",");              //for SKU and QUANTITY
        							//positions.
        while(i < tokens.length){
            token = tokens[i];
            if (token.equals("sku"))
                skuPos = i;
                         
            if (token.equals("quantity"))
                quaPos = i;
            
            i++;
        }
	
        List<String>  skuList = skuParse(inputname, skuPos);                    //The SKUs are collected on the first pass
        List<Integer> quanList = quanPass(inputname, skuList, skuPos, quaPos);  //The quantities are totalled on the 2nd pass
                                                                                //using the SKU list
        printList(outputname, skuList, quanList);                               //The lists are printed to a CSV
        
	}
    
    public void printList(String outputname, List<String> skuList, List<Integer> quanList) throws FileNotFoundException{
        PrintStream output = new PrintStream(new File(outputname));
        output.println("sku, quantity,");
        for(int i = 0; i < skuList.size(); i++){
            output.println(skuList.get(i) + "," + quanList.get(i) + ",");
        }
     
        output.close();
    }
    
    private boolean filterToken(String token){
        for (int i = 0; i < filter.size(); i++){
            if (token.contains(filter.get(i)))
                return true;
        }
        return false;                 
    }
    
    public List<Integer> quanPass(String inputname, List<String> skuList, int skuPos, int quaPos) throws FileNotFoundException
    {
      List<Integer> quanList = new ArrayList<Integer>(); //Filling the quantity list with 0's
      for(int i = 0; i < skuList.size(); i++){
          quanList.add(0);
      }
      
      String line;
      String[] tokens;
      String token = "0";   
      Scanner input = new Scanner(new File(inputname));
      int quanToken = 0;
      int ite = 0;
      
      input.nextLine(); //skip header line
    
      while(input.hasNextLine()){
         
          line = input.nextLine();
          tokens = line.split(",");
     
          try{
              token = tokens[skuPos].toUpperCase();
          }catch(Exception e){
              System.out.println("2nd Pass - Bad string in SKU line");
              token = "nil";
          }
       
          if (filterToken(token))
              if (skuList.contains(token)){
                    try{
                        quanToken = Integer.parseInt(tokens[quaPos]);
                    }catch(Exception e){
                        System.out.println("Bad quantity in line");
                        quanToken = 0;
                    }
                    try{
                        ite = skuList.indexOf(token);
                        quanList.set(ite, quanList.get(ite) + quanToken);
                    }catch(Exception e){
                        System.out.print("Something is very wrong");
                    }
                    //Checks if it could be a valid SKU, then if it is, then tries to fetch and update the quantity.
                }
        }
      return quanList;
    }
   
    public List<String> skuParse(String inputname, int skuPos) throws FileNotFoundException{
        
        List<String> skuList = new ArrayList<String>();
        
        String line;
        String[] tokens;
        String token = " ";           
        System.out.println("sku,name,quantity,");
   
        Scanner input = new Scanner(new File(inputname));
        
        input.nextLine(); //skip header line
        
        while(input.hasNextLine()){
            
            line = input.nextLine();
            tokens = line.split(",");
     
            try{
                token = tokens[skuPos].toUpperCase();
            }catch(Exception e){
                System.out.println("Bad string in SKU line");
            }
       
            if (filterToken(token))
                if (!skuList.contains(token))
                    skuList.add(token);
        }       
            //this loop collects all unique SKUs that meet the check criteria.
        
        return skuList;
        
    }
}
