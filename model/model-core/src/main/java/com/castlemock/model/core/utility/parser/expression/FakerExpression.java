package com.castlemock.model.core.utility.parser.expression;

import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentString;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

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
	 */
	@Override
	public String transform(final ExpressionInput input) {
		final ExpressionArgument<?> apiArgument = input.getArgument(API_ARGUMENT);

		if(apiArgument == null) {
			return "";
		}

		final ExpressionArgument<?> localeArgument = input.getArgument(LOCALE_ARGUMENT);
		final String locale = getLocaleLanguageTag(localeArgument);
		try {
			final Faker faker = getFaker(locale);
			final String springExpression = getApiArgumentString(apiArgument)
					.orElseThrow(() -> new IllegalStateException("Unable to extract api argument"));

			org.springframework.expression.ExpressionParser parser = new SpelExpressionParser();
			org.springframework.expression.Expression exp = parser.parseExpression(springExpression);
			org.springframework.expression.EvaluationContext context = new StandardEvaluationContext(faker);

			final Object result = exp.getValue(context);

			if (result != null) {
				return String.valueOf(result);
			}
		} catch (Exception e) {
			LOGGER.warn("Can not transform ${FAKER(api=\"" + apiArgument.getValue() + "\", locale=\""
					+ locale + "\")", e);
		}

		return "";
	}

	@Override
	public boolean match(final String input) {
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
		return "en";
	}

	private Optional<String> getApiArgumentString(final ExpressionArgument<?> apiArgument) {
		if (apiArgument instanceof ExpressionArgumentString) {
			return Optional.of(((ExpressionArgumentString) apiArgument).getValue());
		}
		return Optional.empty();
	}

}
