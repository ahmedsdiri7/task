package tn.ichrak.spring.repository;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tn.ichrak.spring.entities.Employe;
import tn.ichrak.spring.entities.Task;


@Repository
public interface EmployeRepository extends CrudRepository<Employe, Integer>  {
	
	@Query("SELECT e FROM Employe e WHERE e.email=:email and e.password=:password")
	public Employe getEmployeByEmailAndPassword(@Param("email")String login, @Param("password")String password);
	
	@Query("SELECT count(*) FROM Employe")
    public int countemp();
	
    @Query("SELECT nom FROM Employe")
    public List<String> employeNames();


    @Query("SELECT t FROM Task t WHERE t.employe.id = :employeId")
    List<Task> findTasksByEmployeId(@Param("employeId") int employeId);

    @Modifying
    @Transactional
    @Query("UPDATE Employe e SET e.email=:email1 where e.id=:employeId")
    public void mettreAjourEmailByEmployeIdJPQL(@Param("email1")String email, @Param("employeId")int employeId);

    
    @Modifying
    @Transactional
    @Query("DELETE from Task")
    public void deleteAllTaskJPQL();
;


    Optional<Employe> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("Select u from Employe u where u.email=:email")
    public Employe getEmployeByEmail(@Param("email") String email);


}
