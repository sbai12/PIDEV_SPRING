import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './FrontOfficie/components/home/home.component';
import { AboutComponent } from './FrontOfficie/components/about/about.component'; 
import { CoursesComponent } from './FrontOfficie/components/courses/courses.component';
import { TrainersComponent } from './FrontOfficie/components/trainers/trainers.component'; 
import { PricingComponent } from './FrontOfficie/components/pricing/pricing.component'; 
import { DropdownComponent } from './FrontOfficie/components/dropdown/dropdown.component'; 
import { ContactComponent } from './FrontOfficie/components/contact/contact.component'; 
import { PostComponent } from './FrontOfficie/components/post/post.component'; 
import { FormsModule } from '@angular/forms'; 
import { HttpClientModule } from '@angular/common/http'; 
import { AddTrainingComponent } from './FrontOfficie/pages/add-training/add-training.component';
import { TrainingsListComponent } from './FrontOfficie/pages/trainings-list/trainings-list.component'; 
import { EventsComponent } from './FrontOfficie/components/events/events.component'; 
import { TrainingsAvailableComponent } from './FrontOfficie/pages/trainings-available/trainings-available.component'; 


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AboutComponent,
    CoursesComponent,
    TrainersComponent,
    PricingComponent,
    DropdownComponent,
    ContactComponent,
    PostComponent,
    AddTrainingComponent,
    TrainingsListComponent,
    EventsComponent,
    TrainingsAvailableComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
