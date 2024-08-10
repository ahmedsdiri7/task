import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { take, tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { Task } from '../model/task';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  $eventEmit = new EventEmitter();
  task:Task;
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private _router:Router,private tokenStorage:TokenStorageService,private http: HttpClient){}

  public getTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiServerUrl}/getAllTasks`);
  }


  getTaskById(idtask:number): Observable<Task> {
    return this.http.get<Task>(this.apiServerUrl+"/getTaskById/"+idtask);
  }
  sendEventData(idtask : number):any{
      
    this.getTaskById(idtask).pipe(take(1)).subscribe(x=>{
      
      this.task=x;
      this.$eventEmit.emit(this.task);
      return x;
    },err => {
      this._router.navigateByUrl("/auth");
      this.tokenStorage.signOut();
    })
    
  }
  public deleteTask(taskId: number): Observable<void> {
    return this.http.delete<void>(this.apiServerUrl+"/deleteTaskById/"+taskId);
  }

 
  addTask(task: Task, token: string): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.post<void>(`${this.apiServerUrl}/ajouterEtAffecterTaskAEmploye`, task, { headers });
  }

  getTask(token: string): Observable<Task[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<Task[]>(`${this.apiServerUrl}/task`, { headers });
  }
}
