import {Component, ViewChild } from 'angular2/core';
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

	constructor(private _router: Router, private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		
	}
}