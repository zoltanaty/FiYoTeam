import {Component} from 'angular2/core';
import {GetAndPostService} from './service.getandpost'
import {Router} from 'angular2/router';
import 'rxjs/Rx';
import {AdminSkillsComponent} from './component.admin.skills'
import {AdminLanguagesComponent} from './component.admin.languages'

@Component({
    selector: 'main-admin',
    templateUrl: 'app/templates/main.admin.template.html',
    directives: [AdminSkillsComponent, AdminLanguagesComponent],
    providers: [GetAndPostService]

})
export class MainAdminComponent { 

	private userName : string;

    /*
    *   Defines which menu is active
    */
    private activeMenu = 1;

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
        localStorage.removeItem("TOKEN");
        this._router.navigate(['Login']);
    }

    switchMenu(menuToActivate){
        this.activeMenu = menuToActivate;
    }
}