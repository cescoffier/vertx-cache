package io.vertx.ext.cache;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.cache.impl.CacheImpl;

import javax.cache.configuration.Configuration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * An async version of the JCache API.
 * <p>
 * Implementation delegates to a JCache instance created from a JCache provider.
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
@VertxGen
public interface Cache<K, V> {

  /**
   * Creates an instance of {@link Cache}. As the creation may require time to join the underlying distributed
   * infrastructure, the created {@link Cache} is passed to the given {@code resultHandler}.
   * <p>
   * Depending on the JCache provider, configuration may be required to serialize/deserialize {@code K} and {@code V}.
   *
   * @param vertx         the vert.x instance
   * @param name          the name of the cache
   * @param options       the configuration options
   * @param resultHandler the result handler
   * @param <K>           the class of the keys
   * @param <V>           the class of the values
   */
  static <K, V> void create(Vertx vertx, String name, CacheOptions options, Handler<Cache<K, V>> resultHandler) {
    MutableConfiguration<K, V> configuration = new MutableConfiguration<>();
    configuration.setManagementEnabled(options.isManagementEnabled());
    configuration.setStatisticsEnabled(options.isStatisticEnabled());
    configuration.setStoreByValue(options.isStoreByValue());
    switch (options.getExpirationPolicy()) {
      case CREATION:
        configuration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS,
            options.getExpirationTimeInSecond())));
        break;
      case ACCESS:
        configuration.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS,
            options.getExpirationTimeInSecond())));
        break;
      case TOUCH:
        configuration.setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS,
            options.getExpirationTimeInSecond())));
        break;
      case MODIFY:
        configuration.setExpiryPolicyFactory(ModifiedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS,
            options.getExpirationTimeInSecond())));
        break;
      case NEVER:
        configuration.setExpiryPolicyFactory(EternalExpiryPolicy.factoryOf());
    }

    create(vertx, name, configuration, options.getProviderClassName(), resultHandler);
  }

  /**
   * Creates an instance of {@link Cache}. As the creation may require time to join the underlying distributed
   * infrastructure, the created {@link Cache} is passed to the given {@code resultHandler}.
   * <p>
   * Depending on the JCache provider, configuration may be required to serialize/deserialize {@code K} and {@code V}.
   * <p>
   * This construction method allows fine-grained configuration.
   *
   * @param vertx         the vert.x instance
   * @param name          the cache name
   * @param configuration the JCache configuration
   * @param provider      the name of the provider (fully qualified name), {@code null} to use the default one
   * @param resultHandler the result handler
   * @param <K>           the type of the keys
   * @param <V>           the type of the values
   */
  @GenIgnore
  static <K, V> void create(Vertx vertx, String name, Configuration<K, V> configuration, String provider,
                            Handler<Cache<K, V>> resultHandler) {
    // Creating the cache from the configuration is blocking.
    vertx.<Cache<K, V>>executeBlocking(
        future -> future.complete(new CacheImpl<>(vertx, name, configuration, provider)),
        cache -> resultHandler.handle(cache.result()));
  }

  /**
   * Same as {@link #create(Vertx, String, Configuration, String, Handler)} but using the default JCache implementation.
   *
   * @param vertx         the vert.x instance
   * @param name          the cache name
   * @param configuration the JCache configuration
   * @param resultHandler the result handler
   * @param <K>           the type of the keys
   * @param <V>           the type of the values
   */
  @GenIgnore
  static <K, V> void create(Vertx vertx, String name, Configuration<K, V> configuration,
                            Handler<Cache<K, V>> resultHandler) {
    // Creating the cache from the configuration is blocking.
    vertx.<Cache<K, V>>executeBlocking(
        future -> future.complete(new CacheImpl<>(vertx, name, configuration)),
        cache -> resultHandler.handle(cache.result()));
  }

  /**
   * Creates an instance of {@link Cache}. As the creation may require time to join the underlying distributed
   * infrastructure, the created {@link Cache} is passed to the given {@code resultHandler}.
   * <p>
   * Depending on the JCache provider, configuration may be required to serialize/deserialize {@code K} and {@code V}.
   * <p>
   * This construction method allows fine-grained construction of the JCache object delegate as it should be
   * constructed in the given supplier.
   *
   * @param vertx         the vert.x instance
   * @param cacheSupplier a method returning the JCache object to use
   * @param resultHandler the result handler
   * @param <K>           the type of the keys
   * @param <V>           the type of the values
   */
  @GenIgnore
  static <K, V> void create(Vertx vertx, Supplier<javax.cache.Cache<K, V>> cacheSupplier,
                            Handler<Cache<K, V>> resultHandler) {
    vertx.<Cache<K, V>>executeBlocking(
        future -> future.complete(new CacheImpl<>(vertx, cacheSupplier)),
        cache -> resultHandler.handle(cache.result()));
  }

  /**
   * Creates an instance of {@link Cache}. This constructor method takes the JCache object to use as parameter, so
   * does not need to be asynchronous.
   * <p>
   * Depending on the JCache provider, configuration may be required to serialize/deserialize {@code K} and {@code V}.
   *
   * @param vertx the vert.x instance
   * @param cache the JCache object to use
   * @param <K>   the type of the keys
   * @param <V>   the type of the values
   */
  @GenIgnore
  static <K, V> Cache<K, V> create(Vertx vertx, javax.cache.Cache<K, V> cache) {
    return new CacheImpl<>(vertx, cache);
  }

  @Fluent
  Cache<K, V> clear(Handler<Void> done);

  @Fluent
  Cache<K, V> close(Handler<Void> done);

  /**
   * @return whether or not the cache is closed.
   */
  boolean isClosed();

  @Fluent
  Cache<K, V> containsKey(K key, Handler<Boolean> result);

  @Fluent
  Cache<K, V> get(K key, Handler<AsyncResult<V>> result);

  @Fluent
  @GenIgnore
  Cache<K, V> getAll(Set<K> keys, Handler<AsyncResult<Map<K, V>>> result);

  @Fluent
  Cache<K, V> getAndPut(K key, V value, Handler<AsyncResult<V>> result);

  @Fluent
  Cache<K, V> getAndRemove(K key, Handler<AsyncResult<V>> result);

  @Fluent
  Cache<K, V> getAndReplace(K key, V value, Handler<AsyncResult<V>> result);

  /**
   * @return the name of the cache.
   */
  String name();

  @Fluent
  @GenIgnore
  Cache<K, V> getKeys(Handler<AsyncResult<Set<K>>> result);

  @Fluent
  @GenIgnore
  Cache<K, V> getAll(Handler<AsyncResult<Map<K, V>>> result);

  @Fluent
  Cache<K, V> put(K key, V value, Handler<AsyncResult<Void>> result);

  @Fluent
  @GenIgnore
  Cache<K, V> putAll(Map<K, V> values, Handler<AsyncResult<Void>> result);

  @Fluent
  Cache<K, V> putIfAbsent(K key, V value, Handler<AsyncResult<Boolean>> result);

  @Fluent
  Cache<K, V> remove(K key, Handler<AsyncResult<Boolean>> result);

  @Fluent
  Cache<K, V> remove(K key, V value, Handler<AsyncResult<Boolean>> result);

  @Fluent
  @GenIgnore
  Cache<K, V> removeAll(Set<K> keys, Handler<AsyncResult<Void>> result);

  @Fluent
  Cache<K, V> removeAll(Handler<AsyncResult<Void>> result);

  @Fluent
  Cache<K, V> replace(K key, V v1, V v2, Handler<AsyncResult<Boolean>> resultHandler);

  @Fluent
  Cache<K, V> replace(K key, V v, Handler<AsyncResult<Boolean>> resultHandler);

  /**
   * Destroys the cache. Once this method is called, the cache is not usable anymore.
   *
   * @return the cache.
   */
  void destroy();

  /**
   * @return the underlying JCache object. Be aware that method called on this object may be blocking.
   */
  @GenIgnore
  javax.cache.Cache<K, V> unwrap();

  // TODO loadAll and load methods

}
