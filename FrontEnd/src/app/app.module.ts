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
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { EditPostComponent } from './components/edit-post/edit-post.component';
import { ViewAllComponent } from './components/view-all/view-all.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { ViewPostComponent } from './components/view-post/view-post.component';
import { AdminStatisticsComponent } from './components/admin-statistics/admin-statistics.component';
import { AdminViewComponent } from './components/admin-view/admin-view.component';
import { PostDetailComponent } from './components/post-detail/post-detail.component';
import { NavadminComponent } from './components/navadmin/navadmin.component';
import { AdminFooterComponent } from './components/admin-footer/admin-footer.component';
import { TrainingListComponent } from './admin/training-list/training-list.component';
import { AddTrainingComponent } from './admin/add-training/add-training.component';
import { EditTrainingComponent } from './admin/edit-training/edit-training.component';
import { LoginComponent } from './login/login.component';
import { SignInComponent } from './sign-in/sign-in.component';


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
    CreatePostComponent,
    EditPostComponent,
    ViewAllComponent,
    ViewPostComponent,
    AdminStatisticsComponent,
    AdminViewComponent,
    PostDetailComponent,
    NavadminComponent,
    AdminFooterComponent,
    TrainingListComponent,
    AddTrainingComponent,
    EditTrainingComponent,
    LoginComponent,
    SignInComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgxPaginationModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
