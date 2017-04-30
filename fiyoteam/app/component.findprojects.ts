import {Component, ViewChild, EventEmitter, Output } from 'angular2/core';
import {Router} from 'angular2/router';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService, ProjectResponse} from './service.getandpost'

@Component({
	selector: 'findprojects',
	templateUrl: 'app/templates/findprojects.template.html',
	directives: [],
	providers: [GetAndPostService]

})

export class FindProjectsComponent { 

	private userId;
	private projects: ProjectResponse[];
	@Output() onChange = new EventEmitter();

	constructor(private getAndPostService: GetAndPostService){}

	ngOnInit(){
		this.getUserProjects();
	}

	getUserProjects(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'project/').map(res => res.json())

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

}