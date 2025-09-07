package me.mispz.crud;

import jakarta.persistence.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import me.mispz.entities.*;
import me.mispz.util.PersistenceEntityManager;
import me.mispz.util.Rezervacija;
import me.mispz.util.TIP_PLACANJA;

import java.sql.Timestamp;
import java.util.Calendar;

public class Crud {

    private final PersistenceEntityManager pem = PersistenceEntityManager.getInstance();

    public ObservableList<Konobar> getKonobars(){
        EntityManager em = pem.getEntityManager();
        TypedQuery<Konobar> tq = em.createQuery("Select k from Konobar k", Konobar.class);
        ObservableList<Konobar> list = FXCollections.observableList(tq.getResultList());
        em.close();
        return list;
    }
    public ObservableList<Rezervacija> getMus2stos(){
        EntityManager em = pem.getEntityManager();
        ObservableList<Rezervacija> list = FXCollections.observableList(em.createQuery("Select m.sto.id, m.musterija.name, m.musterija.phone_number ,m.id.id from Mus2sto m", Rezervacija.class).getResultList());
        em.close();
        return list;
    }
    public void deleteMus2stoDate(){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createQuery("DELETE FROM Mus2sto m WHERE m.id.id < :timestamp").setParameter("timestamp", new Timestamp(System.currentTimeMillis())).executeUpdate();
        et.commit();
        em.close();
    }
    public void delMus2sto(Rezervacija rez){

        EntityManager em = pem.getEntityManager();
		try(em) {
			EntityTransaction et = em.getTransaction();
			et.begin();
			Mus2sto m2s = em.createQuery("Select m from Mus2sto m where m.time = :time", Mus2sto.class).setParameter("time", rez.getRezTime()).getSingleResult();
			em.remove(m2s);
			et.commit();
		} catch(Exception ignored) {
		}
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
        em.close();
        return list;
    }
    public ObservableList<Nastup> getNastupi(){
        EntityManager em = pem.getEntityManager();
        ObservableList<Nastup> list = FXCollections.observableArrayList(em.createQuery("Select n from Nastup n", Nastup.class).getResultList());
        em.close();
        return list;
    }
    public ObservableList<Obavestenje> getObavestenja(){
        EntityManager em = pem.getEntityManager();
        ObservableList<Obavestenje> list = FXCollections.observableArrayList(em.createQuery("Select o from Obavestenje o", Obavestenje.class).getResultList());
        em.close();
        return list;
    }
    public Pice getPice(Pic2sto p2s){
        EntityManager em = pem.getEntityManager();
        Pice pice = em.createQuery("Select p.pice from Pic2sto p where p = :p", Pice.class).setParameter("p", p2s).getSingleResult();
        em.close();
        return pice;
    }
    public void deletePicaPic2sto(int stoID){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        Sto sto = em.find(Sto.class, stoID);

        em.createQuery("delete from Pic2sto where sto.id = :stoID").setParameter("stoID", stoID).executeUpdate();
        sto.setRacun(null);
        sto.setKonobar(null);
        et.commit();
        em.close();
    }

