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
import { PostComponent } from './components/post/post.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'courses', component: CoursesComponent },
  { path: 'Contact', component: ContactComponent },
  { path: 'dropdown', component: DropdownComponent },
  { path: 'Events', component: EventsComponent },
  { path: 'pricing', component: PricingComponent },
  { path: 'Trainers', component: TrainersComponent },
  { path: 'post', component: PostComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
