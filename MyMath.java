import java.math.BigInteger;

public class MyMath {
    public static <Type> Type add(Type num1, Type num2) {
        if(num1 instanceof Integer && num2 instanceof Integer)
            return (Type) (Integer) ((Integer) num1 + (Integer) num2);
        else if(num1 instanceof Float && num2 instanceof Float)
            return (Type) (Float) ((Float) num1 + (Float) num2);
        else if(num1 instanceof Double && num2 instanceof Double)
            return (Type) (Double) ((Double) num1 + (Double) num2);
        else if(num1 instanceof Fraction && num2 instanceof Fraction)
            return (Type) Fraction.add((Fraction) num1, (Fraction) num2);
        else throw new IllegalArgumentException("Zły typ");
    }

    public static <Type> Type sub(Type num1, Type num2) {
        if(num1 instanceof Integer && num2 instanceof Integer)
            return (Type) (Integer) ((Integer) num1 - (Integer) num2);
        else if(num1 instanceof Float && num2 instanceof Float)
            return (Type) (Float) ((Float) num1 - (Float) num2);
        else if(num1 instanceof Double && num2 instanceof Double)
            return (Type) (Double) ((Double) num1 - (Double) num2);
        else if(num1 instanceof Fraction && num2 instanceof Fraction)
            return (Type) Fraction.sub((Fraction) num1, (Fraction) num2);
        else throw new IllegalArgumentException("Zły typ");
    }


    public static <Type> Type mul(Type num1, Type num2) {
        if(num1 instanceof Integer && num2 instanceof Integer)
            return (Type) (Integer) ((Integer) num1 * (Integer) num2);
        else if(num1 instanceof Float && num2 instanceof Float)
            return (Type) (Float) ((Float) num1 * (Float) num2);
        else if(num1 instanceof Double && num2 instanceof Double)
            return (Type) (Double) ((Double) num1 * (Double) num2);
        else if(num1 instanceof Fraction && num2 instanceof Fraction)
            return (Type) Fraction.mul((Fraction) num1, (Fraction) num2);
        else throw new IllegalArgumentException("Zły typ");
    }

    public static <Type> Type div(Type num1, Type num2) {
        if(num1 instanceof Integer && num2 instanceof Integer)
            return (Type) (Integer) ((Integer) num1 / (Integer) num2);
        else if(num1 instanceof Float && num2 instanceof Float)
            return (Type) (Float) ((Float) num1 / (Float) num2);
        else if(num1 instanceof Double && num2 instanceof Double)
            return (Type) (Double) ((Double) num1 / (Double) num2);
        else if(num1 instanceof Fraction && num2 instanceof Fraction)
            return (Type) Fraction.div((Fraction) num1, (Fraction) num2);
        else throw new IllegalArgumentException("Zły typ");
    }

    public static <Type> Type abs(Type num1) {
        if(num1 instanceof Integer)
            if((Integer) num1 < 0)
                return (Type) (Integer) ((Integer) num1*(-1));
            else return num1;
        else if(num1 instanceof Float)
            if((Float) num1 < 0)
                return (Type) (Float) ((Float) num1*(-1));
            else return num1;
        else if(num1 instanceof Double)
            if((Double) num1 < 0)
                return (Type) (Double) ((Double) num1*(-1));
            else return num1;
        else if(num1 instanceof Fraction)
            if(((Fraction) num1).isPositive())
                return num1;
            else
                return (Type) new Fraction(((Fraction) num1).getNominator().multiply(BigInteger.valueOf(-1)),
                        ((Fraction) num1).getDenominator());
        else throw new IllegalArgumentException("Zły typ");
    }

    public static <Type> int compare(Type num1, Type num2) {
        if(num1 instanceof Integer && num2 instanceof Integer)
            return Integer.compare((Integer) num1, (Integer)  num2);
        else if(num1 instanceof Float && num2 instanceof Float)
            return Float.compare((Float) num1, (Float)  num2);
        else if(num1 instanceof Double && num2 instanceof Double)
            return Double.compare((Double) num1, (Double)  num2);
        else if(num1 instanceof Fraction && num2 instanceof Fraction)
            return Fraction.compare((Fraction) num1, (Fraction)  num2);
        else throw new IllegalArgumentException("Zły typ");
    }

    public static <Type> Type sqrt(Type num1) {
        if(num1 instanceof Float)
            return (Type) (Double) Math.sqrt((Float) num1);
        else if(num1 instanceof Double)
            return (Type) (Double) Math.sqrt((Double) num1);
        else if(num1 instanceof Fraction)
            return (Type) num1;
        else throw new IllegalArgumentException("Zły typ");
    }

}

