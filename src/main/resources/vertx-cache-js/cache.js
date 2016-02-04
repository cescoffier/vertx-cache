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

/** @module vertx-cache-js/cache */
var utils = require('vertx-js/util/utils');
var Vertx = require('vertx-js/vertx');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JCache = io.vertx.ext.cache.Cache;
var CacheOptions = io.vertx.ext.cache.CacheOptions;

/**
 An async version of the JCache API.

 @class
*/
var Cache = function(j_val) {

  var j_cache = j_val;
  var that = this;

  /**

   @public
   @param done {function} 
   @return {Cache}
   */
  this.clear = function(done) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_cache["clear(io.vertx.core.Handler)"](done);
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param done {function} 
   @return {Cache}
   */
  this.close = function(done) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_cache["close(io.vertx.core.Handler)"](done);
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {boolean}
   */
  this.isClosed = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_cache["isClosed()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param key {Object} 
   @param result {function} 
   @return {Cache}
   */
  this.containsKey = function(key, result) {
    var __args = arguments;
    if (__args.length === 2 && true && typeof __args[1] === 'function') {
      j_cache["containsKey(java.lang.Object,io.vertx.core.Handler)"](utils.convParamTypeUnknown(key), function(jVal) {
      result(jVal);
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param key {Object} 
   @param result {function} 
   @return {Cache}
   */
  this.get = function(key, result) {
    var __args = arguments;
    if (__args.length === 2 && true && typeof __args[1] === 'function') {
      j_cache["get(java.lang.Object,io.vertx.core.Handler)"](utils.convParamTypeUnknown(key), function(ar) {
      if (ar.succeeded()) {
        result(utils.convReturnTypeUnknown(ar.result()), null);
      } else {
        result(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param key {Object} 
   @param value {Object} 
   @param result {function} 
   @return {Cache}
   */
  this.getAndPut = function(key, value, result) {
    var __args = arguments;
    if (__args.length === 3 && true && true && typeof __args[2] === 'function') {
      j_cache["getAndPut(java.lang.Object,java.lang.Object,io.vertx.core.Handler)"](utils.convParamTypeUnknown(key), utils.convParamTypeUnknown(value), function(ar) {
      if (ar.succeeded()) {
        result(utils.convReturnTypeUnknown(ar.result()), null);
      } else {
        result(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param key {Object} 
   @param result {function} 
   @return {Cache}
   */
  this.getAndRemove = function(key, result) {
    var __args = arguments;
    if (__args.length === 2 && true && typeof __args[1] === 'function') {
      j_cache["getAndRemove(java.lang.Object,io.vertx.core.Handler)"](utils.convParamTypeUnknown(key), function(ar) {
      if (ar.succeeded()) {
        result(utils.convReturnTypeUnknown(ar.result()), null);
      } else {
        result(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param key {Object} 
   @param value {Object} 
   @param result {function} 
   @return {Cache}
   */
  this.getAndReplace = function(key, value, result) {
    var __args = arguments;
    if (__args.length === 3 && true && true && typeof __args[2] === 'function') {
      j_cache["getAndReplace(java.lang.Object,java.lang.Object,io.vertx.core.Handler)"](utils.convParamTypeUnknown(key), utils.convParamTypeUnknown(value), function(ar) {
      if (ar.succeeded()) {
        result(utils.convReturnTypeUnknown(ar.result()), null);
      } else {
        result(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {string}
   */
  this.name = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_cache["name()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param key {Object} 
   @param value {Object} 
   @param result {function} 
   @return {Cache}
   */
  this.put = function(key, value, result) {
    var __args = arguments;
    if (__args.length === 3 && true && true && typeof __args[2] === 'function') {
      j_cache["put(java.lang.Object,java.lang.Object,io.vertx.core.Handler)"](utils.convParamTypeUnknown(key), utils.convParamTypeUnknown(value), function(ar) {
      if (ar.succeeded()) {
        result(null, null);
      } else {
        result(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param key {Object} 
   @param value {Object} 
   @param result {function} 
   @return {Cache}
   */
  this.putIfAbsent = function(key, value, result) {
    var __args = arguments;
    if (__args.length === 3 && true && true && typeof __args[2] === 'function') {
      j_cache["putIfAbsent(java.lang.Object,java.lang.Object,io.vertx.core.Handler)"](utils.convParamTypeUnknown(key), utils.convParamTypeUnknown(value), function(ar) {
      if (ar.succeeded()) {
        result(ar.result(), null);
      } else {
        result(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param key {Object} 
   @param value {Object} 
   @param result {function} 
   @return {Cache}
   */
  this.remove = function() {
    var __args = arguments;
    if (__args.length === 2 && true && typeof __args[1] === 'function') {
      j_cache["remove(java.lang.Object,io.vertx.core.Handler)"](utils.convParamTypeUnknown(__args[0]), function(ar) {
      if (ar.succeeded()) {
        __args[1](ar.result(), null);
      } else {
        __args[1](null, ar.cause());
      }
    });
      return that;
    }  else if (__args.length === 3 && true && true && typeof __args[2] === 'function') {
      j_cache["remove(java.lang.Object,java.lang.Object,io.vertx.core.Handler)"](utils.convParamTypeUnknown(__args[0]), utils.convParamTypeUnknown(__args[1]), function(ar) {
      if (ar.succeeded()) {
        __args[2](ar.result(), null);
      } else {
        __args[2](null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param result {function} 
   @return {Cache}
   */
  this.removeAll = function(result) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_cache["removeAll(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        result(null, null);
      } else {
        result(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param key {Object} 
   @param v1 {Object} 
   @param v2 {Object} 
   @param resultHandler {function} 
   @return {Cache}
   */
  this.replace = function() {
    var __args = arguments;
    if (__args.length === 3 && true && true && typeof __args[2] === 'function') {
      j_cache["replace(java.lang.Object,java.lang.Object,io.vertx.core.Handler)"](utils.convParamTypeUnknown(__args[0]), utils.convParamTypeUnknown(__args[1]), function(ar) {
      if (ar.succeeded()) {
        __args[2](ar.result(), null);
      } else {
        __args[2](null, ar.cause());
      }
    });
      return that;
    }  else if (__args.length === 4 && true && true && true && typeof __args[3] === 'function') {
      j_cache["replace(java.lang.Object,java.lang.Object,java.lang.Object,io.vertx.core.Handler)"](utils.convParamTypeUnknown(__args[0]), utils.convParamTypeUnknown(__args[1]), utils.convParamTypeUnknown(__args[2]), function(ar) {
      if (ar.succeeded()) {
        __args[3](ar.result(), null);
      } else {
        __args[3](null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {Cache}
   */
  this.destroy = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_cache["destroy()"]();
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_cache;
};

/**

 @memberof module:vertx-cache-js/cache
 @param vertx {Vertx} 
 @param name {string} 
 @param options {Object} 
 @param resultHandler {function} 
 */
Cache.create = function(vertx, name, options, resultHandler) {
  var __args = arguments;
  if (__args.length === 4 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'string' && (typeof __args[2] === 'object' && __args[2] != null) && typeof __args[3] === 'function') {
    JCache["create(io.vertx.core.Vertx,java.lang.String,io.vertx.ext.cache.CacheOptions,io.vertx.core.Handler)"](vertx._jdel, name, options != null ? new CacheOptions(new JsonObject(JSON.stringify(options))) : null, function(jVal) {
    resultHandler(utils.convReturnVertxGen(jVal, Cache));
  });
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = Cache;