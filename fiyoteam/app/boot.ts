import {bootstrap} from 'angular2/platform/browser';
import {provide} from 'angular2/core';
import {HTTP_PROVIDERS, XHRBackend} from 'angular2/http';
import {ROUTER_PROVIDERS} from 'angular2/router';
import {AppComponent} from './component.app';

bootstrap(AppComponent, [
        HTTP_PROVIDERS,
        ROUTER_PROVIDERS,
]);