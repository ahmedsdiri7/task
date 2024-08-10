import { NgModule } from '@angular/core';
import { NbMenuModule } from '@nebular/theme';

import { ThemeModule } from '../@theme/theme.module';
import { PagesComponent } from './pages.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { ECommerceModule } from './e-commerce/e-commerce.module';
import { PagesRoutingModule } from './pages-routing.module';
import { MiscellaneousModule } from './miscellaneous/miscellaneous.module';
import { MatDialogModule } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
import { TaskComponent } from './task/task.component';
import { AddTaskComponent } from './task/add-task/add-task.component';
import { UpdateTaskComponent } from './task/update-task/update-task.component';
import { EmployeComponent } from './employe/employe.component';
import { AddEmployeComponent } from './employe/add-employe/add-employe.component';
import { UpdateEmployeComponent } from './employe/update-employe/update-employe.component';
import { ChartModule } from 'angular2-chartjs';


@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    DashboardModule,
    ECommerceModule,
    MiscellaneousModule,
    MatDialogModule,
    FormsModule,
    ChartModule,
    
  ],
  declarations: [
    PagesComponent,
   
    TaskComponent,
    AddTaskComponent,
    UpdateTaskComponent,
    EmployeComponent,
    AddEmployeComponent,
    UpdateEmployeComponent,

   
  ],
})
export class PagesModule {
}
