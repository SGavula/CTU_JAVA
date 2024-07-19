package cz.cvut.fel.pjv;

import java.util.Scanner;

public class Lab01 {
   //Constants
   private final int MIN_NUM = 1;
   private final int MAX_NUM = 4;
   private Scanner scanner;
   private int operation;
   private double num1;
   private double num2;
   private int decimalPlaces;
   public void start (String[] args) {
    homework();
   }
   public void homework() {
      boolean correctInput;
      scanner = new Scanner(System.in);
      System.out.println("Vyber operaci (1-soucet, 2-rozdil, 3-soucin, 4-podil):");
      operation = scanner.nextInt();
      correctInput = validateInput();
      if(correctInput == true) {
         doCalculatorLogic();
      }else {
         System.out.println("Chybna volba!");
      }
   }
   public boolean validateInput() {
      boolean isValid = false;
      if(operation >= MIN_NUM && operation <= MAX_NUM) {
         isValid = true;
      }
      return isValid;
   }

   public void doCalculatorLogic() {
      printTextToFirstNum();
      num1 = scanner.nextDouble();
      printTextToSecondNum();
      num2 = scanner.nextDouble();
      if(num2 == 0 && operation == 4) {
         System.out.println("Pokus o deleni nulou!");
         return;
      }
      System.out.println("Zadej pocet desetinnych mist: ");
      decimalPlaces = scanner.nextInt();
      if(decimalPlaces < 0) {
         System.out.println("Chyba - musi byt zadane kladne cislo!");
         return;
      }
      calculateResult();
   }

   public void printTextToFirstNum() {
      switch (operation) {
         case 1:
            System.out.println("Zadej scitanec: ");
            break;
         case 2:
            System.out.println("Zadej mensenec: ");
            break;
         case 3:
            System.out.println("Zadej cinitel: ");
            break;
         default:
            System.out.println("Zadej delenec: ");
            break;
      }
   }

   public void printTextToSecondNum() {
      switch (operation) {
         case 1:
            System.out.println("Zadej scitanec: ");
            break;
         case 2:
            System.out.println("Zadej mensitel: ");
            break;
         case 3:
            System.out.println("Zadej cinitel: ");
            break;
         default:
            System.out.println("Zadej delitel: ");
            break;
      }
   }

   public void calculateResult() {
      double result;
      String formatString = "%." + decimalPlaces + "f";
      switch (operation) {
         case 1:
            result = num1 + num2;
            System.out.printf(formatString + " + " + formatString + " = " + formatString, num1, num2, result);
            break;
         case 2:
            result = num1 - num2;
            System.out.printf(formatString + " - " + formatString + " = " + formatString, num1, num2, result);
            break;
         case 3:
            result = num1 * num2;
            System.out.printf(formatString + " * " + formatString + " = " + formatString, num1, num2, result);
            break;
         default:
            result = num1 / num2;
            System.out.printf(formatString + " / " + formatString + " = " + formatString, num1, num2, result);
            break;
      }
      System.out.printf("\n");
   }
}
