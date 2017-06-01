import {Component, ViewChild, EventEmitter, Output  } from 'angular2/core';
import {Router} from 'angular2/router';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService, ProjectResponse} from './service.getandpost'

@Component({
	selector: 'projectsappliedfor',
	templateUrl: 'app/templates/projectsappliedfor.template.html',
	directives: [],
	providers: [GetAndPostService]

})

export class ProjectsAppliedForComponent { 

	private userId;
	private projects: ProjectResponse[];
	private projectCollaborations: Collaboration[];

	@Output() onChange = new EventEmitter();

	constructor(private getAndPostService: GetAndPostService){}

	ngOnInit(){
		this.userId = localStorage.getItem("USERID");

		this.getAppliedProjects();
	}

	getAppliedProjects(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'collaboration/projects/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				this.projects = res;
			}
			);
	}

	changeSelectedUser(selectedUser) {
		localStorage.setItem("SELECTEDUSER", selectedUser);
        this.onChange.emit({value: selectedUser});
    }

    getCollaborationsForProject(projectId: number, ownerId: number){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'collaboration/requests/' + projectId + '/' + ownerId).map(res => res.json())

		.subscribe(
			(res) => {
				this.projectCollaborations = res;
			}
			);
	}

}