package io.vertx.ext.cache.impl.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.cache.Cache;
import io.vertx.ext.cache.CacheOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class BackendVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> future) throws Exception {
    Cache.<String, String>create(vertx, "web-test",
        new CacheOptions().setProviderClassName("com.hazelcast.cache.impl.HazelcastServerCachingProvider"), cache -> {
          Random random = new Random();
          Map<String, String> data = new HashMap<>();
          for (int i = 0; i < 10; i++) {
            data.put(Integer.toString(i),
                new JsonObject().put("timestamp", System.currentTimeMillis()).put("data", random.nextInt(10)).encodePrettily());
          }
          cache.putAll(data, v -> future.complete());
        });

  }
}
