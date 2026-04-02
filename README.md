# Comp128 HW3: Auto-Complete

Your third homework assignment is to implement a new type of tree data structure --- a Trie. This is a data structure that we have not discussed in class, so this assignment is meant to simulate the sorts of problems you might encounter as a developer: implementing a new data structure for a particular task. You should be well prepared for this based on your existing knowledge of trees and ADT implementations.

The task in question for this assignment is a text auto-complete program. Auto-complete checkers, like the predictive ones on your phone that provide word suggestions once you start typing, need to store a spelling dictionary in a way that makes it as fast as possible to see what words start with a specific prefix. For example if you started typing the word "do", the auto-complete program should quickly be able to suggest longer words like "dog" or "doctor" that contain the prefix "do".

To make this work efficiently with a tree, rather than storing each word as a node in the tree. We will break the words into individual characters. Each character will correspond to a node in the tree. If the node corresponds with the last letter in the word, then we will mark it using a boolean value that it is the end of a word. You can see an example of building a Trie in the "Example" section below, but a diagram of a fully instantiated Trie looks like this:

<img src="/images/Trie.png" width=60%>

It is very fast to check whether a word is in the tree: start from the root and trace out the word along the edges; if one ends up at a word-terminating node (a purple node in the images above), then the word is in the tree, otherwise it is not.

This tree can also efficiently support auto-completion. Given the prefix-based structure of the tree, it is straightforward to enumerate all of the words that begin with a given letter sequence: these will be represented by nodes that are descendants of the node corresponding to that prefix.

There are multiple ways you might implement the structure shown in the images. We will use a simple implementation that uses a lot of space, but is fast. Each node of the tree has a boolean variable indicating whether a string ending there is a word (corresponding to whether the node is purple or orange in the diagrams). Each node also contains a Map representing outgoing links to child nodes. The map keys are individual letters, and the values are references to other tree nodes. Notice that this is *not* a binary tree. Each node might have up to 26 children.

Another convenient feature of the Trie is that it makes implementing *predictive* auto-completion rather straightforward. If we store information about how often a word appears in text (and how often each *prefix* appears in text) at each node, it's straightforward to estimate the probability of each possible completion for each prefix. This allows you to build an autocomplete system like you'd see in Google Search, where likely completions are presented before less likely ones. You can see this with the modified trie structure below, where each node is annotated by two numbers --- how many times it appears as a word, and how many times it has appeared as a prefix respectively. 

<img src="/images/TrieWithCounts.png" width=60%>

## Task

Explore the starter code that we have given you. 

We have included a `TreeNode` class to represent nodes in the tree. There is also a `PrefixTree` class where you will write most of your code, and an `AutoComplete` class to play with your completed tree. These suffice for completing Part 1 of the assignment, where you build a non-predictive autocomplete system.

We additionally provide a few classes to help as you implement the probabilistic features of `PrefixTree` in Part 2. These include `WordProb`, which simply stores a word paired with its probability, and `PredictiveAutoComplete`, which modifies `AutoComplete` to suggest likely words first. 

Before getting started on the next two sections, you should also familiarize yourself with the mechanics of the Trie --- the two sections under the Example header below provide examples of how to build a non-predictive trie (as you will do in Part 1) and how your predictive trie should operate (the goal of part 2).


### Part 1: The Trie

In the `PrefixTree` class, implement the tree structure defined above without considering the frequency of various words you add. You should implement functionality that allows for new words to be added to the trie, and for words presence in the trie to be quickly computed.

Specifically, you should implement the following methods:

#### add(String word)

