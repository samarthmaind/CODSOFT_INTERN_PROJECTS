import java.util.Scanner;

public class WordCounter_Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Word Counter!");
        System.out.println("Enter the text or provide a file path to count the words:");
        String userInput = scanner.nextLine();
        String textToCount = getUserInputText(userInput);
        String[] words = textToCount.split("[\\s,.!?]+");
        int wordCount = 0;
        for (String word : words) {
            if (!word.isEmpty()) {
                wordCount++;
            }
        }
        System.out.println("Total number of words: " + wordCount);

        scanner.close();
    }
    private static String getUserInputText(String userInput) {
        return userInput;
    }
}
