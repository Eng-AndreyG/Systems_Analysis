import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Computer_Science_Researcher {

    // Ocurrences counter method
    public static int countMotifOccurrences(String file, String candidate) throws IOException {
        int count = 0;

        long startTime = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                int index = line.indexOf(candidate);// Search total candidate ocurrences in actual line
                while (index != -1) {
                    count++;
                    index = line.indexOf(candidate, index + 1); // Go to next line
                }
            }
        }

        long endTime = System.currentTimeMillis(); 
        long elapsedTime = endTime - startTime; // Execution time in milliseconds

        System.out.println(candidate +" total ocurrences number: " + count);
        System.out.println("Execution time: " + elapsedTime + " milliseconds");

        return count;
    }

    public static void main(String[] args) throws IOException {
        String file = "genetic_sequences.txt"; // The sequences file that´s gonna be readed
        String candidate = "ACTG"; // The motif candidate

        // It´s for call the ocurrences counter method
        countMotifOccurrences(file, candidate);
    }
}
