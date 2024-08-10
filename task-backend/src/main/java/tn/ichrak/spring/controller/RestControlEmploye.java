package tn.ichrak.spring.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import tn.ichrak.spring.entities.Employe;
import tn.ichrak.spring.entities.Task;
import tn.ichrak.spring.repository.TaskRepository;
import tn.ichrak.spring.security.jwt.JwtUtils;
import tn.ichrak.spring.services.ITaskService;
import tn.ichrak.spring.services.IEmployeService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RestControlEmploye {


	@Autowired
	IEmployeService iemployeservice;

	@Autowired
	ITaskService itaskservice;
	@Autowired
	JwtUtils jwtUtils;


    @Autowired
    TaskRepository taskRepository;



	@PostMapping("/ajouterEmployer")
	@ResponseBody
	public Employe ajouterEmploye(@RequestBody Employe employe) {
		iemployeservice.ajouterEmploye(employe);
		return employe;
	}



	@PutMapping(value = "/modifyEmail/{id}/{newemail}")
	@ResponseBody
	public void mettreAjourEmailByEmployeId(@PathVariable("newemail") String email, @PathVariable("id") int employeId) {
		iemployeservice.mettreAjourEmailByEmployeId(email, employeId);

	}



	@PutMapping(value = "/affecterTaskAEmploye/{idtask}/{idemp}")
	public void affecterTaskAEmploye(@PathVariable("idtask") int taskId, @PathVariable("idemp") int employeId) {
		iemployeservice.affecterTaskAEmploye(taskId, employeId);
	}

	@PostMapping("/ajouterEtAffecterTaskAEmploye")
	public void ajouterEtAffecterTaskAEmploye(@RequestBody Task task, @RequestHeader("Authorization") String token) {
		try {
			// Log the received token
			System.out.println("Received token: " + token);

			// Remove the 'Bearer ' prefix if it exists
			String cleanedToken = token.replace("Bearer ", "").trim();
			System.out.println("Cleaned token: " + cleanedToken);

			// Decode the token to get the username
			String userName = jwtUtils.getUserNameFromJwtToken(cleanedToken);
			System.out.println("Decoded username: " + userName);

			// Find the employee by email (username)
			Employe employe = iemployeservice.getUserByEmail(userName);

			if (employe != null) {
				// Set the employee for the task
				task.setEmploye(employe);

				// Add and assign the task to the employee
				iemployeservice.ajouterEtAffecterTaskAEmploye(employe.getId(), task, cleanedToken);
			} else {
				// Handle case where employee is not found
				System.out.println("Employe not found with email: " + userName);
			}
		} catch (Exception e) {
			// Print the stack trace for debugging
			e.printStackTrace();
			System.out.println("Error processing token: " + e.getMessage());
		}
	}

	@DeleteMapping("/deleteEmployeById/{idemp}")
	@ResponseBody
	public void deleteEmployeById(@PathVariable("idemp") int employeId) {
		iemployeservice.deleteEmployeById(employeId);

	}

	@DeleteMapping("/deleteTaskById/{idtask}")
	@ResponseBody
	public void deleteTaskById(@PathVariable("idtask") int taskId) {
		iemployeservice.deleteTaskById(taskId);
	}


	@GetMapping(value = "/getNombreEmployeJPQL")
	@ResponseBody
	public int getNombreEmployeJPQL() {

		return iemployeservice.getNombreEmployeJPQL();
	}

	@GetMapping(value = "/getAllEmployeNamesJPQL")
	@ResponseBody
	public List<String> getAllEmployeNamesJPQL() {

		return iemployeservice.getAllEmployeNamesJPQL();
	}



	@PutMapping(value = "/mettreAjourEmailByEmployeIdJPQL/{id}/{newemail}")
	@ResponseBody
	public void mettreAjourEmailByEmployeIdJPQL(@PathVariable("newemail") String email, @PathVariable("id") int employeId) {
		iemployeservice.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}

	@DeleteMapping("/deleteAllTaskJPQL")
	@ResponseBody
	public void deleteAllTaskJPQL() {
		iemployeservice.deleteAllTaskJPQL();

	}



	@GetMapping("/getTasksByEmploye/{id}")
	public ResponseEntity<List<Task>> getTasksByEmploye(
			@PathVariable int id,
			@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
		try {
			List<Task> tasks = iemployeservice.getTasksByEmploye(id);
			if (tasks.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(tasks);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}






	@GetMapping(value = "/getAllEmployes")
    @ResponseBody
	public List<Employe> getAllEmployes() {
		
		return iemployeservice.getAllEmployes();
	}
	@GetMapping("/getAllTasks")
	@ResponseBody
	public List<Task> getAllTask(){
		return iemployeservice.getAllTask();
	}
	@GetMapping("/gettaskById/{id}")
	@ResponseBody
	public Task getTaskById(@PathVariable int id){
		return iemployeservice.getTaskById(id);
	}
	@GetMapping("/task")
	public List<Task> getAllTaskByUser(@RequestHeader("Authorization") String token) {
		try {
			// Log the received token
			System.out.println("Received token: " + token);

			// Remove the 'Bearer ' prefix if it exists
			String cleanedToken = token.replace("Bearer ", "").trim();
			System.out.println("Cleaned token: " + cleanedToken);

			// Decode the token to get the username
			String userName = jwtUtils.getUserNameFromJwtToken(cleanedToken);
			System.out.println("Decoded username: " + userName);

			// Get all tasks for the user
			return itaskservice.getAllTask(cleanedToken);
		} catch (Exception e) {
			// Print the stack trace for debugging
			e.printStackTrace();
			System.out.println("Error processing token: " + e.getMessage());
			return Collections.emptyList(); // Return an empty list in case of error
		}
	}


	@GetMapping("/getemployeById/{id}")
	@ResponseBody
	public Employe getEmployeById(@PathVariable int id){
		return iemployeservice.getEmployeById(id);
	}


	@GetMapping("/getEmployeByEmail/{email}")
	@ResponseBody
	public Employe getEmployeByEmail(@PathVariable("email") String email) {return iemployeservice.getUserByEmail(email);}

}
