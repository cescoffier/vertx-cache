package io.vertx.ext.cache.impl;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Person {

  private String name;

  private int age;

  private Address address;

  public Person(String n, int a, String add) {
    setName(n).setAge(a).setAddress(new Address(add));
  }

  public Person() {

  }

  public Address getAddress() {
    return address;
  }

  public Person setAddress(Address address) {
    this.address = address;
    return this;
  }

  public int getAge() {
    return age;
  }

  public Person setAge(int age) {
    this.age = age;
    return this;
  }

  public String getName() {
    return name;
  }

  public Person setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Person person = (Person) o;

    if (getAge() != person.getAge()) return false;
    if (!getName().equals(person.getName())) return false;
    return getAddress().equals(person.getAddress());

  }

  @Override
  public int hashCode() {
    int result = getName().hashCode();
    result = 31 * result + getAge();
    result = 31 * result + getAddress().hashCode();
    return result;
  }
}
