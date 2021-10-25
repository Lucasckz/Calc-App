
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
            System.out.println("Solution:  " + solution);
        }
        catch(Exception e){
            System.out.println("Improper Format...  Please Try Again");
        }
    }

//  Converts the string to a List pf type String
    public static List<String> toList(String in){
        List<String> out = new ArrayList<String>();
        int i = 0;
        int p;
        while (i < in.length()) {
            if (in.charAt(i)==' '){
                i++;
            }
            if ( in.charAt(i)=='(' || in.charAt(i)==')' ){
                out.add(in.substring(i, i+1));
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
                while (i < in.length() && Character.isAlphabetic(in.charAt(i))){
                    i++;
                }
                out.add(in.substring(p, i));
            }
            else if (  (in.charAt(i)=='-' && i == 0) || (in.charAt(i)=='-' && !Character.isDigit(in.charAt(i-1)))  ){
                out.add("-1");
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
        for(int i = 0; i < in.size(); i++){
            //Deals with Paranthe
            k = i;
            if ( in.get(i).equals("(") ){
                int p = 1;
                while(p != 0){
                    k++;
                    if ( in.get(k).equals("(") ){
                        p++;
                    }
                    else if ( in.get(k).equals(")") ) {
                        p--;
                    }
                }
                in.add( i,  String.valueOf(solve(in.subList(i+1, k))));
                for (int j = 0; j <= (k-i); j++) {
                    in.remove(i+1);
                }
            }
            else if(in.get(i).equals("+") || in.get(i).equals("-")){
                return solve(in.subList(0, i)) + solve(in.subList(i+1, in.size()));
            }
            else if (in.get(i).equals("-")) {
                return solve(in.subList(0, i)) - solve(in.subList(i+1, in.size()));
            }
        }
        return mult(in);
    }

    

    public static Double mult(List<String> in){
        //System.out.println(3);
        if (in.size()==1) { 
            return Double.parseDouble(in.get(0));
        }
        else if(in.get(in.size()-2).equals("/"))    {
            return mult(in.subList(0, in.size()-2)) / Double.parseDouble(in.get(in.size()-1)); 
        }
        else if(in.get(in.size()-2).equals("*"))    { 
            return mult(in.subList(0, in.size()-2)) * Double.parseDouble(in.get(in.size()-1)); 
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
            return Double.parseDouble(in.get(0));
        }
    }
}