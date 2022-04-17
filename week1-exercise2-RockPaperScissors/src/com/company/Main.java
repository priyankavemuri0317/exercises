package com.company;

import java.util.Random;
import java.util.Scanner;


public class Main {

    @SuppressWarnings("MultipleVariablesInDeclaration")
    public static void main(String[] args) {
/**
 * Create a Rock-Paper-Scissors game that allows 2 people to play against each other.
 * Requirements:
 * - use Scanner to get user input
 * - use control-flow statements to determine the winner and print it out
 * - allow user to repeatedly play games
 * - must create at least one class
 * - Make your own Git Repo and paste the link in the Excel sheet.
 * - have the computer randomly pick a an option ("rock", "paper", or "scissors")
 * - Use this link https://www.codegrepper.com/code-examples/java/how+to+select+a+random+element+from+an+array+in+java for assistance with picking a random element from an array
 * Stretch Goals:
 * - allow players to enter their name for a more personalized experience
 * - keep track of score
 * - Extend your game to be tic-tac-toe instead of Rock-Paper-Scissors
 */
        String userAnswer, playAgain;
        int computeranswer;
        int losses = 0, wins = 0, ties = 0;

        //getting input from user
        Scanner myScanner = new Scanner(System.in);
        //Getting input from the computer
        Random myrandom = new Random();


        do {
            System.out.println("Enter Rock,Paper or Scissors: ");
            userAnswer = myScanner.next();
            //this code user enter correct data or not will check it will run until the user give proper data.
            if (!(userAnswer.equals("Rock")) && !userAnswer.equals("Paper") && !userAnswer.equals("Scissors")) {
                System.out.println("You have entered wrong option");
            } else {
                //computer pics randomly 1 to 3 number
                computeranswer = myrandom.nextInt(3) + 1;
                System.out.println("computeranswer " + computeranswer);

                //1 == rock
                //2 == paper
                //3 == scissors
                //any of those cases true then you win any other cases like tie(rock-rock)
                //else you lose

                if (userAnswer.equals("Rock")) {
                    if (computeranswer == 1) {
                        System.out.println("You Tie");
                        ties++;
                    } else if (computeranswer == 2) {
                        System.out.println("You Loose");
                        losses++;
                    } else if (computeranswer == 3) {
                        System.out.println("You Win");
                        wins++;
                    }
                } else if (userAnswer.equals("Paper")) {
                    if (computeranswer == 1) {
                        System.out.println("You Win");
                        wins++;
                    } else if (computeranswer == 2) {
                        System.out.println("You Tie");
                        ties++;
                    } else if (computeranswer == 3) {
                        System.out.println("You Loose");
                        losses++;
                    }
                } else if (userAnswer.equals("Scissors")) {
                    if (computeranswer == 1) {
                        System.out.println("You Loose");
                        losses++;
                    } else if (computeranswer == 2) {
                        System.out.println("You Win");
                        wins++;
                    } else if (computeranswer == 3) {
                        System.out.println("You Tie");
                        ties++;
                    }
                }

                System.out.println("would you like to play again Yes or No: ");
                playAgain = myScanner.next();
                if (playAgain.equals("No")) {
                    break;
                }
            }
        } while (true);

        System.out.println("statistics for game");
        System.out.println("-------------------");
        System.out.println("wins: " + wins);
        System.out.println("Ties: " + ties);
        System.out.println("Losses: " + losses);


    }
}