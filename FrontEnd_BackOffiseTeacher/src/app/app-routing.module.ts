import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { CoursesComponent } from './components/courses/courses.component';
import { ContactComponent } from './components/contact/contact.component';
import { DropdownComponent } from './components/dropdown/dropdown.component';
import { PricingComponent } from './components/pricing/pricing.component';
import { TrainersComponent } from './components/trainers/trainers.component';
import { PostComponent } from './components/post/post.component';
import { AddTrainingComponent } from './pages/add-training/add-training.component';
import { TrainingsListComponent } from './pages/trainings-list/trainings-list.component';
import { EventsComponent } from './components/events/events.component';  // Assurez-vous que le chemin est correct

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
  { path: '', redirectTo: '/trainings', pathMatch: 'full' }  ,

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
