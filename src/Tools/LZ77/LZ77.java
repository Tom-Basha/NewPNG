package Tools.LZ77;

public class LZ77 {
    private PrefixTable threesPrefix = new PrefixTable();          // holds prefix in length 3
    private char[] toEncode;
    private int s = 32768;
    private int t = 258;

    public LZ77() {
    }

    // input: input and output files directories, includes the string to encode, s and t.
    public StringBuilder Compress(StringBuilder input) {
        toEncode = new char[input.length()];
        input.getChars(0, input.length(), toEncode, 0);
        int curr = 0, prefixFillPtr = 0;        // pointers
        StringBuilder output = new StringBuilder();
        Duo nextDuo;

        while (curr < toEncode.length) {
            nextDuo = findBestMatch(curr);            // finds/creates the next tuple

            if (nextDuo.getLength() == 0) {
                output.append(toEncode[curr]);
                curr++;
            } else {
                output.append(nextDuo);
                curr += nextDuo.getLength();          // update current position pointer
            }

            if (curr == toEncode.length) {
                break;
            }

            fillPrefixTable(curr, prefixFillPtr);      // fill prefix arrays
            prefixFillPtr = curr;
        }

        return output;
    }

    // input: current location.
    // output: the next tuple.
    // finds matching prefix and counts the match length.
    // if not found returns default tuple.
    private Duo findBestMatch(int curr) {
        int currMatchLength = -1;
        String find;
        PrefixNode check;
        Duo currDuo = new Duo(0, 0);      // creates default tuple
        int bufferStart = curr - s;
        if (bufferStart < 0) bufferStart = 0;

        /*********** SEARCH IN LENGTH 3 ***********/
        if (curr > 2 && curr < toEncode.length - 3) {
            find = "" + toEncode[curr] + toEncode[curr + 1] + toEncode[curr + 2];
            check = threesPrefix.search(find, bufferStart);

            while (check != null && check.getStart() >= bufferStart) {          // looking for longest match
                currMatchLength = checkMatchLength(check.getStart(), curr, 3);
                if (currMatchLength > currDuo.getLength())
                    currDuo = new Duo(curr - check.getStart(), currMatchLength);
                if (currMatchLength == t)
                    break;
                check = threesPrefix.searchNext(find, check.getNext(), bufferStart);
            }
        }

        return currDuo;
    }

    // input: prefix pointer, current location pointer, prefix length.
    // output: match length.
    // calculates match length by comparing char by char.
    private int checkMatchLength(int checkPtr, int currPtr, int matchCnt) {
        checkPtr += matchCnt;
        currPtr += matchCnt;
        int maxSteps = t - matchCnt;         // calculates how many steps available
        while (currPtr < toEncode.length - 1 && maxSteps != 0) {
            if (toEncode[checkPtr] == toEncode[currPtr]) {
                matchCnt++;
                checkPtr++;
                currPtr++;
                maxSteps--;
            } else
                return matchCnt;
        }
        return matchCnt;
    }

    // input: current location, from where to start filling.
    // fill prefixes in length 3 from the start position to current location.
    private void fillPrefixTable(int curr, int prefixFillPtr) {
        String pref;

        /***** FILL IN LENGTH 3 *****/
        while (curr > 2 && prefixFillPtr != curr && prefixFillPtr + 2 < toEncode.length - 3) {
            pref = "" + toEncode[prefixFillPtr] + toEncode[prefixFillPtr + 1] + toEncode[prefixFillPtr + 2];
            threesPrefix.insert(new PrefixNode(prefixFillPtr, pref));
            prefixFillPtr++;
        }

    }

}