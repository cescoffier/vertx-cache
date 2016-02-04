package io.vertx.ext.cache.impl;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import io.vertx.core.json.Json;

import java.io.IOException;

public class PersonStreamSerializer
    implements StreamSerializer<Person> {

  @Override
  public int getTypeId () {
    return 1; 
  }

  @Override
  public void write(ObjectDataOutput out, Person employee )
      throws IOException {
    out.writeUTF(Json.encode(employee));
  }

  @Override
  public Person read( ObjectDataInput in ) 
      throws IOException { 
    String data = in.readUTF();
    return Json.decodeValue(data, Person.class);
  }

  @Override
  public void destroy () { 
  }
}