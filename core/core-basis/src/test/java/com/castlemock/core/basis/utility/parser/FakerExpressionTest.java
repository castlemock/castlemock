package com.castlemock.core.basis.utility.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.castlemock.core.basis.utility.parser.expression.ExpressionInput;
import com.castlemock.core.basis.utility.parser.expression.FakerExpression;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgumentString;

public class FakerExpressionTest {

	private final FakerExpression fakerExpression = new FakerExpression();

	@Test
	public void testMatch() {
		assertTrue(fakerExpression.match("FAKER"));
		assertTrue(fakerExpression.match("faker"));
	}
	
	@Test
	public void testWithoutParams() {
		String result = fakerExpression.transform(createExpressionInput("name().fullName()"));
		assertNotNull(result);
		assertTrue(result.length() > 0);
	}

	@Test
	public void testWithParamsInt() {
		String result = fakerExpression.transform(createExpressionInput("number().numberBetween(1, 2)"));
		assertTrue(Arrays.asList("1", "2").contains(result));
	}

	@Test
	public void testWithParamsLong() {
		String result = fakerExpression
				.transform(createExpressionInput("number().numberBetween(2147483647000L, 2147483647001L)"));
		assertTrue(Arrays.asList("2147483647000", "2147483647001").contains(result));
	}

	@Test
	public void testWithParamsDouble() {
		String result = fakerExpression.transform(createExpressionInput("commerce().price(10.5, 1000.29)"));
		Double resultDouble = Double.valueOf(result);
		assertTrue(resultDouble >= 10.5 && resultDouble <= 1000.29);
	}

	@Test
	public void testWithParamBoolean() {
		String result = fakerExpression.transform(createExpressionInput("address().streetAddress(true)"));
		assertTrue(result.length() > 0);
	}

	@Test
	public void testWithParamString() {
		String result = fakerExpression.transform(createExpressionInput("address().zipCodeByState('FL')"));
		assertTrue(result.length() > 0);
	}

	@Test
	public void testWithParamEnumAndDate() {
		String fakerApi = "date().future(7, T(java.util.concurrent.TimeUnit).DAYS, new java.util.Date())";
		String springExpression = "new java.text.SimpleDateFormat(\"YYYY-MM-dd\").format(" + fakerApi + ")";
		String result = fakerExpression.transform(createExpressionInput(springExpression));
		assertTrue(result.length() > 0);
	}
	
	@Test
	public void testLocale() {
		String result = fakerExpression.transform(createExpressionInput("name().fullName()", "pt-BR"));
		assertNotNull(result);
		assertTrue(result.length() > 0);
	}
	
	@Test
	public void testInvalidLocale() {
		String result = fakerExpression.transform(createExpressionInput("name().fullName()", "invalidLocale"));
		assertEquals("", result);
	}
	
	@Test
	public void testInvalidApi() {
		String result = fakerExpression.transform(createExpressionInput("notExisting().notExisting()"));
		assertEquals("", result);
	}

	private ExpressionInput createExpressionInput(String argumentApiValue) {
		return createExpressionInput(argumentApiValue, null);
	}
	
	private ExpressionInput createExpressionInput(String argumentApiValue, String argumentLocaleValue) {
		final ExpressionInput expressionInput = new ExpressionInput(FakerExpression.IDENTIFIER);
		expressionInput.addArgument(FakerExpression.API_ARGUMENT, new ExpressionArgumentString(argumentApiValue));
		if (argumentApiValue != null) {
			expressionInput.addArgument(FakerExpression.LOCALE_ARGUMENT, new ExpressionArgumentString(argumentLocaleValue));	
		}
		return expressionInput;
	}

}
