import { Component, OnInit } from '@angular/core';
import { Employe } from '../../../model/employe';
import { EmployeService } from '../../../services/employe.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { TokenStorageService } from '../../../services/token-storage.service';
import { Task } from '../../../model/task';
import { TaskService } from '../../../services/task.service';

@Component({
  selector: 'ngx-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss', './list.component.css'],
})
export class ListComponent implements OnInit {
  listtasks: Task[] = [];
  listemploye: Employe[] = [];
  selectedEmploye: Employe | null = null;
  task: Task | null = null;

  constructor(
    private tokenStorage: TokenStorageService,
    private employeService: EmployeService,
    private taskService: TaskService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getEmployes();
    this.taskService.$eventEmit.subscribe(
      (data: Task) => {
        this.task = data;
        console.log('Task received:', this.task);
      },
      (err: HttpErrorResponse) => {
        this.handleAuthError();
      }
    );
  }

  employeSelection(employe: Employe): void {
    this.selectedEmploye = employe;
    this.getTasksForEmploye();
  }

  public getEmployes(): void {
    this.employeService.getEmployes().subscribe(
      (response: Employe[]) => {
        this.listemploye = response;
        console.log('Employes received:', this.listemploye);
        if (this.listemploye.length > 0) {
          this.selectedEmploye = this.listemploye[0];
          this.getTasksForEmploye();
        }
      },
      (error: HttpErrorResponse) => {
        this.handleAuthError();
      }
    );
  }

  public getTasksForEmploye(): void {
    if (this.selectedEmploye) {
      this.employeService.getTasksByEmploye(this.selectedEmploye.id).subscribe(
        (data: Task[]) => {
          this.listtasks = data;
          console.log('Tasks received:', this.listtasks);
        },
        (err: HttpErrorResponse) => {
          this.handleAuthError();
        }
      );
    }
  }

  private handleAuthError(): void {
    this.router.navigateByUrl('/auth');
    this.tokenStorage.signOut();
  }
}
