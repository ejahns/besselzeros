package com.github.ejahns;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Precision;

/**
 * Container for finding values of the Bessel function of the first kind.
 */
public class BesselJ {

	/**
	 * Evaluates the Bessel function for a given index and argument. Returns an
	 * unspecified number of digits.
	 *
	 * @param index    the index of the Bessel function
	 * @param argument the argument of the Bessel function
	 * @return the value of the Bessel function
	 */
	public static double evaluate(double index, double argument) {
		return org.apache.commons.math3.special.BesselJ.value(index, argument);
	}

	/**
	 * Evaluates the Bessel function for a given index and argument. Returns
	 * a value rounded at the decimal place specified by <code>precision</code>.
	 *
	 * @param index     the index of the Bessel function
	 * @param argument  the argument of the Bessel function
	 * @param precision the number of decimal places to return
	 * @return the value of the Bessel function
	 */
	public static double evaluate(double index, double argument, int precision) {
		return Precision.round(evaluate(index, argument), precision);
	}

	/**
	 * Returns the smallest value of the argument of the Bessel function larger than
	 * <code>start</code> for which the Bessel function evaluates to zero.
	 *
	 * @param index      the index of the Bessel function
	 * @param start      the starting point for locating a zero
	 * @param resolution the number of decimal places to return
	 * @return the value for which the Bessel function is zero
	 */
	public static double findZeroAfter(double index, double start, int resolution) {
		if (start < index) {
			start = index;
		}
		return new OscillatingFunction() {
			@Override
			public Double apply(double value) {
				return org.apache.commons.math3.special.BesselJ.value(index, value);
			}
		}.findCrossing(start, resolution, 0);
	}

	/**
	 * Returns the smallest index of the Bessel function larger than
	 * <code>start</code> such that <code>zero</code> is a zero of the Bessel function.
	 *
	 * @param zero       the desired zero
	 * @param start      the starting point for locating an index
	 * @param resolution the number of decimal places to return
	 * @return the index for which <code>zero</code> is a zero
	 */
	public static double findIndexAfter(double zero, double start, int resolution) {
		return new OscillatingFunction() {
			@Override
			public Double apply(double value) {
				return org.apache.commons.math3.special.BesselJ.value(value, zero);
			}
		}.findCrossing(start, resolution, 0);
	}

	/**
	 * Returns a list of all indices of the Bessel function for which
	 * <code>zero</code> is a zero.
	 *
	 * @param zero       the desired zero
	 * @param resolution the number of decimal places to return
	 * @return the list
	 * @throws IllegalZeroException if <code>zero</code> is smaller than the first zero of J_0
	 */
	public static List<Double> findIndices(double zero, int resolution) throws IllegalZeroException {
		List<Double> indices = new ArrayList<>();
		double bessel0Zero = findZeroAfter(0.0, 0.0, resolution);
		if (zero < bessel0Zero) {
			throw new IllegalZeroException();
		}
		int count = 0;
		while (zero > bessel0Zero) {
			count++;
			bessel0Zero = findZeroAfter(0.0, bessel0Zero, resolution);
			if (zero == bessel0Zero) {
				indices.add(0.0);
				break;
			}
		}
		double lastIndex = 0.0;
		while (count-- > 0) {
			lastIndex = findIndexAfter(zero, lastIndex+1, resolution);
			indices.add(lastIndex);
		}
		return indices;
	}
}
