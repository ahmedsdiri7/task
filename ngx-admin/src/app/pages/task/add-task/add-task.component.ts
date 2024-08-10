import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Task } from '../../../model/task';
import { Employe } from '../../../model/employe';
import { TaskService } from '../../../services/task.service';
import { EmployeService } from '../../../services/employe.service';
import { TokenStorageService } from '../../../services/token-storage.service';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'ngx-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.scss']
})
export class AddTaskComponent implements OnInit {
  selectedEmployeId: number;
  employees: Employe[] = [];
  task: Task = new Task();
  loggedInEmployeeId: number | null;

  constructor(
    private tokenStorage: TokenStorageService,
    private employeService: EmployeService,
    private taskService: TaskService,
    private _router:Router,
    private authService: AuthService,
    private dialogRef: MatDialogRef<AddTaskComponent>
  ) {}

  ngOnInit(): void {
    this.getEmployees();
    this.loggedInEmployeeId = this.authService.getLoggedInEmployeeId();
  }

  public getEmployees(): void {
    this.employeService.getEmployes().subscribe(
      (response: Employe[]) => {
        this.employees = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        this._router.navigateByUrl("/auth");
        this.tokenStorage.signOut();
      }
    );
  }

  onSubmit(): void {
    const token = this.authService.getToken();
    this.taskService.addTask(this.task, token).subscribe(
      () => {
        this.dialogRef.close();
        this._router.navigateByUrl("/pages/task").then(()=>window.location.reload());
      },
      (error) => {
        console.error('Error adding task:', error);
      }
    );
  }
}
