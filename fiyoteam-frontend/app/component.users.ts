import {Component, ViewChild } from 'angular2/core';
import {Router} from 'angular2/router';
import {Observable, Subject} from 'rxjs/Rx';
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
	private profilePicUrls  = [];
	private subject = new Subject();

	constructor(private _router: Router, private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("USERID");
		this.getUsers();
	}

	getUsers(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/').map(res => res.json())

		.subscribe(
			(res) => {
				this.users = res;

				for(var user of this.users){
					this.createImageURL(user);
				}
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

	createImageURL(user){

		this.downloadImage(this.getAndPostService.baseUrl + 'user/profilepic/' + user.id).subscribe(imageData =>{
			user.profilePicURL = URL.createObjectURL(new Blob([imageData]));
		});	
	}

}