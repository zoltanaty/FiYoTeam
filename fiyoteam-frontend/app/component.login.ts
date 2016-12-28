import {Component} from 'angular2/core';
import {GetAndPostService, User} from './service.getandpost'
import {Router} from 'angular2/router';
import 'rxjs/Rx';

@Component({
    selector: 'login',
    templateUrl: 'app/templates/login.template.html',
    providers: [GetAndPostService]

})
export class LoginComponent { 

	public user = new User(null, '','');
    private ipv4 = "192.168.0.104";

	constructor(private _router: Router, private getAndPostService:GetAndPostService) {}

	login() {
        this.getAndPostService.postData(this.user, 'http://' + this.ipv4 + ':8080/fiyoteam-backend/rest/authentication/login').map(res => res.json())

        .subscribe(
          (res) => {
            if(res.id >= 0 && res.role=="user"){
                this._router.navigate(['MainUser']); 
                localStorage.setItem("USERID", res.id);
                localStorage.setItem("USEREMAIL", res.email);

            }else if(res.id >= 0 && res.role=="admin"){
                this._router.navigate(['MainAdmin']);    
                localStorage.setItem("USERID", res.id);
                localStorage.setItem("USEREMAIL", res.email);

            }else if(res.id < 0){
                console.log("Failed to Login :(");
            }
            
        }
        );
    }
}