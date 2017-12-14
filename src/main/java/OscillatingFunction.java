import java.util.function.DoubleFunction;

import org.apache.commons.math3.util.Precision;

abstract class OscillatingFunction implements DoubleFunction<Double> {

	double findCrossing(double start, int resolution, int minPower) {
		boolean isPositive = (this.apply(start + 5.0 * Math.pow(10, -resolution - 1)) > 0);
		double trialValue = start;
		int currentPower = minPower;
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
