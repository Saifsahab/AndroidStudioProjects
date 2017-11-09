package com.kpgsoftworks.apps.moneycounter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author ericjbruno
 * @see <a href="http://www.drdobbs.com/jvm/working-with-javas-bigdecimal/240168852">Working with Java's BigDecimal</a>
 */
public class Decimal {

    private BigDecimal bd;

    public Decimal(BigInteger val) {
        bd = new BigDecimal(val);
    }
    public Decimal(BigInteger unscaledVal, int scale) {
        bd = new BigDecimal(unscaledVal, scale);
    }
    public Decimal(BigInteger unscaledVal, int scale, MathContext mc) {
        bd = new BigDecimal(unscaledVal, scale, mc);
    }
    public Decimal(BigInteger val, MathContext mc) {
        bd = new BigDecimal(val, mc);
    }
    public Decimal(char[] in) {
        bd = new BigDecimal(in);
    }
    public Decimal(char[] in, int offset, int len) {
        bd = new BigDecimal(in, offset, len);
    }
    public Decimal(char[] in, int offset, int len, MathContext mc) {
        bd = new BigDecimal(in, offset, len, mc);
    }
    public Decimal(char[] in, MathContext mc) {
        bd = new BigDecimal(in, mc);
    }
    public Decimal(double val) {
        bd = new BigDecimal(val);
    }
    public Decimal(double val, MathContext mc) {
        bd = new BigDecimal(val, mc);
    }
    public Decimal(int val) {
        bd = new BigDecimal(val);
    }
    public Decimal(int val, MathContext mc) {
        bd = new BigDecimal(val, mc);
    }
    public Decimal(long val) {
        bd = new BigDecimal(val);
    }
    public Decimal(long val, MathContext mc) {
        bd = new BigDecimal(val, mc);
    }
    public Decimal(String val) {
        bd = new BigDecimal(val);
    }
    public Decimal(String val, MathContext mc)  {
        bd = new BigDecimal(val, mc);
    }

    public Decimal(BigDecimal bd) {
        this.bd = bd;
    }

    public Decimal() {
    }

    ////////////

    public void setBigDecimal(BigDecimal bd) {
        this.bd = bd;
    }

    public BigDecimal getBigDecimal() {
        return bd;
    }

    public static Decimal valueOf(
            long unscaledVal, int scale) {
        BigDecimal bd = BigDecimal.valueOf(unscaledVal, scale);
        return new Decimal(bd);
    }

    public static Decimal valueOf(long val) {
        BigDecimal bd = BigDecimal.valueOf(val);
        return new Decimal(bd);
    }

    public static Decimal valueOf(double val) {
        BigDecimal bd = BigDecimal.valueOf(val);
        return new Decimal(bd);
    }

    public void add(Decimal dec) {
        bd = bd.add( dec.getBigDecimal() );
    }

    public void add(BigDecimal bdParam) {
        bd = bd.add(bdParam);
    }

    public void add(BigDecimal bdParam, MathContext mc) {
        bd = bd.add(bdParam, mc);
    }

    public void subtract(Decimal dec) {
        bd = bd.subtract( dec.getBigDecimal() );
    }

    public void subtract(BigDecimal bdParam) {
        bd = bd.subtract(bdParam);
    }

    public void subtract(BigDecimal bdParam, MathContext mc) {
        bd = bd.subtract(bdParam, mc);
    }

    public void multiply(Decimal dec) {
        bd = bd.multiply( dec.getBigDecimal() );
    }

    public void multiply(BigDecimal bdParam) {
        bd = bd.multiply(bdParam);
    }

    public void multiply(BigDecimal bdParam, MathContext mc) {
        bd = bd.multiply(bdParam, mc);
    }

    public void divide(Decimal dec) {
        bd = bd.divide( dec.getBigDecimal() );
    }

    public void divide(BigDecimal bdParam) {
        bd = bd.divide(bdParam);
    }

    public void divide(BigDecimal bdParam, MathContext mc) {
        bd = bd.divide(bdParam, mc);
    }

    public void divide(BigDecimal divisor,
                       int scale,
                       int roundingMode) {
        bd = bd.divide(divisor, scale, roundingMode);
    }

    public void divide(BigDecimal divisor,
                       int scale,
                       RoundingMode roundingMode) {
        bd = bd.divide(divisor, scale, roundingMode);
    }

    public void divide(BigDecimal divisor,
                       int roundingMode) {
        bd = bd.divide(divisor, roundingMode);
    }

