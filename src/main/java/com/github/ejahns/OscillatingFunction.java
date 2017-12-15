package com.github.ejahns;

import java.util.function.DoubleFunction;

import org.apache.commons.math3.util.Precision;

/**
 * Represents a real-valued function that is oscillatory and has crossings at various points along the x-axis.
 */
public abstract class OscillatingFunction implements DoubleFunction<Double> {

	/**
	 * Identifies the first value of the argument greater than <code>start</code> for which
	 * the function crosses the x-axis. The largest step size taken in identifying the crossing
	 * is <code>10^maxPower</code>.
	 *
	 * @param start      the minimum trial value of the argument
	 * @param resolution the number of decimal places for the result
	 * @param maxPower   the maximum negative power of 10 step size
	 * @return the x-value of the crossing point
	 */
	public double findCrossing(double start, int resolution, int maxPower) {
		boolean isPositive;
		if (start > Math.pow(10, -resolution)) {
			double step = Math.pow(10, -resolution);
			double left = start - step;
			double right = start + step;
			while (Math.signum(this.apply(left -= step)) == this.apply(right += step)) {

			}
			isPositive = (this.apply(right) > 0);
		}
		else {
			isPositive = (this.apply(start + Math.pow(10, -resolution)) > 0);
		}

		double trialValue = start;
		int currentPower = maxPower;
		while (currentPower <= resolution + 1) {
			trialValue += Math.pow(10, -currentPower);
			if (this.apply(trialValue) > 0 != isPositive) {
				trialValue -= Math.pow(10, -currentPower);
				currentPower += 1;
			}
		}
		return Precision.round(trialValue, resolution);
	}
}
