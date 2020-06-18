    package calculator;


    import java.util.*;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;

    public class Main {
       public static Map<String, Integer> variables = new HashMap<>();
        public static void calc(String[] s) {



          try {
                int out = Integer.parseInt(s[0]);
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

              if (s.length == 2) {

                  if (s[0].matches("[A-Za-z]+")) {

                      if (s[1].matches("[a-zA-Z]+")) {

                          if (variables.containsKey(s[1])) {

                              variables.put(s[0], variables.get(s[1]));
                          } else {
                              System.out.println("Unknown variable");
                          }
                      } else if (s[1].matches("\\d+")) {

                          variables.put(s[0], Integer.parseInt(s[1]));

                      } else {
                          System.out.println("Invalid assignment");
                      }
                  } else {
                      System.out.println("Invalid identifier");
                  }
              } else {
                  if (s.length > 1 &&!s[1].matches("\\++-*|\\+*-+")){
                      System.out.println("Invalid assignment");
                      return;
                  }
                  Integer out = 0;
                  if (variables.containsKey(s[0])) {
                      out = (Integer) variables.get(s[0]);
                  } else {
                      System.out.println("Unknown variable");
                      return;
                  }
                  for (int i = 1; i < s.length - 1 ; i += 2) {

                      Matcher matcher = Pattern.compile("--").matcher(s[i]);
                      s[i] = matcher.replaceAll("+");
                      if (s[i].matches("\\++") ) {
                          if (s[i +1].matches("[A-Za-z]+")) {
                          if (variables.containsKey(s[i + 1])) {
                              out += (Integer) variables.get(s[i + 1]);
                          } else {
                              System.out.println("Unknown variable");
                              return;
                          }} else if (s[i + 1].matches("\\d+")) {
                              out += Integer.parseInt(s[i + 1]);
                          } else {
                              System.out.println("Invalid expression");
                          }


                      } else if (s[i].matches("-+") || s[i].matches("-\\++") || s[i].matches("\\++-")) {
                          if (s[i +1].matches("[A-Za-z]+")) {
                              if (variables.containsKey(s[i + 1])) {
                                  out -= (Integer) variables.get(s[i + 1]);
                              } else {
                                  System.out.println("Unknown variable");
                                  return;
                              }} else if (s[i + 1].matches("\\d+")) {
                              out -= Integer.parseInt(s[i + 1]);
                          } else {
                              System.out.println("Invalid expression");
                          }


                      } else {
                          System.out.println("Invalid expression");
                          return;
                      }
                  }
                  System.out.println(out);
              }
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

                String[] n = s.split("\\s*=\\s*|\\s+");
                //System.out.println(Arrays.toString(n));

                if (s.charAt(0) == '/') {

               if(s.matches("/help")) {
                    System.out.println("To add ao subtract given numbers and variables comprising of latin letters can also be used and they are case sensitive");
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
