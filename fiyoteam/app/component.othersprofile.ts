import {Component, ViewChild } from 'angular2/core';
import {Router} from 'angular2/router';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService, User} from './service.getandpost'
import {OthersLanguageTemplateComponent} from './component.otherslanguages';
import {OthersSkillTemplateComponent} from './component.othersskills';
import {OthersProjectsComponent} from './component.othersprojects';
import {OthersRatingComponent} from './component.othersrating'
import {OthersProjectsAppliedForComponent} from './component.othersprojectsappliedfor'

@Component({
	selector: 'othersprofile',
	templateUrl: 'app/templates/othersprofile.template.html',
	directives: [OthersLanguageTemplateComponent, OthersSkillTemplateComponent, OthersProjectsComponent, OthersProjectsAppliedForComponent, OthersRatingComponent],
	providers: [GetAndPostService]

})

export class OthersProfileComponent { 

	private userId;
	private user = new User(null, '', '', '','', '', '', '', '', null);

	constructor(private _router: Router, private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("SELECTEDUSER");
		this.getUser();
	}

	getUser(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				this.user = res;
			}
			);
	}
}