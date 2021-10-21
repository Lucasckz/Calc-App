
import java.util.*;

public class calc{

//  Main method to take in the input and call the solve method.
    public static void main(String[] args){
        
        System.out.println("Please type out your equation: ");

        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();
        keyboard.close();

        //List<String> a = toList(input);
        //for (int i = 0; i < a.size(); i++){
        //    System.out.print( a.get(i) + " ! " );
        //}

        System.out.println("= " + solve(toList(input)));
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
                while (i < in.length() && Character.isDigit(in.charAt(i))){
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
            else {
                out.add(in.substring(i, i+1));
                i++;
            }
        }
        return out;
    }

    public static double solve(List<String> in){
        return 0;
    }

    public static void recurse(String in){
    }

    public static void breakUp(String in){
    }


    //method to take in two Strings and combine them based on the middle String
    public static double combine(String a, String b, String c){
        if (c.equals("")) {
            return Double.parseDouble(a);
        }
        else if (b.equals("")) {
            return Double.parseDouble(a)*Double.parseDouble(b);
        }

        return 0;
    }
}