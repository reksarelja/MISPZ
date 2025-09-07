package me.mispz.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sto")
public class Sto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "konobarId")
    private Konobar konobar;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "racunId")
    private Racun racun;

    @OneToMany(mappedBy = "sto", fetch = FetchType.LAZY)
    private List<Pic2sto> pica = new ArrayList<>();

    @OneToMany(mappedBy = "sto", fetch = FetchType.LAZY)
    private List<Mus2sto> musterije = new ArrayList<>();

    public Pic2sto addPice(Pice pice){
        Pic2sto pic2sto = new Pic2sto(pice, this);
        pica.add(pic2sto);
        return pic2sto;
    }

    public void removePice(Pice pice) {
        for (Iterator<Pic2sto> iterator = pica.iterator(); iterator.hasNext(); ) {
            Pic2sto pic2sto = iterator.next();
            if(pic2sto.getSto().equals(this) && pic2sto.getPice().equals(pice)){
                iterator.remove();
                pic2sto.setSto(null);
                pic2sto.setPice(null);
                return;
            }
        }
    }

    public Mus2sto addMusterije(Musterija musterija, Timestamp timestamp){
        Mus2sto m2s = new Mus2sto(musterija, this, timestamp);
        musterije.add(m2s);
        return m2s;
    }

    public void removeMusterija(Musterija musterija) {
        for (Iterator<Mus2sto> iterator = musterije.iterator(); iterator.hasNext(); ) {
            Mus2sto mus2sto = iterator.next();
            if(mus2sto.getSto().equals(this) && mus2sto.getMusterija().equals(musterija)){
                iterator.remove();
                mus2sto.setSto(null);
                mus2sto.setMusterija(null);
                return;
            }
        }
    }

    public List<Pic2sto> getPica() {
        return pica;
    }

    public void setPica(List<Pic2sto> pica) {
        this.pica = pica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Konobar getKonobar() {
        return konobar;
    }

    public void setKonobar(Konobar konobar) {
        this.konobar = konobar;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public List<Mus2sto> getMusterije() {
        return musterije;
    }

    public void setMusterije(List<Mus2sto> musterije) {
        this.musterije = musterije;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Sto sto = (Sto) o;
        return Objects.equals(id, sto.id) && Objects.equals(konobar, sto.konobar) && Objects.equals(racun, sto.racun) && Objects.equals(pica, sto.pica) && Objects.equals(musterije, sto.musterije);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, konobar, racun, pica, musterije);
    }

    @Override
    public String toString() {
        return "Sto{" +
                "id=" + id +
                '}';
    }

}
