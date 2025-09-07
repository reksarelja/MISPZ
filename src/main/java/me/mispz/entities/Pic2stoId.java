package me.mispz.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class Pic2stoId implements Serializable{

    @Column(name = "piceId")
    private Integer piceId;
    @Column(name = "stoId")
    private Integer stoId;
    @Column(name = "id")
    private Timestamp time;

    public Pic2stoId(){}

    public Pic2stoId(Integer pice, Integer sto, Timestamp time){
        piceId = pice;
        stoId = sto;
        this.time = time;
    }

    public Integer getPiceId() {
        return piceId;
    }

    public void setPiceId(Integer piceId) {
        this.piceId = piceId;
    }

    public Integer getStoId() {
        return stoId;
    }

    public void setStoId(Integer stoId) {
        this.stoId = stoId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Pic2stoId{" +
                "piceId=" + piceId +
                ", stoId=" + stoId +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pic2stoId pic2stoId = (Pic2stoId) o;
        return Objects.equals(piceId, pic2stoId.piceId) && Objects.equals(stoId, pic2stoId.stoId) && Objects.equals(time, pic2stoId.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(piceId, stoId, time);
    }

}
