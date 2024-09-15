import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

public class GeneticsSequences{

    // Generate an artificial genetics sequences data base method
    public static void generateSequences(int n, int m, double pA, double pC, double pG, double pT, String file) throws IOException {
        Random random = new Random();
        int batchSize = 500000;
        int numBatches = (n + batchSize - 1) / batchSize;
        int s = 0;

        // Generate sequences in different batch and save it in a new temporal file
        for (int batch = 0; batch < numBatches; batch++) {
            String temporalFile = "genetics_sequences_temp_" + batch + ".txt";
            try (FileWriter writer = new FileWriter(temporalFile)) {
                int start = batch * batchSize;
                int end = Math.min(n, (batch + 1) * batchSize);

                // Generate the sequences for the current batch
                for (int i = start; i < end; i++) {
                    StringBuilder sequence = new StringBuilder();
                    for (int j = 0; j < m; j++) {
                        double probability = random.nextDouble();
                        if (probability < pA) {
                            sequence.append("A");
                        } else if (probability < pA + pC) {
                            sequence.append("C");
                        } else if (probability < pA + pC + pG) {
                            sequence.append("G");
                        } else {
                            sequence.append("T");
                        }
                    }
                    writer.write(sequence.toString() + "\n");

                    s++;

                    //Print the progress every certain sequences number
                    if (s % 100000 == 0) {
                        System.out.println("Generate sequences: " + s);
                    }
                }
            }
            System.out.println("Created temporal file: " + temporalFile);
        }

        // Merge all temporal files to the final file
        try (FileWriter writer = new FileWriter(file)) {
            for (int batch = 0; batch < numBatches; batch++) {
                String temporalFile = "genetics_sequences_temp_" + batch + ".txt";
                try (BufferedReader reader = new BufferedReader(new FileReader(temporalFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line + "\n");
                    }
                }
                //Delete the temporal file later to append it to the final file
                new java.io.File(temporalFile).delete();
            }
        }

        System.out.println("The total generate sequences number is: " + s);
        System.out.println("Sequences data base saved in: " + file);
    }

    public static void main(String[] args) throws IOException {
        int n = 2000000; // Sequences number
        int m = 50; //Sequence size
        double pA = 0.25;
        double pC = 0.25;
        double pG = 0.25;
        double pT = 0.25;

        if (pA + pC + pG + pT != 1) {
            throw new IllegalArgumentException("The probabilities of A, C, G, and T must sum exactly to 1");
        }

        String file = "genetic_sequences.txt"; 

        // It´s for call the method
        generateSequences(n, m, pA, pC, pG, pT, file);
    }
}
