package Part1;

import java.util.Scanner;

// Kasparas Skruibis
// R00179144

public class PaperRollCuttingBottomUp {
    public static void PaperRollCutting(int length, double[] prices) {
        // array that stores the maximum revenues
        double[] revArr = new double[length + 1];
        // array that stores the first cut
        int[] cutArr = new int[length + 1];
        // sets max revenue of length 0 to 0
        revArr[0] = 0;
        for (int i = 1; i <= length; i++){
            // sets initial revenue to below 0
            double revenue = -1;
            for (int x = 1; x <= i; x++){
                    // checks if the current price + the previous solution is larger than revenue
                    if (revenue < prices[x] + revArr[i - x]){
                        // if it is sets the current revenue to price + the previous solution
                        revenue = prices[x] + revArr[i - x];
                        // sets the first cut
                        cutArr[i] = x;
                    }
            }
            // sets the max price of length i
            revArr[i] = revenue;
        }
        int  i = 1;
        // prints out max price
        System.out.printf("$%.2f", revArr[length]);
        System.out.println();
        // counters for the different length cuts
        int length1Count = 0;
        int length2Count = 0;
        int length3Count = 0;
        int length5Count = 0;
        // while loop to get the different cuts used
        while (length > 0) {
            // sets item to the first cut of the current length
            int item = cutArr[length];
            // if statements to increment the length cut counts
            if (item == 1){
                length1Count++;
            }
            else if (item == 2){
                length2Count++;
            }
            else if (item == 3){
                length3Count++;
            }
            else if (item == 5){
                length5Count++;
            }
            // takes away the first cut from the length
            length -= item;
        }
        // prints all of the length cut counts
        System.out.println("Amount of cuts of length 1: " + length1Count);
        System.out.println("Amount of cuts of length 2: " + length2Count);
        System.out.println("Amount of cuts of length 3: " + length3Count);
        System.out.println("Amount of cuts of length 5: " + length5Count);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter length Of Roll: ");
        int length = sc.nextInt();
        sc.nextLine();
        double[] prices = new double[99999];
        prices[0] = 0;
        prices[1] = 1.2;
        prices[2] = 3;
        prices[3] = 5.8;
        prices[4] = 0;
        prices[5] = 10.1;
        PaperRollCutting(length, prices);
    }
}
