/*
 * Copyright 2024 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.model.core.utility.parser;

import com.castlemock.model.core.utility.parser.expression.ExpressionInput;
import com.castlemock.model.core.utility.parser.expression.FakerExpression;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentString;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class FakerExpressionTest {

	private final FakerExpression fakerExpression = new FakerExpression();

	@Test
	public void testMatch() {
		assertTrue(fakerExpression.match("FAKER"));
		assertTrue(fakerExpression.match("faker"));
	}

	@Test
	public void testWithoutParams() {
		final String result = fakerExpression.transform(createExpressionInput("name().fullName()"));
		assertNotNull(result);
        assertFalse(result.isEmpty());
	}

	@Test
	public void testWithParamsInt() {
		final String result = fakerExpression.transform(createExpressionInput("number().numberBetween(1, 2)"));
		assertTrue(Arrays.asList("1", "2").contains(result));
	}

	@Test
	public void testWithParamsLong() {
		final String result = fakerExpression
				.transform(createExpressionInput("number().numberBetween(2147483647000L, 2147483647001L)"));
		assertTrue(Arrays.asList("2147483647000", "2147483647001").contains(result));
	}

	@Test
	public void testWithParamsDouble() throws ParseException {
		Locale.setDefault(Locale.of("en", "UK"));
		final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.UK);
		final String result = fakerExpression.transform(createExpressionInput("commerce().price(10.5, 1000.29)"));
		final double resultDouble = numberFormat.parse(result).doubleValue();
		assertTrue(resultDouble >= 10.5 && resultDouble <= 1000.29);
	}

	@Test
	public void testWithParamBoolean() {
		final String result = fakerExpression.transform(createExpressionInput("address().streetAddress(true)"));
		assertFalse(result.isEmpty());
	}

	@Test
	public void testWithParamString() {
		final String result = fakerExpression.transform(createExpressionInput("address().zipCodeByState('FL')"));
		assertFalse(result.isEmpty());
	}

	@Test
	public void testWithParamEnumAndDate() {
		final String fakerApi = "date().future(7, T(java.util.concurrent.TimeUnit).DAYS, new java.util.Date())";
		final String springExpression = "new java.text.SimpleDateFormat(\"YYYY-MM-dd\").format(" + fakerApi + ")";
		final String result = fakerExpression.transform(createExpressionInput(springExpression));
        assertFalse(result.isEmpty());
	}

	@Test
	public void testLocale() {
		final String result = fakerExpression.transform(createExpressionInput("name().fullName()", "pt-BR"));
		assertNotNull(result);
        assertFalse(result.isEmpty());
	}

	@Test
	public void testInvalidApi() {
		final String result = fakerExpression.transform(createExpressionInput("notExisting().notExisting()"));
		assertEquals("", result);
	}

	private ExpressionInput createExpressionInput(final String argumentApiValue) {
		return createExpressionInput(argumentApiValue, null);
	}

	private ExpressionInput createExpressionInput(final String argumentApiValue, final String argumentLocaleValue) {
		final ExpressionInput expressionInput = new ExpressionInput(FakerExpression.IDENTIFIER);
		expressionInput.addArgument(FakerExpression.API_ARGUMENT, new ExpressionArgumentString(argumentApiValue));
		if (argumentLocaleValue != null) {
			expressionInput.addArgument(FakerExpression.LOCALE_ARGUMENT, new ExpressionArgumentString(argumentLocaleValue));
		}
		return expressionInput;
	}

}
