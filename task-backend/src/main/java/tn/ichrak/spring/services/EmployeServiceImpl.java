package tn.ichrak.spring.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.ichrak.spring.entities.Employe;
import tn.ichrak.spring.entities.Task;
import tn.ichrak.spring.repository.EmployeRepository;
import tn.ichrak.spring.repository.TaskRepository;
import tn.ichrak.spring.security.jwt.JwtUtils;

@Service
public class EmployeServiceImpl implements IEmployeService {

	@Autowired
	EmployeRepository employeRepository;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	JwtUtils jwtUtils;

	@Override
	public Employe authenticate(String login, String password) {
		return employeRepository.getEmployeByEmailAndPassword(login, password);
	}

	@Override
	public List<Task> getAllTask() {
		return (List<Task>) taskRepository.findAll();
	}

	@Override
	public Task getTaskById(int id) {
		return taskRepository.findById(id).orElse(null);
	}

	@Override
	public void ajouterEtAffecterTaskAEmploye(int idEmploye, Task task, String token) {
		String name = jwtUtils.getUserNameFromJwtToken(token);
		Employe employeManagedEntity = employeRepository.findById(idEmploye).orElse(null);
		if (employeManagedEntity != null) {
			task.setEmploye(employeManagedEntity);
			task.setIduser(name);
			taskRepository.save(task);
		}
	}

	@Override
	public Employe getEmployeById(int id) {
		return employeRepository.findById(id).orElse(null);
	}

	@Override
	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	@Override
	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Employe employe = employeRepository.findById(employeId).orElse(null);
		if (employe != null) {
			employe.setEmail(email);
			employeRepository.save(employe);
		}
	}

	@Override
	public void affecterTaskAEmploye(int taskId, int employeId) {
		Task taskManagedEntity = taskRepository.findById(taskId).orElse(null);
		Employe employeManagedEntity = employeRepository.findById(employeId).orElse(null);
		if (taskManagedEntity != null && employeManagedEntity != null) {
			taskManagedEntity.setEmploye(employeManagedEntity);
			taskRepository.save(taskManagedEntity);
		}
	}
	@Override
	public List<Task> getTasksByEmploye(int id) {
		return employeRepository.findTasksByEmployeId(id);
	}

	@Override
	public void deleteTaskById(int taskId) {
		Task taskManagedEntity = taskRepository.findById(taskId).orElse(null);
		if (taskManagedEntity != null) {
			taskRepository.delete(taskManagedEntity);
		}
	}

	@Override
	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}

	@Override
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();
	}

	@Override
	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);
	}

	@Override
	public void deleteAllTaskJPQL() {
		employeRepository.deleteAllTaskJPQL();
	}

	@Override
	public List<Employe> getAllEmployes() {
		return (List<Employe>) employeRepository.findAll();
	}

	@Override
	public Employe getUserByEmail(String email) {
		return employeRepository.getEmployeByEmail(email);
	}

	@Override
	public void deleteEmployeById(int employeId) {
		Employe employe = employeRepository.findById(employeId).get();
		employeRepository.delete(employe);
	}
}
