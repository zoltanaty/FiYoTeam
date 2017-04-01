import {Component, ViewChild } from 'angular2/core';
import {Router} from 'angular2/router';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService, User} from './service.getandpost'
import {OthersLanguageTemplateComponent} from './component.otherslanguages';
import {OthersSkillTemplateComponent} from './component.othersskills';
import {RatingComponent} from './component.rating'

@Component({
	selector: 'othersprofile',
	templateUrl: 'app/templates/othersprofile.template.html',
	directives: [OthersLanguageTemplateComponent, OthersSkillTemplateComponent, RatingComponent],
	providers: [GetAndPostService]

})

export class OthersProfileComponent { 

	private userId;
	private user = new User(null, '', '', '','', '', '', '', '', '');
	private profilePicURL: string = null;

	constructor(private _router: Router, private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("SELECTEDUSER");
		this.getUser();
		this.createImageURL();
	}

	getUser(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				this.user = res;
			}
			);
	}

	downloadImage(url:string){ 
		return Observable.create(observer=>{
			let req = new XMLHttpRequest();
			req.open('get',url);
			req.responseType = "arraybuffer";
			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					observer.next(req.response);
					observer.complete();
				}
			};
			req.send();
		});
	}

	createImageURL(){
		this.downloadImage(this.getAndPostService.baseUrl + 'user/profilepic/' + this.userId).subscribe(imageData =>{
			this.profilePicURL = URL.createObjectURL(new Blob([imageData]));
		});
	}
}