import javafx.scene.transform.MatrixType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class Main<matrixTest> {
    public static void main(String[] args) {

        int[] rows = {2};

        for(int row : rows) {
            MatrixSet randomMatrix = Randomizer.generateMatrix(row, row);
            MatrixSet randomVector = Randomizer.generateMatrix(row, 1);

            gaussTest(randomMatrix.getFloatMatrix(), randomVector.getFloatMatrix(), "wyniki/float");
            gaussTest(randomMatrix.getDoubleMatrix(), randomVector.getDoubleMatrix(), "wyniki/double");
            if(row <= 200)
                gaussTest(randomMatrix.getFractionMatrix(),randomVector.getFractionMatrix(), "wyniki/fraction");
        }
    }

    private static void matrixGenerationTest(int rows, int columns) {
        MatrixSet set = Randomizer.generateMatrix(rows, columns);
        System.out.println("Float matrix:\n" + set.getFloatMatrix() + "\n\nDouble matrix:\n" + set.getDoubleMatrix() + "\n\nFraction matrix:\n" + set.getFractionMatrix());
       // System.out.println("set" + set);
    }

    private static <Type extends Number> void gaussTest(MyMatrix<Type> randomMatrix, MyMatrix<Type> randomVector, String fileName) {
        long millisActualTime;
        long executionTime;
        MyMatrix<Type> gaussMatrix;
        MyMatrix<Type> timesMatrix;
        MyMatrix<Type> error;

        MyMatrix<Type> testMatrix = new MyMatrix<Type>(randomMatrix);	//test macierz A
        MyMatrix<Type> testVector = new MyMatrix<Type>(randomVector); // test wektor X

        int rows = testMatrix.getRows();
        int columns = testMatrix.getColumns();

        //test

        // gauss podstawowy
        millisActualTime = System.currentTimeMillis();

        gaussMatrix = testMatrix.gaussG(testVector);	//A1 = rozwiazanie A i X
        System.out.println("test matrix: " + testMatrix);
        System.out.println("test vector: " + testVector);
        System.out.println("gauss Matrix: " + gaussMatrix);
        executionTime = System.currentTimeMillis() - millisActualTime;
        timesMatrix = testMatrix.times(gaussMatrix); // A * A1
        System.out.println("times matrix: " + timesMatrix);

        // obliczenie bledu
        error = randomVector.minus(timesMatrix);
        error.absVector();

       // toFile((fileName + "TimeGaussG" + ".csv"), (randomMatrix.getRows() + "," + executionTime));
       // toFile((fileName + "ErrorGaussG" + ".csv"), (randomMatrix.getRows() + "," + error.vectorAvg()));

        testMatrix = new MyMatrix<Type>(randomMatrix);
        testVector = new MyMatrix<Type>(randomVector);
       // System.out.println(fileName + " 2 " + rows + ": " + executionTime);

        // z czesciowym wyborem
        millisActualTime = System.currentTimeMillis();
        gaussMatrix = testMatrix.gaussPG(testVector);
        executionTime = System.currentTimeMillis() - millisActualTime;
        timesMatrix = testMatrix.times(gaussMatrix);

        // obliczenie bledu
        error = randomVector.minus(timesMatrix);
        error.absVector();

       // toFile((fileName + "TimeGaussPG" + ".csv"), (randomMatrix.getRows() + "," + executionTime));
       // toFile((fileName + "ErrorGaussPG" + ".csv"), (randomMatrix.getRows() + "," + error.vectorAvg()));

        testMatrix = new MyMatrix<Type>(randomMatrix);
        testVector = new MyMatrix<Type>(randomVector);
        //System.out.println(fileName + " 2 " + rows + ": " + executionTime);

        // z pelnym wyborem
        millisActualTime = System.currentTimeMillis();
        gaussMatrix = testMatrix.gaussFG(testVector);
        executionTime = System.currentTimeMillis() - millisActualTime;
        timesMatrix = testMatrix.times(gaussMatrix);

        // obliczenie bledu
        error = randomVector.minus(timesMatrix);
        error.absVector();

       // toFile((fileName + "TimeGaussFG" + ".csv"), (randomMatrix.getRows() + "," + executionTime));
      //  toFile((fileName + "ErrorGaussFG" + ".csv"), (randomMatrix.getRows() + "," + error.vectorAvg()));
       // System.out.println(fileName + " 2 " + rows + ": " + executionTime);
    }

    private static void toFile(String fileName, String content) {
        try(FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}