This should add the word to the tree by creating nodes for each character in the word (if they don't already exist in the tree!), and linking the nodes together by correctly inserting them in the children maps stored at each node.

When the last character is reached, that node should be marked as the end of a word.
If the word does not already exist in the tree, the size variable should be incremented. If the word already exists, this method should not change the tree structure or size variable. 
 
The string `charAt` method may be helpful to get individual characters from the word.

Once you complete this, the testAdd method in the `TestPrefixTree` file should pass.

At this point, you should not worry about `add(String word, int count)` --- the additional `count` argument will be relevant in Part 2. Be prepared to refactor this work when you reach that step. 

#### find(String prefix)/contains(String word)

`find` should return the node that corresponds to a particular prefix. We can find it by starting at the root and iterating through the word's characters searching down the path of nodes.

`contains` should determine whether a word is in the trie. Note that this is very similar to `find`, and you should not duplicate work. Be careful --- there will be some nodes in the tree that do not correspond to words!

Once you complete this, the `testContains` method should pass.

#### getWordsForPrefix(String prefix)

This should return a list of words contained in the tree that start with the prefix characters, including the prefix if it is a word itself. These words can be found by traversing through the letters of the prefix and then traversing any child decendents of the last prefix node.

There are multiple ways to implement this, but probably the easiest is to write a separate helper, recursive method to preorder-traverse the child decendents. Look in the BST activity for an example of a recursive pre-order traversal. However, note that this tree is not binary --- account for this!

Once you complete this, the `testPrefix` method should pass.

### Part 2: Counts and Prediction

For the second part of the assignment, you will update your trie to be able to store the frequency with which various words and prefixes appear. These will be stored using the `wordCount` and `prefixCount` instance variables in `TreeNode`. `wordCount` should track the number of times the word represented by this node has been seen (determined by the `count` parameter in calls to `add`), and `prefixCount` should track the number times a word has been seen that has the sequence represented by this node as a prefix (equivalently, the number of times a descendent word has been seen through calls to `add`).

With these, we can relatively easily estimate the (conditional) probabilities of a word being the completion of a prefix! If we've seen the prefix "who" 10 times and the word "whomever" 3 times, we can naively estimate the probability of completing "who" as "whomever" as 3/10. We can then imagine ranking all possible completions by this probability so that we can list likely completions first (as `PredictiveAutoComplete` will do!). A more throrough example of intended behavior is below in the Examples section. 

To complete part 2, you must complete the following methods:

#### add(String word) / add(String word, int count)

We must now modify `add` to consider how often the word we added was seen. We can now reframe `add(String word)` as indicating that we have seen the word a single time and `add(String word, int count)` as indicating that we've seen the word `count` times. 

Ths means that in addition to expanding the trie to include the provided word, we must update `prefixCount` and `wordCount` for every relevant node where that might change. For example, the trie must record that if we call `add("whom", 3)`, we have seen the prefixes "", "w", "wh", "who" and "whom" 3 more times, and that we've seen the full word "whom" 3 more times (remember every word has itself as a prefix!). 

Be sure to avoid duplicating code between methods!

#### getCount(String word)

This method should return the number of times we've seen this word. 

#### getCompletionProb(String prefix, String word)

This method should determine the probability with which you estimate the prefix will be completed with the word. The provided code ensures that you return 0 when prefix is not actually a prefix of word.

You should estimate this as (# of times we've seen word)/(# of times the prefix has appeared as a prefix) -- see the Example section below!

#### getLikelyWordsForPrefix(String prefix)

This method, just as `getWordsForPrefix`, should find all words that can complete prefix. However, this method should also order the list from most likely to least likely completion. The `WordProb` class may be helpful, as may be the various techniques for ordering and sorting objects that we've discussed in class. 

## Play!
Once Part 1 is working, run the `AutoComplete` class. Enter the text for a prefix and it will show you the results from a dictionary:

```
Enter text prefix to search, or 'stop'.
appl
========== Results =============
applause
applaud
applauds
apple
apples
applique
applier
appliers
applies
applied
apply
applying
```

You may do the same once you complete Part 2 with `PredictiveAutoComplete`, noting that now we print and order by probability! Be warned that counts.txt --- the source we use to build the tree --- is relatively large. It's a truncated version of counts from the [Corpus of Contemporary American English](https://www.english-corpora.org/coca/). This contains ~470,000 words, compared to the ~80,000 in dictionary.txt. 

```
Enter text prefix to search, or 'stop'.
appl
========== Results =============
apple (0.25)
apply (0.16)
applied (0.11)
applications (0.10)
application (0.09)
apples (0.06)
applying (0.05)
applies (0.04)
appliances (0.03)
applicants (0.02)
```
## Submit

When you are done, commit and push your code back to Github.

Please make sure to follow the guidelines for [good Java style](https://docs.google.com/document/d/1GT207Pia0q7bETKrqSi--C3X7N_67XgRXYEvQFrwHdI/edit?usp=sharing) you've seen in previous assignments. To verify correctness, passing the provided tests may be insufficient --- you should write additional tests to cover any edge cases that may not be covered by the starter code.

## Examples 
### Example - Building a Trie
Each `add` should proceed downward from the root moving to a child that represents the next letter in the word. If that node doesn't exists, that node should be created. The last node along the path should be marked as a word.

Let's look at an example for how a series of calls to `add` should modify the structure of the trie. Nodes that are filled in represent words (i.e., the `isWord` parameter), and nodes in red are nodes that are modified as part of each add. 

<img src="/images/trie_1.jpg" width=60%>
<img src="/images/trie_2.jpg" width=60%>
<img src="/images/trie_3.jpg" width=60%>
<img src="/images/trie_4.jpg" width=60%>
<img src="/images/trie_5.jpg" width=60%>
<img src="/images/trie_6.jpg" width=60%>

Note that adding "do" after "don" results in no new nodes being added! We simply modify the node representing "do" to mark that the prefix denotes a word!

### Example - Computing Completion Probabilities
Suppose we modify the above example to provide the number of times each word was seen. Suppose we make the following calls to our trie methods:

```
trie.add('cat", 3);
trie.add('car", 2);
trie.add('do", 4);
trie.add('don", 1);
```

We should build the following trie, with node labels representing `wordCount` and `prefixCount` respectively. You are responsible for keeping track of these values with each call to `add` --- update `prefixCount` as you move downward from the root and update `wordCount` when you reach the node representing the word you added.

<img src="/images/TrieWithCounts.png" width=60%>

We have seen 5 total words that begin with "c": 3 of them have been "cat" and 2 of them have been "car", so we should expect "c" to be completed with "cat" with probability 0.6 (3 of 5) and to be completed with "car" with probability 0.4 (2 of 5). 

Similarly, we have seen 5 words with prefix "do": 4 of them have been completed as "do" and 1 as "don", so the probability of the prefix "do" being completed as "do" is 0.8 and "do" being completed as "don" is 0.2. 

In general, we should estimate the completion probability by finding the `prefixCount` of the node that represents the prefix and the `wordCount` of the node representing the word (which should be a descendent of the prefix node). Then we divide the `wordCount` by the `prefixCount`. In other words, we ask out of all the times we've seen the prefix, what proportion of the time was it as part of the word of interest?
