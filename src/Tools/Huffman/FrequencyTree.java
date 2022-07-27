package Tools.Huffman;

/**
 * Assignment 1
 * Submitted by:
 * Tom Basha 	ID# 311425714
 * Roei Lahav	ID# 315808469
 * Batel Ovad 	ID# 209204320
 */

public class FrequencyTree {
    final Node root;
    final Node mostFrequent;
    final Node[] currentValues;             // betterEnDe
    int longestHuffmanCode;

    public FrequencyTree(Node root, Node mostFrequent, Node[] possibleValues){
        this.root = root;
        this.mostFrequent = mostFrequent;
        currentValues = new Node[256];
        for (int i = 0; i < 256; i++)
            currentValues[i] = possibleValues[i];
    }
}
