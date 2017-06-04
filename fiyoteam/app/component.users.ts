import {Component, ViewChild,  EventEmitter, Output } from 'angular2/core';
import {Router} from 'angular2/router';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService, User} from './service.getandpost'

@Component({
	selector: 'users',
	templateUrl: 'app/templates/users.template.html',
	directives: [],
	providers: [GetAndPostService]
})

export class UsersComponent { 

	private userId;
	private users: Array<User> = [];
	private nrOfPages: number[] = [];
	private currentPageNr: number;
	private searchCriteria = "";
	@Output() onChange = new EventEmitter();

	constructor(private _router: Router, private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("USERID");
		this.getNrOfPages();
	}

	getUsers(pageNumber: number){
		this.users = null;
		this.currentPageNr = pageNumber;
		
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/page/' + pageNumber + '/' + this.searchCriteria).map(res => res.json())

		.subscribe(
			(res) => {
				this.users = res;
			}
			);
	}

	getNrOfPages(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/nrpages/' + this.searchCriteria).map(res => res.json())

		.subscribe(
			(res) => {
				this.nrOfPages = res;
				this.getUsers(0);
			}
			);
	}

	changeSelectedUser(selectedUser) {
		localStorage.setItem("SELECTEDUSER", selectedUser);
        this.onChange.emit({value: selectedUser});
    }

}