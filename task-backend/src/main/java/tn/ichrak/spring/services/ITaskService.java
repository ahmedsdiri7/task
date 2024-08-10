package tn.ichrak.spring.services;

import tn.ichrak.spring.entities.Task;

import java.util.List;




public interface ITaskService {
	
	
	public List<Task> getAllTasks();

	public List<Task> getAllTask(String token);

}
