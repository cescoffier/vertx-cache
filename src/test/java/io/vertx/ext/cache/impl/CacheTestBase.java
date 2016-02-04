package io.vertx.ext.cache.impl;

import io.vertx.core.Vertx;
import io.vertx.ext.cache.Cache;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.jayway.awaitility.Awaitility.await;
import static io.vertx.ext.cache.impl.VertxMatcherAssert.assertSucceeded;
import static io.vertx.ext.cache.impl.VertxMatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
@RunWith(VertxUnitRunner.class)
public abstract class CacheTestBase {

  // TODO create, destroy with entries, create, get
  // TODO test with ignite
  // TODO with RI of the JSR 107

  protected static Cache<String, Person> cache;
  protected static Vertx vertx;

  public static final Person ANAKIN = new Person("anakin", 10, "tatoine");
  public static final Person AMIDALA = new Person("amidala", 20, "naboo");
  public static final Person JAR = new Person("jar jar bins", 18, "tatoine");


  public static Cache<String, Person> getCache() {
    return cache;
  }

  public static void setCache(Cache<String, Person> cache) {
    CacheTestBase.cache = cache;
  }

  @BeforeClass
  public static void initialize() {
    vertx = Vertx.vertx();
  }

  @AfterClass
  public static void cleanup(TestContext context) {
    AsyncLock<Void> lock = new AsyncLock<>();
    cache.close(lock.directHandler());
    lock.waitForSuccess();
    cache.unwrap().getCacheManager().destroyCache(cache.name());

    cache = null;
    Async async2 = context.async();
    vertx.close((v) -> async2.complete());
  }

  @After
  public void tearDown(TestContext context) {
    Async async = context.async();
    cache.clear((v) -> async.complete());
  }

  @Test
  public void testPutAndGet(TestContext context) {
    Async async = context.async();
    cache.put("key1", ANAKIN, (r1) -> {
      assertThat(context, r1.succeeded(), is(true));
      cache.put("key2", AMIDALA, (r2) -> {
        assertThat(context, r2.succeeded(), is(true));
        cache.get("key1", p -> {
          assertThat(context, p.succeeded(), is(true));
          assertThat(context, p.result().getName(), is("anakin"));
          async.complete();
        });
      });
    });
  }

  @Test
  public void testMissingEntry(TestContext context) {
    Async async = context.async();
    cache.get("missing", ar -> {
      assertThat(context, ar.succeeded(), is(true));
      assertThat(context, ar.result(), nullValue());
      async.complete();
    });
  }

  @Test
  public void testContainsKey(TestContext context) {
    Async async = context.async();
    cache.containsKey("key", found -> {
      assertThat(context, found, is(false));
      cache.put("key", ANAKIN, ar -> {
        assertSucceeded(context, ar);
        assertThat(context, ar.succeeded(), is(true));
        cache.containsKey("key", found2 -> {
          assertThat(context, found2, is(true));
          async.complete();
        });
      });
    });
  }

