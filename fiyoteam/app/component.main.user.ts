import {Component} from 'angular2/core';
import {GetAndPostService} from './service.getandpost'
import {Router} from 'angular2/router';
import 'rxjs/Rx';
import {UserProfileComponent} from './component.userprofile';
import {UsersComponent} from './component.users';
import {OthersProfileComponent} from './component.othersprofile';

@Component({
    selector: 'main-user',
    templateUrl: 'app/templates/main.user.template.html',
    directives: [UserProfileComponent, UsersComponent, OthersProfileComponent],
    providers: [GetAndPostService]

})
export class MainUserComponent { 

    private userName : string;

    /*
    *   Defines which menu is active
    */
    private activeMenu = 1;
    private selectedUser = -1;

    private isUserClicked = false;

    constructor(private _router: Router, private getAndPostService:GetAndPostService) {}

    ngOnInit(){
        if (localStorage.getItem("USERID") === null){
            this._router.navigate(['Login']);
        }
        
        this.userName = localStorage.getItem("USERNAME");
    }

    logout() {
        localStorage.removeItem("USERID");
        localStorage.removeItem("USERNAME");
        localStorage.removeItem("SELECTEDUSER");
        localStorage.removeItem("TOKEN");

        this._router.navigate(['Login']);
    }

    clickOnUser(){
        this.isUserClicked = !this.isUserClicked;
    }

    switchMenu(menuToActivate){
        this.activeMenu = menuToActivate;
        this.selectedUser = -1;
    }

}