    public void addKonobar(String jmbg, String name, String lastName, String pass){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            Konobar konobar = new Konobar();
            konobar.setJmbg(jmbg);
            konobar.setName(name);
            konobar.setLastName(lastName);
            konobar.setPassword(pass);
            et.begin();
            em.persist(konobar);
            et.commit();

        }catch(Exception ignored){
        }
        finally{
            em.close();
        }
    }
    public void addPice(String naziv, boolean alc, int stanje, int cena){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        Pice pice = new Pice();
        pice.setName(naziv);
        pice.setAlc(alc);
        pice.setBalance(stanje);
        pice.setPrice(cena);
        et.begin();
        try{
            em.persist(pice);
            et.commit();
        } finally {
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
        Sto sto = em.find(Sto.class, id);
        sto.setKonobar(konobar);
        EntityTransaction et = em.getTransaction();

        et.begin();
        em.merge(sto);
        et.commit();
        em.close();
    }

    public Konobar getTableKonobar(int id){
        try {
            EntityManager em = pem.getEntityManager();
            Konobar konobar = em.createQuery("Select s.konobar from Sto s where s.id = :id", Konobar.class).setParameter("id", id).getSingleResult();
            em.close();
            return konobar;
        } catch(NoResultException e){
            return null;
        }
    }

    public void setKonobarRacun(Racun racun, Konobar konobar){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        racun.setKonobar(konobar.getJmbg());
        em.merge(racun);
        et.commit();
        em.close();
    }


    public void addPic2Sto(Pice pice, Sto sto){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        sto = em.find(Sto.class, sto.getId());
        pice = em.find(Pice.class, pice.getId());

        em.merge(sto.addPice(pice));
        em.merge(sto);

        et.commit();
        em.close();
    }

    public Racun getRacun(int stoId){

        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        Sto sto = em.find(Sto.class, stoId);
        if(sto.getRacun() != null) {
            et.begin();
            Racun racun = sto.getRacun();
            racun = em.merge(racun);
            et.commit();
            em.close();
            return racun;
        } return null;
    }

    public Racun closeRacun(int stoId){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Sto sto = em.find(Sto.class, stoId);
        Racun racun = sto.getRacun();
        racun.setOpen(false);
        racun = em.merge(racun);
        et.commit();
        em.close();
        return racun;
    }

    public void setRacun(int stoId, Enum<TIP_PLACANJA> tipPlacanja) {
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        Sto sto = em.find(Sto.class, stoId);

        float bill = 0F;
        for (Pic2sto p : sto.getPica()) {
            bill += p.getPice().getPrice();
        }

        et.begin();
        if(bill != 0) {
            if(sto.getRacun() == null || !(sto.getRacun().isOpen())) {
                Racun racun = new Racun(bill, tipPlacanja.name());
                racun.setOpen(true);
                em.persist(racun);
                sto.setRacun(racun);
                em.merge(sto);
                et.commit();

            } else if(sto.getRacun().isOpen()) {

                Racun racun = sto.getRacun();
                racun.setBill(bill);
                racun.setPayType(tipPlacanja.name());
                em.merge(racun);
                et.commit();
                em.close();

            }
        }
        em.close();
    }

    public Sto getTable(int id){
        EntityManager em = pem.getEntityManager();
        Sto sto = em.find(Sto.class, id);
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

    public void addMus2sto(Sto sto, Musterija musterija, Timestamp timestamp){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        sto = em.find(Sto.class, sto.getId());
        musterija = em.find(Musterija.class, musterija.getId());
        em.merge(sto.addMusterije(musterija, timestamp));
        em.merge(sto);
        et.commit();
        em.close();
    }

    public ObservableList<Mus2sto> getMus2sto(Sto sto){
        EntityManager em = pem.getEntityManager();
        TypedQuery<Mus2sto> tq = em.createQuery("select p " +
                "from Mus2sto p " +
                "where p.sto.id = :sto", Mus2sto.class);
        tq.setParameter("sto", sto.getId());
        ObservableList<Mus2sto> list = FXCollections.observableList(tq.getResultList());
        em.close();
        return list;
    }

    public Musterija addMusterija(String name,String last, int age){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        Musterija musterija = new Musterija();
        musterija.setName(name);
        musterija.setPhone_number(last);
        musterija.setAge(age);
        et.begin();
        try {
            em.persist(musterija);
            et.commit();
        } finally {
            em.close();
        }
        return musterija;
    }
    public void addTable(){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Sto sto = new Sto();
        em.persist(sto);
        et.commit();
        em.close();
    }

    public void delPice(Pice pice){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        pice.setBalance(pice.getBalance() - 1);
        em.merge(pice);
        et.commit();
        em.close();
    }
    public void addKolicina(Pice pice, int kol){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        pice.setBalance(pice.getBalance() + kol);
        em.merge(pice);
        et.commit();
        em.close();
    }
    public void addNastup(String name, Timestamp timestamp){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Nastup nastup = new Nastup();
        nastup.setNastupName(name);
        nastup.setNastupDate(timestamp);
        em.persist(nastup);
        et.commit();
        em.close();
    }
    public void addObavestenje(String text, Timestamp timestamp){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Obavestenje obavestenje = new Obavestenje();
        obavestenje.setText(text);
        obavestenje.setDate(timestamp);
        em.persist(obavestenje);
        et.commit();
        em.close();
    }
    public void delNastup(){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createQuery("Delete from Nastup n where n.nastupDate < :date").setParameter("date", new Timestamp(System.currentTimeMillis())).executeUpdate();
        et.commit();
        em.close();
    }
    public void delSelNastup(Nastup nastup){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createQuery("Delete from Nastup n where n.id = :id").setParameter("id", nastup.getId()).executeUpdate();
        et.commit();
        em.close();
    }
    public void delObavestenje(){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createQuery("Delete from Obavestenje o where o.date < :date").setParameter("date", new Timestamp(System.currentTimeMillis())).executeUpdate();
        et.commit();
        em.close();
    }
    public void delSelObavestenje(Obavestenje obavestenje){
        EntityManager em = pem.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createQuery("Delete from Obavestenje o where o.id = :id").setParameter("id", obavestenje.getId()).executeUpdate();
        et.commit();
        em.close();
    }
    public Musterija getMusterijaRez(Mus2sto m2s){
        EntityManager em = pem.getEntityManager();
        Musterija mus = em.createQuery("Select m.musterija from Mus2sto m where m.id = :id", Musterija.class).setParameter("id", m2s.getId()).getSingleResult();
        em.close();
        return mus;
    }
    public ObservableList<Musterija> getMus2sto(Timestamp timestamp){
        EntityManager em = pem.getEntityManager();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        Timestamp dayLater = new Timestamp(calendar.getTime().getTime());
        TypedQuery<Musterija> tq = em.createQuery("select m.musterija " +
                "from Mus2sto m " +
                "where m.time > :time and m.time < :dayAFter", Musterija.class);
        tq.setParameter("time",timestamp);
        tq.setParameter("dayAFter",dayLater);
        ObservableList<Musterija> list = FXCollections.observableList(tq.getResultList());
        em.close();
        return list;
    }
    public void setDateMus2sto(Mus2sto m2s, Timestamp timestamp){
        try {
            EntityManager em = pem.getEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            Sto sto = em.find(Sto.class, m2s.getId().getStoId());
            Musterija mus = em.find(Musterija.class, m2s.getId().getMusterijaId());
            em.merge(sto.addMusterije(mus, timestamp));
            em.merge(sto);
            et.commit();
            em.close();
        } catch(Exception ignored){
        }
    }
}
