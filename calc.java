
import java.io.IOException;
import java.util.*;

public class calc{

//  Main method to take in the input and call the solve method.
    public static void main(String[] args){
        
        System.out.println("Please type out your equation: ");

        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();
        keyboard.close();

        try{
            Double solution;
            List<String> toke = toList(input);
            solution = solve(toke);

            if ( solution.isInfinite() ) {throw new IOException();}

            System.out.println("Solution:  " + solution);
        }
        catch(Exception e){
            System.out.println("Improper Format...  Please Try Again");
        }
    }

//  Converts the string to a List of type String
    public static List<String> toList(String in){
        List<String> out = new ArrayList<String>();
        int i = 0;
        int p;
        List<String> wordBank = List.of("SIN", "COS", "TAN" , "COT", "ARCSIN", 
                                        "ARCCOS", "ARCTAN", "ARCCTG", "LN", "LOG");
        while (i < in.length()) {
            if (in.charAt(i)==' '){
                i++;
            }
            else if ( Character.isDigit(in.charAt(i)) ){
                p = i;
                while (i < in.length() && ( Character.isDigit(in.charAt(i)) || in.charAt(i)=='.') ){
                    i++;
                }
                out.add(in.substring(p, i));
            }
            else if ( Character.isAlphabetic(in.charAt(i)) ) {
                p = i;
                boolean head = true;
                while (i < in.length() && Character.isAlphabetic(in.charAt(i)) && head){
                    if ( wordBank.contains(in.substring(p, i+1).toUpperCase()) ){
                        out.add(in.substring(p, i+1).toUpperCase());
                        head = false;
                    }
                    i++;
                    if ((i == in.length() || !Character.isAlphabetic(in.charAt(i)))  && head ) {throw new NullPointerException();}
                }
            }
            else if (  (in.charAt(i)=='-' && i == 0) || (in.charAt(i)=='-' && (!Character.isDigit(in.charAt(i-1)) 
                        && !(in.charAt(i-1)== ')' || in.charAt(i-1)== ']' || in.charAt(i-1)== '}' )
                        && !(in.charAt(i+1)== ')' || in.charAt(i+1)== ']' || in.charAt(i+1)== '}' ))) 
                        && i != in.length()-1 ){
                out.add("-1");
                i++;
            }
            else if (in.charAt(i)=='=' && i == in.length()-1) {
                i++;
            }
            else {
                out.add(in.substring(i, i+1));
                i++;
            }
        }
        return out;
    }
    //Solves the given equation
    public static Double solve(List<String> in){

        //Mask
        int k;
        String open;
        String close;
        for(int i = 0; i < in.size(); i++){
            //Deals with Paranthe
            k = i;
            if ( in.get(i).equals("(") || in.get(i).equals("{") || in.get(i).equals("[") ){
                open = in.get(i);
                if (open.equals("(")) {close = ")";}
                else if (open.equals("{")) {close = "}";}
                else {close = "]";}
                List<String> newList = new ArrayList<String>();
                int p = 1;
                while(p > 0){
                    k++;
                    if ( in.get(k).equals(open) ){
                        p++;
                    }
                    else if ( in.get(k).equals(close) ) {
                        p--;
                    }
                    newList.add(in.get(k));
                }
                newList.remove(newList.size()-1);
                int z = newList.size()+2;
                in.add( i,  String.valueOf(solve(newList)));
                for (int j = 0; j < z; j++) {
                    if (in.size()==1) {break;}
                    in.remove(i+1);
                }
            }
            else if(in.get(i).equals("+")){
                return solve(in.subList(0, i)) + solve(in.subList(i+1, in.size()));
            }
            else if (in.get(i).equals("-")) {
                return solve(in.subList(0, i)) - solve(in.subList(i+1, in.size()));
            }
        }
        
        for (int i = 0; i < in.size(); i++) {
            if (in.get(i).equals("^")){
                in.add(i-1, String.valueOf(Math.pow( Double.parseDouble(in.get(i-1)), Double.parseDouble(in.get(i+1))) ) );
                in.remove(i);
                in.remove(i);
            }
        }
        return mult(in);
    }

    

