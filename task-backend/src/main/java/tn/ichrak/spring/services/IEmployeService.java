package tn.ichrak.spring.services;

import java.util.List;
import tn.ichrak.spring.entities.Employe;
import tn.ichrak.spring.entities.Task;

public interface IEmployeService {
	Employe authenticate(String login, String password);
	List<Task> getAllTask();
	Task getTaskById(int id);
	void ajouterEtAffecterTaskAEmploye(int idEmploye, Task task, String token);
	Employe getEmployeById(int id);
	int ajouterEmploye(Employe employe);
	void mettreAjourEmailByEmployeId(String email, int employeId);
	void affecterTaskAEmploye(int taskId, int employeId);
	void deleteTaskById(int taskId);
	int getNombreEmployeJPQL();
	List<String> getAllEmployeNamesJPQL();
	void mettreAjourEmailByEmployeIdJPQL(String email, int employeId);
	void deleteAllTaskJPQL();
	List<Employe> getAllEmployes();
	Employe getUserByEmail(String email);
	void deleteEmployeById(int employeId);
	public List<Task> getTasksByEmploye(int id);
}
