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

	private loginError = false;
	private successfulRegistration = false;
	private unsuccessfulRegistration = false;

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
		if(this.userToRegister.password == this.userToRegister.passwordAgain){
			this.getAndPostService.postData(this.userToRegister, 'http://' + this.ipv4 + ':8080/fiyoteam-backend/rest/authentication/register').map(res => res.json())

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
		}else{
			this.passwordAgainError = true;
		}	
	}


	disableLoginError(){
		this.loginError = false;
	}

	validatePassword(){
		if(this.userToRegister.password.length == 0){
			return 0;
		}
		else if(this.userToRegister.password.length >= 6){
			return 1;
		}else{
			return -1;
		}
	}

	validatePasswordAgain(){
		if(this.userToRegister.passwordAgain.length == 0){
			return 0;
		}
		else if(this.userToRegister.password.valueOf() == this.userToRegister.passwordAgain.valueOf()){
			return 1;
		}else{
			return -1;
		}
	}
}