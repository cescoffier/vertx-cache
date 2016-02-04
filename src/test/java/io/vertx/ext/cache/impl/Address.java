package io.vertx.ext.cache.impl;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Address {

  private String address;

  public Address(String address) {
    this.address = address;
  }

  public Address() {

  }

  public String getAddress() {
    return address;
  }

  public Address setAddress(String address) {
    this.address = address;
    return this;
  }
}
