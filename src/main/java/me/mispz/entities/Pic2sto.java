package me.mispz.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "pic2sto")
public class Pic2sto {
    @EmbeddedId
    private Pic2stoId id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "piceId")
    @MapsId("piceId")
    private Pice pice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stoId")
    @MapsId("stoId")
    private Sto sto;
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Timestamp time;

    public Pic2sto(){}

    public Pic2sto(Pice pice, Sto sto){
        this.pice = pice;
        this.sto = sto;
        this.id = new Pic2stoId(pice.getId(), sto.getId(), new Timestamp(System.currentTimeMillis()));
    }

    public Pic2stoId getId() {
        return id;
    }

    public void setId(Pic2stoId id) {
        this.id = id;
    }

    public Pice getPice() {
        return pice;
    }

    public void setPice(Pice pice) {
        this.pice = pice;
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
    public String toString() {
        return "Pic2sto{" +
                "id=" + id +
                ", pice=" + pice.getId() +
                ", sto=" + sto.getId() +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pic2sto pic2sto = (Pic2sto) o;
        return Objects.equals(id, pic2sto.id) && Objects.equals(pice, pic2sto.pice) && Objects.equals(sto, pic2sto.sto) && Objects.equals(time, pic2sto.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pice, sto, time);
    }
}
