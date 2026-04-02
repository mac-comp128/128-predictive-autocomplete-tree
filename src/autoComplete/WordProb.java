package autoComplete;

/**
 * Represents a word/prob pair. 
 */
public class WordProb  {
    double prob;
    String word;

    static double eps = 0.000001;

    public WordProb(String word, double prob) {
        this.prob = prob;
        this.word = word;
    }

    public double getProb() {
        return prob;
    }

    public String getWord() {
        return word;
    }

    public String toString() {
        return String.format("%s (%.2f)", word, prob);
    }

    @Override
    public boolean equals(Object o) {
        WordProb p = (WordProb) o;
        
        // Use a small threshold to account for small precision errors with double
        return (Math.abs(prob - p.prob) < eps) && (word.equals(p.word));
    }
}
