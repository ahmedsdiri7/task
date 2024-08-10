import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Task } from '../../model/task';
import { TaskService } from '../../services/task.service';
import { ExcelService } from '../../services/excel.service';
import { TokenStorageService } from '../../services/token-storage.service';
import { AddTaskComponent } from './add-task/add-task.component';
import { UpdateTaskComponent } from './update-task/update-task.component';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'ngx-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss']
})
export class TaskComponent implements OnInit {
  listTasks: Task[] = [];
  loggedInEmployeeId: number | null = null;
  token: string | null = null;
  constructor(
    private tokenStorage: TokenStorageService,
    private router: Router,
    private authService: AuthService,
    private taskService: TaskService,
    private matDialog: MatDialog,
    private excelService: ExcelService
  ) {}

  ngOnInit(): void {
    this.fetchTasks();
  }

  onOpenDialogClick(): void {
    this.matDialog.open(AddTaskComponent);
  }

  updateTask(idtask: number): void {
    this.taskService.getTaskById(idtask).subscribe(
      (task: Task) => {
        const dialogRef = this.matDialog.open(UpdateTaskComponent, {
          data: task
        });

        dialogRef.afterClosed().subscribe(() => {
          this.fetchTasks(); // Refresh tasks after update
        });
      },
      (error: HttpErrorResponse) => {
        console.error('Error fetching task:', error.message);
      }
    );
  }

  deleteTask(id: number): void {
    this.taskService.deleteTask(id).subscribe(
      () => {
        this.fetchTasks(); // Refresh tasks after deletion
      },
      (error: HttpErrorResponse) => {
        console.error('Error deleting task:', error.message);
        this.handleError(error);
      }
    );
  }

  fetchTasks(): void {
    const token = this.authService.getToken();
    console.log('Retrieved token:', token); // Ajoutez un log pour vérifier le token
    if (token) {
      this.taskService.getTask(token).subscribe(
        (tasks: Task[]) => {
          console.log('Fetched tasks:', tasks); // Ajoutez un log pour vérifier les tâches reçues
          this.listTasks = tasks;
        },
        (error) => console.error('Error fetching tasks:', error)
      );
    } else {
      console.warn('No token found');
    }
  }

  exportAsXLSX(): void {
    this.excelService.exportAsExcelFile(this.listTasks, 'listtasks');
  }

  private handleError(error: HttpErrorResponse): void {
    this.router.navigateByUrl("/auth");
    this.tokenStorage.signOut();
  }
}
