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
 * Address generated by hbm2java
 */
@Entity
@Table(name="address"
    ,catalog="restdb"
)
public class Address  implements java.io.Serializable {


     private Integer idaddress;
     private String city;
     private String street;
     private String building;
     private Set<Association> associations = new HashSet<Association>(0);

    public Address() {
    }

    public Address(String city, String street, String building, Set<Association> associations) {
       this.city = city;
       this.street = street;
       this.building = building;
       this.associations = associations;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idaddress", unique=true, nullable=false)
    public Integer getIdaddress() {
        return this.idaddress;
    }
    
    public void setIdaddress(Integer idaddress) {
        this.idaddress = idaddress;
    }

    
    @Column(name="city", length=45)
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    
    @Column(name="street", length=45)
    public String getStreet() {
        return this.street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }

    
    @Column(name="building", length=45)
    public String getBuilding() {
        return this.building;
    }
    
    public void setBuilding(String building) {
        this.building = building;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="address")
    public Set<Association> getAssociations() {
        return this.associations;
    }
    
    public void setAssociations(Set<Association> associations) {
        this.associations = associations;
    }




}


