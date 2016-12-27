import {Component} from 'angular2/core';
import {GetAndPostService, User} from './getandpost.service'
import 'rxjs/Rx';

@Component({
    selector: 'login',
    templateUrl: 'app/templates/login.template.html',
    providers: [GetAndPostService]

})
export class LoginComponent { 

	public user = new User(null, '','');

	constructor(private getAndPostService:GetAndPostService) {}

	login() {
        this.getAndPostService.postData(this.user, 'http://localhost:8080/fiyoteam-backend/rest/authentication/login').map(res => res.json())

        .subscribe(
          (res) => {
            console.log(res);
            }
         );
    }
}