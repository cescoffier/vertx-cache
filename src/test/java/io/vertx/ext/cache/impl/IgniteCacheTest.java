package io.vertx.ext.cache.impl;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.cache.Caching;
import javax.cache.configuration.Factory;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static io.vertx.ext.cache.impl.VertxMatcherAssert.assertSucceeded;
import static io.vertx.ext.cache.impl.VertxMatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

/**
 * Checks the API behavior with Ignite implementation.
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class IgniteCacheTest extends CacheTestBase {

  @BeforeClass
  public static void createCache() {
    MutableConfiguration<String, Person> configuration = new MutableConfiguration<>();
    Factory<ExpiryPolicy> factory = AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 3));
    configuration.setExpiryPolicyFactory(factory);
    configuration.setStoreByValue(true);

    setCache(new CacheImpl<>(vertx, () -> {
      return Caching.getCachingProvider("org.apache.ignite.cache.CachingProvider")
          .getCacheManager().createCache("test", configuration);
    }));
  }

  @Test
  @Ignore("TODO - it seems that object equality does not work in ignite")
  public void testRemoveWithKeyAndValue(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();
    map.put("a", ANAKIN);
    map.put("j", JAR);
    map.put("q", AMIDALA);
    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.remove("a", ANAKIN, ar2 -> {
        boolean removed = assertSucceeded(context, ar2);
        dumpContentSync();

        assertThat(context, removed, is(true));
        cache.getAll(ar3 -> {
          Map<String, Person> result = assertSucceeded(context, ar3);
          assertThat(context, result.size(), is(2));
          assertThat(context, result.keySet(), hasItems("j", "q"));

          cache.remove("q", ANAKIN, ar4 -> {
            boolean removed2 = assertSucceeded(context, ar4);
            assertThat(context, removed2, is(false));
            async.complete();
          });
        });
      });
    });
  }

  @Test
  @Ignore("TODO - it seems that object equality does not work in ignite")
  public void testReplaceWithCheck(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();
    map.put("a", ANAKIN);
    map.put("j", JAR);
    cache.putAll(map, insertion -> {
      assertSucceeded(context, insertion);
      cache.replace("a", ANAKIN, AMIDALA, replaced -> {
        boolean rep = assertSucceeded(context, replaced);
        assertThat(context, rep, is(true));
        cache.get("a", person -> {
          assertThat(context, person.result().getName(), is(AMIDALA.getName()));
          cache.replace("a", ANAKIN, AMIDALA, not_replaced -> {
            boolean rep2 = assertSucceeded(context, not_replaced);
            assertThat(context, rep2, is(false));
            async.complete();
          });
        });
      });
    });
  }

  private void dumpContentSync() {
    cache.getAll(map -> {
      System.out.println("Content...");
       map.result().forEach((k, v) -> {
         System.out.println(k + " => " + v);
       });
    });
  }
}