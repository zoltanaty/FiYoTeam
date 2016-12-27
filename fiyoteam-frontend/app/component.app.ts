import {Component} from 'angular2/core';
import {RouteConfig, ROUTER_DIRECTIVES} from 'angular2/router';
import {LoginComponent} from './component.login';
import {MainUserComponent} from './component.main.user';
import {MainAdminComponent} from './component.main.admin';

@Component({
	selector: 'my-app',
	directives: [ROUTER_DIRECTIVES],
	template: `
	<router-outlet></router-outlet>
	`
})

@RouteConfig([
	{ path: '/login', name: 'Login', component: LoginComponent, useAsDefault:true  },
	{ path: '/main-user', name: 'MainUser', component: MainUserComponent  },
	{ path: '/main-admin', name: 'MainAdmin', component: MainAdminComponent  }
	])

export class AppComponent {}

