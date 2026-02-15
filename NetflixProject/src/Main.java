import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        String path = "src/movies.csv";
        String[] featureNames = CsvLoader.loadFeatureNames(path);
        double[] weights = CsvLoader.loadWeights(path);
        List<Movies> movies = CsvLoader.loadMovies(path);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMovie Recommendation System:");
            System.out.println("1) Recommend by existing movie");
            System.out.println("2) Recommend by preferences");
            System.out.println("3) Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice == 3) {
                System.out.println("Exiting program...");
                break;
            }
            if (choice == 1) {
                printMoviesWithIndex(movies);
                System.out.print("Select a movie by index: ");
                int index = Integer.parseInt(scanner.nextLine().trim());
                if (index < 1 || index > movies.size()) {
                    System.out.println("Invalid index.");
                    continue;
                }
                Movies selected = movies.get(index - 1);
                List<Movies> recommendations = Recommendation.getRecommendations(movies, selected.toVector(), weights, 3);
                System.out.println("\nTop 3 similar movies to: " + selected.getTitle());
                for (Movies m : recommendations) {
                    if (!m.getTitle().equals(selected.getTitle())) {
                        System.out.println("- " + m.getTitle());
                    }
                }
            } else if (choice == 2) {
                double[] preferences = new double[featureNames.length];
                for (int i = 0; i < featureNames.length; i++) {
                    System.out.print("Rate " + featureNames[i] + " (0-5): ");
                    int rating = Integer.parseInt(scanner.nextLine().trim());
                    if (rating < 0) rating = 0;
                    if (rating > 5) rating = 5;
                    preferences[i] = rating / 5.0;
                }
                List<Movies> recommendations = Recommendation.getRecommendations(movies, preferences, weights, 3);
                System.out.println("\nTop 3 recommended movies:");
                for (Movies m : recommendations) {
                    System.out.println("- " + m.getTitle());
                }
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMoviesWithIndex(List<Movies> movies) {
        System.out.println("\nAvailable Movies:");
        for (int i = 0; i < movies.size(); i++) {
            System.out.printf("%2d) %s%n", i + 1, movies.get(i).getTitle());
        }
    }
}
