/*
 * File name: Lab06.java
 * Date:      2014/08/26 21:39
 * Author:    @author
 */

package cz.cvut.fel.pjv;

public class Lab02 {
   //Constants
   private final int MAX = 10;
   private final int MIN = 1;
   private int counter = 1;
   private int numItems = 0;
   private double average = 0;
   private double deviation = 0;
   private double sum = 0;
   private double sumq = 0;
   private double calculateAverage() {
      return (double) sum / numItems;
   }
   //Source: https://stackoverflow.com/questions/57101064/how-can-i-get-standard-deviation-without-using-arrays-in-java
   private double calculateDeviation() {
      double variance = (numItems * sumq - sum * sum)/(numItems * numItems);
      return Math.sqrt(variance);
   }
   private void printLine() {
      average = calculateAverage();
      deviation = calculateDeviation();
      System.out.printf("%2d %.3f %.3f\n", numItems, average, deviation);
   }
   private void calculate(String line) {
      double convertedNum = Double.parseDouble(line);
      if(numItems >= MAX) {
         printLine();
         numItems = 0;
         sum = 0;
         sumq = 0;
      }
      sum += convertedNum;
      sumq += convertedNum * convertedNum;
   }
   private void homework() {
      TextIO textObj = new TextIO();
      while(true) {
         String line = textObj.getLine();
         if(line == "") {
            System.err.println("End of input detected!");
            if(numItems > MIN) {
               printLine();
            }
            break;
         }
         if(textObj.isDouble(line) == false) {
            System.err.println("A number has not been parsed from line " + counter);
         }else {
            calculate(line);
            numItems++;
         }
         counter++;
      }
   }
   public void start(String[] args) {
      homework();
   }
}
