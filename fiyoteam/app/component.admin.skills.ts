import {Component} from 'angular2/core';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService, Skill} from './service.getandpost'

@Component({
	selector: 'admin-skills',
	templateUrl: 'app/templates/admin-skills.template.html',
	directives: [],
	providers: [GetAndPostService]

})

export class AdminSkillsComponent { 

	private userId;
	private availableSkills: Skill[];
	private newSkill = "";

	constructor(private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("USERID");
		this.getAvailableSkills();
	}

	getAvailableSkills(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'skill/').map(res => res.json())

		.subscribe(
			(res) => {
				this.availableSkills = res;
			}
			);
	}

	addNewSkill(){

		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'skill/' + this.newSkill).map(res => res.json())

		.subscribe(
			(res) => {
				this.availableSkills = res;
			}
			);
	}

	deleteSkill(skillId: number){
		this.getAndPostService.delete(this.getAndPostService.baseUrl + 'skill/' + skillId).map(res => res.json())

		.subscribe(
			(res) => {
				this.availableSkills = res;
			}
			);
	}

}