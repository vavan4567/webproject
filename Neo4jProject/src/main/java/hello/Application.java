package hello;

import java.io.File;
import java.util.ArrayList;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

        RestTemplate restTemplate = new RestTemplate();
        private static final String IP = "";
        private static String myUrl = "localhost:8080/get";        
        private static String url = "10.15.32.154:8080/vykonavets?_action=select&_name=all";
    
        Vykonavets[] vykonavets_array ;
        Association[] association_array;
        
	@Configuration
	@EnableNeo4jRepositories(basePackages = "hello")
	static class ApplicationConfig extends Neo4jConfiguration {

		public ApplicationConfig() {
			setBasePackage("hello");
		}

		@Bean
		GraphDatabaseService graphDatabaseService() {
			return new GraphDatabaseFactory().newEmbeddedDatabase("accessingdataneo4j.db");
		}
	}

        public static ArrayList<Association> Associations;
        
        @Autowired AssociationRepository associationRepository;
        
        @Autowired VykonavetsRepository vykonavetsRepository;
        
	@Autowired AssociationRepository personRepository;

	@Autowired GraphDatabase graphDatabase;

	public void run(String... args) throws Exception {

               getAl1Data();
                Associations = new ArrayList<Association>();
                
	//	Association greg = new Association("Greg");
	//	Association roy = new Association("Roy");
	//	Association craig = new Association("Craig");

		/*System.out.println("Before linking up with Neo4j...");
		for (Association person : new Association[] { greg, roy, craig }) {
			System.out.println(person);
		}*/

		Transaction tx = graphDatabase.beginTx();
		try {
                    
                        for (Vykonavets v : vykonavets_array) {
                            vykonavetsRepository.save(v);
                            association_array[0].set_vykonavets(v);
                        }
                    
                        for (Association a : association_array) {
                            associationRepository.save(a);
                            Associations.add(a);
                            System.out.println(a.toString());
                        }
                        
                        
			//personRepository.save(greg);
			//personRepository.save(roy);
			//personRepository.save(craig);

			//greg = personRepository.findByName(greg.name);
			//greg.worksWith(roy);
			//greg.worksWith(craig);
			//personRepository.save(greg);

			//roy = personRepository.findByName(roy.name);
			//roy.worksWith(craig);
			// We already know that roy works with greg
			//personRepository.save(roy);

			// We already know craig works with roy and greg

			/*System.out.println("Lookup each person by name...");
			for (String name : new String[] { greg.name, roy.name, craig.name }) {
				System.out.println(personRepository.findByName(name));
			}

			System.out.println("Looking up who works with Greg...");
			for (Association person : personRepository.findByTeammatesName("Greg")) {
				System.out.println(person.name + " works with Greg.");
			}
                        */
                        
                        
                        
			tx.success();
		} finally {
			tx.close();
		}
	}

	public static void main(String[] args) throws Exception {
		FileUtils.deleteRecursively(new File("accessingdataneo4j.db"));

		SpringApplication.run(Application.class, args);
	}
        
        
        private void getAllData() {
             vykonavets_array  = restTemplate.getForObject(url, Vykonavets[].class);
               association_array  = restTemplate.getForObject(myUrl, Association[].class);
                    } 
        
        
        
        
       
        private void outData () {
            //personRepository.save(greg);
			//personRepository.save(roy);
			//personRepository.save(craig);

			//greg = personRepository.findByName(greg.name);
			//greg.worksWith(roy);
			//greg.worksWith(craig);
			//personRepository.save(greg);

			//roy = personRepository.findByName(roy.name);
			//roy.worksWith(craig);
			// We already know that roy works with greg
			//personRepository.save(roy);

			// We already know craig works with roy and greg

			/*System.out.println("Lookup each person by name...");
			for (String name : new String[] { greg.name, roy.name, craig.name }) {
				System.out.println(personRepository.findByName(name));
			}

			System.out.println("Looking up who works with Greg...");
			for (Association person : personRepository.findByTeammatesName("Greg")) {
				System.out.println(person.name + " works with Greg.");
			}
                        */
        }

        private void getAl1Data() {
            vykonavets_array =  new Vykonavets[2];
            vykonavets_array[0] = new Vykonavets("petro", "Kyiv", "president");  
            vykonavets_array[1] = new Vykonavets("arseniy", "Kyiv", "petrovich");  
            association_array =  new Association[1];
            association_array[0] = new Association("Ukraine");  
           
        
        } 
}