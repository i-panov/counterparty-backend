package ru.bellintegrator.practice.util;

import org.junit.*;
import java.util.*;

import static org.junit.Assert.*;

public class ConvertTest {
    public static class Person {
        public Integer id;
        public String name;
        public Person subperson;
        public List<Person> manyPeoples = new ArrayList<>();

        public static Person of(Integer id, String name, Person subperson) {
            Person person =  new Person();
            person.id = id;
            person.name = name;
            person.subperson = subperson;
            return person;
        }
    }

    private static final double DELTA = 1e-15;

    @Test
    public void changeType() {
        assertEquals(123.0, Convert.changeType(123.0, Long.class), DELTA);
        assertEquals(123L, Convert.changeType("123", Long.class).longValue());
        assertEquals("123.0", Convert.changeType(123.0, String.class));
    }

    @Test
    public void toNumber() {
        assertEquals(123, Convert.toNumber(123, Integer.class).intValue());
        assertEquals(123.0, Convert.toNumber(123, Double.class), DELTA);
        assertEquals(123, Convert.toNumber("123", Integer.class).intValue());
    }

    @Test
    public void tableToObject() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        Map<String, Object> table = new HashMap<>();
        table.put("id", 1);
        table.put("name", "person1");
        table.put("subperson_id", 2);
        Person person = Convert.tableToObject(table, Person.class);

        assertEquals(1, (long)person.id);
        assertEquals("person1", person.name);
        assertEquals(2, (long)person.subperson.id);
    }

    @Test
    public void objectToTable() throws IllegalAccessException, NoSuchFieldException {
        Person person = Person.of(1, "person1", Person.of(2, "person2", null));
        Map<String, Object> table = Convert.objectToTable(person);

        assertEquals(person.id, table.get("id"));
        assertEquals(person.name, table.get("name"));
        assertEquals(person.subperson.id, table.get("subperson_id"));
    }
}
