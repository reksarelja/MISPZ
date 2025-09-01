package me.mispz.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import me.mispz.crud.Crud;
import me.mispz.util.PersistenceEntityManager;
import org.hibernate.Session;
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

    public void addPice(Pice pice){
        Pic2sto pic2sto = new Pic2sto(pice, this);
        pica.add(pic2sto);
        pice.getStolovi().add(pic2sto);
    }

    public void removePice(Pice pice) {
        for (Iterator<Pic2sto> iterator = pica.iterator(); iterator.hasNext(); ) {
            Pic2sto pic2sto = iterator.next();
            if(pic2sto.getSto().equals(this) && pic2sto.getPice().equals(pice)){
                iterator.remove();
                pic2sto.getPice().getStolovi().remove(pic2sto);
                pic2sto.setSto(null);
                pic2sto.setPice(null);
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

    @Override
    public String toString() {
        return "Sto{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sto sto = (Sto) o;
        return Objects.equals(id, sto.id) && Objects.equals(konobar, sto.konobar) && Objects.equals(racun, sto.racun) && Objects.equals(pica, sto.pica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, konobar, racun, pica);
    }
}
