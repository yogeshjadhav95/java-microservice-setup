package com.prime.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.prime.common.library.SecurityLibrary;

public class CalculationUtil {

	private static final DecimalFormat df1 = new DecimalFormat("#,###,###,##0.0");
	private static final DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
	private static final DecimalFormat df3 = new DecimalFormat("#,###,###,##0.000");
	private static final DecimalFormat df4 = new DecimalFormat("#,###,###,##0.0000");

	private static final String ZERO = "0";

	public static BigDecimal multiply(BigDecimal... number) {
		BigDecimal product = BigDecimal.ONE;

		for (BigDecimal bigDecimal : number) {
			if (bigDecimal == null) {
				bigDecimal = BigDecimal.ZERO;
			}
			product = round(product).multiply(round(bigDecimal));
		}
		return round(product);
	}

	public static BigDecimal add(BigDecimal... number) {
		BigDecimal product = BigDecimal.ZERO;
		for (BigDecimal bigDecimal : number) {
			if (bigDecimal == null) {
				bigDecimal = BigDecimal.ZERO;
			}
			product = round(product).add(round(bigDecimal));
		}
		return round(product);
	}

	public static BigDecimal sub(BigDecimal... number) {
		BigDecimal product = BigDecimal.ZERO;

		for (BigDecimal bigDecimal : number) {
			if (bigDecimal == null) {
				bigDecimal = BigDecimal.ZERO;
			}
			if (bigDecimal.compareTo(product) > 0) {
				product = round(bigDecimal).subtract(round(product));
			} else {
				product = round(product).subtract(round(bigDecimal));
			}
		}
		return round(product);
	}

	public static BigDecimal sub(boolean isPlan, BigDecimal... number) {
		BigDecimal product = BigDecimal.ZERO;

		for (BigDecimal bigDecimal : number) {
			if (bigDecimal == null) {
				bigDecimal = BigDecimal.ZERO;
			}
			product = round(product).subtract(round(bigDecimal));
		}
		return round(product);
	}

	public static BigDecimal percentOfNumber(BigDecimal number, BigDecimal percent) {
		if (number == null || percent == null || BigDecimal.ZERO.equals(number) || BigDecimal.ZERO.equals(percent)) {
			return BigDecimal.ZERO;
		}
		BigDecimal divdant = new BigDecimal("100");
		if (percent.compareTo(divdant) > 0)
			return BigDecimal.ZERO;

		return round(round(number)
				.multiply(round(percent).divide(round(divdant), SecurityLibrary.getDecimalScale(), RoundingMode.DOWN)));
	}

	public static BigDecimal devide(BigDecimal number, BigDecimal percent) {

		if (number == null || percent == null || BigDecimal.ZERO.equals(number) || BigDecimal.ZERO.equals(percent)) {
			return BigDecimal.ZERO;
		}
		return round(round(number).divide(round(percent), SecurityLibrary.getDecimalScale(), RoundingMode.DOWN));
	}

	private static BigDecimal round(BigDecimal number) {
		return number.setScale(SecurityLibrary.getDecimalScale(), RoundingMode.DOWN);
	}

	public static String formatDecimal(BigDecimal value, int decimalScale) {

		if (value == null)
			return ZERO;

		if (decimalScale == 1) {
			return df1.format(value);
		}
		if (decimalScale == 2) {
			return df2.format(value);
		}
		if (decimalScale == 3) {
			return df3.format(value);
		}
		return df4.format(value);
	}
}
