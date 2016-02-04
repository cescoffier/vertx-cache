package io.vertx.ext.cache.impl;

import org.junit.BeforeClass;

import javax.cache.Caching;
import javax.cache.configuration.Factory;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import java.util.concurrent.TimeUnit;

/**
 * Checks the API behavior with Hazelcast implementation.
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class HazelcastCacheTest extends CacheTestBase {

  @BeforeClass
  public static void createCache() {
    MutableConfiguration<String, Person> configuration = new MutableConfiguration<>();
    Factory<ExpiryPolicy> factory = AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 3));
    configuration.setExpiryPolicyFactory(factory);

    setCache(new CacheImpl<>(vertx, () -> {
      return Caching.getCachingProvider("com.hazelcast.cache.impl.HazelcastServerCachingProvider")
          .getCacheManager().createCache("test", configuration);
    }));
  }
}