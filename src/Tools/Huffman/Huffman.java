package Tools.Huffman;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Huffman {
    FileInputStream input;
    FileOutputStream output;
    Node freqTreeRoot = null;
    final int BYTE = 256, ZERO = 0;
    Node[] possibleValues = new Node[BYTE];
    protected int uniqueValues, totalBytes;

    public Huffman() {
    }

    // input: input and output files directories
    public void Compress(StringBuilder input, String outputDst) {
        MinHeap values = calculateFrequencies(input);
        freqTreeRoot = createFrequencyTree(values);
        encode(input, outputDst);
    }

    // input: file directory
    // output: min heap (property: node frequency)
    // count appearances for every single value from the file
    // creates minimum heap
    protected MinHeap calculateFrequencies(StringBuilder input) {
        char[] inputArr = new char[input.length()];
        input.getChars(0, input.length(), inputArr, 0);
        MinHeap values;
        int uniqueValues = 0, totalBytes = 0;

        for (int i = 0; i < inputArr.length; i++) {
            int value = inputArr[i];
            if (possibleValues[value] == null) {
                possibleValues[value] = new Node(value);
                uniqueValues++;
            }
            possibleValues[value].incCnt();
            totalBytes++;
        }

        values = new MinHeap(uniqueValues);
        values.heapifyRealValues(possibleValues, totalBytes);
        return values;
    }

    // input: minimum heap
    // output: frequency tree root
    // creates frequency tree by extracting 2 minimums, combine them to a new node and repeat
    // goes on until 1 node left (the root)
    protected Node createFrequencyTree(MinHeap values) {
        Node min1, min2;
        while (MinHeap.usedSpace != 1) {
            min1 = values.extractMin();
            min2 = values.extractMin();
            values.insert(new Node(min1, min2));
        }
        values.getRoot().setHuffmanCode("");
        return values.getRoot();
    }

    // input: array with Huffman codes, file directory to encode, file directory for new file
    // writes the Huffman code counters for the decompression process
    // read the file given and replace every byte with its Huffman code from the array
    protected void encode(StringBuilder input, String outputDst) {
        StringBuilder encoded = new StringBuilder();
        char[] inputArr = new char[input.length()];
        input.getChars(0, input.length(), inputArr, 0);
        String convertToWrite;
        int converted;

        try {
            output = new FileOutputStream(outputDst);
            freqTreeRoot.setPadding();
            writeMap(output);

            for (int i = 0; i < inputArr.length; i++) {
                int x = inputArr[i];

                encoded.append(possibleValues[x].getCode());
                while (encoded.length() >= 8) {
                    convertToWrite = encoded.substring(0, 8);
                    encoded = new StringBuilder(encoded.substring(8));
                    converted = Integer.parseInt(convertToWrite, 2);
                    output.write(converted);
                }
            }

            for (int j = 0; j < freqTreeRoot.getPadding(); j++) {            // padding last byte to 8 bits
                encoded.append(0);
            }
            output.write(Integer.parseInt(encoded.toString(), 2));
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // input: output stream
    // writes the counters to the compressed file
    protected void writeMap(FileOutputStream output) {
        String mapCnt;
        try {
            for (int i = 0; i < BYTE; i++) {                    // write map to encoded file
                if (possibleValues[i] == null) {
                    mapCnt = String.format("%16s", Integer.toBinaryString(ZERO)).replace(' ', '0');
                } else
                    mapCnt = String.format("%16s", Integer.toBinaryString(possibleValues[i].getCnt())).replace(' ', '0');
                output.write(Integer.parseInt(mapCnt.substring(0, 8), 2));            // first byte
                output.write(Integer.parseInt(mapCnt.substring(8, 16), 2));            // second byte
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}