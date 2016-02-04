package io.vertx.ext.cache.impl;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.jayway.awaitility.Awaitility;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.cache.impl.verticles.BackendVerticle;
import io.vertx.ext.cache.impl.verticles.WebVerticle;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class CacheWithDistributedVerticlesTest {

  AtomicReference<Vertx> backend = new AtomicReference<>();
  AtomicReference<Vertx> web = new AtomicReference<>();

  OkHttpClient client = new OkHttpClient();

  @Before
  public void setUp() {
    HazelcastInstance dataNode1 = Hazelcast.newHazelcastInstance(new Config());
    HazelcastInstance dataNode2 = Hazelcast.newHazelcastInstance(new Config());

    Vertx.clusteredVertx(new VertxOptions().setClustered(true)
            .setClusterManager(new HazelcastClusterManager(dataNode1)).setClusterHost("127.0.0.1"),
        ar -> {
          Vertx vertx = ar.result();
          vertx.deployVerticle(BackendVerticle.class.getName(), done -> {
            backend.set(vertx);
          });
        });

    Vertx.clusteredVertx(new VertxOptions().setClustered(true)
        .setClusterManager(new HazelcastClusterManager(dataNode2)).setClusterHost("127.0.0.1"), ar -> {
      Vertx vertx = ar.result();
      vertx.deployVerticle(WebVerticle.class.getName(), done -> {
        web.set(vertx);
      });
    });

    Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> backend.get() != null);
    Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> web.get() != null);
  }

  @After
  public void tearDown() {
    AsyncLock<Void> lock1 = new AsyncLock<>();
    AsyncLock<Void> lock2 = new AsyncLock<>();
    backend.get().close(lock1.handler());
    web.get().close(lock2.handler());

    lock1.waitForSuccess();
    lock2.waitForSuccess();
  }

  @Test
  public void test() throws IOException {
    Request request = new Request.Builder()
        .url("http://localhost:9999?i=1")
        .build();

    Response response = client.newCall(request).execute();
    String resp = response.body().string();

    assertNotNull(resp);
    assertTrue(resp.contains("timestamp"));
    assertTrue(resp.contains("data"));
  }
}

