
package tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InputHandle {

    public static Scanner sc = new Scanner(System.in);

    public static int getInt(String inputMsg, String errorMsg) {
        while (true) {
            try {
                System.out.print(inputMsg);
                int inputNum = Integer.parseInt(sc.nextLine());
                return inputNum;
            } catch (NumberFormatException e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static int getInt(String inputMsg, String errorMsg, int lowerBound, int upperBound) {
        while (true) {
            try {
                System.out.print(inputMsg);
                int inputNum = Integer.parseInt(sc.nextLine());
                if (lowerBound > upperBound) {
                    upperBound = upperBound + lowerBound - (lowerBound = upperBound);
                }
                if (inputNum < lowerBound || inputNum > upperBound) {
                    throw new Exception();
                }
                return inputNum;
            } catch (NumberFormatException e) {
                System.out.println(errorMsg);
            } catch (Exception e) {
                String msg = String.format("Must be in [%d,%d]", lowerBound, upperBound);
                System.out.println(msg);
            }
        }
    }

    public static int getPositiveInt(String inputMsg, String errorMsg) {
        while (true) {
            try {
                System.out.print(inputMsg);
                int inputNum = Integer.parseInt(sc.nextLine());
                if (inputNum <= 0) {
                    throw new Exception();
                }
                return inputNum;
            } catch (NumberFormatException e) {
                System.out.println(errorMsg);
            } catch (Exception e) {
                System.out.println("Must be greater than 0!");
            }
        }
    }

    public static double getReal(String inputMsg, String errorMsg) {
        while (true) {
            try {
                System.out.print(inputMsg);
                double inputNum = Double.parseDouble(sc.nextLine());
                return inputNum;
            } catch (NumberFormatException e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static double getPositiveReal(String inputMsg, String errorMsg) {
        while (true) {
            try {
                System.out.print(inputMsg);
                double inputNum = Double.parseDouble(sc.nextLine());
                if (inputNum <= 0) {
                    throw new Exception();
                }
                return inputNum;
            } catch (NumberFormatException e) {
                System.out.println(errorMsg);
            } catch (Exception e) {
                System.out.println("Must be greater than 0");
            }
        }
    }

    public static String getString(String inputMsg, String errorMsg) {
        while (true) {
            try {
                System.out.print(inputMsg);
                String inputStr = sc.nextLine();
                if (inputStr.isEmpty() || inputStr == null) {
                    throw new Exception();
                }
                return inputStr;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static String getString(String inputMsg) {
        System.out.print(inputMsg);
        return sc.nextLine();
    }

    public static String getString(String inputMsg, String errorMsg, String regex) {
        while (true) {
            try {
                System.out.print(inputMsg);
                String inputStr = sc.nextLine();
                if (!inputStr.matches(regex)) {
                    throw new Exception();
                }
                return inputStr;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static String getString(String inputMsg, String errorMsg, int minLength, int maxLength) {
        while (true) {
            try {
                System.out.print(inputMsg);
                String inputStr = sc.nextLine();
                int strLen = inputStr.length();
                if (strLen < minLength || strLen > maxLength) {
                    throw new Exception();
                }
                return inputStr;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static boolean getBoolean(String inputMsg) {
        while (true) {
            try {
                System.out.print(inputMsg);
                boolean inputBool = Boolean.parseBoolean(sc.nextLine());
                return inputBool;
            } catch (Exception e) {
                System.out.println("True or False!!");
            }
        }
    }

    public static Date getDate(String inputMsg) {
        while (true) {
            try {
                System.out.print(inputMsg);
                String inputStr = sc.nextLine();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                formatter.setLenient(false);
                return formatter.parse(inputStr);
            } catch (ParseException e) {
                System.out.println("Invalid date: (dd/MM/yyyy)");
            }
        }
    }
}
