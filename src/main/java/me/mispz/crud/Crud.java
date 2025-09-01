package me.mispz.crud;

import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import me.mispz.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import me.mispz.util.PersistenceEntityManager;
import me.mispz.util.TIP_PLACANJA;

public class Crud {

    private final PersistenceEntityManager pem = PersistenceEntityManager.getInstance();

    public ObservableList<Konobar> getKonobars(){
        EntityManager em = pem.getEntityManager();
        TypedQuery<Konobar> tq = em.createQuery("Select k from Konobar k", Konobar.class);
        ObservableList<Konobar> list = FXCollections.observableList(tq.getResultList());
        em.close();
        return list;
    }

    public ObservableList<Sto> getTables(){
        EntityManager em = pem.getEntityManager();
        TypedQuery<Sto> tq = em.createQuery("Select s " +
                "from Sto s ", Sto.class);
        ObservableList<Sto> list = FXCollections.observableArrayList(tq.getResultList());
        em.close();
        return list;
    }

    public ObservableList<Pice> getPica(){
        EntityManager em = pem.getEntityManager();
        TypedQuery<Pice> tq = em.createQuery("Select p " +
                "from Pice p ", Pice.class);
        ObservableList<Pice> list = FXCollections.observableArrayList(tq.getResultList());
        em.close();
        return list;
    }

    public ObservableList<Pic2sto> getStoPica(Sto sto){
        EntityManager em = pem.getEntityManager();
        TypedQuery<Pic2sto> tq = em.createQuery("select p " +
                "from Pic2sto p " +
                "where p.sto = :sto", Pic2sto.class);
        tq.setParameter("sto", sto);
        ObservableList<Pic2sto> list = FXCollections.observableList(tq.getResultList());
        return list;
    }

    public void deletePicaPic2sto(Sto sto){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin();

        int id = sto.getId();
        Sto s = em.createQuery("Select s from Sto s where s.id = :id", Sto.class).setParameter("id", id).getSingleResult();

        Pic2sto p2s = s.getPica().getLast();

        Pice pice = p2s.getPice();

        s.removePice(pice);

        em.merge(s);
        em.remove(p2s);

        et.commit();
        em.close();
    }

    public void addKonobar(String jmbg, String name, String lastName, String pass){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        Konobar konobar = new Konobar();
        konobar.setJmbg(jmbg);
        konobar.setName(name);
        konobar.setLastName(lastName);
        konobar.setPassword(pass);
        et.begin();
        try{
            em.persist(konobar);
            et.commit();

        } finally{
            em.close();
        }
    }

    public Vlasnik getVlasnik(){
        EntityManager em = pem.getEntityManager();
        TypedQuery<Vlasnik> tq = em.createQuery("Select v from Vlasnik v", Vlasnik.class);
        Vlasnik vlasnik = tq.getSingleResult();
        em.close();
        return vlasnik;
    }

    public void setTableKonobar(Konobar konobar, int id){
        EntityManager em = pem.getEntityManager();
        TypedQuery<Sto> tq = em.createQuery("Select s " +
                "from Sto s " +
                "where s.id = :id", Sto.class);
        tq.setParameter("id", id);
        Sto sto = tq.getSingleResult();
        sto.setKonobar(konobar);
        EntityTransaction et = em.getTransaction();

        et.begin();
        em.merge(sto);
        et.commit();
        em.close();
    }

    public void setTableRacun(int racunId, int stoId) {
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();

        Sto sto = em.find(Sto.class, stoId);
        sto.setRacun(em.find(Racun.class, racunId));

        et.begin();
        em.merge(sto);
        et.commit();
        em.close();
    }

    public void addPic2Sto(Pice pice, Sto sto){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        sto.addPice(pice);
        em.merge(sto);
        et.commit();
        em.close();
    }

    public Racun getRacun(Sto sto){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Racun racun = sto.getRacun();
        racun.setOpen(false);
        em.merge(racun);
        et.commit();
        em.close();
        return racun;
    }

    public void setRacun(Sto sto){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        float bill = 0F;
        for(Pic2sto p : sto.getPica()){
            bill += p.getPice().getPrice();
        }
        TypedQuery<Boolean> tq = em.createQuery("select s.racun is null from Sto s where s.id = :id", Boolean.class);
        tq.setParameter("id", sto.getId());
        et.begin();
        boolean temp = tq.getSingleResult();
        if(temp) {
            Racun racun = new Racun();
            racun.setBill(bill);
            racun.setPayType(TIP_PLACANJA.credit_card.name());
            racun.setOpen(true);
            em.persist(racun);
            sto.setRacun(racun);
            em.merge(sto);
            et.commit();
            System.out.println("create");
            em.close();

        } else if(sto.getRacun().isOpen()){
            Racun racun = sto.getRacun();
            racun.setBill(bill);
            racun.setPayType(TIP_PLACANJA.credit_card.name());
            em.merge(racun);
            et.commit();
            System.out.println("alter");
            em.close();
        } else {
            em.close();
        }
    }

    public Sto getTable(int id){
        EntityManager em = pem.getEntityManager();
        TypedQuery<Sto> tq = em.createQuery("Select s " +
                "from Sto s " +
                "where s.id = :id", Sto.class);
        tq.setParameter("id", id);
        Sto sto = tq.getSingleResult();
        em.close();
        return sto;
    }

    public boolean checkLog(String name, String pass){
        Vlasnik vlasnik = getVlasnik();
        ObservableList<Konobar> konobari = getKonobars();
        if((vlasnik.getName().equals(name) && vlasnik.getPass().equals(pass))){
            return true;
        }
        for(Konobar t : konobari){
            if((t.getName().equals(name) && t.getPassword().equals(pass)))
                return true;
        }
        return false;
    }

    public Konobar getKonobar(String name, String pass){
        ObservableList<Konobar> konobari = getKonobars();
        for (Konobar t : konobari) {
            if ((t.getName().equals(name) && t.getPassword().equals(pass)))
                return t;
        }
        return null;
    }

}
