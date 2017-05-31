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

	private projects: ProjectResponse[];
	private nrOfPages: number[] = [];
	private currentPageNr: number;
	private searchCriteria = "";

	private userId = localStorage.getItem("USERID");

	@Output() onChange = new EventEmitter();

	constructor(private getAndPostService: GetAndPostService){}

	ngOnInit(){
		this.getNrOfPages();
	}

	getUserProjects(pageNumber: number){
		this.projects = null;
		this.currentPageNr = pageNumber;

		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'project/' + pageNumber + '/' + this.searchCriteria).map(res => res.json())

		.subscribe(
			(res) => {
				this.projects = res;
			}
			);
	}

	getNrOfPages(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'project/nrpages/' + this.searchCriteria).map(res => res.json())

		.subscribe(
			(res) => {
				this.nrOfPages = res;
				this.getUserProjects(0);
			}
			);
	}

	requestCollaboration(userId: number, projectId: number, ownerId: number, project: ProjectResponse){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'collaboration/' + userId + '/' + projectId + '/' + ownerId).map(res => res.json())

		.subscribe(
			(res) => {
				project.collaborationRequestResponse = res;
			}
			);


	}

	changeSelectedUser(selectedUser) {
		localStorage.setItem("SELECTEDUSER", selectedUser);
        this.onChange.emit({value: selectedUser});
    }

}