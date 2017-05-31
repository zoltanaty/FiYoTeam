System.register(['angular2/core', './service.getandpost', 'angular2/router', 'rxjs/Rx', './component.userprofile', './component.users', './component.othersprofile', './component.findprojects'], function(exports_1, context_1) {
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
    var core_1, service_getandpost_1, router_1, component_userprofile_1, component_users_1, component_othersprofile_1, component_findprojects_1;
    var MainUserComponent;
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
            function (component_userprofile_1_1) {
                component_userprofile_1 = component_userprofile_1_1;
            },
            function (component_users_1_1) {
                component_users_1 = component_users_1_1;
            },
            function (component_othersprofile_1_1) {
                component_othersprofile_1 = component_othersprofile_1_1;
            },
            function (component_findprojects_1_1) {
                component_findprojects_1 = component_findprojects_1_1;
            }],
        execute: function() {
            MainUserComponent = (function () {
                function MainUserComponent(_router, getAndPostService) {
                    this._router = _router;
                    this.getAndPostService = getAndPostService;
                    /*
                    *   Defines which menu is active
                    */
                    this.activeMenu = 1;
                    this.selectedUser = -1;
                    this.isUserClicked = false;
                }
                MainUserComponent.prototype.ngOnInit = function () {
                    if (localStorage.getItem("USERID") === null) {
                        this._router.navigate(['Login']);
                    }
                    this.userName = localStorage.getItem("USERNAME");
                };
                MainUserComponent.prototype.logout = function () {
                    localStorage.removeItem("USERID");
                    localStorage.removeItem("USERNAME");
                    localStorage.removeItem("SELECTEDUSER");
                    localStorage.removeItem("TOKEN");
                    this._router.navigate(['Login']);
                };
                MainUserComponent.prototype.clickOnUser = function () {
                    this.isUserClicked = !this.isUserClicked;
                };
                MainUserComponent.prototype.switchMenu = function (menuToActivate) {
                    this.activeMenu = menuToActivate;
                    this.selectedUser = -1;
                };
                MainUserComponent.prototype.changeSelectedUser = function (newSelectedUser) {
                    this.selectedUser = newSelectedUser;
                    console.log("NEW SELECTED USER: ", this.selectedUser);
                };
                MainUserComponent = __decorate([
                    core_1.Component({
                        selector: 'main-user',
                        templateUrl: 'app/templates/main.user.template.html',
                        directives: [component_userprofile_1.UserProfileComponent, component_users_1.UsersComponent, component_othersprofile_1.OthersProfileComponent, component_findprojects_1.FindProjectsComponent],
                        providers: [service_getandpost_1.GetAndPostService]
                    }), 
                    __metadata('design:paramtypes', [router_1.Router, service_getandpost_1.GetAndPostService])
                ], MainUserComponent);
                return MainUserComponent;
            }());
            exports_1("MainUserComponent", MainUserComponent);
        }
    }
});
//# sourceMappingURL=component.main.user.js.map