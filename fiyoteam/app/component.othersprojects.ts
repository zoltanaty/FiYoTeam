import {Component, ViewChild } from 'angular2/core';
import {Router} from 'angular2/router';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService, ProjectResponse} from './service.getandpost'

@Component({
	selector: 'othersprojects',
	templateUrl: 'app/templates/othersprojects.template.html',
	directives: [],
	providers: [GetAndPostService]

})

export class OthersProjectsComponent { 

	private userId;
	private projects: ProjectResponse[];

	constructor(private getAndPostService: GetAndPostService){}

	ngOnInit(){
		this.userId = localStorage.getItem("SELECTEDUSER");

		this.getUserProjects();
	}

	getUserProjects(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/projects/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				this.projects = res;
			}
			);
	}

}