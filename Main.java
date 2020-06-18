package  SmartCalculator;


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void calc(String[] s) {
      try { int out = Integer.parseInt(s[0]);
        if (s.length == 2) {
            System.out.println("Invalid expression");
            return;
        }

        for (int i = 1; i < s.length - 1 ; i += 2) {

            Matcher matcher = Pattern.compile("--").matcher(s[i]);
            s[i] = matcher.replaceAll("+");
            if (s[i].matches("\\++") ) {
                out += Integer.parseInt(s[i + 1]);

            } else if (s[i].matches("-+") || s[i].matches("-\\++") || s[i].matches("\\++-")) {
                out -= Integer.parseInt(s[i + 1]);

            } else {
                System.out.println("Invalid expression");
                return;
            }
        }
          System.out.println(out);
      }
      catch (NumberFormatException e) {
          System.out.println("Invalid expression");
      }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        String s = scanner.nextLine();
        while (!s.equals("/exit")) {
            s = s.trim();
            if (s.matches("\\s*")) {
                s = scanner.nextLine();
                continue;
            }

            String[] n = s.split("\\s+");
            if (s.charAt(0) == '/') {

           if(s.matches("/help")) {
                System.out.println("To add ao subtract given numbers");
            } else   {
               System.out.println("Unknown command");
           }}
            else  {

               calc(n);
            }
            s = scanner.nextLine();
        }
        System.out.println("Bye!");



    }
}
