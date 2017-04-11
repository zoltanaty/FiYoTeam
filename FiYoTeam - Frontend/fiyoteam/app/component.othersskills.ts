import {Component} from 'angular2/core';
import 'rxjs/Rx';
import {GetAndPostService, Skill} from './service.getandpost'

@Component({
  selector: 'othersskill-template',
  providers: [GetAndPostService],
  templateUrl: 'app/templates/othersskill.template.html'
})

export class OthersSkillTemplateComponent {

  private private userId;
  private skills: Skill[];

  constructor(private getAndPostService: GetAndPostService){}

  ngOnInit(){
   this.userId = localStorage.getItem("SELECTEDUSER");

   this.getUserSkills();
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

}