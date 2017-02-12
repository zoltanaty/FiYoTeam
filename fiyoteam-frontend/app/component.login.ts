import {Component} from 'angular2/core';
import {Router} from 'angular2/router';
import 'rxjs/Rx';
import {GetAndPostService, User} from './service.getandpost'


@Component({
	selector: 'login',
	templateUrl: 'app/templates/login.template.html',
	providers: [GetAndPostService]

})
export class LoginComponent { 

	private user = new User(null, '','', '', '', '');
	private userToRegister = new User(null, '','', '', '', '');

	private loginError: false;

	private ipv4 = "localhost";

	constructor(private _router: Router, private getAndPostService:GetAndPostService) {}

	login() {
		this.getAndPostService.postData(this.user, 'http://' + this.ipv4 + ':8080/fiyoteam-backend/rest/authentication/login').map(res => res.json())

		.subscribe(
			(res) => {
				if(res.id >= 0 && res.role=="user"){
					this._router.navigate(['MainUser']); 
					localStorage.setItem("USERID", res.id);
					localStorage.setItem("USEREMAIL", res.email);
					this.loginError = false;

				}else if(res.id >= 0 && res.role=="admin"){
					this._router.navigate(['MainAdmin']);    
					localStorage.setItem("USERID", res.id);
					localStorage.setItem("USEREMAIL", res.email);
					this.loginError = false;

				}else if(res.id < 0){
					this.loginError = true;
				}
			}
			);
	}

	register(){
		this.getAndPostService.postData(this.userToRegister, 'http://' + this.ipv4 + ':8080/fiyoteam-backend/rest/authentication/register').map(res => res.json())

		.subscribe(
			(res) => {
				if(res.id >= 0){
					console.log("Registration Successful");
				}else{
					console.log("Registration Unsuccessful");
				}
			}
			);
	}


	disableError(value){
		this.loginError = false;
	}
}