    package calculator;


    import java.math.BigInteger;
    import java.util.*;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;

    public class Main {
       public static Map<String, BigInteger> variables = new HashMap<>();
       public static Queue<String> infixToPostfix(String inp) {

           Stack<String> postfixTemp= new Stack<>();
           Queue<String> postfixExp = new ArrayDeque<>();

           String[] input = inp.split("\\s+");

           for (String s: input) {

               if (s.equals("(")) {
                   postfixTemp.push(s);
               } else if (s.matches("-?\\d+")) {
                   postfixExp.add(s);
               } else if (s.equals("*") || s.equals("/")) {
                   if (postfixTemp.isEmpty()) {
                       postfixTemp.push(s);
                   } else {
                       String temp = postfixTemp.peek();
                       if (temp.equals("+") || temp.equals("-") || temp.equals("(")) {
                           postfixTemp.push(s);
                       } else if (temp.equals("/") || temp.equals("*")) {
                           while (!(temp.equals("+") || temp.equals("-") || temp.equals("("))) {
                               postfixExp.add(postfixTemp.pop());
                               if (postfixTemp.isEmpty()) {
                                   break;
                               } else {
                                   temp = postfixTemp.peek();
                               }
                           }
                           postfixTemp.push(s);
                       } else {
                           System.out.println("Invalid expression");
                           return null;
                       }
                   }
               } else if(s.equals("+") || s.equals("-")) {
                   if (postfixTemp.isEmpty()) {
                       postfixTemp.push(s);
                   } else  {
                       String temp = postfixTemp.peek();
                       while (!temp.equals("(")) {
                           postfixExp.add(postfixTemp.pop());
                           if (postfixTemp.isEmpty()) {
                               break;
                           } else {
                               temp = postfixTemp.peek();
                           }
                       }
                       postfixTemp.push(s);
                   }

               } else if (s.equals(")")) {
                   String temp = postfixTemp.peek();
                   while (!temp.equals("(")) {
                       postfixExp.add(postfixTemp.pop());
                       temp = postfixTemp.peek();
                   }
                   postfixTemp.pop();
               } else {
                   System.out.println("Invalid expression");
                   return null;
               }
           }
           while (!postfixTemp.isEmpty()) {
               postfixExp.add(postfixTemp.pop());
           }

           return postfixExp;
       }
       public static BigInteger calculatePostfix(Queue<String> postfixExp) {

           Stack<BigInteger> result = new Stack<>();
           if (postfixExp == null) {
               return null;
           }
           int n = postfixExp.toArray().length;

           for (int i = 0; i < n; i++) {

               String s = postfixExp.poll();
               if (s.matches("-?\\d+")) {
                   result.push(new BigInteger(s));
               } else if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
                 try {  BigInteger a = result.pop();
                   BigInteger b = result.pop();
                   if (s.equals("+")) {
                       result.push(b.add(a));
                   } else if (s.equals("-")) {
                       result.push(b.subtract(a));
                   } else if (s.equals("*")) {
                       result.push(b.multiply(a));
                   } else {
                       result.push(b.divide(a));
                   }
                 } catch (EmptyStackException e) {
                     System.out.println("Invalid expression");

                     return null;
                 }

               } else {
                   System.out.println("Invalid expression");
               //    System.out.println("ssssss");////////////
                   return null;
               }
             //  System.out.println(result + " " + i); //////////////////////////
           }
           //System.out.println(result.peek() + "peek");///////////////
           return result.pop();
       }
        public static String validity(String[] s) {
           // System.out.println(Arrays.toString(s)); /////////////////
            //System.out.println("valid"); //fffffffffffffff
            StringBuilder infixExp = new StringBuilder();

            if (s.length == 2) {
                System.out.println("Invalid expression");
                //System.out.println("hhhh");//     ddddddddddddddd
                return null;
            }

            for (int i = 0; i < s.length; i++) {
                //System.out.println(infixExp); //////////////
                //System.out.println(s[i]); //dddddddddddddddd
                Matcher matcher = Pattern.compile("--").matcher(s[i]);
                s[i] = matcher.replaceAll("+");
                if (s[i].matches("\\++")) {
                    infixExp.append(" + ");



                } else if (s[i].matches("-+") || s[i].matches("-\\s*\\++") || s[i].matches("\\++\\s*-")) {
                    infixExp.append(" - ");


                } else if (s[i].equals("*")) {
                    infixExp.append(" * ");

                } else if (s[i].equals("/")) {
                    infixExp.append(" / ");

                } else if (s[i].equals("(")) {
                    infixExp.append(" ( ");

                } else if (s[i].equals(")")) {
                    infixExp.append(" ) ");

                } else if (s[i].matches("-?\\d+")) {
                    infixExp.append(s[i]);

                } else if (s[i].matches("[a-zA-Z]+")) {
                    if (variables.containsKey(s[i])) {

                        infixExp.append(variables.get(s[i]));
                    } else {
                        System.out.println("Unknown Variable");
                        return null;
                    }
                } else if (s[i].matches("\\s+")) {
                //  System.out.println("ssssssssss"); //hhhhhhhhhhhhh
                    continue;
                } else {
                    System.out.println("Invalid expression");
              //      System.out.println("kkkkkkkkkkkk");//sssssssssssssssssss
                    return null;
                }

            }
            //System.out.println(infixExp.toString().trim().replaceAll("-\\s*\\++" ,"-")); ////////////////////////
            return infixExp.toString().trim().replaceFirst("-\\s*\\++","-");
        }
        public static void assignVariable (String variable) {

            String[] assign = variable.split("\\s*=\\s*");

            if (assign.length > 2) {
                System.out.println("Invalid expression");
            }
            if (assign[0].matches("[a-zA-Z]+")) {

                if (assign[1].matches("[a-zA-Z]+")) {
                    if (variables.containsKey(assign[1])) {
                        variables.put(assign[0], variables.get(assign[1]));
                    } else {
                        System.out.println("Unknown variable");
                        return;
                    }
                } else if (assign[1].matches("-?\\d+")) {
                    variables.put(assign[0], new BigInteger(assign[1]));
                } else {
                    System.out.println("Invalid assignment");
                }
            } else {
                System.out.println("Invalid expression");
                return;

            }
        }
        public static void calculate (String  s) {


            s = s.replaceAll("-\\s*\\+", "-");
            s = s.replaceAll("\\+\\s*-", "-");

            String[] validity = s.split("\\s+");

            if (validity(validity) != null) {
                Queue<String> postfix = infixToPostfix(validity(validity));
                if (postfix != null) {
                    BigInteger result = calculatePostfix(postfix);
                    if (result != null) {
                        System.out.println(result);
                    }
                }
            }
        }


        public static void main(String[] args) {

              Scanner scanner = new Scanner(System.in);
              // put your code here
              String s = scanner.nextLine().trim();
              while (!s.equals("/exit")) {
                  s = s.trim();
                  if (s.matches("\\s*")) {
                      s = scanner.nextLine();
                      continue;
                  }
                  if (s.contains("//") || s.contains("**")) {
                      System.out.println("Invalid expression");
                      s = scanner.nextLine().trim();
                      continue;
                  }

                  s = s.replaceAll("\\(", " ( ");
                  s = s.replaceAll("\\)", " ) ");
                  s = s.replaceAll("\\*", " * ");
                  s = s.replaceAll("/\\W", " / ");


                  s = s.replaceAll("--", " + ");
                  s = s.replaceAll("\\s*-\\s", " - ");

                  s = s.replaceAll("=\\s*-", "= -");
                  s = s.replaceAll("\\S\\+\\S", " + ");
                  //System.out.println(s);////////
                  s = s.replaceAll("\\+\\s+\\+" ," + ");
                  s = s.replaceAll("\\+\\s+\\+" ," + ");
                  s = s.replaceAll("\\+\\s+\\+" ," + ");
                  s.replaceAll("\\s+\\+\\s+", " + ");

                try {


                  if (s.charAt(0) == '/') {

                      if (s.matches("/help")) {
                          System.out.println("To add ao subtract given numbers and variables comprising of latin letters can also be used and they are case sensitive");
                      } else {
                          System.out.println("Unknown command");
                      }
                  } else if (s.contains("=")) {
                      //System.out.println("ff"); /////////////
                      assignVariable(s);
                  } else if (s.matches("[a-zA-Z]+")) {
                      if (variables.containsKey(s)) {
                          System.out.println(variables.get(s));
                      } else {
                          System.out.println("Unknown Variable");
                      }
                  } else if (s.matches("-?\\d+")) {
                      System.out.println(s);
                  } else if (s.matches("\\+\\d+")) {
                      System.out.println(s.substring(1));
                  } else {
                      calculate(s);
                    //  System.out.println(1); //       dddddddddddddddd
                  } } catch (EmptyStackException e) {
                    System.out.println("Invalid expression");
                }
                  s = scanner.nextLine();
              }
              System.out.println("Bye!");



        }
    }
