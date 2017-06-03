import {Component, ViewChild,  EventEmitter, Output } from 'angular2/core';
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

	private userId;
	private users: Array<User> = [];
	private profilePicUrls  = [];
	private nrOfPages: number[] = [];
	private currentPageNr: number;
	private searchCriteria = "";
	@Output() onChange = new EventEmitter();

	constructor(private _router: Router, private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("USERID");
		this.getNrOfPages();
	}

	getUsers(pageNumber: number){
		this.users = null;
		this.currentPageNr = pageNumber;
		
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/page/' + pageNumber + '/' + this.searchCriteria).map(res => res.json())

		.subscribe(
			(res) => {
				this.users = res;

				for(var user of this.users){
					this.getProlilePictureURL(user);
				}
			}
			);
	}

	getNrOfPages(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/nrpages/' + this.searchCriteria).map(res => res.json())

		.subscribe(
			(res) => {
				this.nrOfPages = res;
				this.getUsers(0);
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

	createImageURL(user){

		this.downloadImage(this.getAndPostService.baseUrl + 'user/profilepic/' + user.id).subscribe(imageData =>{
			user.profilePicURL = URL.createObjectURL(new Blob([imageData]));
		});	
	}*/

	getProlilePictureURL(user) {
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/profilepicurl/' + user.id).map(res => res.json())

		.subscribe(
			(res) => {
				if(res.profilePicUrl != 'null'){
					user.profilePicURL = res.profilePicUrl;
				}
			}
			);
	}

	changeSelectedUser(selectedUser) {
		localStorage.setItem("SELECTEDUSER", selectedUser);
        this.onChange.emit({value: selectedUser});
    }

}