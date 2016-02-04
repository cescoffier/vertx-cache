/**
 * = Vert.x Cache
 *
 * Vert.x Cache offers a caching API similar to JCache (JSR 107) but with an _async flavor_.
 *
 * == Using vertx-cache
 *
 * To use the Vert.x Cache, add the following dependency to the _dependencies_ section of your build
 * descriptor:
 *
 * * Maven (in your `pom.xml`):
 *
 * [source,xml,subs="+attributes"]
 * ----
 * <dependency>
 *   <groupId>${maven.groupId}</groupId>
 *   <artifactId>${maven.artifactId}</artifactId>
 *   <version>${maven.version}</version>
 * </dependency>
 * ----
 *
 * * Gradle (in your `build.gradle` file):
 *
 * [source,groovy,subs="+attributes"]
 * ----
 * compile '${maven.groupId}:${maven.artifactId}:${maven.version}'
 * ----
 *
 * You will need an implementation of the JCache API. See https://jcp.org/aboutJava/communityprocess/implementations/jsr107/index.html.
 *
 * == Creating an instance
 *
 * This API is intended to be used for distributed cache, so creating an instance may take times to join the
 * underlying distributed infrastructure. That's why all creation method are asynchronous (except if you provide the
 * underlying JCache object.
 *
 * To create a {@link io.vertx.ext.cache.Cache} instance, use the
 * {@link io.vertx.ext.cache.Cache#create(io.vertx.core.Vertx, java.lang.String, io.vertx.ext.cache.CacheOptions, io.vertx.core.Handler)}
 * method. You can configure the name of the cache and various options. Once the cache has been created, the result
 * handler is called:
 *
 * [source,$lang]
 * ----
 * {@link examples.Examples#create(io.vertx.core.Vertx)}
 * ----
 *
 * If you are in Java, you can use:
 *
 * * {@link io.vertx.ext.cache.Cache#create(io.vertx.core.Vertx, javax.cache.Cache)} - you provide the underlying
 * cache instance
 * *
 * {@link io.vertx.ext.cache.Cache#create(io.vertx.core.Vertx, java.lang.String, javax.cache.configuration.Configuration, io.vertx.core.Handler)}
 * - you provide the JCache configuration
 * * {@link io.vertx.ext.cache.Cache#create(io.vertx.core.Vertx, java.util.function.Supplier, io.vertx.core.Handler)}
 * - you provide a method returning the JCache object
 *
 * Notice that in the (pure) JCache API, you configure the expiration policy for the whole cache (and not per entry).
 * Don't forget to configure it.
 *
 * == Adding cache entries
 *
 * There are various method to add entries to the cache. The simplest is
 * {@link io.vertx.ext.cache.Cache#put(java.lang.Object, java.lang.Object, io.vertx.core.Handler)}:
 *
 * [source,$lang]
 * ----
 * {@link examples.Examples#put(io.vertx.ext.cache.Cache)}
 * ----
 *
 * There are more advanced method such as:
 *
 * * {@link io.vertx.ext.cache.Cache#putAll(java.util.Map, io.vertx.core.Handler)}
 * * {@link io.vertx.ext.cache.Cache#putIfAbsent(java.lang.Object, java.lang.Object, io.vertx.core.Handler)}
 * * {@link io.vertx.ext.cache.Cache#getAndPut(java.lang.Object, java.lang.Object, io.vertx.core.Handler)}
 *
 * == Reading cached values
 *
 *  * There are various method to read entries from the cache. The simplest is
 * {@link io.vertx.ext.cache.Cache#get(java.lang.Object, io.vertx.core.Handler)}:
 *
 * [source,$lang]
 * ----
 * {@link examples.Examples#get(io.vertx.ext.cache.Cache)}
 * ----
 *
 * There are more advanced method such as:
 *
 * * {@link io.vertx.ext.cache.Cache#getAll(io.vertx.core.Handler)}
 * * {@link io.vertx.ext.cache.Cache#getAll(java.util.Set, io.vertx.core.Handler)}
 * * {@link io.vertx.ext.cache.Cache#getAndPut(java.lang.Object, java.lang.Object, io.vertx.core.Handler)}
 * * {@link io.vertx.ext.cache.Cache#getAndRemove(java.lang.Object, io.vertx.core.Handler)}
 * * {@link io.vertx.ext.cache.Cache#getAndReplace(java.lang.Object, java.lang.Object, io.vertx.core.Handler)}
 *
 * == Closing the cache
 *
 * Don't forget to close and destroy the cache when done:
 *
 * [source,$lang]
 * ----
 * {@link examples.Examples#close(io.vertx.ext.cache.Cache)}
 * ----
 *
 */
@ModuleGen(name = "vertx-cache", groupPackage = "io.vertx.ext.cache")
@Document(fileName = "index.html")
package io.vertx.ext.cache;

import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.docgen.Document;