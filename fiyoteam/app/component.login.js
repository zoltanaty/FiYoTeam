System.register(['angular2/core', 'angular2/router', 'rxjs/Rx', './service.getandpost'], function(exports_1, context_1) {
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
    var core_1, router_1, service_getandpost_1;
    var LoginComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (_1) {},
            function (service_getandpost_1_1) {
                service_getandpost_1 = service_getandpost_1_1;
            }],
        execute: function() {
            LoginComponent = (function () {
                function LoginComponent(_router, getAndPostService) {
                    this._router = _router;
                    this.getAndPostService = getAndPostService;
                    this.user = new service_getandpost_1.User(null, '', '', '', '', '', '', '', '', '');
                    this.userToRegister = new service_getandpost_1.User(null, '', '', '', '', '', '', '', '', '');
                    this.loginError = false;
                    this.successfulRegistration = false;
                    this.unsuccessfulRegistration = false;
                }
                LoginComponent.prototype.login = function (user) {
                    var _this = this;
                    this.getAndPostService.postData(user, this.getAndPostService.baseUrl + 'authentication/login').map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        if (res.id >= 0 && res.role == "user") {
                            _this._router.navigate(['MainUser']);
                            localStorage.setItem("USERID", res.id);
                            localStorage.setItem("TOKEN", res.token);
                            localStorage.setItem("USERNAME", res.firstName + " " + res.lastName);
                            _this.loginError = false;
                        }
                        else if (res.id >= 0 && res.role == "admin") {
                            _this._router.navigate(['MainAdmin']);
                            localStorage.setItem("USERID", res.id);
                            localStorage.setItem("TOKEN", res.token);
                            localStorage.setItem("USERNAME", res.firstName + " " + res.lastName);
                            _this.loginError = false;
                        }
                        else if (res.id < 0) {
                            _this.loginError = true;
                        }
                    });
                };
                LoginComponent.prototype.register = function () {
                    var _this = this;
                    this.getAndPostService.postData(this.userToRegister, this.getAndPostService.baseUrl + 'authentication/register').map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        if (res.id >= 0) {
                            //this.successfulRegistration = true;
                            //this.unsuccessfulRegistration = false;
                            _this.login(_this.userToRegister);
                        }
                        else {
                            _this.unsuccessfulRegistration = true;
                            _this.successfulRegistration = false;
                        }
                    });
                };
                LoginComponent.prototype.disableLoginError = function () {
                    this.loginError = false;
                };
                LoginComponent.prototype.hasLowerCase = function (str) {
                    return str.toUpperCase() != str;
                };
                LoginComponent.prototype.hasUpperCase = function (str) {
                    return str.toLowerCase() != str;
                };
                LoginComponent.prototype.validatePassword = function () {
                    var format = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;
                    if (this.userToRegister.password.length == 0) {
                        return 0;
                    }
                    else if (this.userToRegister.password.length >= 6) {
                        if (this.hasLowerCase(this.userToRegister.password) && this.hasUpperCase(this.userToRegister.password)) {
                            if (format.test(this.userToRegister.password)) {
                                return 1;
                            }
                            else {
                                return -1;
                            }
                        }
                        else {
                            return -1;
                        }
                    }
                    else {
                        return -1;
                    }
                };
                LoginComponent = __decorate([
                    core_1.Component({
                        selector: 'login',
                        templateUrl: 'app/templates/login.template.html',
                        providers: [service_getandpost_1.GetAndPostService]
                    }), 
                    __metadata('design:paramtypes', [router_1.Router, service_getandpost_1.GetAndPostService])
                ], LoginComponent);
                return LoginComponent;
            }());
            exports_1("LoginComponent", LoginComponent);
        }
    }
});
//# sourceMappingURL=component.login.js.map