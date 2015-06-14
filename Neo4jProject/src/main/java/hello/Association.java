package hello;

import java.util.HashSet;
import java.util.Set;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Association {

    @GraphId Long id;
    public String name;
    public String fname;

    public Association() {}
    public Association(String name) { this.name = name; }

    @RelatedTo(type="VYKONAVETS", direction=Direction.BOTH)
    public @Fetch Set<Vykonavets> vykonavets;

    public  Set<Vykonavets> getVykonavets() {
      return vykonavets;
    }
    

    public void set_vykonavets(Vykonavets vykonavets)
    {
        if (this.vykonavets == null) {
            this.vykonavets = new HashSet<Vykonavets>();
        }
        this.vykonavets.add(vykonavets);
    }
    
    public String toString() {
        String results = "ID + " + id +"\n" + name + "'s members include\n";
        if (vykonavets != null) {
            for (Vykonavets person : vykonavets) {
                results += "\t- " + person.toString() + "\n";
            }
        }
        return results;
    }

    public Long getId() {
        return id;
    }
}