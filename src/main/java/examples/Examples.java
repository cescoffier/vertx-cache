package examples;

import io.vertx.core.Vertx;
import io.vertx.ext.cache.Cache;
import io.vertx.ext.cache.CacheOptions;
import io.vertx.ext.cache.ExpirationPolicy;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Examples {

  public void create(Vertx vertx) {
    Cache.create(vertx, "my-cache", new CacheOptions().setExpirationPolicy(ExpirationPolicy.TOUCH)
        .setExpirationTimeInSecond(3600), cache -> {
      // cache is the instance you can use
    });
  }

  public void put(Cache<String, String> cache) {
    cache.put("key", "my value", v -> {
      if (v.failed()) {
        // cannot insert the value
      } else {
        // value inserted
      }
    });
  }

  public void get(Cache<String, String> cache) {
    cache.get("key", retrieved -> {
      if (retrieved.failed()) {
        // cannot get the value
      } else {
        String cachedValue = retrieved.result();
        // cachedValue may be null if the cache don't have a mapping for "key".
      }
    });
  }

  public void close(Cache<String, String> cache) {
    cache.close(done -> {
      // you can destroy it to unregister it from the cache manager
      cache.destroy();
    });
  }
}
