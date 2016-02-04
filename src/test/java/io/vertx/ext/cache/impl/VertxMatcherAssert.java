package io.vertx.ext.cache.impl;

import io.vertx.core.AsyncResult;
import io.vertx.ext.unit.TestContext;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import static org.hamcrest.CoreMatchers.is;

/**
 * Provides {@code assertThat} methods usable with Vertx-Unit.
 */
public class VertxMatcherAssert {

  /**
   * Checks that {@code actual} matches {@code matchers}. If not, the error is reported on the given {@code
   * TestContext}.
   *
   * @param context the test context
   * @param actual  the object to test
   * @param matcher the matcher
   * @param <T>     the type of the object to test
   */
  public static <T> void assertThat(TestContext context, T actual, Matcher<? super T> matcher) {
    assertThat(context, "", actual, matcher);
  }

  /**
   * Checks that {@code actual} matches {@code matchers}. If not, the error is reported on the given {@code
   * TestContext}.
   *
   * @param context the test context
   * @param reason  the reason
   * @param actual  the object to test
   * @param matcher the matcher
   * @param <T>     the type of the object to test
   */
  public static <T> void assertThat(TestContext context, String reason, T actual, Matcher<? super T> matcher) {
    if (!matcher.matches(actual)) {
      Description description = new StringDescription();
      description.appendText(reason)
          .appendText("\nExpected: ")
          .appendDescriptionOf(matcher)
          .appendText("\n     but: ");
      matcher.describeMismatch(actual, description);
      context.fail(description.toString());
    }
  }

  /**
   * Checks whether or not {@code assertion} is {@code true}. If not, it reports the error on the given {@code
   * TestContext}.
   *
   * @param context   the test context
   * @param reason    the reason
   * @param assertion the assertion state
   */
  public static void assertThat(TestContext context, String reason, boolean assertion) {
    if (!assertion) {
      context.fail(reason);
    }
  }

  public static <T> T assertSucceeded(TestContext context, AsyncResult<T> ar) {
    assertThat(context, ar.succeeded(), is(true));
    return ar.result();
  }

}