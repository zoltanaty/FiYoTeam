import {Component} from 'angular2/core';
import {GetAndPostService, User} from './service.getandpost'
import 'rxjs/Rx';

@Component({
    selector: 'main-user',
    templateUrl: 'app/templates/main.user.template.html',
    providers: [GetAndPostService]

})
export class MainUserComponent { 

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