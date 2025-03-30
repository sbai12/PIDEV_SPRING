import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { CoursesComponent } from './components/courses/courses.component';
import { ContactComponent } from './components/contact/contact.component';
import { DropdownComponent } from './components/dropdown/dropdown.component';
import { EventsComponent } from './components/events/events.component';
import { PricingComponent } from './components/pricing/pricing.component';
import { TrainersComponent } from './components/trainers/trainers.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { EditPostComponent } from './components/edit-post/edit-post.component';
import { ViewAllComponent } from './components/view-all/view-all.component';
import { ViewPostComponent } from './components/view-post/view-post.component';
import { AdminStatisticsComponent } from './components/admin-statistics/admin-statistics.component';
import { AdminViewComponent } from './components/admin-view/admin-view.component';
import { PostDetailComponent } from './components/post-detail/post-detail.component';
import { NavadminComponent } from './components/navadmin/navadmin.component';
import { AddTrainingComponent } from './admin/add-training/add-training.component';
import { TrainingListComponent } from './admin/training-list/training-list.component';
import { EditTrainingComponent } from './admin/edit-training/edit-training.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'courses', component: CoursesComponent },
  { path: 'Contact', component: ContactComponent },
  { path: 'dropdown', component: DropdownComponent },
  { path: 'Events', component: EventsComponent },
  { path: 'pricing', component: PricingComponent },
  { path: 'Trainers', component: TrainersComponent },
  { path: 'createpost', component: CreatePostComponent },
  { path: 'editpost/:id', component: EditPostComponent },
  { path: 'viewall', component: ViewAllComponent },
  { path: 'viewpost/:id', component: ViewPostComponent },
  {
    path: 'admin',
    component: NavadminComponent,  // Parent layout pour l'administration
    children: [
      { path: '', redirectTo: 'view', pathMatch: 'full' }, // ✅ Redirige vers /admin/view si vide
      { path: 'statistic', component: AdminStatisticsComponent },
      { path: 'view', component: AdminViewComponent },
      { path: 'post/:id', component: PostDetailComponent },
      
      // Nouvelle route pour afficher la liste des formations
      { path: 'trainings', component: TrainingListComponent },
      
      // Nouvelle route pour ajouter une formation
      { path: 'trainings/add', component: AddTrainingComponent },
      
      { path: 'trainings/edit/:id', component: EditTrainingComponent }, // Route pour l'édition

      
      { path: '**', redirectTo: 'view', pathMatch: 'full' } // ✅ Gestion des routes inconnues sous /admin
    ]
  },  



  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
