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
  selector: 'ngx-update-task',
  templateUrl: './update-task.component.html',
  styleUrls: ['./update-task.component.scss']
})
export class UpdateTaskComponent implements OnInit {
  selectedEmployeId:number;
  employees:Employe[];
  task:Task=new Task();
  tasks: Task[]
  constructor(private tokenStorage:TokenStorageService,private authService : AuthService ,private employeService:EmployeService,private taskService:TaskService,private _router:Router,private dialogRef:MatDialogRef<UpdateTaskComponent>) { }

  ngOnInit(): void {
    this.taskService.$eventEmit.subscribe((data)=> {
      this.task=data;
      this.selectedEmployeId=data.employe.id;
      console.log(this.task);
    },err => {
      this._router.navigateByUrl("/auth");
      this.tokenStorage.signOut();
    })
    this.getEmployees();
  }
  public getEmployees(): void {
    this.employeService.getEmployes().subscribe(
      (response: Employe[]) => {
        this.employees = response;
        console.log(this.employees);
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
      },
      (error) => {
        console.error('Error adding task:', error);
      }
    );
  }

 

}
