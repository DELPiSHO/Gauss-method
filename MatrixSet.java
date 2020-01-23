public class MatrixSet {
    private MyMatrix<Float> floatMatrix;
    private MyMatrix<Double> doubleMatrix;
    private MyMatrix<Fraction> fractionMatrix;

    public MatrixSet(MyMatrix<Float> floatMatrix, MyMatrix<Double> doubleMatrix, MyMatrix<Fraction> fractionMatrix) {
        super();
        this.floatMatrix = floatMatrix;
        this.doubleMatrix = doubleMatrix;
        this.fractionMatrix = fractionMatrix;
    }

    public MyMatrix<Float> getFloatMatrix() {
        return floatMatrix;
    }
    public void setFloatMatrix(MyMatrix<Float> floatMatrix) {
        this.floatMatrix = floatMatrix;
    }
    public MyMatrix<Double> getDoubleMatrix() {
        return doubleMatrix;
    }
    public void setDoubleMatrix(MyMatrix<Double> doubleMatrix) {
        this.doubleMatrix = doubleMatrix;
    }
    public MyMatrix<Fraction> getFractionMatrix() {
        return fractionMatrix;
    }
    public void setFractionMatrix(MyMatrix<Fraction> fractionMatrix) {
        this.fractionMatrix = fractionMatrix;
    }
}

