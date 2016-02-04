package io.vertx.ext.cache.impl.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.cache.Cache;
import io.vertx.ext.cache.CacheOptions;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class WebVerticle extends AbstractVerticle {


  @Override
  public void start(Future<Void> future) throws Exception {
    Cache.create(vertx, "web-test",
        new CacheOptions().setProviderClassName("com.hazelcast.cache.impl.HazelcastServerCachingProvider"), cache -> {
      vertx.createHttpServer().requestHandler(request -> {
        String param = request.getParam("i");
        cache.get(param, s -> request.response().end(s.result().toString()));
      }).listen(9999, done -> {
        if (done.failed()) {
          future.fail(done.cause());
        } else {
          future.complete();
        }
      });
    });
  }
}
