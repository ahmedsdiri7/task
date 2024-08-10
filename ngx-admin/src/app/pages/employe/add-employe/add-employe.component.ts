import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Employe } from '../../../model/employe';
import { EmployeService } from '../../../services/employe.service';

@Component({
  selector: 'ngx-add-employe',
  templateUrl: './add-employe.component.html',
  styleUrls: ['./add-employe.component.scss']
})
export class AddEmployeComponent implements OnInit {
  employe:Employe=new Employe();
  selectedDirectionId:number;
  constructor(private _router:Router,private dialogRef:MatDialogRef<AddEmployeComponent>,private serviceEmploye:EmployeService) { }

  ngOnInit(): void {
    
  }
  addEmploye(){
    
    this.serviceEmploye.addEmploye1(this.employe).subscribe(()=>{
      this.dialogRef.close();
      this._router.navigateByUrl("/pages/employe").then(()=>window.location.reload());
      console.log(this.employe);
    })
  }
}
