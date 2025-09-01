package me.mispz.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity

@Table(name = "pice")
public class Pice {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "alc", nullable = false)
    private boolean alc;
    @Column(name = "balance", nullable = false)
    private boolean balance;
    @Column(name = "price", nullable = false)
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlc() {
        return alc;
    }

    public void setAlc(boolean alc) {
        this.alc = alc;
    }

    public boolean isBalance() {
        return balance;
    }

    public void setBalance(boolean balance) {
        this.balance = balance;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alc=" + alc +
                ", balance=" + balance +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pice pice = (Pice) o;
        return alc == pice.alc && balance == pice.balance && Objects.equals(id, pice.id) && Objects.equals(name, pice.name) && Objects.equals(price, pice.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, alc, balance, price);
    }
}
