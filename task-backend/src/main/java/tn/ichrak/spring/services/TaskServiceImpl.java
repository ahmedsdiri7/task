package tn.ichrak.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.ichrak.spring.entities.Task;
import tn.ichrak.spring.repository.TaskRepository;
import tn.ichrak.spring.security.jwt.JwtUtils;

@Service
public class TaskServiceImpl implements ITaskService {


	@Autowired
	TaskRepository taskRepository;
	@Autowired
	JwtUtils jwtUtils;

	public List<Task> getAllTasks() {
		return (List<Task>) taskRepository.findAll();
	}


	@Override
	public List<Task> getAllTask(String token){
		String name=jwtUtils.getUserNameFromJwtToken(token);
		return  taskRepository.findByIduser(name);
	}
}
