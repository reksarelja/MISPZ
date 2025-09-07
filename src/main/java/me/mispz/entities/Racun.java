package me.mispz.entities;

import jakarta.persistence.*;

@Entity

@Table(name = "racun")
public class Racun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "bill", nullable = false)
    private float bill;
    @Column(name = "payement_type", nullable = false)
    private String payType;
    @Column(name = "open", nullable = false)
    private boolean open;
    @Column(name = "konobar")
    private String konobar;
    public Racun() {}

    public Racun(float bill, String payType) {
        this.bill = bill;
        this.payType = payType;
        this.open = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBill() {
        return bill;
    }

    public void setBill(float bill) {
        this.bill = bill;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getKonobar() {
        return konobar;
    }

    public void setKonobar(String konobar) {
        this.konobar = konobar;
    }

    @Override
    public String toString() {
        return "Racun{" +
                "id=" + id +
                ", bill=" + bill +
                ", payType='" + payType + '\'' +
                '}';
    }
}
