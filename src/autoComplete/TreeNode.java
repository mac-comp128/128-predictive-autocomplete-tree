package autoComplete;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a node in the prefix tree
 */
public class TreeNode {
    // True if this node represents the last character in a word based on the path
    // from the root of the tree to this node.
    public boolean isWord;

    // The letter this node represents
    // This isn't strickly necessary since you can get the letter from the key used to link this node's parent,
    // but this can make things easier.
    public char letter;

    // Counts for the number of times this *word* (i.e., path ending at this node) was inserted.
    public int wordCount;

    // Counts for the number of times a word with this word as it's *prefix* was inserted.
    public int prefixCount;

    // The node's children keyed by each child node's letter
    public Map<Character, TreeNode> children;

    public TreeNode(){
        children = new HashMap<>();
        isWord = false;
        wordCount = 0;
        prefixCount = 0;
    }
}
