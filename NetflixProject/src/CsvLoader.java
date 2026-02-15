import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvLoader {
    //load features name from the file
    public static String[] loadFeatureNames(String csvPath) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String headerLine = br.readLine();
            if (headerLine == null || headerLine.isBlank()) {
                throw new IllegalArgumentException("CSV missing header line.");
            }
            String[] headers = headerLine.split(",");
            if (headers.length != 8) {
                throw new IllegalArgumentException("Expected 8 columns (Title + 7 features). Got: " + headers.length);
            }
            return Arrays.copyOfRange(headers, 1, headers.length);
        }
    }
    //load the weights from the file
    public static double[] loadWeights(String csvPath) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            br.readLine();
            String weightsLine = br.readLine();
            if (weightsLine == null || weightsLine.isBlank()) {
                throw new IllegalArgumentException("CSV missing weights line.");
            }
            String[] w = weightsLine.split(",");
            if (w.length != 8 || !w[0].equalsIgnoreCase("Weights")) {
                throw new IllegalArgumentException("Second line must start with 'Weights' and contain 8 columns.");
            }
            double[] weights = new double[7];
            for (int i = 1; i < w.length; i++) {
                weights[i - 1] = Double.parseDouble(w[i].trim());
            }
            return weights;
        }
    }
    //load the movies(from row 3) from the file
    public static List<Movies> loadMovies(String csvPath) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            br.readLine();
            br.readLine();
            List<Movies> movies = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] v = line.split(",");
                if (v.length != 8) {
                    throw new IllegalArgumentException("Bad row (expected 8 columns): " + line);
                }
                //Convert the weight from a string to a double number
                Movies movie = new Movies(
                        v[0].trim(),
                        Double.parseDouble(v[1].trim()),
                        Double.parseDouble(v[2].trim()),
                        Double.parseDouble(v[3].trim()),
                        Double.parseDouble(v[4].trim()),
                        Double.parseDouble(v[5].trim()),
                        Double.parseDouble(v[6].trim()),
                        Double.parseDouble(v[7].trim())
                );
                movies.add(movie);
            }
            return movies;
        }
    }
}
