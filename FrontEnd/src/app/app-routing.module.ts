import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './FrontOfficie/components/home/home.component';
import { AboutComponent } from './FrontOfficie/components/about/about.component';

import { CoursesComponent } from './FrontOfficie/components/courses/courses.component';
import { ContactComponent } from './FrontOfficie/components/contact/contact.component'; 
import { DropdownComponent } from './FrontOfficie/components/dropdown/dropdown.component'; 
import { PricingComponent } from './FrontOfficie/components/pricing/pricing.component'; 
import { TrainersComponent } from './FrontOfficie/components/trainers/trainers.component'; 
import { PostComponent } from './FrontOfficie/components/post/post.component'; 
import { AddTrainingComponent } from './FrontOfficie/pages/add-training/add-training.component'; 
import { TrainingsListComponent } from './FrontOfficie/pages/trainings-list/trainings-list.component'; 
import { EventsComponent } from './FrontOfficie/components/events/events.component'; 
import { TrainingsAvailableComponent } from './FrontOfficie/pages/trainings-available/trainings-available.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'courses', component: CoursesComponent },
  { path: 'Contact', component: ContactComponent },
  { path: 'dropdown', component: DropdownComponent },
  { path: 'pricing', component: PricingComponent },
  { path: 'Trainers', component: TrainersComponent },
  { path: 'post', component: PostComponent },
  { path: 'add-training', component: AddTrainingComponent },
  { path: 'trainings-list', component: TrainingsListComponent },
  { path: 'events', component: EventsComponent },
  { path: 'trainings-available', component: TrainingsAvailableComponent },
  { path: '', redirectTo: '/trainings', pathMatch: 'full' }  ,

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
