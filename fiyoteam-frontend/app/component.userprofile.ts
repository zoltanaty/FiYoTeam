import {Component, ViewChild } from 'angular2/core';
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
	private user = new User(null, '', '', '','', '', '', '');
	filesToUpload: Array<File> = [];

	private showUpdatedUserMessage = false;

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
        this.makeFileRequest( this.getAndPostService.baseUrl + 'user/profilepic/' + this.userId, [], this.filesToUpload).then((result) => {
            console.log(result);
        }, (error) => {
            console.error(error);
        });
    }
 
    fileChangeEvent(fileInput: any){
        this.filesToUpload = <Array<File>> fileInput.target.files;
    }
 
    makeFileRequest(url: string, params: Array<string>, files: Array<File>) {
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
            xhr.send(formData);
        });
    }

}