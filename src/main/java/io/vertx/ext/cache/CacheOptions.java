package io.vertx.ext.cache;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
@DataObject(generateConverter = true)
public class CacheOptions {

//  boolean readThrough;
//  boolean writeThrough;
//
//  String readThroughAddress;
//  String writeThroughAddress;

  private boolean storeByValue = false;

  private boolean managementEnabled = false;
  private boolean statisticEnabled = false;

  private ExpirationPolicy expirationPolicy = ExpirationPolicy.NEVER;
  private long expirationTimeInSecond = -1;

  private String providerClassName;

  public CacheOptions() {

  }

  public CacheOptions(CacheOptions other) {
    storeByValue = other.storeByValue;
    managementEnabled = other.managementEnabled;
    statisticEnabled = other.statisticEnabled;
    expirationPolicy = other.expirationPolicy;
    expirationTimeInSecond = other.expirationTimeInSecond;
    providerClassName = other.providerClassName;
  }

  public CacheOptions(JsonObject json) {
    CacheOptionsConverter.fromJson(json, this);
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    CacheOptionsConverter.toJson(this, json);
    return json;
  }

  public ExpirationPolicy getExpirationPolicy() {
    return expirationPolicy;
  }

  public CacheOptions setExpirationPolicy(ExpirationPolicy expirationPolicy) {
    this.expirationPolicy = expirationPolicy;
    return this;
  }

  public long getExpirationTimeInSecond() {
    return expirationTimeInSecond;
  }

  public CacheOptions setExpirationTimeInSecond(long expirationTimeInSecond) {
    this.expirationTimeInSecond = expirationTimeInSecond;
    return this;
  }

  public boolean isManagementEnabled() {
    return managementEnabled;
  }

  public CacheOptions setManagementEnabled(boolean managementEnabled) {
    this.managementEnabled = managementEnabled;
    return this;
  }

  public boolean isStatisticEnabled() {
    return statisticEnabled;
  }

  public CacheOptions setStatisticEnabled(boolean statisticEnabled) {
    this.statisticEnabled = statisticEnabled;
    return this;
  }

  public boolean isStoreByValue() {
    return storeByValue;
  }

  public CacheOptions setStoreByValue(boolean storeByValue) {
    this.storeByValue = storeByValue;
    return this;
  }

  public String getProviderClassName() {
    return providerClassName;
  }

  public CacheOptions setProviderClassName(String providerClassName) {
    this.providerClassName = providerClassName;
    return this;
  }
}
