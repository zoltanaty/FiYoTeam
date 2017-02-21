import {Component} from 'angular2/core';
import {GetAndPostService} from './service.getandpost'
import {Router} from 'angular2/router';
import 'rxjs/Rx';
import {UserProfileComponent} from './component.userprofile';

@Component({
    selector: 'main-user',
    templateUrl: 'app/templates/main.user.template.html',
    directives: [UserProfileComponent],
    providers: [GetAndPostService]

})
export class MainUserComponent { 

	private userEmail : string;
    private userName : string;

    /*
    *   Defines which menu is active
    */
    private activeMenu = 1;

    private isUserClicked = false;

    constructor(private _router: Router, private getAndPostService:GetAndPostService) {}

    ngOnInit(){
        if (localStorage.getItem("USEREMAIL") === null){
            this._router.navigate(['Login']);
        }
        
        this.userEmail = localStorage.getItem("USEREMAIL");
        this.userName = localStorage.getItem("USERNAME");
        console.log(this.userEmail);
    }

    logout() {
        localStorage.removeItem("USEREMAIL");
        localStorage.removeItem("USERID");
        localStorage.removeItem("USERNAME");

        this._router.navigate(['Login']);
    }

    clickOnUser(){
        this.isUserClicked = !this.isUserClicked;
    }

    switchMenu(menuToActivate){
        this.activeMenu = menuToActivate;
    }
}