    public static Double mult(List<String> in){
        if (in.size()==1) { 
            return Double.parseDouble(in.get(0));
        }
        else if(in.get(in.size()-2).equals("/"))    {
            return mult(in.subList(0, in.size()-2)) / Double.parseDouble(in.get(in.size()-1)); 
        }
        else if(in.get(in.size()-2).equals("*"))    { 
            return mult(in.subList(0, in.size()-2)) * Double.parseDouble(in.get(in.size()-1)); 
        }
        else if(in.get(in.size()-2).equals("SIN"))  {
            if (in.size()==2) {
               return Math.sin(Double.parseDouble(in.get(in.size()-1)));
            }
            else{
                in.add( in.size()-2, String.valueOf(mult(in.subList(in.size()-2, in.size()))) );
                in.remove(in.size()-1);
                in.remove(in.size()-1);
                return mult(in);
            }
        }
        else if(in.get(in.size()-2).equals("COS"))  {
            if (in.size()==2) {
                return Math.cos(Double.parseDouble(in.get(in.size()-1)));
            }
            else{
                in.add( in.size()-2, String.valueOf(mult(in.subList(in.size()-2, in.size()))) );
                in.remove(in.size()-1);
                in.remove(in.size()-1);
                return mult(in);
            }
        }
        else if(in.get(in.size()-2).equals("TAN"))  {
            if (in.size()==2) {
                return Math.tan(Double.parseDouble(in.get(in.size()-1)));
            }
            else{
                in.add( in.size()-2, String.valueOf(mult(in.subList(in.size()-2, in.size()))) );
                in.remove(in.size()-1);
                in.remove(in.size()-1);
                return mult(in);
            }
        }
        else if(in.get(in.size()-2).equals("COT"))  {
            if (in.size()==2) {
                return 1.0/Math.tan(Double.parseDouble(in.get(in.size()-1)));
            }
            else{
                in.add( in.size()-2, String.valueOf(mult(in.subList(in.size()-2, in.size()))) );
                in.remove(in.size()-1);
                in.remove(in.size()-1);
                return mult(in);
            }
        }
        else if(in.get(in.size()-2).equals("LN"))  {
            if (in.size()==2) {
                return Math.log(Double.parseDouble(in.get(in.size()-1)));
            }
            else{
                in.add( in.size()-2, String.valueOf(mult(in.subList(in.size()-2, in.size()))) );
                in.remove(in.size()-1);
                in.remove(in.size()-1);
                return mult(in);
            }
        }
        else if(in.get(in.size()-2).equals("LOG"))  {
            if (in.size()==2) {
                return 1.0/Math.log10(Double.parseDouble(in.get(in.size()-1)));
            }
            else{
                in.add( in.size()-2, String.valueOf(mult(in.subList(in.size()-2, in.size()))) );
                in.remove(in.size()-1);
                in.remove(in.size()-1);
                return mult(in);
            }
        }
        else if(in.get(in.size()-2).equals("ARCSIN"))  {
            if (in.size()==2) {
               return Math.asin(Double.parseDouble(in.get(in.size()-1)));
            }
            else{
                in.add( in.size()-2, String.valueOf(mult(in.subList(in.size()-2, in.size()))) );
                in.remove(in.size()-1);
                in.remove(in.size()-1);
                return mult(in);
            }
        }
        else if(in.get(in.size()-2).equals("ARCCOS"))  {
            if (in.size()==2) {
               return Math.acos(Double.parseDouble(in.get(in.size()-1)));
            }
            else{
                in.add( in.size()-2, String.valueOf(mult(in.subList(in.size()-2, in.size()))) );
                in.remove(in.size()-1);
                in.remove(in.size()-1);
                return mult(in);
            }
        }
        else if(in.get(in.size()-2).equals("ARCTAN"))  {
            if (in.size()==2) {
               return Math.atan(Double.parseDouble(in.get(in.size()-1)));
            }
            else{
                in.add( in.size()-2, String.valueOf(mult(in.subList(in.size()-2, in.size()))) );
                in.remove(in.size()-1);
                in.remove(in.size()-1);
                return mult(in);
            }
        }
        else if(in.get(in.size()-2).equals("ARCCTG"))  {
            if (in.size()==2) {
               return 1/Math.atan(Double.parseDouble(in.get(in.size()-1)));
            }
            else{
                in.add( in.size()-2, String.valueOf(mult(in.subList(in.size()-2, in.size()))) );
                in.remove(in.size()-1);
                in.remove(in.size()-1);
                return mult(in);
            }
        }
        else if ( Character.isDigit( in.get(in.size()-2).charAt(0) )
                                || ((in.get(in.size()-2).length() > 1) && Character.isDigit(in.get(in.size()-2).charAt(1)) )
                                || ((in.get(in.size()-2).length() > 2) && Character.isDigit(in.get(in.size()-2).charAt(2)) )){
            if (in.size()==2){
                return Double.parseDouble(in.get(0)) * Double.parseDouble(in.get(1));
            }
            else{
                return mult(in.subList(0, in.size()-1)) * Double.parseDouble(in.get(in.size()-1));
            }
        }
        else{
            throw new NullPointerException();
        }
    }
}