import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { CoursesComponent } from './components/courses/courses.component';
import { TrainersComponent } from './components/trainers/trainers.component';
import { EventsComponent } from './components/events/events.component';
import { PricingComponent } from './components/pricing/pricing.component';
import { DropdownComponent } from './components/dropdown/dropdown.component';
import { ContactComponent } from './components/contact/contact.component';
import { PostComponent } from './components/post/post.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CompanyComponent } from './components/admin/company/company.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DeleteConfirmationComponent } from './components/admin/company/delete-confirmation/delete-confirmation.component';
import { MatDialogModule } from '@angular/material/dialog';
import { EncadrementComponent } from './components/admin/encadrement/encadrement.component';
import { MatButtonModule } from '@angular/material/button';
import { DeleteEncadrementConfirmationComponent } from './components/admin/encadrement/delete-confirmation/delete-confirmation.component';
import { InternshipOfferComponent } from './components/admin/internship-offer/internship-offer/internship-offer.component';
import { DeleteOfferConfirmationComponent } from './components/admin/internship-offer/delete-confirmation/delete-confirmation.component';
import { AssignCandidateComponent } from './components/admin/internship-offer/assign-candidate/assign-candidate.component';
import { AssignCompanyComponent } from './components/admin/internship-offer/assign-company/assign-company.component';
import { UserComponent } from './components/admin/user/user/user.component';
import { DeleteUserComponent } from './components/admin/user/delete-user/delete-user.component';
import { HeaderComponent } from './components/header/header.component';
import { PostulerComponent } from './internship/postuler/postuler.component';
import { InternshipOfferUserComponent } from './internship/internship-offer/internship-offer.component';
import { InternshipChartComponent } from './components/admin/internship-offer/internship-chart/internship-chart.component';
import { NavadminComponent } from './components/navadmin/navadmin.component';
import { AdminViewComponent } from './components/admin-view/admin-view.component';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AboutComponent,
    CoursesComponent,
    TrainersComponent,
    EventsComponent,
    PricingComponent,
    DropdownComponent,
    ContactComponent,
    PostComponent,
    CompanyComponent,
    DeleteConfirmationComponent,
    EncadrementComponent,
    DeleteEncadrementConfirmationComponent,
    InternshipOfferComponent,
    DeleteOfferConfirmationComponent,
    AssignCandidateComponent,
    AssignCompanyComponent,
    UserComponent,
    DeleteUserComponent,
    HeaderComponent,
    PostulerComponent,
    InternshipOfferUserComponent,
    InternshipChartComponent,
    NavadminComponent,
    AdminViewComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule, // <-- Required for Angular Material
    MatDialogModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
