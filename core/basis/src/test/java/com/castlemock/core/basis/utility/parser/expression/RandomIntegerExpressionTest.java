package com.castlemock.core.basis.utility.parser.expression;

import static org.junit.Assert.assertTrue;

import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgumentNumber;
import java.util.stream.IntStream;
import org.junit.Test;

public class RandomIntegerExpressionTest {

  @Test
  public void transform_outOfBounds() {
    RandomIntegerExpression rie = new RandomIntegerExpression();
    ExpressionInput ei = new ExpressionInput("");
    ei.addArgument("min", new ExpressionArgumentNumber(100.0) {});
    ei.addArgument("max", new ExpressionArgumentNumber(999.0) {});

    IntStream.range(1, 1000).forEach(
        i -> {
          String ri = rie.transform(ei);
          assertTrue(ri + " </= 999", Integer.parseInt(ri) <= 999);
        }
    );
  }
}