    public void divide(BigDecimal divisor,
                       RoundingMode roundingMode) {
        bd = bd.divide(divisor, roundingMode);
    }

    public void pow(int n) {
        bd = bd.pow(n);
    }

    public void pow(int n,
                    MathContext mc) {
        bd = bd.pow(n, mc);
    }

    public void negate() {
        bd = bd.negate();
    }

    public void negate(MathContext mc) {
        bd = bd.negate(mc);
    }

    public void plus() {
        bd = bd.plus();
    }

    public void plus(MathContext mc) {
        bd = bd.plus(mc);
    }

    public void round(MathContext mc) {
        bd = bd.round(mc);
    }

    public void setScale(int newScale,
                         RoundingMode roundingMode) {
        bd = bd.setScale(newScale, roundingMode);
    }

    public void setScale(int newScale,
                         int roundingMode) {
        bd = bd.setScale(newScale, roundingMode);
    }

    public void setScale(int newScale) {
        bd = bd.setScale(newScale);
    }

    public void movePointLeft(int n) {
        bd = bd.movePointLeft(n);
    }

    public void movePointRight(int n) {
        bd = bd.movePointRight(n);
    }

    public void scaleByPowerOfTen(int n) {
        bd = bd.scaleByPowerOfTen(n);
    }

    public void stripTrailingZeros() {
        bd = bd.stripTrailingZeros();
    }

    public int compareTo(BigDecimal val) {
        return bd.compareTo(val);
    }

    public boolean equals(Object x) {
        return bd.equals(x);
    }

    public BigDecimal min(BigDecimal val) {
        return bd.min(val);
    }

    public Decimal min(Decimal val) {
        if ( bd.min(val.getBigDecimal()).equals(bd) )
            return this;
        else
            return val;
    }

    public BigDecimal max(BigDecimal val) {
        return bd.max(val);
    }

    public Decimal max(Decimal val) {
        if ( bd.max(val.getBigDecimal()).equals(bd) )
            return this;
        else
            return val;
    }

    public int hashCode() {
        return bd.hashCode();
    }

    public String toString() {
        return bd.toString();
    }

    public String toEngineeringString() {
        return bd.toEngineeringString();
    }

    public String toPlainString() {
        return bd.toPlainString();
    }

    public BigInteger toBigInteger() {
        return bd.toBigInteger();
    }

    public BigDecimal abs() {
        return bd.abs();
    }

    public BigDecimal abs(MathContext mc) {
        return bd.abs();
    }

    public int signum() {
        return bd.signum();
    }

    public int scale() {
        return bd.scale();
    }

    public int precision() {
        return bd.precision();
    }

    public BigInteger toBigIntegerExact() {
        return bd.toBigIntegerExact();
    }

    public long longValue() {
        return bd.longValue();
    }

    public long longValueExact() {
        return bd.longValueExact();
    }

    public int intValue() {
        return bd.intValue();
    }

    public int intValueExact() {
        return bd.intValueExact();
    }

    public short shortValue() {
        return bd.shortValue();
    }

    public short shortValueExact() {
        return bd.shortValueExact();
    }

    public byte byteValue() {
        return bd.byteValue();
    }

    public byte byteValueExact() {
        return bd.byteValueExact();
    }

    public float floatValue() {
        return bd.floatValue();
    }

    public double doubleValue() {
        return bd.doubleValue();
    }

    public BigInteger unscaledValue() {
        return bd.unscaledValue();
    }

    public BigDecimal divideToIntegralValue(BigDecimal divisor) {
        return bd.divideToIntegralValue(divisor);
    }

    public BigDecimal divideToIntegralValue(BigDecimal divisor,
                                            MathContext mc) {
        return bd.divideToIntegralValue(divisor, mc);
    }

    public BigDecimal remainder(BigDecimal divisor) {
        return bd.remainder(divisor);
    }

    public BigDecimal remainder(BigDecimal divisor,
                                MathContext mc) {
        return bd.remainder(divisor, mc);
    }

    public BigDecimal[] divideAndRemainder(BigDecimal divisor) {
        return bd.divideAndRemainder(divisor);
    }

    public BigDecimal[] divideAndRemainder(BigDecimal divisor,
                                           MathContext mc) {
        return bd.divideAndRemainder(divisor, mc);
    }

    public BigDecimal ulp() {
        return bd.ulp();
    }

}
