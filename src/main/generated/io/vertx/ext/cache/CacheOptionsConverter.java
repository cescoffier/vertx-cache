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

package io.vertx.ext.cache;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link io.vertx.ext.cache.CacheOptions}.
 *
 * NOTE: This class has been automatically generated from the {@link io.vertx.ext.cache.CacheOptions} original class using Vert.x codegen.
 */
public class CacheOptionsConverter {

  public static void fromJson(JsonObject json, CacheOptions obj) {
    if (json.getValue("expirationPolicy") instanceof String) {
      obj.setExpirationPolicy(io.vertx.ext.cache.ExpirationPolicy.valueOf((String)json.getValue("expirationPolicy")));
    }
    if (json.getValue("expirationTimeInSecond") instanceof Number) {
      obj.setExpirationTimeInSecond(((Number)json.getValue("expirationTimeInSecond")).longValue());
    }
    if (json.getValue("managementEnabled") instanceof Boolean) {
      obj.setManagementEnabled((Boolean)json.getValue("managementEnabled"));
    }
    if (json.getValue("providerClassName") instanceof String) {
      obj.setProviderClassName((String)json.getValue("providerClassName"));
    }
    if (json.getValue("statisticEnabled") instanceof Boolean) {
      obj.setStatisticEnabled((Boolean)json.getValue("statisticEnabled"));
    }
    if (json.getValue("storeByValue") instanceof Boolean) {
      obj.setStoreByValue((Boolean)json.getValue("storeByValue"));
    }
  }

  public static void toJson(CacheOptions obj, JsonObject json) {
    if (obj.getExpirationPolicy() != null) {
      json.put("expirationPolicy", obj.getExpirationPolicy().name());
    }
    json.put("expirationTimeInSecond", obj.getExpirationTimeInSecond());
    json.put("managementEnabled", obj.isManagementEnabled());
    if (obj.getProviderClassName() != null) {
      json.put("providerClassName", obj.getProviderClassName());
    }
    json.put("statisticEnabled", obj.isStatisticEnabled());
    json.put("storeByValue", obj.isStoreByValue());
  }
}