/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handin;

import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author jmm4115
 */
public class SlotMachine {
    private Random generator;
    private int tokenCredit;
    private static int houseCredit = 0;
    
    public SlotMachine()
    {
        generator = new Random();
        tokenCredit = 0;
    }
    
    public void topupTokens(int tokens)
    {
        tokenCredit += tokens;
        houseCredit += tokens;
    }
    
    public int cashOutTokens()
    {
        int cashOut = tokenCredit;
        houseCredit -= cashOut;
        tokenCredit = 0;
        
        return cashOut;
    }
    
    public void pullLever()
    {
        pullLever(1);
    }
    
    public void pullLever(int tokenInput)
    {
        if (tokenCredit >= tokenInput)
        {
            tokenCredit -= tokenInput;

            int number1 = generator.nextInt(10);
            int number2 = generator.nextInt(10);
            int number3 = generator.nextInt(10);

            System.out.println("\n$$$$$$$$$$$$$$$$$$$$");
            System.out.println("..PULLIN THE LEVER..");
            System.out.println("$$$$$$$$$$$$$$$$$$$$");
            System.out.println("$$$$:::ROLLED:::$$$$");
//            System.out.println(">>> <" + number1 + ">   <" + number2 + ">   <" + number3 + ">\n");
            System.out.print(">>> ");
            
            try
            {
                Thread.sleep(400);
                System.out.print("<" + number1 + ">   ");
                Thread.sleep(800);
                System.out.print("<" + number2 + ">   ");
                Thread.sleep(1200);
                System.out.println("<" + number3 + ">\n");
                Thread.sleep(400);    
            }
            catch (Exception e)
            {
            }
            
            boolean win = false;

//            Three of a kind:
            if (number1 == number2 && number2 == number3)
            {
                if (number1 == 0)
                {
                    tokenCredit += 500 * tokenInput;
                    System.out.println("SUPER JACKPOT WINNER!!!! You won " + (500 * tokenInput));
                }
                else
                {
                    tokenCredit += 50 * tokenInput;
                    System.out.println("JACKPOT Winner! You got the skills. You won " + (50 * tokenInput));
                }

                win = true;
            }

//            Pair:
            if (win == false)
            {
                if (number1 == number2 || number2 == number3 || number1 == number3)
                {
                    tokenCredit += tokenInput;
                    System.out.println("A pair! You earned a free spin.");
                    win = true;
                }
            }

//            No win:
            if (win == false)
            {
                System.out.println("Nothing for you...");
                System.out.println("Bad luck, try again. Take some rest when you are tired.");
            }
        }
        else
        {
            System.out.println("\nInsufficient balance! 'Top Up' or place smaller bet...");
        }
    }
    
    public int getTokenBalance()
    {
        return tokenCredit;
    }
    
    public static int getHouseCredit()
    {
        return houseCredit;
    }
    
    public static void main(String[] args)
    {   
        Scanner scan = new Scanner(System.in);
        String playerChoice = "YES";

        while (playerChoice.equalsIgnoreCase("YES"))
        {
            SlotMachine game = new SlotMachine();
            System.out.println("You have 0 token. Input amount to top up or [QUIT] to exit");
        
            boolean isPlaying = true;
            boolean isTopUp = true;
            boolean isNotValid = false;
            
            while (isPlaying)
            {
                do
                {
                    System.out.print("> ");
                    playerChoice = scan.nextLine();
                    
                    if (playerChoice.equalsIgnoreCase("TOP UP"))
                    {
                        isTopUp = true;
                        System.out.println("\nEnter amount:");
                    }

//                    Simple input validation when user enters 0 or less.
                    if (!playerChoice.isEmpty())
                    {
                        if ((playerChoice.charAt(0) - '0') <= 0)
                        {
                            System.out.println("\nInput valid number or [TOP UP] or [QUIT]!");
                            isNotValid = true; 
                        }
                        else
                        {
                            isNotValid = false;
                        }
                    }
                    else
                    {
                        if (game.getTokenBalance() == 0)
                        {
                            System.out.println("\nCurrent balance: 0 token. [TOP UP] or [QUIT]!");
                            isNotValid = true;
                            isTopUp = true;
                        }
                    }
                }
                while (playerChoice.equalsIgnoreCase("TOP UP") || isNotValid);

                if (!playerChoice.equalsIgnoreCase("QUIT"))
                {
                    if (isTopUp)
                    {
                        game.topupTokens(Integer.parseInt(playerChoice));
                    }
                    else
                    {
                        if (playerChoice.isEmpty())
                        {
                            game.pullLever();
                        }
                        else
                        {
                            game.pullLever(Integer.parseInt(playerChoice));
                        }
                    }

                    System.out.println("\nYou have " + game.getTokenBalance() + " token" + ((game.getTokenBalance() == 0) ? "" : "s") + " remaining");
                }

                if (playerChoice.equalsIgnoreCase("QUIT"))
                {
                    System.out.println("\n-----------------------------");
                    System.out.println("You have cashed out: " + game.cashOutTokens());
                    System.out.println("Casino house balance: " + SlotMachine.getHouseCredit());
                    System.out.println("-----------------------------");
                    System.out.println("\nChange Slot Machine? [YES] to change OR [NO] to leave casino.");
                    isPlaying = false;
                }

                if (isPlaying)
                {
                    System.out.println("\n$$ [ENTER] to pull lever (Default: 1 token)");
                    System.out.println("$$ Input amount to play");
                    System.out.println("$$ [TOP UP] to refill.");
                    System.out.println("$$ [QUIT] to cashout");
                }

                isTopUp = false;
            }
            
            //how to refactor?
            System.out.print("> ");
            playerChoice = scan.nextLine();
            System.out.println();
        }
    }
}
