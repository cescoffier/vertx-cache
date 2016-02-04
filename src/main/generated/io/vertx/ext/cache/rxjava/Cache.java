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

package io.vertx.ext.cache.rxjava;

import java.util.Map;
import io.vertx.lang.rxjava.InternalHelper;
import rx.Observable;
import io.vertx.rxjava.core.Vertx;
import io.vertx.ext.cache.CacheOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * An async version of the JCache API.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.vertx.ext.cache.Cache original} non RX-ified interface using Vert.x codegen.
 */

public class Cache<K,V> {

  final io.vertx.ext.cache.Cache delegate;

  public Cache(io.vertx.ext.cache.Cache delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public static <K, V> void create(Vertx vertx, String name, CacheOptions options, Handler<Cache<K,V>> resultHandler) { 
    io.vertx.ext.cache.Cache.create((io.vertx.core.Vertx) vertx.getDelegate(), name, options, new Handler<io.vertx.ext.cache.Cache<K,V>>() {
      public void handle(io.vertx.ext.cache.Cache<K,V> event) {
        resultHandler.handle(new Cache<K,V>(event));
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
    boolean ret = this.delegate.isClosed();
    return ret;
  }

  public Cache<K,V> containsKey(K key, Handler<Boolean> result) { 
    this.delegate.containsKey(key, result);
    return this;
  }

  public Cache<K,V> get(K key, Handler<AsyncResult<V>> result) { 
    this.delegate.get(key, result);
    return this;
  }

  public Observable<V> getObservable(K key) { 
    io.vertx.rx.java.ObservableFuture<V> result = io.vertx.rx.java.RxHelper.observableFuture();
    get(key, result.toHandler());
    return result;
  }

  public Cache<K,V> getAndPut(K key, V value, Handler<AsyncResult<V>> result) { 
    this.delegate.getAndPut(key, value, result);
    return this;
  }

  public Observable<V> getAndPutObservable(K key, V value) { 
    io.vertx.rx.java.ObservableFuture<V> result = io.vertx.rx.java.RxHelper.observableFuture();
    getAndPut(key, value, result.toHandler());
    return result;
  }

  public Cache<K,V> getAndRemove(K key, Handler<AsyncResult<V>> result) { 
    this.delegate.getAndRemove(key, result);
    return this;
  }

  public Observable<V> getAndRemoveObservable(K key) { 
    io.vertx.rx.java.ObservableFuture<V> result = io.vertx.rx.java.RxHelper.observableFuture();
    getAndRemove(key, result.toHandler());
    return result;
  }

  public Cache<K,V> getAndReplace(K key, V value, Handler<AsyncResult<V>> result) { 
    this.delegate.getAndReplace(key, value, result);
    return this;
  }

  public Observable<V> getAndReplaceObservable(K key, V value) { 
    io.vertx.rx.java.ObservableFuture<V> result = io.vertx.rx.java.RxHelper.observableFuture();
    getAndReplace(key, value, result.toHandler());
    return result;
  }

  public String name() { 
    String ret = this.delegate.name();
    return ret;
  }

  public Cache<K,V> put(K key, V value, Handler<AsyncResult<Void>> result) { 
    this.delegate.put(key, value, result);
    return this;
  }

  public Observable<Void> putObservable(K key, V value) { 
    io.vertx.rx.java.ObservableFuture<Void> result = io.vertx.rx.java.RxHelper.observableFuture();
    put(key, value, result.toHandler());
    return result;
  }

  public Cache<K,V> putIfAbsent(K key, V value, Handler<AsyncResult<Boolean>> result) { 
    this.delegate.putIfAbsent(key, value, result);
    return this;
  }

  public Observable<Boolean> putIfAbsentObservable(K key, V value) { 
    io.vertx.rx.java.ObservableFuture<Boolean> result = io.vertx.rx.java.RxHelper.observableFuture();
    putIfAbsent(key, value, result.toHandler());
    return result;
  }

  public Cache<K,V> remove(K key, Handler<AsyncResult<Boolean>> result) { 
    this.delegate.remove(key, result);
    return this;
  }

  public Observable<Boolean> removeObservable(K key) { 
    io.vertx.rx.java.ObservableFuture<Boolean> result = io.vertx.rx.java.RxHelper.observableFuture();
    remove(key, result.toHandler());
    return result;
  }

  public Cache<K,V> remove(K key, V value, Handler<AsyncResult<Boolean>> result) { 
    this.delegate.remove(key, value, result);
    return this;
  }

  public Observable<Boolean> removeObservable(K key, V value) { 
    io.vertx.rx.java.ObservableFuture<Boolean> result = io.vertx.rx.java.RxHelper.observableFuture();
    remove(key, value, result.toHandler());
    return result;
  }

  public Cache<K,V> removeAll(Handler<AsyncResult<Void>> result) { 
    this.delegate.removeAll(result);
    return this;
  }

  public Observable<Void> removeAllObservable() { 
    io.vertx.rx.java.ObservableFuture<Void> result = io.vertx.rx.java.RxHelper.observableFuture();
    removeAll(result.toHandler());
    return result;
  }

  public Cache<K,V> replace(K key, V v1, V v2, Handler<AsyncResult<Boolean>> resultHandler) { 
    this.delegate.replace(key, v1, v2, resultHandler);
    return this;
  }

  public Observable<Boolean> replaceObservable(K key, V v1, V v2) { 
    io.vertx.rx.java.ObservableFuture<Boolean> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    replace(key, v1, v2, resultHandler.toHandler());
    return resultHandler;
  }

  public Cache<K,V> replace(K key, V v, Handler<AsyncResult<Boolean>> resultHandler) { 
    this.delegate.replace(key, v, resultHandler);
    return this;
  }

  public Observable<Boolean> replaceObservable(K key, V v) { 
    io.vertx.rx.java.ObservableFuture<Boolean> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    replace(key, v, resultHandler.toHandler());
    return resultHandler;
  }

  public Cache<K,V> destroy() { 
    this.delegate.destroy();
    return this;
  }


  public static <K, V> Cache newInstance(io.vertx.ext.cache.Cache arg) {
    return arg != null ? new Cache<K, V> (arg) : null;
  }
}
