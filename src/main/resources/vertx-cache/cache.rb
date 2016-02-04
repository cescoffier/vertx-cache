require 'vertx/vertx'
require 'vertx/util/utils.rb'
# Generated from io.vertx.ext.cache.Cache
module VertxCache
  #  An async version of the JCache API.
  class Cache
    # @private
    # @param j_del [::VertxCache::Cache] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::VertxCache::Cache] the underlying java delegate
    def j_del
      @j_del
    end
    # @param [::Vertx::Vertx] vertx 
    # @param [String] name 
    # @param [Hash] options 
    # @yield 
    # @return [void]
    def self.create(vertx=nil,name=nil,options=nil)
      if vertx.class.method_defined?(:j_del) && name.class == String && options.class == Hash && block_given?
        return Java::IoVertxExtCache::Cache.java_method(:create, [Java::IoVertxCore::Vertx.java_class,Java::java.lang.String.java_class,Java::IoVertxExtCache::CacheOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(vertx.j_del,name,Java::IoVertxExtCache::CacheOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |event| yield(::Vertx::Util::Utils.safe_create(event,::VertxCache::Cache)) }))
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx,name,options)"
    end
    # @yield 
    # @return [self]
    def clear
      if block_given?
        @j_del.java_method(:clear, [Java::IoVertxCore::Handler.java_class]).call(Proc.new { yield })
        return self
      end
      raise ArgumentError, "Invalid arguments when calling clear()"
    end
    # @yield 
    # @return [self]
    def close
      if block_given?
        @j_del.java_method(:close, [Java::IoVertxCore::Handler.java_class]).call(Proc.new { yield })
        return self
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
    # @return [true,false]
    def closed?
      if !block_given?
        return @j_del.java_method(:isClosed, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling closed?()"
    end
    # @param [Object] key 
    # @yield 
    # @return [self]
    def contains_key(key=nil)
      if (key.class == String  || key.class == Hash || key.class == Array || key.class == NilClass || key.class == TrueClass || key.class == FalseClass || key.class == Fixnum || key.class == Float) && block_given?
        @j_del.java_method(:containsKey, [Java::java.lang.Object.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_object(key),(Proc.new { |event| yield(event) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling contains_key(key)"
    end
    # @param [Object] key 
    # @yield 
    # @return [self]
    def get(key=nil)
      if (key.class == String  || key.class == Hash || key.class == Array || key.class == NilClass || key.class == TrueClass || key.class == FalseClass || key.class == Fixnum || key.class == Float) && block_given?
        @j_del.java_method(:get, [Java::java.lang.Object.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_object(key),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ::Vertx::Util::Utils.from_object(ar.result) : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling get(key)"
    end
    # @param [Object] key 
    # @param [Object] value 
    # @yield 
    # @return [self]
    def get_and_put(key=nil,value=nil)
      if (key.class == String  || key.class == Hash || key.class == Array || key.class == NilClass || key.class == TrueClass || key.class == FalseClass || key.class == Fixnum || key.class == Float) && (value.class == String  || value.class == Hash || value.class == Array || value.class == NilClass || value.class == TrueClass || value.class == FalseClass || value.class == Fixnum || value.class == Float) && block_given?
        @j_del.java_method(:getAndPut, [Java::java.lang.Object.java_class,Java::java.lang.Object.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_object(key),::Vertx::Util::Utils.to_object(value),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ::Vertx::Util::Utils.from_object(ar.result) : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling get_and_put(key,value)"
    end
    # @param [Object] key 
    # @yield 
    # @return [self]
    def get_and_remove(key=nil)
      if (key.class == String  || key.class == Hash || key.class == Array || key.class == NilClass || key.class == TrueClass || key.class == FalseClass || key.class == Fixnum || key.class == Float) && block_given?
        @j_del.java_method(:getAndRemove, [Java::java.lang.Object.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_object(key),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ::Vertx::Util::Utils.from_object(ar.result) : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling get_and_remove(key)"
    end
    # @param [Object] key 
    # @param [Object] value 
    # @yield 
    # @return [self]
    def get_and_replace(key=nil,value=nil)
      if (key.class == String  || key.class == Hash || key.class == Array || key.class == NilClass || key.class == TrueClass || key.class == FalseClass || key.class == Fixnum || key.class == Float) && (value.class == String  || value.class == Hash || value.class == Array || value.class == NilClass || value.class == TrueClass || value.class == FalseClass || value.class == Fixnum || value.class == Float) && block_given?
        @j_del.java_method(:getAndReplace, [Java::java.lang.Object.java_class,Java::java.lang.Object.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_object(key),::Vertx::Util::Utils.to_object(value),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ::Vertx::Util::Utils.from_object(ar.result) : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling get_and_replace(key,value)"
    end
    # @return [String]
    def name
      if !block_given?
        return @j_del.java_method(:name, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling name()"
    end
    # @param [Object] key 
    # @param [Object] value 
    # @yield 
    # @return [self]
    def put(key=nil,value=nil)
      if (key.class == String  || key.class == Hash || key.class == Array || key.class == NilClass || key.class == TrueClass || key.class == FalseClass || key.class == Fixnum || key.class == Float) && (value.class == String  || value.class == Hash || value.class == Array || value.class == NilClass || value.class == TrueClass || value.class == FalseClass || value.class == Fixnum || value.class == Float) && block_given?
        @j_del.java_method(:put, [Java::java.lang.Object.java_class,Java::java.lang.Object.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_object(key),::Vertx::Util::Utils.to_object(value),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling put(key,value)"
    end
    # @param [Object] key 
    # @param [Object] value 
    # @yield 
    # @return [self]
    def put_if_absent(key=nil,value=nil)
      if (key.class == String  || key.class == Hash || key.class == Array || key.class == NilClass || key.class == TrueClass || key.class == FalseClass || key.class == Fixnum || key.class == Float) && (value.class == String  || value.class == Hash || value.class == Array || value.class == NilClass || value.class == TrueClass || value.class == FalseClass || value.class == Fixnum || value.class == Float) && block_given?
        @j_del.java_method(:putIfAbsent, [Java::java.lang.Object.java_class,Java::java.lang.Object.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_object(key),::Vertx::Util::Utils.to_object(value),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling put_if_absent(key,value)"
    end
    # @param [Object] key 
    # @param [Object] value 
    # @yield 
    # @return [self]
    def remove(key=nil,value=nil)
      if (key.class == String  || key.class == Hash || key.class == Array || key.class == NilClass || key.class == TrueClass || key.class == FalseClass || key.class == Fixnum || key.class == Float) && block_given? && value == nil
        @j_del.java_method(:remove, [Java::java.lang.Object.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_object(key),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      elsif (key.class == String  || key.class == Hash || key.class == Array || key.class == NilClass || key.class == TrueClass || key.class == FalseClass || key.class == Fixnum || key.class == Float) && (value.class == String  || value.class == Hash || value.class == Array || value.class == NilClass || value.class == TrueClass || value.class == FalseClass || value.class == Fixnum || value.class == Float) && block_given?
        @j_del.java_method(:remove, [Java::java.lang.Object.java_class,Java::java.lang.Object.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_object(key),::Vertx::Util::Utils.to_object(value),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling remove(key,value)"
    end
    # @yield 
    # @return [self]
    def remove_all
      if block_given?
        @j_del.java_method(:removeAll, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling remove_all()"
    end
    # @param [Object] key 
    # @param [Object] v1 
    # @param [Object] v2 
    # @yield 
    # @return [self]
    def replace(key=nil,v1=nil,v2=nil)
      if (key.class == String  || key.class == Hash || key.class == Array || key.class == NilClass || key.class == TrueClass || key.class == FalseClass || key.class == Fixnum || key.class == Float) && (v1.class == String  || v1.class == Hash || v1.class == Array || v1.class == NilClass || v1.class == TrueClass || v1.class == FalseClass || v1.class == Fixnum || v1.class == Float) && block_given? && v2 == nil
        @j_del.java_method(:replace, [Java::java.lang.Object.java_class,Java::java.lang.Object.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_object(key),::Vertx::Util::Utils.to_object(v1),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      elsif (key.class == String  || key.class == Hash || key.class == Array || key.class == NilClass || key.class == TrueClass || key.class == FalseClass || key.class == Fixnum || key.class == Float) && (v1.class == String  || v1.class == Hash || v1.class == Array || v1.class == NilClass || v1.class == TrueClass || v1.class == FalseClass || v1.class == Fixnum || v1.class == Float) && (v2.class == String  || v2.class == Hash || v2.class == Array || v2.class == NilClass || v2.class == TrueClass || v2.class == FalseClass || v2.class == Fixnum || v2.class == Float) && block_given?
        @j_del.java_method(:replace, [Java::java.lang.Object.java_class,Java::java.lang.Object.java_class,Java::java.lang.Object.java_class,Java::IoVertxCore::Handler.java_class]).call(::Vertx::Util::Utils.to_object(key),::Vertx::Util::Utils.to_object(v1),::Vertx::Util::Utils.to_object(v2),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling replace(key,v1,v2)"
    end
    # @return [self]
    def destroy
      if !block_given?
        @j_del.java_method(:destroy, []).call()
        return self
      end
      raise ArgumentError, "Invalid arguments when calling destroy()"
    end
  end
end
