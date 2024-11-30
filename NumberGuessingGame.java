import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        boolean playAgain = true;
        int rounds = 0;
        int score = 0;

        System.out.println("🎲 Welcome to the Number Guessing Game! 🎲");
        
        while (playAgain) {
            System.out.println("\nNew Round! 🎉");
            int generatedNumber = random.nextInt(100) + 1; // Generate random number between 1 and 100
            int maxAttempts = 5; // Limit of attempts
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("I've picked a number between 1 and 100. You have " + maxAttempts + " attempts to guess it!");

            while (attempts < maxAttempts) {
                System.out.print("Attempt " + (attempts + 1) + "/" + maxAttempts + ": Enter your guess: ");
                
                // Handle invalid input
                if (!scanner.hasNextInt()) {
                    System.out.println("❌ Please enter a valid number!");
                    scanner.next(); // Clear invalid input
                    continue;
                }
                
                int guess = scanner.nextInt();
                attempts++;

                if (guess == generatedNumber) {
                    System.out.println("🎉 Correct! You've guessed the number!");
                    score++;
                    guessedCorrectly = true;
                    break;
                } else if (guess < generatedNumber) {
                    System.out.println("🔼 Too low!");
                } else {
                    System.out.println("🔽 Too high!");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("😔 Out of attempts! The correct number was " + generatedNumber + ".");
            }

            rounds++;
            System.out.println("📊 Your current score: " + score + "/" + rounds + " rounds won.");
            
            System.out.print("Would you like to play another round? (yes/no): ");
            String response = scanner.next().trim().toLowerCase();
            playAgain = response.equals("yes");
        }

        System.out.println("\n🎮 Game Over! You played " + rounds + " rounds and won " + score + " of them. Thanks for playing!");
        scanner.close();
    }
}
