System.register(['angular2/core', './service.getandpost', 'angular2/router', 'rxjs/Rx', './component.admin.skills', './component.admin.languages'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, service_getandpost_1, router_1, component_admin_skills_1, component_admin_languages_1;
    var MainAdminComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (service_getandpost_1_1) {
                service_getandpost_1 = service_getandpost_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (_1) {},
            function (component_admin_skills_1_1) {
                component_admin_skills_1 = component_admin_skills_1_1;
            },
            function (component_admin_languages_1_1) {
                component_admin_languages_1 = component_admin_languages_1_1;
            }],
        execute: function() {
            MainAdminComponent = (function () {
                function MainAdminComponent(_router, getAndPostService) {
                    this._router = _router;
                    this.getAndPostService = getAndPostService;
                    /*
                    *   Defines which menu is active
                    */
                    this.activeMenu = 1;
                }
                MainAdminComponent.prototype.ngOnInit = function () {
                    if (localStorage.getItem("USERID") === null) {
                        this._router.navigate(['Login']);
                    }
                    this.userName = localStorage.getItem("USERNAME");
                };
                MainAdminComponent.prototype.logout = function () {
                    localStorage.removeItem("USERID");
                    localStorage.removeItem("USERNAME");
                    localStorage.removeItem("TOKEN");
                    this._router.navigate(['Login']);
                };
                MainAdminComponent.prototype.switchMenu = function (menuToActivate) {
                    this.activeMenu = menuToActivate;
                };
                MainAdminComponent = __decorate([
                    core_1.Component({
                        selector: 'main-admin',
                        templateUrl: 'app/templates/main.admin.template.html',
                        directives: [component_admin_skills_1.AdminSkillsComponent, component_admin_languages_1.AdminLanguagesComponent],
                        providers: [service_getandpost_1.GetAndPostService]
                    }), 
                    __metadata('design:paramtypes', [router_1.Router, service_getandpost_1.GetAndPostService])
                ], MainAdminComponent);
                return MainAdminComponent;
            }());
            exports_1("MainAdminComponent", MainAdminComponent);
        }
    }
});
//# sourceMappingURL=component.main.admin.js.map