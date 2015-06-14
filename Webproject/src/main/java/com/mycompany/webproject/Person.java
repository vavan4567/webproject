package com.mycompany.webproject;
// Generated May 13, 2015 8:02:14 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Person generated by hbm2java
 */
@Entity
@Table(name="person"
    ,catalog="restdb"
)
public class Person  implements java.io.Serializable {


     private Integer idperson;
     private String fname;
     private String lname;
     private String passport;
     private Set<Association> associations = new HashSet<Association>(0);

    public Person() {
    }

    public Person(String fname, String lname, String passport, Set<Association> associations) {
       this.fname = fname;
       this.lname = lname;
       this.passport = passport;
       this.associations = associations;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idperson", unique=true, nullable=false)
    public Integer getIdperson() {
        return this.idperson;
    }
    
    public void setIdperson(Integer idperson) {
        this.idperson = idperson;
    }

    
    @Column(name="fname", length=45)
    public String getFname() {
        return this.fname;
    }
    
    public void setFname(String fname) {
        this.fname = fname;
    }

    
    @Column(name="lname", length=45)
    public String getLname() {
        return this.lname;
    }
    
    public void setLname(String lname) {
        this.lname = lname;
    }

    
    @Column(name="passport", length=45)
    public String getPassport() {
        return this.passport;
    }
    
    public void setPassport(String passport) {
        this.passport = passport;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="person")
    public Set<Association> getAssociations() {
        return this.associations;
    }
    
    public void setAssociations(Set<Association> associations) {
        this.associations = associations;
    }




}