  @Test
  public void testPutAll(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();
    map.put("a", ANAKIN);
    map.put("j", JAR);
    map.put("q", AMIDALA);
    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.getAll(ar2 -> {
        Map<String, Person> result = assertSucceeded(context, ar2);
        assertThat(context, result.size(), is(3));
        assertThat(context, result.keySet(), hasItems("a", "j", "q"));
        async.complete();
      });
    });
  }

  @Test
  public void testGetAllWithKeySet(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();
    Set<String> keys = new HashSet<>();

    map.put("a", ANAKIN);
    map.put("j", JAR);
    map.put("q", AMIDALA);

    keys.add("j");
    keys.add("a");
    keys.add("missing");

    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.getAll(keys, ar2 -> {
        Map<String, Person> succeeded = assertSucceeded(context, ar2);
        assertThat(context, succeeded.size(), is(2));
        assertThat(context, succeeded.keySet(), hasItems("j", "a"));
        async.complete();
      });
    });
  }

  @Test
  public void testGetAndPut(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();

    map.put("a", ANAKIN);
    map.put("j", JAR);

    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.getAndPut("a", AMIDALA, ar2 -> {
        Person person = assertSucceeded(context, ar2);
        assertThat(context, person.getName(), is(ANAKIN.getName()));
        async.complete();
      });
    });
  }

  @Test
  public void testGetAndPutWithMissingEntry(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();

    map.put("j", JAR);

    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.getAndPut("a", AMIDALA, ar2 -> {
        Person person = assertSucceeded(context, ar2);
        assertThat(context, person, nullValue());
        async.complete();
      });
    });
  }

  @Test
  public void testGetAndRemove(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();

    map.put("a", ANAKIN);
    map.put("j", JAR);

    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.getAndRemove("a", ar2 -> {
        Person person = assertSucceeded(context, ar2);
        assertThat(context, person.getName(), is(ANAKIN.getName()));
        // Check removal
        cache.containsKey("a", found -> {
          assertThat(context, found, is(false));
          async.complete();
        });
      });
    });
  }

  @Test
  public void testGetAndRemoveWithMissingEntry(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();

    map.put("j", JAR);

    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.getAndRemove("a", ar2 -> {
        Person person = assertSucceeded(context, ar2);
        assertThat(context, person, nullValue());
        cache.containsKey("a", found -> {
          assertThat(context, found, is(false));
          async.complete();
        });
      });
    });
  }

  @Test
  public void testGetAndReplace(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();

    map.put("a", ANAKIN);
    map.put("j", JAR);

    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.getAndReplace("a", AMIDALA, ar2 -> {
        Person person = assertSucceeded(context, ar2);
        assertThat(context, person.getName(), is(ANAKIN.getName()));
        // Check replace
        cache.get("a", found -> {
          Person newPerson = assertSucceeded(context, found);
          assertThat(context, newPerson.getName(), is(AMIDALA.getName()));
          async.complete();
        });
      });
    });
  }

  @Test
  public void testName() {
    assertEquals(cache.name(), "test");
  }

  @Test
  public void testGetKeys(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();
    map.put("a", ANAKIN);
    map.put("j", JAR);
    map.put("q", AMIDALA);
    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.getKeys(ar2 -> {
        Set<String> result = assertSucceeded(context, ar2);
        assertThat(context, result.size(), is(3));
        assertThat(context, result, hasItems("a", "j", "q"));
        async.complete();
      });
    });
  }

  @Test
  public void testPutIfAbsent(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();

    map.put("j", JAR);

    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.putIfAbsent("a", ANAKIN, ar2 -> {
        boolean inserted = assertSucceeded(context, ar2);
        assertThat(context, inserted, is(true));
        // Check insertion
        cache.get("a", found -> {
          Person newPerson = assertSucceeded(context, found);
          assertThat(context, newPerson.getName(), is(ANAKIN.getName()));

          // Check when there already
          cache.putIfAbsent("a", AMIDALA, ar3 -> {
            boolean inserted2 = assertSucceeded(context, ar3);
            assertThat(context, inserted2, is(false));

            cache.get("a", found2 -> {
              Person newPerson2 = assertSucceeded(context, found2);
              assertThat(context, newPerson2.getName(), is(ANAKIN.getName()));
              async.complete();
            });
          });
        });
      });
    });
  }

  @Test
  public void testRemove(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();
    map.put("a", ANAKIN);
    map.put("j", JAR);
    map.put("q", AMIDALA);
    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.remove("a", ar2 -> {
        boolean removed = assertSucceeded(context, ar2);
        assertThat(context, removed, is(true));
        cache.getAll(ar3 -> {
          Map<String, Person> result = assertSucceeded(context, ar3);
          assertThat(context, result.size(), is(2));
          assertThat(context, result.keySet(), hasItems("j", "q"));
          async.complete();
        });
      });
    });
  }

  @Test
  public void testRemoveAll(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();
    map.put("a", ANAKIN);
    map.put("j", JAR);
    map.put("q", AMIDALA);
    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.removeAll(ar2 -> {
        assertSucceeded(context, ar2);
        cache.getAll(ar3 -> {
          Map<String, Person> result = assertSucceeded(context, ar3);
          assertThat(context, result.size(), is(0));
          async.complete();
        });
      });
    });
  }

  @Test
  public void testRemoveAllWithKeys(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();
    map.put("a", ANAKIN);
    map.put("j", JAR);
    map.put("q", AMIDALA);

    Set<String> keys = new HashSet<>();
    keys.add("j");
    keys.add("q");

    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.removeAll(keys, ar2 -> {
        assertSucceeded(context, ar2);
        cache.getAll(ar3 -> {
          Map<String, Person> result = assertSucceeded(context, ar3);
          assertThat(context, result.size(), is(1));
          async.complete();
        });
      });
    });
  }

  @Test
  public void testRemoveWithKeyAndValue(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();
    map.put("a", ANAKIN);
    map.put("j", JAR);
    map.put("q", AMIDALA);
    cache.putAll(map, ar -> {
      assertSucceeded(context, ar);
      cache.remove("a", ANAKIN, ar2 -> {
        boolean removed = assertSucceeded(context, ar2);
        assertThat(context, removed, is(true));
        cache.getAll(ar3 -> {
          Map<String, Person> result = assertSucceeded(context, ar3);
          assertThat(context, result.size(), is(2));
          assertThat(context, result.keySet(), hasItems("j", "q"));

          cache.remove("q", ANAKIN, ar4 -> {
            boolean removed2 = assertSucceeded(context, ar4);
            assertThat(context, removed2, is(false));
            async.complete();
          });
        });
      });
    });
  }

  @Test
  public void testReplace(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();
    map.put("a", ANAKIN);
    map.put("j", JAR);
    cache.putAll(map, insertion -> {
      assertSucceeded(context, insertion);
      cache.replace("a", AMIDALA, replaced -> {
        boolean rep = assertSucceeded(context, replaced);
        assertThat(context, rep, is(true));
        cache.get("a", person -> {
          assertThat(context, person.result().getName(), is(AMIDALA.getName()));
          cache.replace("b", AMIDALA, not_replaced -> {
            boolean rep2 = assertSucceeded(context, not_replaced);
            assertThat(context, rep2, is(false));
            async.complete();
          });
        });
      });
    });
  }

  @Test
  public void testReplaceWithCheck(TestContext context) {
    Async async = context.async();
    Map<String, Person> map = new HashMap<>();
    map.put("a", ANAKIN);
    map.put("j", JAR);
    cache.putAll(map, insertion -> {
      assertSucceeded(context, insertion);
      cache.replace("a", ANAKIN, AMIDALA, replaced -> {
        boolean rep = assertSucceeded(context, replaced);
        assertThat(context, rep, is(true));
        cache.get("a", person -> {
          assertThat(context, person.result().getName(), is(AMIDALA.getName()));
          cache.replace("a", ANAKIN, AMIDALA, not_replaced -> {
            boolean rep2 = assertSucceeded(context, not_replaced);
            assertThat(context, rep2, is(false));
            async.complete();
          });
        });
      });
    });
  }

  @Test
  public void testExpiration() throws InterruptedException {
    AtomicBoolean injectionDone = new AtomicBoolean();
    cache.put("key", ANAKIN, v -> {
      injectionDone.set(true);
    });

    await().atMost(10, TimeUnit.SECONDS).until(injectionDone::get);

    // Wait 4 seconds to be sure the entry has expired.
    Thread.sleep(4000);

    AtomicBoolean retrieval = new AtomicBoolean();
    AtomicReference retrieved = new AtomicReference();
    cache.get("key", p -> {
      retrieved.set(p.result());
      retrieval.set(p.succeeded());
    });

    await().atMost(10, TimeUnit.SECONDS).until(retrieval::get);

    assertNull(retrieved.get());
  }

}
