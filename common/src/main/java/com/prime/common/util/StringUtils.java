package com.prime.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.lang.Nullable;

public class StringUtils {

	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()]).{8,15})";
	private static final String REGEX = "([a-z])([A-Z])";
	private static final String REPLACEMENT = "$1_$2";
	private static final String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
	private static final String specialCharacters = "!@#$";
	private static final String numbers = "1234567890";
	private static final String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
	private static final String alfaNumric = capitalCaseLetters + numbers;

	private static final Random rnd = new Random();

	private static final String[] tensNames = { "", " ten", " twenty", " thirty", " forty", " fifty", " sixty",
			" seventy", " eighty", " ninety" };

	private static final String[] numNames = { "", " one", " two", " three", " four", " five", " six", " seven",
			" eight", " nine", " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen",
			" seventeen", " eighteen", " nineteen" };

	private static String convertLessThanOneThousand(int number) {
		String soFar;

		if (number % 100 < 20) {
			soFar = numNames[number % 100];
			number /= 100;
		} else {
			soFar = numNames[number % 10];
			number /= 10;

			soFar = tensNames[number % 10] + soFar;
			number /= 10;
		}
		if (number == 0)
			return soFar;
		return numNames[number] + " hundred" + soFar;
	}

	public static String numberToText(long number) {
		// 0 to 999 999 999 999
		if (number == 0) {
			return "zero";
		}

		String snumber = Long.toString(number);

		// pad with "0"
		String mask = "000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = df.format(number);

		// XXXnnnnnnnnn
		int billions = Integer.parseInt(snumber.substring(0, 3));
		// nnnXXXnnnnnn
		int millions = Integer.parseInt(snumber.substring(3, 6));
		// nnnnnnXXXnnn
		int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
		// nnnnnnnnnXXX
		int thousands = Integer.parseInt(snumber.substring(9, 12));

		String tradBillions;
		switch (billions) {
		case 0:
			tradBillions = "";
			break;
		case 1:
			tradBillions = convertLessThanOneThousand(billions) + " billion ";
			break;
		default:
			tradBillions = convertLessThanOneThousand(billions) + " billion ";
		}
		String result = tradBillions;

		String tradMillions;
		switch (millions) {
		case 0:
			tradMillions = "";
			break;
		case 1:
			tradMillions = convertLessThanOneThousand(millions) + " million ";
			break;
		default:
			tradMillions = convertLessThanOneThousand(millions) + " million ";
		}
		result = result + tradMillions;

		String tradHundredThousands;
		switch (hundredThousands) {
		case 0:
			tradHundredThousands = "";
			break;
		case 1:
			tradHundredThousands = "one thousand ";
			break;
		default:
			tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
		}
		result = result + tradHundredThousands;

		String tradThousand;
		tradThousand = convertLessThanOneThousand(thousands);
		result = result + tradThousand;

		// remove extra spaces!
		return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}

	public static String checkString(String s) {
		if (s == null) {
			return "";
		}
		return s.trim();
	}

	public static String pad(String str, int size, char padChar) {
		if (str == null)
			str = "";
		StringBuilder padded = new StringBuilder(str.trim());
		while (padded.length() < size) {
			padded.append(padChar);
		}
		return padded.toString();
	}

	public static String lpad(String str, int size, char padChar) {
		if (str == null)
			str = "";
		StringBuilder padded = new StringBuilder(str.trim());
		while (padded.length() < size) {
			padded.insert(0, padChar);
		}
		return padded.toString();
	}

	public static boolean isValidEmail(String email) {

		Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+;");
		Matcher matcher = pattern.matcher(email);
		boolean matchFound = matcher.matches();
		if (matchFound) {
			pattern = Pattern.compile(".+@.+\\.[a-z]+");
			String[] splitStr = email.split(";");
			for (String str : splitStr) {
				matcher = pattern.matcher(str);
				if (!matcher.matches())
					return false;
			}
		} else
			return false;

		return true;
	}

	public static boolean isValidAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern p = Pattern.compile(ePattern);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isUrlValidate(String url) {

		String urlPattern = "\\b(https?://|ftp://|www.)" + "[-A-Za-z0-9+&@#/%?=~_|!:,.;]" + "*[-A-Za-z0-9+&@#/%=~_|]";
		Pattern p = Pattern.compile(urlPattern);
		Matcher m = p.matcher(url);
		return m.matches();
	}

	public static String removeChar(String originalString, char c) {
		StringBuilder newString = new StringBuilder();
		originalString = checkString(originalString);
		if (originalString.indexOf(c) != -1) {
			for (int i = 0; i < originalString.length(); i++) {
				if (originalString.charAt(i) != c)
					newString.append(originalString.charAt(i));
			}
		} else {
			return originalString;
		}
		return newString.toString();
	}

	public static String replaceChar(String originalString, char oldChar, char newChar) {
		originalString = checkString(originalString);
		if (originalString.indexOf(oldChar) != -1) {
			originalString = originalString.replace(oldChar, newChar);
		}
		return originalString;
	}

	public static String[] parseForwardingAgent(String agentName) {
		String[] details = new String[2];
		int lastIndex = agentName.lastIndexOf("-");
		details[0] = agentName.substring(0, lastIndex).trim();
		details[1] = agentName.substring(lastIndex + 1, agentName.length()).trim();

		return details;
	}

	public static String[] splitByCodeAndName(String value) {
		String[] details = new String[2];
		int firstIndex = value.indexOf("-");
		details[0] = value.substring(0, firstIndex).trim();
		details[1] = value.substring(firstIndex + 1, value.length()).trim();

		return details;
	}

	public static String validate(String emailString) {
		Pattern pattern;
		Matcher matcher;
		StringBuilder result = new StringBuilder();
		String mailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(mailPattern);

		if (StringUtils.checkString(emailString).length() > 0) {
			if (!emailString.startsWith(",")) {
				if (emailString.contains(",")) {
					String[] splitEmails = emailString.split(",");
					for (String email : splitEmails) {
						matcher = pattern.matcher(email);
						if (!matcher.matches())
							result.append(email).append(",");
					}
					if (result.toString().trim().length() > 0)
						result = new StringBuilder(result.substring(0, result.length() - 1));
				} else {
					matcher = pattern.matcher(emailString);
					if (!matcher.matches())
						result.append(emailString);
				}
			} else {
				result.append(emailString);
			}
		}
		return result.toString();
	}

	public static boolean equals(String str1, String str2) {
		return ((str1 != null) && ((str2 == null) || str1.equals(str2)));
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		return ((str1 != null) && ((str2 == null) || str1.equalsIgnoreCase(str2)));
	}

	public static boolean isNumber(String value) {
		Pattern p2 = Pattern.compile("^[0-9]+$");
		Matcher m2 = p2.matcher(value);
		return m2.matches();
	}

	public static boolean validatePassword(final String password) {
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();

	}

	public static BigDecimal roundingToUpperHalf(BigDecimal weight) {
		if (weight == null)
			return weight;
		weight = BigDecimal.valueOf(Math.ceil(weight.doubleValue() * 2) / 2);
		weight.setScale(2);
		return weight;
	}

	public static boolean isEmpty(@Nullable Object str) {
		return str == null || Global.EMPTY.equals(str);
	}

	public static int length(final CharSequence cs) {
		return cs == null ? 0 : cs.length();
	}

	public static boolean isNotEmpty(@Nullable Object str) {
		return str != null && !"".equals(str);
	}

	public static String toSnakeCase(String value) {
		if (value == null)
			return Global.EMPTY;
		return value.replaceAll(REGEX, REPLACEMENT).toLowerCase();
	}

	public static String generatePassword(int length) {

		StringBuilder builder = new StringBuilder();

		builder.append(lowerCaseLetters.charAt(rnd.nextInt(lowerCaseLetters.length())));
		builder.append(capitalCaseLetters.charAt(rnd.nextInt(capitalCaseLetters.length())));
		builder.append(specialCharacters.charAt(rnd.nextInt(specialCharacters.length())));
		builder.append(numbers.charAt(rnd.nextInt(numbers.length())));

		for (int i = 4; i < length; i++) {
			builder.append(combinedChars.charAt(rnd.nextInt(combinedChars.length())));
		}
		return builder.toString();
	}

	public static String generateOtp(int length) {

		StringBuilder builder = new StringBuilder();
		while (length-- != 0) {
			builder.append(alfaNumric.charAt(rnd.nextInt(alfaNumric.length())));
		}
		return builder.toString();
	}

	private final static Random rndm_method = new Random();

	public static String generateBarcode() {
		return "" + (100000000 + rndm_method.nextInt(900000000));
	}

	static int largestWordLength = 0;

	/*
	 * public static void flag(String word) { String[]
	 * ignore_in_combination_with_words = new String[]{}; if (word.length() >
	 * largestWordLength) { largestWordLength = word.length(); }
	 * words.put(LongHashFunction.xx().hashChars(word.replaceAll(" ", "")),
	 * ignore_in_combination_with_words); }
	 */

	/*
	 * public static void loadConfigs() { try { BufferedReader reader = new
	 * BufferedReader(new InputStreamReader(new URL(
	 * "https://docs.google.com/spreadsheets/d/1hIEi2YG3ydav1E06Bzf2mQbGZ12kh2fe4ISgLg_UBuM/export?format=csv"
	 * ).openConnection().getInputStream())); String line = ""; int counter = 0;
	 * while ((line = reader.readLine()) != null) { counter++; String[] content =
	 * null; try { content = line.split(","); if (content.length == 0) { continue; }
	 * String word = content[0]; String[] ignore_in_combination_with_words = new
	 * String[]{}; if (content.length > 1) { ignore_in_combination_with_words =
	 * content[1].split("_"); }
	 * 
	 * if (word.length() > largestWordLength) { largestWordLength = word.length(); }
	 * words.put(LongHashFunction.xx().hashChars(word.replace(" ", "")),
	 * ignore_in_combination_with_words);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * } System.out.println("Loaded " + counter + " words to filter out"); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 */

	private static final char[][] convert = { { 'o', '0' }, { 'i', '1' }, { 'l', '1' }, { 't', '+' }, { 'e', '3' },
			{ 'i', '!' }, { 'l', '!' }, { 's', '$' }, { 'a', '&' }, { 'a', '@' }, { 'c', '(' }, { 'd', ')' },
			{ 'd', '0' }, { 'g', '6' }, { 't', '7' }, { 'g', '9' }, { 's', '5' }, { 'a', '4' } };

	private static final ThreadLocal<StringBuilder> sb = ThreadLocal.withInitial(StringBuilder::new); // make this
																										// regular if
																										// you don't
																										// need thread
																										// safety.

	/**
	 * Iterates over a String input and checks whether a cuss word was found in a
	 * list, then checks if the word should be ignored (e.g. bass contains the word
	 * *ss).
	 *
	 * @param input
	 * @return
	 */
	public static boolean badWordsFound(String input) {
		if (input == null) {
			return false;
		}

		StringBuilder sb = StringUtils.sb.get();
		sb.setLength(0);

		removeLeetspeak: for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (Character.isLetter(c)) {
				sb.append(Character.toLowerCase(c));
			} else {
				for (char[] conversion : convert) {
					if (c == conversion[1]) {
						sb.append(conversion[0]);
						continue removeLeetspeak;
					}
				}
			}
		}

		// iterate over each letter in the word
		/*
		 * for (int start = 0; start < sb.length(); start++) { // from each letter, keep
		 * going to find bad words until either the end of the sentence is reached, or
		 * the max word length is reached. for (int offset = 1; offset < (sb.length() +
		 * 1 - start) && offset < largestWordLength; offset++) { long hash =
		 * LongHashFunction.xx().hashChars(sb, start, offset); if
		 * (words.containsKey(hash)) { // for example, if you want to say the word bass,
		 * that should be possible. String[] ignoreCheck = words.get(hash); boolean
		 * ignore = false; for (int s = 0; s < ignoreCheck.length; s++) { if
		 * (indexOf(sb, ignoreCheck[s]) >= 0) { ignore = true; break; } } if (!ignore) {
		 * return true; } } } }
		 */

		return false;
	}

//	private static int indexOf(CharSequence source, CharSequence target) {
//		int sourceCount = source.length();
//		int targetCount = target.length();
//		int sourceOffset = 0;
//		int targetOffset = 0;
//
//		if (0 >= sourceCount) {
//			return (targetCount == 0 ? sourceCount : -1);
//		}
//		if (targetCount == 0) {
//			return 0;
//		}
//
//		char first = target.charAt(targetOffset);
//		int max = sourceOffset + (sourceCount - targetCount);
//
//		for (int i = sourceOffset; i <= max; i++) {
//			/* Look for first character. */
//			if (source.charAt(i) != first) {
//				while (++i <= max && source.charAt(i) != first)
//					;
//			}
//
//			/* Found first character, now look at the rest of v2 */
//			if (i <= max) {
//				int j = i + 1;
//				int end = j + targetCount - 1;
//				for (int k = targetOffset + 1; j < end && source.charAt(j) == target.charAt(k); j++, k++)
//					;
//
//				if (j == end) {
//					/* Found whole string. */
//					return i - sourceOffset;
//				}
//			}
//		}
//		return -1;
//	}

}
