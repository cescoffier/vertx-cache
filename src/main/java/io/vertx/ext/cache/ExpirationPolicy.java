package io.vertx.ext.cache;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public enum ExpirationPolicy {

  ACCESS,
  CREATION,
  TOUCH,
  MODIFY,
  NEVER

}
