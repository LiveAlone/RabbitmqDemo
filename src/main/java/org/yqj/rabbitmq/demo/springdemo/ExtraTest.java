package org.yqj.rabbitmq.demo.springdemo;

import org.springframework.util.SerializationUtils;

import java.io.Serializable;

/**
 * Created by yaoqijun on 2017-05-11.
 */
public class ExtraTest {
    public static void main(String[] args) {
        Person person = new Person("yaoqijun", 12);
        String s = new String(SerializationUtils.serialize(person));
        System.out.println(s);
    }

    public static class Person implements Serializable{

        private static final long serialVersionUID = -1223063369278758394L;

        private String name;

        private Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
