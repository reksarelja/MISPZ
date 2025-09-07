package me.mispz.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "musterija")
public class Musterija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone_number", nullable = false)
    private String phone_number;
    @Column(name = "age", nullable = false)
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String lastName) {
        this.phone_number = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Musterija{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone number='" + phone_number + '\'' +
                ", age=" + age +
                '}';
    }
}
