import {Component} from 'angular2/core';
import {GetAndPostService} from './service.getandpost'
import {Router} from 'angular2/router';
import 'rxjs/Rx';

@Component({
    selector: 'main-admin',
    templateUrl: 'app/templates/main.admin.template.html',
    providers: [GetAndPostService]

})
export class MainAdminComponent { 

	public userEmail : string;

    constructor(private _router: Router, private getAndPostService:GetAndPostService) {}

    ngOnInit(){
        this.userEmail = localStorage.getItem("USEREMAIL");
        console.log(this.userEmail);
    }

    logout() {
        localStorage.removeItem("USEREMAIL");
        localStorage.removeItem("USERID");
        this._router.navigate(['Login']);
    }
}