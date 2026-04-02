package autoComplete;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Main class that autocompletes words
 * This version is able to suggest likely words above unlikely words.
 * 
 * @author Bret, Suhas
 */
public class PredictiveAutoComplete {

    // Number of suggestions to print
    static int NUM_LIST = 10;
    
    public static void main(String args[]) throws IOException {
        PrefixTree tree = new PrefixTree();

        // this file is very large! Don't test until you're confident!
        File file = getFile("counts.txt");
        try (BufferedReader r = new BufferedReader(new FileReader(file))) {
            while (true) {
                String word = r.readLine();
                if (word == null) {
                    break;
                }
                String[] wordCount = word.split(",", 2);
                
                tree.add(wordCount[0], Integer.parseInt(wordCount[1]));
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "utf-8"));
        while (true) {
            System.out.println("Enter text prefix to search, or 'stop'.");
            String text = in.readLine();
            if (text.trim().equalsIgnoreCase("stop")) {
                break;
            }
            
            List<WordProb> words = tree.getLikelyWordsForPrefix(text);
            int num_list = Math.min(NUM_LIST, words.size());
            words = words.subList(0, num_list);

            if (words.isEmpty()){
                System.out.println("No words found with the prefix: "+ text);
            }
            else {
                System.out.println("========== Results =============");
                words.forEach(System.out::println);
            }
            System.out.println();
        }
    }


    /**
     * Loads a file from the res folder.
     **/ 
    public static File getFile(String resourceName){
        try {
            URL url = AutoComplete.class.getResource("/" + resourceName);
            if (url != null) {
                return new File(url.toURI());
            }
            else {
                System.out.println("Cannot find file with name "+resourceName);
                return null;
            }
        } catch (URISyntaxException syntaxException){
            syntaxException.printStackTrace();
            return null;
        }
    }

}
