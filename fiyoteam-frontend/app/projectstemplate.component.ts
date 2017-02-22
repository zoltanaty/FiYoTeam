import {Component} from 'angular2/core';
import {GetAndPostService, Project} from './service.getandpost'

@Component({
  selector: 'project-template',
  providers: [GetAndPostService],
  templateUrl: `app/templates/projects.template.html`
})

export class ProjectsTemplateComponent {

  public projects: Project[];
  public newProject = new Project(null,null);
  public id;
  public projectsfromadmins;

  constructor(private getAndPostService: GetAndPostService){}

  ngOnInit(){
   this.id = localStorage.getItem('user');

   this.getAndPostService.getData('http://localhost:8080/addressbook_rest/api/v1/projects/getallprojects/').map(res => res.json())

   .subscribe(
    (res) => {
      this.projectsfromadmins = res;
    }
    );

   this.getAndPostService.getData('http://localhost:8080/addressbook_rest/api/v1/projecthasuser/getprojecthasuser/' + this.id).map(res => res.json())

   .subscribe(
    (res) => {
      this.projects = res;
      console.log("VALUE RECEIVED: ",res);
    }
    );
   
 }

 updateProjects() {

   this.getAndPostService.postData(this.projects,'http://localhost:8080/addressbook_rest/api/v1/projecthasuser/updatesprojecthasuser').map(res => res.json())

   .subscribe(
    (res) => {
      this.projects = res;
      console.log(res);
    }
    );
 }
 

 addNewProject() {

  this.newProject.userId = this.id;

  this.getAndPostService.postData(this.newProject,'http://localhost:8080/addressbook_rest/api/v1/projecthasuser/addprojecthasuser').map(res => res.json())

  .subscribe(
    (res) => {
      this.projects = res;
    }
    );
}

deleteProject(project){

  console.log(project);

  this.getAndPostService.postData(project,'http://localhost:8080/addressbook_rest/api/v1/projecthasuser/deleteprojecthasuser').map(res => res.json())

  .subscribe(
    (res) => {
      this.projects = res;
      console.log(res);
    }
    );
}
}