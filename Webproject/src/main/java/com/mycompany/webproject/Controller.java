package com.mycompany.webproject;


import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private SessionFactory factory;
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /*
     http://localhost:8080/add?name=%22roshen%22&fname=%22petro%22&lname=%22poroshenko%22&passport=%22AA000001%22&address=%22kyiv;khreshchatik;1%22
     */
    @RequestMapping("/add")
    public AssociationId add(@RequestParam(value = "name", defaultValue = "testAssociation") String name,
            @RequestParam(value = "fname", defaultValue = "testFname") String fname,
            @RequestParam(value = "lname", defaultValue = "testLname") String lname,
            @RequestParam(value = "passport", defaultValue = "testPassport") String passport,
            @RequestParam(value = "address", defaultValue = "testCity;testStreet;testBuilding") String address) {

        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        AssociationId associationId = null;
        try {
            tx = session.beginTransaction();

            Address a = new Address();
            a.setCity(address.split(";")[0]);
            a.setStreet(address.split(";")[1]);
            a.setBuilding(address.split(";")[2]);

            Integer aid = (Integer) session.save(a);

            Person p = new Person();
            p.setFname(fname);
            p.setLname(lname);
            p.setPassport(passport);

            Integer pid = (Integer) session.save(p);

            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            cal.add(Calendar.YEAR, 1); // to get previous year add -1
            Date nextYear = cal.getTime();

            Certificate c = new Certificate();
            c.setDateStart(today);
            c.setDateEnd(nextYear);
            c.setNumber("rfgyuyGUY&RFUTR987");

            Integer cid = (Integer) session.save(c);

            Association association = new Association();

            AssociationId asid = new AssociationId();

            asid.setAddressIdaddress(aid);
            asid.setCertificateIdcertificate(cid);
            asid.setPersonIdperson(pid);

            association.setId(asid);
            association.setAddress(a);
            association.setCertificate(c);
            association.setPerson(p);
            association.setName(name);

            associationId = (AssociationId) session.save(association);

            session.persist(association);

            int id = association.getId().getIdAssociation();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return associationId;
    }

    @RequestMapping("/delete")
    public ArrayList<AssociationId> deleteAssociation(@RequestParam(value = "all", defaultValue = "0") int all,
            @RequestParam(value = "id", defaultValue = "-1") int id) {

        ArrayList<AssociationId> list = new ArrayList<AssociationId>();

        String query = "FROM Association";
        if (all == 0 && id != -1) {
            query = "FROM Association where id.idAssociation = " + id;
        }

        Session session = getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            for (Object o : session.createQuery(query).list()) {
                Association obj = (Association) o;
                AssociationId a = obj.getId();
                list.add(a);
                session.delete(obj);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return list;
    }

    @RequestMapping("/update")
    public AssociationId add(@RequestParam(value = "id", defaultValue = "-1") int id,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "fname", defaultValue = "") String fname,
            @RequestParam(value = "lname", defaultValue = "") String lname,
            @RequestParam(value = "passport", defaultValue = "") String passport,
            @RequestParam(value = "address", defaultValue = "") String address) {

        if (id == -1) {
            return null;
        }

        String query = "FROM Association where id.idAssociation = " + id;

        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        AssociationId associationId = null;
        try {
            tx = session.beginTransaction();

            Association obj = (Association) session.createQuery(query).list().get(0);

            if (!name.equals("")) {
                obj.setName(name);
            }

            Person p = obj.getPerson();
            Address a = obj.getAddress();

            if (!fname.equals("")) {
                p.setFname(fname);
            }

            if (!lname.equals("")) {
                p.setLname(lname);
            }

            if (!passport.equals("")) {
                p.setPassport(passport);
            }

            session.update(p);

            if (!address.equals("")) {
                a.setCity(address.split(";")[0]);
                a.setStreet(address.split(";")[1]);
                a.setBuilding(address.split(";")[2]);
            }

            session.update(a);
            
            session.update(obj);
            
            associationId = obj.getId();
                    
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return associationId;
    }

    @RequestMapping("/get")
    public ArrayList<AssociationItem> getAssociations() {
        ArrayList<AssociationItem> list = new ArrayList<AssociationItem>();

        Session session = getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            for (Object o : session.createQuery("FROM Association").list()) {
                Association obj = (Association) o;
                AssociationItem a = new AssociationItem();

                a.setId(obj.getId().getIdAssociation());
                a.setName(obj.getName());

                a.setDate_reg(obj.getCertificate().getDateStart());
                a.setDate_exp(obj.getCertificate().getDateEnd());
                a.setCertificate_number(obj.getCertificate().getNumber());

                a.setCity(obj.getAddress().getCity());
                a.setStreet(obj.getAddress().getStreet());
                a.setBuilding(obj.getAddress().getBuilding());

                a.setFname(obj.getPerson().getFname());
                a.setLname(obj.getPerson().getLname());
                a.setPassportId(obj.getPerson().getPassport());

                list.add(a);

            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return list;
    }

    private class AssociationItem {

        private int id;
        private String name;
        private String certificate_number;
        private Date date_reg;
        private Date date_exp;
        private String city;
        private String street;
        private String building;
        private String fname;
        private String lname;
        private String passportId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCertificate_number() {
            return certificate_number;
        }

        public void setCertificate_number(String certificate_number) {
            this.certificate_number = certificate_number;
        }

        public Date getDate_reg() {
            return date_reg;
        }

        public void setDate_reg(Date date_reg) {
            this.date_reg = date_reg;
        }

        public Date getDate_exp() {
            return date_exp;
        }

        public void setDate_exp(Date date_exp) {
            this.date_exp = date_exp;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        void setStreet(String street) {
            this.street = street;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getPassportId() {
            return passportId;
        }

        public void setPassportId(String passportId) {
            this.passportId = passportId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    private SessionFactory getSessionFactory() {
        if (factory == null) {
            File f = new File("/home/vova/Webproject/src/main/resources/hibernateUtils/hibernate.cfg.xml");
            factory = new Configuration().configure(f).buildSessionFactory();
        }

        return factory;
    }
}
