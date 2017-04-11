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

	private user = new User(null, '', '', '','', '', '', '', '', '');
	private userToRegister = new User(null, '', '', '','', '', '', '', '', '');

	private loginError = false;
	private successfulRegistration = false;
	private unsuccessfulRegistration = false;

	constructor(private _router: Router, private getAndPostService:GetAndPostService) {}

	login() {
		this.getAndPostService.postData(this.user, this.getAndPostService.baseUrl + 'authentication/login').map(res => res.json())

		.subscribe(
			(res) => {
				if(res.id >= 0 && res.role=="user"){
					this._router.navigate(['MainUser']); 
					localStorage.setItem("USERID", res.id);
					localStorage.setItem("USEREMAIL", res.email);
					localStorage.setItem("USERNAME", res.firstName + " " + res.lastName);
					this.loginError = false;

				}else if(res.id >= 0 && res.role=="admin"){
					this._router.navigate(['MainAdmin']);    
					localStorage.setItem("USERID", res.id);
					localStorage.setItem("USEREMAIL", res.email);
					localStorage.setItem("USERNAME", res.firstName + " " + res.lastName);
					this.loginError = false;

				}else if(res.id < 0){
					this.loginError = true;
				}
			}
			);
	}

	register(){
		
		this.getAndPostService.postData(this.userToRegister, this.getAndPostService.baseUrl + 'authentication/register').map(res => res.json())

		.subscribe(
			(res) => {
				if(res.id >= 0){
					this.successfulRegistration = true;
					this.unsuccessfulRegistration = false;
				}else{
					this.unsuccessfulRegistration = true;
					this.successfulRegistration = false;
				}
			}
			);

	}


	disableLoginError(){
		this.loginError = false;
	}

	hasLowerCase(str) {
		return str.toUpperCase() != str;
	}

	hasUpperCase(str) {
		return str.toLowerCase() != str;
	}

	validatePassword(){
		var format = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;

		if(this.userToRegister.password.length == 0){
			return 0;
		}
		else if(this.userToRegister.password.length >= 6){
			if(this.hasLowerCase(this.userToRegister.password) && this.hasUpperCase(this.userToRegister.password)){
				if(format.test(this.userToRegister.password)){
					return 1;
				}else{
					return -1;
				}
			}else{
				return -1;
			}
		}else{
			return -1;
		}
	}	
}