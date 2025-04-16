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
import { CompanyComponent } from './components/admin/company/company.component';
import { EncadrementComponent } from './components/admin/encadrement/encadrement.component';
import { InternshipOfferComponent } from './components/admin/internship-offer/internship-offer/internship-offer.component';
import { UserComponent } from './components/admin/user/user/user.component';
import { InternshipOfferUserComponent } from './internship/internship-offer/internship-offer.component';
import { PostulerComponent } from './internship/postuler/postuler.component';
import { NavadminComponent } from './components/navadmin/navadmin.component';
import { AdminViewComponent } from './components/admin-view/admin-view.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'courses', component: CoursesComponent },
  { path: 'Contact', component: ContactComponent },
  { path: 'dropdown', component: DropdownComponent },
  { path: 'Events', component: EventsComponent },
  { path: 'pricing', component: PricingComponent },
  { path: 'Trainers', component: TrainersComponent },
  { path: 'post', component: PostComponent },
  { path: 'company', component: CompanyComponent },
  { path: 'encadrement', component: EncadrementComponent },
  { path: 'internship-offer', component: InternshipOfferComponent },
  { path: 'user', component: UserComponent },
  { path: 'user/internship-offer', component: InternshipOfferUserComponent },
  { path: 'postuler/:id', component: PostulerComponent },
  
  {
    path: 'admin',
    component: NavadminComponent,  // Parent layout for admin pages
    children: [
      { path: 'user', component: UserComponent },
      { path: 'company', component: CompanyComponent },
      { path: 'internship-offer', component: InternshipOfferComponent },
      { path: 'encadrement', component: UserComponent },
      
     
    ]
  },

  { path: '**', redirectTo: '' }




];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
