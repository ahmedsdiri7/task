package tn.ichrak.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.ichrak.spring.entities.Task;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer>{
    List<Task> findByIduser(String iduser);
} 
