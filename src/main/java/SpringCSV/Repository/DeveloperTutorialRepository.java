package SpringCSV.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SpringCSV.model.DeveloperTutorial;

@Repository
public interface DeveloperTutorialRepository extends JpaRepository<DeveloperTutorial, Integer>{

}
