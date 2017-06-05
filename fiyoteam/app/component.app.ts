import {Component} from 'angular2/core';
import {RouteConfig, ROUTER_DIRECTIVES} from 'angular2/router';
import {LoginComponent} from './component.login';
import {MainAdminComponent} from './component.main.admin';
import {MainUserComponent} from './component.main.user';

@Component({
	selector: 'my-app',
	directives: [ROUTER_DIRECTIVES],
	template: `
	<router-outlet></router-outlet>
	`
})

@RouteConfig([
	{ path: '/login', name: 'Login', component: LoginComponent, useAsDefault:true  },
	{ path: '/main-admin', name: 'MainAdmin', component: MainAdminComponent  },
	{ path: '/main-user', name: 'MainUser', component: MainUserComponent  }
	])

export class AppComponent {}

