package Tools.LZ77;

public class Duo {
    private final int start;
    private final int length;

    public Duo(int start, int length) {
        this.start = start;             // start index
        this.length = length;           // match length
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "<" + start + "," + length + ">";
    }
}
