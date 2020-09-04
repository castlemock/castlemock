package com.castlemock.core.basis.utility.parser.expression;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgumentString;
import com.github.javafaker.Faker;

/**
 * {@link FakerExpression} is an {@link Expression} and will call {@link Faker}
 * methods api to transform an matching input string to a fake string.
 */
public class FakerExpression extends AbstractExpression {

	private static final Logger LOGGER = LoggerFactory.getLogger(FakerExpression.class);

	public static final String IDENTIFIER = "FAKER";
	public static final String API_ARGUMENT = "api";
	public static final String LOCALE_ARGUMENT = "locale";
	private final Map<String, Faker> fakerByLocale = new HashMap<>();

	/**
	 * Will transform the provided <code>input</code> to a fake string. The implementation use 
	 * Spring Expression Language (SpEL) to call {@link Faker} methods api.
	 *
	 * @param input The input string that will be transformed.
	 * @return A transformed <code>input</code>.
	 * 
	 * @see https://docs.spring.io/spring/docs/5.2.x/spring-framework-reference/core.html#expressions
	 * @see https://github.com/DiUS/java-faker
	 */
	@Override
	public String transform(ExpressionInput input) {
		final ExpressionArgument<?> apiArgument = input.getArgument(API_ARGUMENT);
		final ExpressionArgument<?> localeArgument = input.getArgument(LOCALE_ARGUMENT);

		try {
			Faker faker = getFaker(getLocaleLanguageTag(localeArgument));
			String springExpression = getApiArgumentString(apiArgument);

			org.springframework.expression.ExpressionParser parser = new SpelExpressionParser();
			org.springframework.expression.Expression exp = parser.parseExpression(springExpression);
			org.springframework.expression.EvaluationContext context = new StandardEvaluationContext(faker);

			Object result = exp.getValue(context);

			if (result != null) {
				return String.valueOf(result);
			}
		} catch (Exception e) {
			LOGGER.warn("Can not transform ${FAKER(api=\"" + apiArgument.getValue() + "\", locale=\""
					+ localeArgument.getValue() + "\")", e);
		}

		return "";
	}

	@Override
	public boolean match(String input) {
		return IDENTIFIER.equalsIgnoreCase(input);
	}

	private Faker getFaker(final String localeLanguageTag) {
		if (!fakerByLocale.containsKey(localeLanguageTag)) {
			fakerByLocale.put(localeLanguageTag, createFaker(localeLanguageTag));
		}

		return fakerByLocale.get(localeLanguageTag);
	}

	private Faker createFaker(final String localeLanguageTag) {
		if (localeLanguageTag == null) {
			return new Faker();
		}
		return new Faker(Locale.forLanguageTag(localeLanguageTag));
	}

	private String getLocaleLanguageTag(final ExpressionArgument<?> localeArgument) {
		if (localeArgument instanceof ExpressionArgumentString) {
			return ((ExpressionArgumentString) localeArgument).getValue();
		}
		return null;
	}

	private String getApiArgumentString(final ExpressionArgument<?> apiArgument) {
		if (apiArgument instanceof ExpressionArgumentString) {
			return ((ExpressionArgumentString) apiArgument).getValue();
		}
		return null;
	}

}
