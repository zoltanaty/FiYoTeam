import {Component} from 'angular2/core';
import {GetAndPostService} from './service.getandpost'
import {Router} from 'angular2/router';
import 'rxjs/Rx';

@Component({
    selector: 'userprofile',
    templateUrl: 'app/templates/userprofile.template.html',
    providers: [GetAndPostService]

})

export class UserProfileComponent { 

    constructor(private _router: Router, private getAndPostService:GetAndPostService) {}

    ngOnInit(){

    }

}