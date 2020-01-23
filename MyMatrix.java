import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;

public class MyMatrix<Type extends Number> {
    private Class<Type> c;
    private int rows;
    private int columns;
    private Type matrix[][];


    //konstruktor do tworzenia macierzy zerowej rozmiaru rows x columns
    public MyMatrix(Class<Type> c, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = (Type[][]) Array.newInstance(c, rows, columns);
        this.c = c;

        Type zero;
        if(c == Fraction.class)
            zero = (Type) Fraction.valueOf(0);
        else if(c == Float.class)
            zero = (Type) Float.valueOf(0);
        else if(c == Double.class)
            zero = (Type) Double.valueOf(0);
        else throw new IllegalArgumentException("Wrong type, choose Float, Double or Fraction");

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++)
                this.setCell(zero ,i ,j);
        }
    }

    // konstruktor do tworzenia macierzy z tablicy
    public MyMatrix(Class<Type> c, Type[][] tab) {
        this.rows = tab.length;
        this.columns = tab[0].length;
        this.matrix = (Type[][]) Array.newInstance(c, rows, columns);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                this.matrix[i][j]=tab[i][j];
        this.c = c;
    }

    // konstruktor do tworzenia kopii obiektu
    public MyMatrix(MyMatrix<Type> myMatrix) {
        this.c = myMatrix.getC();
        this.rows = myMatrix.getRows();
        this.columns = myMatrix.getColumns();
        this.matrix = (Type[][]) Array.newInstance(this.c, this.rows, this.columns);
        for(int i = 0; i < myMatrix.getRows(); i++)
            for(int j = 0; j < myMatrix.getColumns(); j++)
                this.matrix[i][j] = myMatrix.matrix[i][j];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Type[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Type[][] matrix) {
        this.matrix = matrix;
    }

    public void setCell(Type value, int i, int j) {
        this.matrix[i][j] = value;
    }

    public Type getCell(int row, int column) {
        return matrix[row][column];
    }

    public Class<Type> getC() {
        return c;
    }

    // transpozycja macierzy
    public MyMatrix<Type> transpose() {
        MyMatrix<Type> A = new MyMatrix<Type>(this.c, columns, rows);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                A.matrix[j][i] = this.matrix[i][j];
        return A;
    }

    // zamiana wierszy
    public void swapRows(int i, int j) {
        Type[] tmp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = tmp;
    }

    // zamiana kolumn
    public void swapColumns(int i, int j) {
        Type tmp;
        for(int k = 0; k < rows; k++) {
            tmp = matrix[k][i];
            matrix[k][i] = matrix[k][j];
            matrix[k][j] = tmp;
        }
    }

    // wartosc bezwzgledna wszystkich elementow w wektorze
    public void absVector() {
        for (int i = 0; i < this.getRows(); i++)
            this.setCell(MyMath.abs(this.getCell(i, 0)), i, 0);
    }

    // liczy sredina ze wszystkich elementow macierzy
    public Type vectorAvg() {
        Type sum = zeroValue();
        for(int i = 0; i < this.rows; i++)
            for(int j = 0; j< this.columns; j++)
                sum = MyMath.add(sum, this.getCell(i, j));
        return (Type) MyMath.div(sum, valueOf(this.rows * this.columns));
    }

    // norma wektora
    public Type vectorNorm() {
        Type sum = zeroValue();
        for(int i = 0; i < this.rows; i++)
            for(int j = 0; j< this.columns; j++)
                sum = MyMath.add(sum, MyMath.mul(this.getCell(i, j), this.getCell(i, j)));
        return (Type) MyMath.sqrt(sum);

    }

    // dodawanie macierzy
    public MyMatrix<Type> plus(MyMatrix<Type> B) {
        MyMatrix<Type> A = this;
        if (B.rows != A.rows || B.columns != A.columns) throw new RuntimeException("Wrong matrix dimensions");
        MyMatrix<Type> W = new MyMatrix<Type>(this.c, rows, columns);		 //macierz wynikowa
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                W.setCell(MyMath.add(A.matrix[i][j], B.matrix[i][j]), i, j);
        return W;
    }

    // odejmowanie macierzy
    public MyMatrix<Type> minus(MyMatrix<Type> B) {
        MyMatrix<Type> A = this;
        if (B.rows != A.rows || B.columns != A.columns) throw new RuntimeException("Wrong matrix dimensions");
        MyMatrix<Type> W = new MyMatrix<Type>(this.c, rows, columns);		 //macierz wynikowa
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                W.setCell(MyMath.sub(A.matrix[i][j], B.matrix[i][j]), i, j);
        return W;
    }

    // mnozenie macierzy
    public MyMatrix<Type> times(MyMatrix<Type> B){
        MyMatrix<Type> A = this;
        if (A.columns != B.rows) throw new RuntimeException("Wrong matrix dimensions");
        MyMatrix<Type> W = new MyMatrix<Type>(this.c, A.rows, B.columns);

        for (int i = 0; i < W.rows; i++)
            for (int j = 0; j < W.columns; j++)
                for (int k = 0; k < A.columns; k++)
                    W.setCell(MyMath.add(W.matrix[i][j], MyMath.mul(A.matrix[i][k], B.matrix[k][j])),i, j);
        return W;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < rows; i++) {
            stringBuilder.append("[");
            for(int j = 0; j < columns; j++) {
                stringBuilder.append(getCell(i, j));
                if(j == columns - 1)
                    stringBuilder.append("]");
                else stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    // zapis do pliku
    public void toFile(String fileName) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(this.toString());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //zwraca wartosc 0 konkretnego typu
    public Type zeroValue() {
        Type zero = null;
        if(c == Float.class)
            zero = (Type) Float.valueOf(0);
        else if(c == Double.class)
            zero = (Type) Double.valueOf(0);
        else if(c == Fraction.class)
            zero = (Type) Fraction.valueOf(0);
        return zero;
    }

    //konwertuje int na typ uzywany przez obiekt
    public Type valueOf(int value) {
        Type sum = null;
        if(c == Float.class)
            sum = (Type) Float.valueOf(value);
        else if(c == Double.class)
            sum = (Type) Double.valueOf(value);
        else if(c == Fraction.class)
            sum = (Type) Fraction.valueOf(value);
        return sum;
    }

    // zapisuje wyniki eliminacji do result
    private void gaussSetResult(MyMatrix<Type> vector, MyMatrix<Type> result, MyMatrix<Type> matrix) {
        for (int i = vector.rows - 1; i >= 0; i--) {
            Type sum = zeroValue();

            for (int j = i + 1; j < vector.rows; j++)
                sum = MyMath.add(sum, MyMath.mul(matrix.matrix[i][j], result.matrix[j][0]));

            result.matrix[i][0] = MyMath.div(MyMath.sub(vector.matrix[i][0], sum), matrix.matrix[i][i]);
        }
    }


    private void buildSteppedMatrix(MyMatrix<Type> matrix, MyMatrix<Type> vector, int i) {
        for (int j = i + 1; j < vector.rows; j++) {
            Type param = MyMath.div(matrix.matrix[j][i], matrix.matrix[i][i]);
            vector.matrix[j][0] = MyMath.sub(vector.matrix[j][0], MyMath.mul(param, vector.matrix[i][0]));

            for (int k = i; k < vector.rows; k++)
                matrix.matrix[j][k] = MyMath.sub(matrix.matrix[j][k], MyMath.mul(param, matrix.matrix[i][k]));
        }
    }

    // eliminacja gaussa bez wyboru elementu podstawowego
    public MyMatrix<Type> gaussG(MyMatrix<Type> vector) {
        MyMatrix<Type> matrix = new MyMatrix<Type>(this);
        MyMatrix<Type> result = new MyMatrix<Type>(c, vector.rows, 1);

        for (int i = 0; i < vector.rows; i++)
            buildSteppedMatrix(matrix, vector, i);

        gaussSetResult(vector, result, matrix);

        return result;
    }

    // eliminacja gaussa z czesciowym wyborem elementu podstawowego
    public MyMatrix<Type> gaussPG(MyMatrix<Type> vector) {
        MyMatrix<Type> matrix = new MyMatrix<Type>(this);
        MyMatrix<Type> result = new MyMatrix<Type>(c, vector.rows, 1);

        for (int i = 0; i < vector.rows; i++) {
            int max = i;
            for (int j = i + 1; j < vector.rows; j++)
                if (MyMath.compare(MyMath.abs(matrix.matrix[j][i]), MyMath.abs(matrix.matrix[max][i])) == 1)
                    max = j;

            matrix.swapRows(i, max);
            vector.swapRows(i, max);

            buildSteppedMatrix(matrix, vector, i);
        }

        gaussSetResult(vector, result, matrix);

        return result;
    }

    // eliminacja gaussa z pelnym wyborem elemenetu podstawowego
    public MyMatrix<Type> gaussFG( MyMatrix<Type> vector) {
        MyMatrix<Type> matrix = new MyMatrix<Type>(this);
        MyMatrix<Type> result = new MyMatrix<Type>(c, vector.rows, 1);
        MyMatrix<Type> originalResult = new MyMatrix<Type>(c, vector.rows, 1);

        int[] originalPosition;
        originalPosition= new int[vector.rows];

        for (int j = 0; j < vector.rows; j++)
            originalPosition[j]=j;


        for (int i = 0; i < vector.rows; i++) {
            int maxRow = i;
            int maxColumn = i;

            for (int j = i; j < matrix.rows; j++) {
                for (int k = i; k < matrix.columns; k++) {
                    if (MyMath.compare(MyMath.abs(matrix.matrix[j][k]), MyMath.abs(matrix.matrix[maxRow][maxColumn])) == 1) {
                        maxRow = j;
                        maxColumn = k;
                    }
                }
            }

            int tmp = originalPosition[i];
            originalPosition[i] = originalPosition[maxColumn];
            originalPosition[maxColumn] = tmp;

            matrix.swapRows(i,  maxRow);
            matrix.swapColumns(i, maxColumn);
            vector.swapRows(i, maxRow);

            buildSteppedMatrix(matrix, vector, i);
        }

        gaussSetResult(vector, result, matrix);

        for (int j = 0; j < vector.rows; j++)
            originalResult.matrix[originalPosition[j]][0] = result.matrix[j][0];

        return originalResult;
    }
}

