package com.mycompany.turnbasedgame;

import java.util.*;

public class TurnBasedGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<Integer> jinguMastery = new Stack<>();
        Stack<Integer> lastHP = new Stack<>();
        Stack<Integer> invulnerableTurns = new Stack<>();
        Random rand = new Random();

        String[] skills = {"Meditate", "Za Warudo"};

        int gameTimer = 2;
        int lowHpCounter = 0;
        int playerHP = 100;
        int botHP = 500;
        int playerMP = 100;
        int maxMP = 100;
        int manaGain = 20;
        int playerDmg = 10;
        int botDmg = 5;
        int jinguStacks = 0;
        int jinguMaxStacks = 4;
        int jinguBonusDmg = 10;
        int jinguHeal = 15;
        int jinguActiveTurns = 0;
        int timeStopCounter = 0;
        int zaWarudoCooldown = 0;
        int damageReturned = botDmg / 2;
        int damageBlocked = botDmg / 2;
        playerMP = Math.min(playerMP + manaGain, maxMP);

        if (isOddorEven(gameTimer)) {
            System.out.println("------------------------------");
            System.out.println("You encountered an enemy!");
            System.out.println("Player HP: " + playerHP);
            System.out.println("Player MP: " + playerMP);
            System.out.println("Bot HP: " + botHP);
            System.out.println("------------------------------");

            while (playerHP > 0 && botHP > -1) {
                System.out.println("------------------------------");
                System.out.println("Jingu Mastery Stacks: " + jinguStacks);
                System.out.println("What would you like to do?");
                System.out.println(">> Attack");
                System.out.println(">> " + skills[0]);
                System.out.println(">> " + skills[1] + " (Costs 50 MP)");
                System.out.println(">> Skip Turn");
                System.out.println("------------------------------");

                String in = scanner.nextLine();

                if (in.equalsIgnoreCase("attack")) {
                    if (lowHpCounter > 0) {
                        System.out.println("The enemy is resilient! No damage taken.");
                        invulnerableTurns.push(botHP);
                        lowHpCounter--;
                    } else {
                        lastHP.push(botHP);
                        botHP -= playerDmg;

                        if (jinguActiveTurns == 0) {
                            jinguStacks++;
                        }

                        System.out.println("------------------------------");
                        System.out.println("You dealt " + playerDmg + " damage to the enemy.");

                        if (jinguStacks >= jinguMaxStacks) {
                            System.out.println("Jingu Mastery activated! Bonus effect will last for 4 turns!");
                            jinguActiveTurns = 4;
                            jinguStacks = 0; // Reset stacks AFTER activation
                        }

                        if (jinguActiveTurns > 0) {
                            System.out.println("Jingu Mastery is active! You deal " + jinguBonusDmg + " bonus damage!");
                            botHP -= jinguBonusDmg;
                            System.out.println("You lifesteal " + jinguHeal + " HP.");
                            playerHP += jinguHeal;
                            jinguActiveTurns--;
                        }

                        System.out.println("Player HP: " + playerHP);
                        System.out.println("Player MP: " + playerMP);
                        System.out.println("Bot HP: " + botHP);
                        System.out.println("------------------------------");
                    }
                } else if (in.equalsIgnoreCase("meditate")) {
                    System.out.println("------------------------------");
                    System.out.println("You entered a meditative state!");
                    System.out.println(">> 50% Damage Block Active.");
                    System.out.println(">> " + damageReturned + " damage will be reflected.");
                    System.out.println(">> Regenerated " + manaGain + " MP.");

                    botHP -= damageReturned;
                    playerHP -= damageBlocked;

                    System.out.println("Player HP: " + playerHP);
                    System.out.println("Player MP: " + playerMP);
                    System.out.println("Bot HP: " + botHP);
                    System.out.println("------------------------------");

                } else if (in.equalsIgnoreCase("za warudo")) {
                    if (zaWarudoCooldown == 0 && playerMP >= 50) {
                        timeStopCounter = 3;
                        zaWarudoCooldown = 10;
                        System.out.println("------------------------------");
                        System.out.println("You stopped time.");
                        lastHP.push(botHP);
                        playerMP -= 10;
                    } else if (zaWarudoCooldown > 0) {
                        System.out.println("------------------------------");
                        System.out.println("Za Warudo is still on cooldown for " + zaWarudoCooldown + " turns.");
                    } else {
                        System.out.println("------------------------------");
                        System.out.println("Not enough MP to use ZA WARUDO.");
                    }
                } else if (in.equalsIgnoreCase("skip")) {
                    System.out.println("------------------------------");
                    System.out.println("You skipped your turn.");
                    System.out.println("The bot attacks and deals " + botDmg + " damage!");
                    playerHP -= botDmg;
                    System.out.println("Player HP: " + playerHP);
                    System.out.println("Player MP: " + playerMP);
                    System.out.println("Bot HP: " + botHP);
                    System.out.println("------------------------------");
                }

                if (botHP <= 0) {
                    System.out.println("------------------------------");
                    System.out.println("You defeated the enemy!");
                    System.out.println("Player HP: " + playerHP);
                    System.out.println("Player MP: " + playerMP);
                    System.out.println("Bot HP: " + botHP);
                    System.out.println("------------------------------");
                    break;
                }
                if (timeStopCounter > 0) {
                    timeStopCounter--; // 
                    System.out.println("------------------------------");
                    System.out.println("Time's stopped. " + timeStopCounter + " second Has passed.");
                    System.out.println("Player HP: " + playerHP);
                    System.out.println("Player MP: " + playerMP);
                    System.out.println("Bot HP: " + botHP);
                    System.out.println("------------------------------");
                } else {
                    int botAction = rand.nextInt(1);
                    if (botAction == 0) {
                        System.out.println("------------------------------");
                        System.out.println("The bot attacks and deals " + botDmg + " damage!");
                        playerHP -= botDmg;
                        System.out.println("Player HP: " + playerHP);
                        System.out.println("Player MP: " + playerMP);
                        System.out.println("Bot HP: " + botHP);
                        System.out.println("------------------------------");
                    }
                }

                if (playerHP <= 0) {
                    System.out.println("------------------------------");
                    System.out.println("You have been defeated...");
                    System.out.println("Player HP: " + playerHP);
                    System.out.println("Bot HP: " + botHP);
                    System.out.println("------------------------------");
                    break;
                }
                if (zaWarudoCooldown > 0) {
                    zaWarudoCooldown--;
                }
                if (botHP <= 20 && lowHpCounter == 0) {
                    lowHpCounter = 5;
                    System.out.println("The enemy has entered an invulnerable state for 5 turns!");
                }
                if (lowHpCounter == 0 && !invulnerableTurns.isEmpty()) {
                    botHP = invulnerableTurns.pop();
                    System.out.println("Enemy's invulnerability has ended!");
                }
                if (lowHpCounter > 0) {
                    lowHpCounter--;
                    if (lowHpCounter == 0) {
                        System.out.println("Enemy’s invulnerable passive has worn off. The enemy has succumbed!");
                        botHP = 0;
                    }
                }
            }
        }
    }

    static boolean isOddorEven(int i) {
        boolean oddOrEven;
        if (i % 2 == 0) {
            return oddOrEven = true;
        } else {
            return oddOrEven = false;
        }
    }
}
