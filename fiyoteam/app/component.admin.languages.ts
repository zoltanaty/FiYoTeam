import {Component} from 'angular2/core';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService} from './service.getandpost'

@Component({
	selector: 'admin-languages',
	templateUrl: 'app/templates/admin-languages.template.html',
	directives: [],
	providers: [GetAndPostService]

})

export class AdminLanguagesComponent { 

	private userId;

	constructor(private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("USERID");
	}

}