package io.vertx.ext.cache.impl;


import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.cache.Cache;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.Configuration;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;


/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class CacheImpl<K, V> implements Cache<K, V> {

  private final Vertx vertx;
  private final String name;
  private final javax.cache.Cache<K, V> delegate;

  //TODO provider selection
  //TODO reuse HZ instance

  public CacheImpl(Vertx vertx, Supplier<javax.cache.Cache<K, V>> cacheSupplier) {
    Objects.requireNonNull(vertx);
    Objects.requireNonNull(cacheSupplier);
    this.vertx = vertx;
    this.delegate = cacheSupplier.get();
    Objects.requireNonNull(delegate);

    this.name = delegate.getName();
    Objects.requireNonNull(name);
  }

  public CacheImpl(Vertx vertx, String name, Configuration<K, V> configuration) {
    this(vertx, name, configuration, null);
  }

  public CacheImpl(Vertx vertx, String name, Configuration<K, V> configuration, String provider) {
    this(vertx, () -> {
      CacheManager manager;
      if (provider != null) {
        manager = Caching.getCachingProvider(provider).getCacheManager();
      } else {
        manager = Caching.getCachingProvider().getCacheManager();
      }
      return manager.createCache(name, configuration);
    });
  }

  public CacheImpl(Vertx vertx, javax.cache.Cache<K, V> cache) {
    this(vertx, () ->  cache);
  }

  @Override
  public Cache<K, V> clear(Handler<Void> done) {
    return execute(delegate::clear, done);
  }

  @Override
  public Cache<K, V> close(Handler<Void> done) {
    return execute(delegate::close, done);
  }

  @Override
  public void destroy() {
    //TODO is this blocking ?
    delegate.getCacheManager().destroyCache(name);
  }

  @Override
  public javax.cache.Cache<K, V> unwrap() {
    return delegate;
  }

  @Override
  public boolean isClosed() {
    return delegate.isClosed();
  }

  @Override
  public Cache<K, V> containsKey(K key, Handler<Boolean> result) {
    return execute(() -> delegate.containsKey(key), result);
  }

  @Override
  public Cache<K, V> get(K key, Handler<AsyncResult<V>> result) {
    return executeWithAsyncResult(() -> delegate.get(key), result);
  }

  @Override
  public Cache<K, V> getAll(Set<K> keys, Handler<AsyncResult<Map<K, V>>> result) {
    return executeWithAsyncResult(() -> delegate.getAll(keys), result);
  }

  @Override
  public Cache<K, V> getAndPut(K key, V value, Handler<AsyncResult<V>> result) {
    return executeWithAsyncResult(() -> delegate.getAndPut(key, value), result);
  }

  @Override
  public Cache<K, V> getAndRemove(K key, Handler<AsyncResult<V>> result) {
    return executeWithAsyncResult(() -> delegate.getAndRemove(key), result);
  }

  @Override
  public Cache<K, V> getAndReplace(K key, V value, Handler<AsyncResult<V>> result) {
    return executeWithAsyncResult(() -> delegate.getAndReplace(key, value), result);
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public Cache<K, V> getKeys(Handler<AsyncResult<Set<K>>> result) {
    return executeWithAsyncResult(() -> {
      Set<K> keys = new LinkedHashSet<>();
      delegate.iterator().forEachRemaining(entry -> keys.add(entry.getKey()));
      return keys;
    }, result);
  }

  @Override
  public Cache<K, V> getAll(Handler<AsyncResult<Map<K, V>>> result) {
    return executeWithAsyncResult(() -> {
      Map<K, V> keys = new LinkedHashMap<>();
      delegate.iterator().forEachRemaining(entry -> keys.put(entry.getKey(), entry.getValue()));
      return keys;
    }, result);
  }

  @Override
  public Cache<K, V> put(K key, V value, Handler<AsyncResult<Void>> result) {
    return executeWithAsyncResult(() -> delegate.put(key, value), result);
  }

  @Override
  public Cache<K, V> putAll(Map<K, V> values, Handler<AsyncResult<Void>> result) {
    return executeWithAsyncResult(() -> delegate.putAll(values), result);
  }

  @Override
  public Cache<K, V> putIfAbsent(K key, V value, Handler<AsyncResult<Boolean>> result) {
    return executeWithAsyncResult(() -> delegate.putIfAbsent(key, value), result);
  }

  @Override
  public Cache<K, V> remove(K key, Handler<AsyncResult<Boolean>> result) {
    return executeWithAsyncResult(() -> delegate.remove(key), result);
  }

  @Override
  public Cache<K, V> remove(K key, V value, Handler<AsyncResult<Boolean>> result) {
    return executeWithAsyncResult(() -> delegate.remove(key, value), result);
  }

  @Override
  public Cache<K, V> removeAll(Set<K> keys, Handler<AsyncResult<Void>> result) {
    return executeWithAsyncResult(() -> delegate.removeAll(keys), result);
  }

  @Override
  public Cache<K, V> removeAll(Handler<AsyncResult<Void>> result) {
    return executeWithAsyncResult((Runnable) delegate::removeAll, result);
  }

  @Override
  public Cache<K, V> replace(K key, V v1, V v2, Handler<AsyncResult<Boolean>> resultHandler) {
    return executeWithAsyncResult(() -> delegate.replace(key, v1, v2), resultHandler);
  }

  @Override
  public Cache<K, V> replace(K key, V v, Handler<AsyncResult<Boolean>> resultHandler) {
    return executeWithAsyncResult(() -> delegate.replace(key, v), resultHandler);
  }


  private <T> Cache<K, V> executeWithAsyncResult(Callable<T> task, Handler<AsyncResult<T>> resultHandler) {
    vertx.<T>executeBlocking(
        future -> {
          try {
            future.complete(task.call());
          } catch (Exception e) {
            future.fail(e);
          }
        },
        resultHandler
    );
    return this;
  }

  private <T> Cache<K, V> execute(Callable<T> task, Handler<T> resultHandler) {
    vertx.<T>executeBlocking(
        future -> {
          try {
            future.complete(task.call());
          } catch (Exception e) {
            future.fail(e);
          }
        },
        t -> resultHandler.handle(t.result())
    );
    return this;
  }

  private Cache<K, V> executeWithAsyncResult(Runnable task, Handler<AsyncResult<Void>> resultHandler) {
    vertx.<Void>executeBlocking(
        future -> {
          try {
            task.run();
            future.complete(null);
          } catch (Exception e) {
            future.fail(e);
          }
        },
        resultHandler
    );
    return this;
  }

  private Cache<K, V> execute(Runnable task, Handler<Void> resultHandler) {
    vertx.<Void>executeBlocking(
        future -> {
          try {
            task.run();
            future.complete(null);
          } catch (Exception e) {
            future.fail(e);
          }
        },
        v -> resultHandler.handle(null)
    );
    return this;
  }
}
