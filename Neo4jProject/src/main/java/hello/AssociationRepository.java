package hello;

import org.springframework.data.repository.CrudRepository;

public interface AssociationRepository extends CrudRepository<Association, String> {

    Association findByName(String name);


}