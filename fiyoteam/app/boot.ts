import {bootstrap} from 'angular2/platform/browser';
import {provide} from 'angular2/core';
import {APP_BASE_HREF} from 'angular2/platform/common';
import {HTTP_PROVIDERS, XHRBackend} from 'angular2/http';
import {ROUTER_PROVIDERS} from 'angular2/router';
import {AppComponent} from './component.app';

bootstrap(AppComponent, [
        HTTP_PROVIDERS,
        ROUTER_PROVIDERS,
        provide(APP_BASE_HREF, {useValue : '/' }
]);