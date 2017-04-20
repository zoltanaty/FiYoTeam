import {Component, ViewChild } from 'angular2/core';
import {Router} from 'angular2/router';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService, Skill, ProjectResponse, Project} from './service.getandpost'
import {LanguageTemplateComponent} from './component.languages';


@Component({
	selector: 'myprojects',
	templateUrl: 'app/templates/myprojects.template.html',
	directives: [LanguageTemplateComponent],
	providers: [GetAndPostService]

})

export class MyProjectsComponent { 

	private userId;
	private skills: Skill[];
	private availableSkills: Skill[];
	private newSkill = new Skill(null, '', 50);
	private projects: ProjectResponse[];
	private newProject = new Project(null, '', '', 'active');

	constructor(private getAndPostService: GetAndPostService){}

	ngOnInit(){
		this.userId = localStorage.getItem("USERID");

		this.getProjectSkills();
		this.getAvailableSkills();

	}

	getProjectSkills(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/projects/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				this.projects = res;
			}
			);
	}

	addNewProject() {

		this.getAndPostService.putData(this.newProject, this.getAndPostService.baseUrl + 'user/projects/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				this.projects = res;
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