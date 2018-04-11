package com.github.ejahns;

import java.util.function.DoubleFunction;

import org.apache.commons.math3.util.Precision;

/**
 * Represents a real-valued function that is oscillatory and has crossings at various points along the x-axis.
 */
public abstract class OscillatingFunction implements DoubleFunction<Double> {

    /**
     * Identifies the first value of the argument greater than <code>start</code> for which
     * the function vanishes. The largest step size taken in identifying the crossing
     * is <code>10^-startingPower</code>. Assumes that <code>start</code> does not evaluate to
     * less that <code>Double.MIN_VALUE</code>.
     *
     * @param start      the minimum trial value of the argument
     * @param resolution the number of decimal places for the result
     * @param startingPower   the maximum negative power of 10 step size
     * @return the x-value for which the function vanishes
     */
    public double findCrossing(double start, int resolution, int startingPower) {
        boolean isPositive = this.apply(start) > 0;

        double trialValue = start;
        int currentPower = startingPower;
        while (currentPower <= resolution + 1) {
            trialValue += Math.pow(10, -currentPower);
            if (this.apply(trialValue) > 0 != isPositive) {
                trialValue -= Math.pow(10, -currentPower);
                currentPower += 1;
            }
        }
        trialValue = Precision.round(trialValue, resolution + 1);
        return Precision.round(trialValue, resolution);
    }

    public double findExtremum(double start, int resolution, int maxPower) {
        boolean isIncreasing;
        if (start > Math.pow(10, -resolution)) {
            double step = Math.pow(10, -resolution);
            while ((this.apply(start - step) < this.apply(start)) != (this.apply(start + step) > this.apply(start))) {
                step /= 10;
            }
            isIncreasing = (this.apply(start + step) > this.apply(start));

        } else {
            isIncreasing = (this.apply(start + Math.pow(10, -resolution)) > this.apply(start + Math.pow(10, -resolution)));
        }
        return 0.0;
    }
}
