package io.vertx.ext.cache.impl;

import io.vertx.core.Vertx;
import io.vertx.ext.cache.Cache;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.cache.Caching;
import javax.cache.configuration.Factory;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import java.util.concurrent.TimeUnit;

import static io.vertx.ext.cache.impl.VertxMatcherAssert.assertSucceeded;
import static io.vertx.ext.cache.impl.VertxMatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Check that we can't access the cache once closed.
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
@RunWith(VertxUnitRunner.class)
public class ClosedCacheTest {

  private static Cache<String, Person> cache;
  private static Vertx vertx;

  private static final Person ANAKIN = new Person("anakin", 10, "tatoine");

  @BeforeClass
  public static void createCache() {
    vertx = Vertx.vertx();
    MutableConfiguration<String, Person> configuration = new MutableConfiguration<>();
    Factory<ExpiryPolicy> factory = AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 5));
    configuration.setExpiryPolicyFactory(factory);
    cache = (new CacheImpl<>(vertx, () -> {
      return Caching.getCachingProvider("org.apache.ignite.cache.CachingProvider")
          .getCacheManager().createCache("test", configuration);
    }));
  }

  @AfterClass
  public static void cleanup(TestContext context) {
    AsyncLock<Void> lock = new AsyncLock<>();
    cache.close(lock.directHandler());
    lock.waitForSuccess();
    cache.unwrap().getCacheManager().destroyCache(cache.name());
    cache = null;

    Async async2 = context.async();
    vertx.close((v) -> async2.complete());
  }

  @After
  public void tearDown(TestContext context) {
    Async async = context.async();
    cache.clear((v) -> async.complete());
  }

  @Test
  public void test(TestContext context) {
    Async async = context.async();
    cache.put("key", ANAKIN, v -> {
      assertSucceeded(context, v);
      cache.close(v2 -> {
        cache.get("key", ar -> {
          assertThat(context, ar.failed(), is(true));
          async.complete();
        });
      });
    });
  }

}
