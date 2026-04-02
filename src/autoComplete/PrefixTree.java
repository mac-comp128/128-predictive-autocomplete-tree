package autoComplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A prefix tree used for autocompletion. The root of the tree just stores links to child nodes (up to 26, one per letter).
 * Each child node represents a letter. A path from a root's child node down to a node where isWord is true represents the sequence
 * of characters in a word.
 */
public class PrefixTree {
    private TreeNode root; 

    // Number of words contained in the tree
    private int size;

    public PrefixTree(){
        root = new TreeNode();
    }

    /**
     * Update the tree to indicate that a word has been seen count times
     * If the word is not in the tree, nodes are added to include it.
     * If the word is already in the tree, then this updates the number of times the word was seen.
     * @param word
     * @param count the number of new instances of the word that have been seen
     */
    public void add(String word, int count){
        // TODO
    }

    /**
     * Update the tree to indicate that a word has been seen a single time
     * If the word is not in the tree, nodes are added to include it.
     * If the word is already in the tree, then this updates the number of times the word was seen.
     * @param word
     */
    public void add(String word){
        // TODO
    }

    /**
     * Finds the node that corresponds to a prefix
     * @param prefix
     * @return the TreeNode representing that prefix
     */
    private TreeNode find(String prefix) {
        // TODO
        return null;
    }

    /**
     * Checks whether the word has been added to the tree
     * @param word
     * @return true if contained in the tree.
     */
    public boolean contains(String word){
        // TODO
        return false;
    }

    /**
     * Determine how many times a word has been seen by our tree.
     * @param word
     * @return the number of times the word has been seen. 0 if the word is not in the tree.
     */
    public int getCount(String word){
        // TODO
        return 0;
    }

    /**
     * Finds the words in the tree that start with prefix (including prefix if it is a word itself).
     * The order of the list can be arbitrary.
     * @param prefix
     * @return list of words with prefix
     */
    public List<String> getWordsForPrefix(String prefix){
        // TODO
        return null;
    }

    /**
     * Finds the words in the tree that start with a prefix (including prefix if it is a word 
     * itself). The words should be in order of frequency, with more frequent words appearing 
     * earlier in the list. 
     * @param prefix
     * @return a list of word/probability pairs
     */
    public List<WordProb> getLikelyWordsForPrefix(String prefix) {
        // TODO
        return null;
    }

    /**
     * Estimates the probability that a prefix will be completed to a word w
     * @param prefix the prefix to be completed
     * @param word the word whose completion probability is to be computed
     * @return the estimated probability for prefix being completed to word
     */
    public double getCompletionProb(String prefix, String word) {
        // TODO
        return 0.0;
    }

    /**
     * @return the number of words in the tree
     */
    public int size() {
        return size;
    }
    
}
