import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Precision;

public class BesselJ {

	public static double evaluate(double index, double argument) {
		return org.apache.commons.math3.special.BesselJ.value(index, argument);
	}

	public static double evaluate(double index, double argument, int precision) {
		return Precision.round(evaluate(index, argument), precision);
	}

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

	public static double findIndexAfter(double zero, double start, int resolution) {
		return new OscillatingFunction() {
			@Override
			public Double apply(double value) {
				return org.apache.commons.math3.special.BesselJ.value(value, zero);
			}
		}.findCrossing(start, resolution, 0);
	}

	public static List<Double> findIndices(double zero, int resolution) {
		List<Double> indices = new ArrayList<>();
		double bessel0Zero = findZeroAfter(0.0, 0.0, resolution);
		if (zero < bessel0Zero) {
			throw new IllegalArgumentException("no zeroes exist before the first zero of J_0");
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
			lastIndex = findIndexAfter(zero, lastIndex, resolution);
			indices.add(lastIndex);
		}
		return indices;
	}
}
