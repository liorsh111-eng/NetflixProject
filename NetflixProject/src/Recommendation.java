import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Recommendation{
    public static double calculateSimilarity(double[] v1, double[] v2, double[] weights) {
        if (v1 == null || v2 == null || weights == null) {
            throw new IllegalArgumentException("Vectors/weights must not be null.");
        }
        if (v1.length == 0 || v2.length == 0 || weights.length == 0) {
            throw new IllegalArgumentException("Vectors/weights must not be empty.");
        }
        if (v1.length != v2.length || v1.length != weights.length) {
            throw new IllegalArgumentException("Length mismatch between vectors/weights.");
        }
        double dot = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        for (int i = 0; i < v1.length; i++) {
            double w = weights[i];
            double a = v1[i] * w;
            double b = v2[i] * w;
            dot += a * b;
            norm1 += a * a;
            norm2 += b * b;
        }
        if (norm1 == 0.0 || norm2 == 0.0) {
            return 0.0;
        }
        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    public static List<Movies> getRecommendations(List<Movies> movies, double[] targetFeatures, double[] weights, int k) {
        if (movies == null) {
            throw new IllegalArgumentException("Movies list is null.");
        }
        if (targetFeatures == null || weights == null) {
            throw new IllegalArgumentException("Target features and weights must not be null.");
        }
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive.");
        }
        List<MovieScore> scored = new ArrayList<>();
        for (Movies m : movies) {
            double score = calculateSimilarity(m.toVector(), targetFeatures, weights);
            // Skip perfect similarity (usually the same movie as the target)
            if (Math.abs(score - 1.0) < 1e-9) {
                continue;
            }
            scored.add(new MovieScore(m, score));
        }
        scored.sort(Comparator.comparingDouble(MovieScore::score).reversed());
        List<Movies> result = new ArrayList<>();
        for (int i = 0; i < Math.min(k, scored.size()); i++) {
            result.add(scored.get(i).movie());
        }
        return result;
    }

    private record MovieScore(Movies movie, double score) {}
}

