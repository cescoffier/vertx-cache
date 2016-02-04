/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.ext.cache.groovy;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.vertx.groovy.core.Vertx
import io.vertx.ext.cache.CacheOptions
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
/**
 * An async version of the JCache API.
*/
@CompileStatic
public class Cache<K,V> {
  private final def io.vertx.ext.cache.Cache delegate;
  public Cache(Object delegate) {
    this.delegate = (io.vertx.ext.cache.Cache) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public static <K, V> void create(Vertx vertx, String name, Map<String, Object> options, Handler<Cache<K,V>> resultHandler) {
    io.vertx.ext.cache.Cache.create((io.vertx.core.Vertx)vertx.getDelegate(), name, options != null ? new io.vertx.ext.cache.CacheOptions(new io.vertx.core.json.JsonObject(options)) : null, new Handler<io.vertx.ext.cache.Cache<java.lang.Object,java.lang.Object>>() {
      public void handle(io.vertx.ext.cache.Cache<java.lang.Object,java.lang.Object> event) {
        resultHandler.handle(new io.vertx.ext.cache.groovy.Cache(event));
      }
    });
  }
  public Cache<K,V> clear(Handler<Void> done) {
    this.delegate.clear(done);
    return this;
  }
  public Cache<K,V> close(Handler<Void> done) {
    this.delegate.close(done);
    return this;
  }
  public boolean isClosed() {
    def ret = this.delegate.isClosed();
    return ret;
  }
  public Cache<K,V> containsKey(K key, Handler<Boolean> result) {
    this.delegate.containsKey(InternalHelper.unwrapObject(key), result);
    return this;
  }
  public Cache<K,V> get(K key, Handler<AsyncResult<V>> result) {
    this.delegate.get(InternalHelper.unwrapObject(key), new Handler<AsyncResult<Object>>() {
      public void handle(AsyncResult<Object> event) {
        AsyncResult<Object> f
        if (event.succeeded()) {
          f = InternalHelper.<Object>result(InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<Object>failure(event.cause())
        }
        result.handle(f)
      }
    });
    return this;
  }
  public Cache<K,V> getAndPut(K key, V value, Handler<AsyncResult<V>> result) {
    this.delegate.getAndPut(InternalHelper.unwrapObject(key), InternalHelper.unwrapObject(value), new Handler<AsyncResult<Object>>() {
      public void handle(AsyncResult<Object> event) {
        AsyncResult<Object> f
        if (event.succeeded()) {
          f = InternalHelper.<Object>result(InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<Object>failure(event.cause())
        }
        result.handle(f)
      }
    });
    return this;
  }
  public Cache<K,V> getAndRemove(K key, Handler<AsyncResult<V>> result) {
    this.delegate.getAndRemove(InternalHelper.unwrapObject(key), new Handler<AsyncResult<Object>>() {
      public void handle(AsyncResult<Object> event) {
        AsyncResult<Object> f
        if (event.succeeded()) {
          f = InternalHelper.<Object>result(InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<Object>failure(event.cause())
        }
        result.handle(f)
      }
    });
    return this;
  }
  public Cache<K,V> getAndReplace(K key, V value, Handler<AsyncResult<V>> result) {
    this.delegate.getAndReplace(InternalHelper.unwrapObject(key), InternalHelper.unwrapObject(value), new Handler<AsyncResult<Object>>() {
      public void handle(AsyncResult<Object> event) {
        AsyncResult<Object> f
        if (event.succeeded()) {
          f = InternalHelper.<Object>result(InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<Object>failure(event.cause())
        }
        result.handle(f)
      }
    });
    return this;
  }
  public String name() {
    def ret = this.delegate.name();
    return ret;
  }
  public Cache<K,V> put(K key, V value, Handler<AsyncResult<Void>> result) {
    this.delegate.put(InternalHelper.unwrapObject(key), InternalHelper.unwrapObject(value), result);
    return this;
  }
  public Cache<K,V> putIfAbsent(K key, V value, Handler<AsyncResult<Boolean>> result) {
    this.delegate.putIfAbsent(InternalHelper.unwrapObject(key), InternalHelper.unwrapObject(value), result);
    return this;
  }
  public Cache<K,V> remove(K key, Handler<AsyncResult<Boolean>> result) {
    this.delegate.remove(InternalHelper.unwrapObject(key), result);
    return this;
  }
  public Cache<K,V> remove(K key, V value, Handler<AsyncResult<Boolean>> result) {
    this.delegate.remove(InternalHelper.unwrapObject(key), InternalHelper.unwrapObject(value), result);
    return this;
  }
  public Cache<K,V> removeAll(Handler<AsyncResult<Void>> result) {
    this.delegate.removeAll(result);
    return this;
  }
  public Cache<K,V> replace(K key, V v1, V v2, Handler<AsyncResult<Boolean>> resultHandler) {
    this.delegate.replace(InternalHelper.unwrapObject(key), InternalHelper.unwrapObject(v1), InternalHelper.unwrapObject(v2), resultHandler);
    return this;
  }
  public Cache<K,V> replace(K key, V v, Handler<AsyncResult<Boolean>> resultHandler) {
    this.delegate.replace(InternalHelper.unwrapObject(key), InternalHelper.unwrapObject(v), resultHandler);
    return this;
  }
  public Cache<K,V> destroy() {
    this.delegate.destroy();
    return this;
  }
}
