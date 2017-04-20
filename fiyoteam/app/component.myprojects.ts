import {Component, ViewChild } from 'angular2/core';
import {Router} from 'angular2/router';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService, Skill, Project, ProjectResponse} from './service.getandpost'
import {LanguageTemplateComponent} from './component.languages';


@Component({
	selector: 'myprojects',
	templateUrl: 'app/templates/myprojects.template.html',
	directives: [LanguageTemplateComponent],
	providers: [GetAndPostService]

})

export class MyProjectsComponent { 

	private userId;
	private availableSkills: Skill[];
	private projects: ProjectResponse[];
	private newProject = new ProjectResponse(new Project(null, '', '', 'active'), new Array<Skill>());
	private projectToEdit = new Project(null, '', '', '');

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

	addSkillToNewProject(skillId){
		var existsAlready = false;
		for (var _i = 0; _i <this.newProject.skills.length; _i++) {
			var skill = this.availableSkills[_i];
			if(skill.id == skillId){
				existsAlready = true;
				break;
			}
		}

		if(existsAlready == false){
			var selectedSkill = new Skill(null, '', null);

			for (var _i = 0; _i < this.availableSkills.length; _i++) {
				var skill = this.availableSkills[_i];
				if(skill.id == skillId){
					selectedSkill.id = skill.id;
					selectedSkill.skill = skill.skill;
					selectedSkill.level = 100;
				}

			}

			this.newProject.skills.push(selectedSkill);
		}
	}

	removeSkillFromNewProject(skill: Skill){
		let index: number = this.newProject.skills.indexOf(skill);
		if (index !== -1) {
			this.newProject.skills.splice(index, 1);
		}  
	}

	resetNewProject(){
		this.newProject = new ProjectResponse(new Project(null, '', '', 'active'), new Array<Skill>());
		this.newProject.project.name = "";
		this.newProject.project.description = "";
		this.newProject.skills = [];
	}

	getAvailableSkills(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'skill/').map(res => res.json())

		.subscribe(
			(res) => {
				this.availableSkills = res;
			}
			);
	}

	setProjectToEdit(prj: Project){
		this.projectToEdit = prj;
	}

}