import java.util.Scanner;

class QuizApplication {
    private static final int TIME_LIMIT = 10; // Time limit per question in seconds
    private static int score = 0;
    private static int questionIndex = 0;
    private static boolean isTimeUp = false;

    private static final String[][] QUESTIONS = {
            {"What is the capital of France?", "Paris", "London", "Rome", "Berlin", "1"},
            {"Who wrote 'Romeo and Juliet'?", "Shakespeare", "Hemingway", "Frost", "Austen", "1"},
            {"What is the smallest prime number?", "0", "1", "2", "3", "3"},
            {"Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Saturn", "2"},
            {"What is 9 + 10?", "19", "21", "18", "20", "1"}
    };

    private static final String[] RESULTS = new String[QUESTIONS.length];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🎯 Welcome to the Quiz Application! 🎯");
        System.out.println("You have " + TIME_LIMIT + " seconds to answer each question.\n");

        for (questionIndex = 0; questionIndex < QUESTIONS.length; questionIndex++) {
            displayQuestion(questionIndex);

            Thread timerThread = startTimer();
            String answer = null;

            // Wait for user input or time-out
            while (!isTimeUp) {
                if (scanner.hasNextLine()) {
                    answer = scanner.nextLine().trim();
                    if (isValidOption(answer)) {
                        break;
                    }
                    System.out.print("❌ Invalid option! Enter a number between 1 and 4: ");
                }
            }

            if (isTimeUp) {
                System.out.println("⏰ Time's up!");
                RESULTS[questionIndex] = "Time's Up!";
            } else {
                processAnswer(answer);
            }

            try {
                timerThread.join(); // Ensure the timer thread completes cleanly
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        displayResults();
        scanner.close();
    }

    private static void displayQuestion(int index) {
        String[] question = QUESTIONS[index];
        System.out.println("Question " + (index + 1) + ": " + question[0]);
        for (int i = 1; i <= 4; i++) {
            System.out.println(i + ". " + question[i]);
        }
    }

    private static boolean isValidOption(String input) {
        try {
            int choice = Integer.parseInt(input);
            return choice >= 1 && choice <= 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void processAnswer(String answer) {
        int correctOption = Integer.parseInt(QUESTIONS[questionIndex][5]);
        int chosenOption = Integer.parseInt(answer);

        if (chosenOption == correctOption) {
            System.out.println("✅ Correct!");
            score++;
            RESULTS[questionIndex] = "Correct";
        } else {
            System.out.println("❌ Wrong! The correct answer was: " + QUESTIONS[questionIndex][correctOption]);
            RESULTS[questionIndex] = "Wrong";
        }
    }

    private static Thread startTimer() {
        isTimeUp = false;

        Thread timerThread = new Thread(() -> {
            try {
                for (int i = 0; i < TIME_LIMIT; i++) {
                    Thread.sleep(1000); // Wait for 1 second
                }
                isTimeUp = true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Ensure proper handling of interruption
            }
        });

        timerThread.start();
        return timerThread;
    }

    private static void displayResults() {
        System.out.println("\n🎉 Quiz Over! Here are your results:");
        System.out.println("====================================");
        for (int i = 0; i < QUESTIONS.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + RESULTS[i]);
        }
        System.out.println("====================================");
        System.out.println("Your Final Score: " + score + "/" + QUESTIONS.length);
    }
}
