import java.math.BigInteger;
import java.util.Random;

public class Randomizer {
    private static final BigInteger minBigInt = BigInteger.valueOf(-65536);
    private static final BigInteger maxBigInt = BigInteger.valueOf(65535);
    private static Random random = new Random();

    // generuje zestaw losowych macierzy typow Float, Double i Fraction
    public static MatrixSet generateMatrix(int rows, int columns) {
        Fraction[][] fractionTab = new Fraction[rows][columns];
        Double[][] doubleTab = new Double[rows][columns];
        Float[][] floatTab = new Float[rows][columns];
        Fraction fractionValue;
        Double doubleValue;
        Float floatValue;

        for(int i = 0; i< rows; i++) {
            for(int j = 0; j < columns; j++) {
                BigInteger randomBigInteger = new BigInteger(maxBigInt.bitLength(), random); // <0;65535>
                BigInteger nominator = randomBigInteger.multiply(BigInteger.valueOf(2)); // <0;131070>
                if(nominator.compareTo(maxBigInt) > 0) // nominator > 65535
                    nominator = nominator.subtract(maxBigInt); // -65535
                else if(nominator.compareTo(maxBigInt) <= 0) // nominator <= 65535
                    nominator = nominator.add(minBigInt); // -65536
                fractionValue = new Fraction(nominator, BigInteger.valueOf(65536));
                doubleValue = fractionValue.getNominator().doubleValue() / fractionValue.getDenominator().doubleValue();
                floatValue = fractionValue.getNominator().floatValue() / fractionValue.getDenominator().floatValue();
                fractionTab[i][j] = fractionValue;;
                doubleTab[i][j] = doubleValue;
                floatTab[i][j] = floatValue;
            }
        }

        return new MatrixSet(new MyMatrix<Float>(Float.class, floatTab),
                new MyMatrix<Double>(Double.class, doubleTab),
                new MyMatrix<Fraction>(Fraction.class, fractionTab));
    }
}