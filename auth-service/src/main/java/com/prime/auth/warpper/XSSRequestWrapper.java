package com.prime.auth.warpper;

import com.prime.common.util.Global;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

	private static final String[] escapeSearchList = { "\0" };
	private static final String[] escapeReplacementList = { Global.EMPTY };

	private static final Pattern[] patterns = new Pattern[] {
			Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("src[\r\n]*=[\r\n]*'(.*?)'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("src[\r\n]*=[\r\n]*\"(.*?)\"",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
			Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
			Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL) };

	public XSSRequestWrapper(HttpServletRequest servletRequest) {

		super(servletRequest);
	}

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return values;
		}

		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = stripXSS(values[i]);
		}

		return encodedValues;
	}

	@Override
	public String getParameter(String parameter) {

		String value = super.getParameter(parameter);

		return stripXSS(value);
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		return stripXSS(value);
	}

	private String stripXSS(String value) {

		if (value != null) {
			value = StringUtils.replaceEach(value, escapeSearchList, escapeReplacementList);
			for (Pattern scriptPattern : patterns) {
				value = scriptPattern.matcher(value).replaceAll(Global.EMPTY);
			}
		}
		return value;
	}
}