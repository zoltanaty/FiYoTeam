import {Component, ViewChild,  EventEmitter, Output } from 'angular2/core';
import {Router} from 'angular2/router';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService, User, CollaboratorsForProject} from './service.getandpost'

@Component({
	selector: 'homepage',
	templateUrl: 'app/templates/homepage.template.html',
	directives: [],
	providers: [GetAndPostService]

})

export class HomePageComponent { 

	private userId;
	private collaboratorsForProjects: CollaboratorsForProject[];
	@Output() onChange = new EventEmitter();

	constructor(private _router: Router, private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("USERID");
		this.getCollaboratorsForProjects();
	}

	getCollaboratorsForProjects(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'collaboration/projectswithcollaborators/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				this.collaboratorsForProjects = res;
			}
			);
	}

	changeSelectedUser(selectedUser) {
		localStorage.setItem("SELECTEDUSER", selectedUser);
		this.onChange.emit({value: selectedUser});
	}

}