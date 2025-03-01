import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { CoursesComponent } from './components/courses/courses.component';
import { TrainersComponent } from './components/trainers/trainers.component';
import { PricingComponent } from './components/pricing/pricing.component';
import { DropdownComponent } from './components/dropdown/dropdown.component';
import { ContactComponent } from './components/contact/contact.component';
import { PostComponent } from './components/post/post.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AddTrainingComponent } from './pages/add-training/add-training.component';
import { TrainingsListComponent } from './pages/trainings-list/trainings-list.component';


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
    TrainingsListComponent
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
