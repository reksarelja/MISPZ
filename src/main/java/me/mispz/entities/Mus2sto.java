package me.mispz.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mus2sto")
public class Mus2sto {
    @EmbeddedId
    private Mus2stoId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musterijaId")
    @MapsId("musterijaId")
    private Musterija musterija;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stoId")
    @MapsId("stoId")
    private Sto sto;
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Timestamp time;

    public Mus2sto(){}

    public Mus2sto(Musterija musterija, Sto sto, Timestamp time) {
        this.musterija = musterija;
        this.sto = sto;
        this.time = time;
        id = new Mus2stoId(musterija.getId(), sto.getId(), time);
    }

    public Mus2stoId getId() {
        return id;
    }

    public void setId(Mus2stoId id) {
        this.id = id;
    }

    public Musterija getMusterija() {
        return musterija;
    }

    public void setMusterija(Musterija musterija) {
        this.musterija = musterija;
    }

    public Sto getSto() {
        return sto;
    }

    public void setSto(Sto sto) {
        this.sto = sto;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Mus2sto mus2sto = (Mus2sto) o;
        return Objects.equals(id, mus2sto.id) && Objects.equals(musterija, mus2sto.musterija) && Objects.equals(sto, mus2sto.sto) && Objects.equals(time, mus2sto.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, musterija, sto, time);
    }

    @Override
    public String toString() {
        return "Mus2sto{" +
                "id=" + id +
                ", musterija=" + musterija.getId() +
                ", sto=" + sto.getId() +
                ", time=" + time +
                '}';
    }
}
