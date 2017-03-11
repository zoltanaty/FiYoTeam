import {Component} from 'angular2/core';
import 'rxjs/Rx';
import {GetAndPostService, Skill} from './service.getandpost'

@Component({
  selector: 'skill-template',
  providers: [GetAndPostService],
  templateUrl: 'app/templates/skill.template.html'
})

export class SkillTemplateComponent {

  private private userId;
  private skills: Skill[];
  private availableSkills: Skill[];
  private newSkill = new Skill(null, '', 50);

  constructor(private getAndPostService: GetAndPostService){}

  ngOnInit(){
   this.userId = localStorage.getItem("USERID");

   this.getUserSkills();
   this.getAvailableSkills();

 }

 getUserSkills(){
  this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/skills/' + this.userId).map(res => res.json())

  .subscribe(
    (res) => {
      this.skills = res;
    }
    );
}

getAvailableSkills(){
  this.getAndPostService.getData(this.getAndPostService.baseUrl + 'skill/').map(res => res.json())

  .subscribe(
    (res) => {
      this.availableSkills = res;
    }
    );
}

updateSkills() {

 this.getAndPostService.postData(this.skills, this.getAndPostService.baseUrl + 'user/skills/' + this.userId).map(res => res.json())

 .subscribe(
  (res) => {
    this.skills = res;
  }
  );
}

addNewSkill() {

  this.getAndPostService.putData(this.newSkill, this.getAndPostService.baseUrl + 'user/skills/' + this.userId).map(res => res.json())

  .subscribe(
    (res) => {
      this.skills = res;
    }
    );
}

deleteSkill(skill){

  this.getAndPostService.delete(this.getAndPostService.baseUrl + 'user/skills/' + this.userId + '/' + skill.id).map(res => res.json())

  .subscribe(
    (res) => {
      this.skills = res;
    }
    );
}

}