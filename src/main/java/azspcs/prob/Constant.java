package azspcs.prob;

public class Constant implements Variable {
    private final int value;

    public Constant(int value) {
        this.value = value;
    }

    @Override
    public int get() {
        return value;
    }

    @Override
    public void boost(int score) {
    }

    @Override
    public void boost(int i, int score) {
    }

    @Override
    public String toString() {
        return "["+value+"]";
    }
}
