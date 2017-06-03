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
	private user = new User(null, '', '', '','', '', '', '', '', '');
	private profilePicURL: string = null;

	constructor(private _router: Router, private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("SELECTEDUSER");
		this.getUser();
		//this.createImageURL();
		this.getProlilePictureURL();
	}

	getUser(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				this.user = res;
			}
			);
	}

	/*
	 *     The old implementation of loading the images, but still good
	 */

	/*downloadImage(url:string){ 
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
	}*/

	getProlilePictureURL() {
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/profilepicurl/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				if(res.profilePicUrl != 'null'){
					this.profilePicURL = res.profilePicUrl;
				}
			}
			);
	}
}