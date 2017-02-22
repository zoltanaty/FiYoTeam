import {Component} from 'angular2/core';
import {GetAndPostService, Skill} from './service.getandpost'

@Component({
    selector: 'skill-template',
    providers: [GetAndPostService],
    templateUrl: 'app/templates/skills.template.html'
})


export class SkillTemplateComponent {


    public skills: Skill[];
    public newSkill = new Skill(null,null,'');
    public id;
    public skillsfromadmins;
    
    constructor(private getandpostservice: GetAndPostService){}
    
    ngOnInit(){
      this.id = localStorage.getItem('user');

      this.getandpostservice.getData('http://localhost:8080/addressbook_rest/api/v1/skills/getallskills/').map(res => res.json())

      .subscribe(
          (res) => {
            this.skillsfromadmins = res;
        }
        );

      this.getandpostservice.getData('http://localhost:8080/addressbook_rest/api/v1/skillhasuser/getskillhasuser/' + this.id).map(res => res.json())

      .subscribe(
          (res) => {
            this.skills = res;
        }
        );


  }

  updateSkills() {

     this.getandpostservice.postData(this.skills,'http://localhost:8080/addressbook_rest/api/v1/skillhasuser/updateskillhasuser').map(res => res.json())

     .subscribe(
      (res) => {
        this.skills = res;
        console.log(res);
    }
    );
 }

 restoreSkills() {
     
 }

 addNewSkill() {

     this.newSkill.userId = this.id;
     console.log(this.newSkill);

     this.getandpostservice.postData(this.newSkill,'http://localhost:8080/addressbook_rest/api/v1/skillhasuser/addskillhasuser').map(res => res.json())

     .subscribe(
      (res) => {
        this.skills = res;
        console.log(res);
    }
    );
 }


 deleteSkill(skill){

  this.getandpostservice.postData(skill,'http://localhost:8080/addressbook_rest/api/v1/skillhasuser/deleteskillhasuser').map(res => res.json())

  .subscribe(
      (res) => {
        this.skills = res;
        console.log(res);
    }
    );
}



}
