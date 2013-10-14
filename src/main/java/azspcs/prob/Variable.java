package azspcs.prob;

public interface Variable {
    int get();

    void boost(int score);

    void boost(int i, int score);
}
