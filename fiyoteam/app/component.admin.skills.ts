import {Component} from 'angular2/core';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService} from './service.getandpost'

@Component({
	selector: 'admin-skills',
	templateUrl: 'app/templates/admin-skills.template.html',
	directives: [],
	providers: [GetAndPostService]

})

export class AdminSkillsComponent { 

	private userId;

	constructor(private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("USERID");
	}


}