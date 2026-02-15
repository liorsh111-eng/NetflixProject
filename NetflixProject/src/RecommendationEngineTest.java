import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class RecommendationEngineTest {
    @Test
    void testIdenticalVectors() { //checks identical vectors
        double[] v1 = {1.0, 2.0, 3.0};
        double[] v2 = {1.0, 2.0, 3.0};
        double[] w  = {1.0, 1.0, 1.0};
        double result = Recommendation.calculateSimilarity(v1, v2, w);
        assertEquals(1.0, result, 1e-9);
    }

    @Test
    void testOrthogonalVectors() { //checks Orthogonal vectors
        double[] v1 = {1.0, 0.0};
        double[] v2 = {0.0, 1.0};
        double[] w  = {1.0, 1.0};
        double result = Recommendation.calculateSimilarity(v1, v2, w);
        assertEquals(0.0, result, 1e-9);
    }

    @Test
    void testWeightedSimilarity() { //checks Weighted similarity
        double[] v1 = {1.0, 1.0};
        double[] v2 = {1.0, 0.0};
        double[] w  = {0.5, 0.5};
        double result = Recommendation.calculateSimilarity(v1, v2, w);
        assertEquals(0.7071, result, 0.01);
    }

    @Test
    void testLengthMismatch() { //checks length mismatch
        double[] v1 = {1.0, 2.0, 3.0};
        double[] v2 = {1.0, 2.0};
        double[] w  = {1.0, 1.0, 1.0};
        assertThrows(IllegalArgumentException.class, () -> Recommendation.calculateSimilarity(v1, v2, w));
    }
}

