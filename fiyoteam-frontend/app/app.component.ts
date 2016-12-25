import {Component} from 'angular2/core';
import {RouteConfig, ROUTER_DIRECTIVES} from 'angular2/router';
import {LoginComponent} from './login.component';

@Component({
    selector: 'my-app',
    directives: [LoginComponent, ROUTER_DIRECTIVES],
    template: `
            <router-outlet></router-outlet>
        `
})

@RouteConfig([
	{ path: '/login', name: 'Login', component: LoginComponent, useAsDefault:true  }
])

export class AppComponent {}

