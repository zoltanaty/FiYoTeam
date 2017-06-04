import {Component, ViewChild, EventEmitter, Output} from 'angular2/core';
import {Router} from 'angular2/router';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService, User} from './service.getandpost'
import {LanguageTemplateComponent} from './component.languages';
import {SkillTemplateComponent} from './component.skills';
import {MyProjectsComponent} from './component.myprojects'
import {RatingComponent} from './component.rating'
import {ProjectsAppliedForComponent} from './component.projectsappliedfor'

@Component({
	selector: 'userprofile',
	templateUrl: 'app/templates/userprofile.template.html',
	directives: [LanguageTemplateComponent, SkillTemplateComponent, MyProjectsComponent, ProjectsAppliedForComponent, RatingComponent],
	providers: [GetAndPostService]

})

export class UserProfileComponent { 

	private userId;
	private user = new User(null, '', '', '','', '', '', '', '', null);
	private filesToUpload: Array<File> = [];

	private showUpdatedUserMessage = false;

	@Output() onChange = new EventEmitter();

	constructor(private _router: Router, private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("USERID");
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

	updateUser() {

		this.getAndPostService.postData(this.user, this.getAndPostService.baseUrl + 'user/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				this.user = res;
				this.showUpdatedMessage();
			}
			);
	}

	showUpdatedMessage(){
		this.showUpdatedUserMessage = true;
	}

	hideUpdatedMessage(){
		this.showUpdatedUserMessage = false;
	}

	upload() {
		this.makeFileRequest( this.getAndPostService.baseUrl + 'user/profilepic/' + this.userId, this.filesToUpload).then((result) => {
			this.getProlilePictureURL();
		}, (error) => {
			console.error(error);
		});
	}

	fileChangeEvent(fileInput: any){
		this.filesToUpload = <Array<File>> fileInput.target.files;
	}

	makeFileRequest(url: string, files: Array<File>) {
		return new Promise((resolve, reject) => {
			var formData: any = new FormData();
			var xhr = new XMLHttpRequest();
			for(var i = 0; i < files.length; i++) {
				formData.append("file", files[i], files[i].name);
			}
			xhr.onreadystatechange = function () {
				if (xhr.readyState == 4) {
					if (xhr.status == 200) {
						resolve(JSON.parse(xhr.response));
					} else {
						reject(xhr.response);
					}
				}
			}
			xhr.open("POST", url, true);
			xhr.setRequestHeader('authorization', localStorage.getItem("TOKEN"));
			xhr.setRequestHeader('identifier', localStorage.getItem("USERID"));
			xhr.send(formData);
		});
	}

	getProlilePictureURL() {
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/profilepicurl/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				if(res.profilePicUrl != 'null'){
					this.user.profilePicUrl = res.profilePicUrl;
				}
			}
			);
	}

	onSelectedUserChange(newSelectedUSer){
		this.onChange.emit({value: newSelectedUSer});
	}
}