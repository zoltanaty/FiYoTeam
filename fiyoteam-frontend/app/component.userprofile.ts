import {Component} from 'angular2/core';
import {Router} from 'angular2/router';
import 'rxjs/Rx';
import {GetAndPostService, User} from './service.getandpost'
import {LanguageTemplateComponent} from './languagetemplate.component';
import {SkillTemplateComponent} from './skilltemplate.component';

@Component({
	selector: 'userprofile',
	templateUrl: 'app/templates/userprofile.template.html',
	directives: [LanguageTemplateComponent, SkillTemplateComponent],
	providers: [GetAndPostService]

})

export class UserProfileComponent { 

	private userId;
	private user = new User(null, '', '', '','', '');

	constructor(private _router: Router, private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("USERID");

		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				this.user = res;
			}
			);

	}

}