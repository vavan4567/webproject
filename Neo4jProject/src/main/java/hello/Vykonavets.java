package hello;
// Generated Apr 27, 2015 3:34:12 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vykonavets  implements java.io.Serializable {

     @GraphId Long id;
     private String name;
     private String address;
     private String type;

	
    public Vykonavets() {
    }
    
    public Vykonavets(String name, String address, String type) {
       this.name = name;
       this.address = address;
       this.type = type;
    }
   
    public long getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        String results  = "name: "+ name  + " address: "+ address + " type"+ type;
        return results;
    }


}


