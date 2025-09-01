package me.mispz.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mus2sto")
public class Mus2sto {
    @Id
    @Column(name = "id", nullable = false)
    private Timestamp time;
    @OneToMany(mappedBy = "id")
    private List<Musterija> musterije = new ArrayList<>();
    @OneToMany(mappedBy = "id")
    private List<Sto> stolovi = new ArrayList<>();

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public List<Musterija> getMusterije() {
        return musterije;
    }

    public void setMusterije(List<Musterija> musterije) {
        this.musterije = musterije;
    }

    public List<Sto> getStolovi() {
        return stolovi;
    }

    public void setStolovi(List<Sto> stolovi) {
        this.stolovi = stolovi;
    }

    @Override
    public String toString() {
        return "mus2sto{" +
                "time=" + time +
                ", musterije=" + musterije +
                ", stolovi=" + stolovi +
                '}';
    }
}
