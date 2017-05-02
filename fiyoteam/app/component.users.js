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
    var core_1, router_1, Rx_1, service_getandpost_1;
    var UsersComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (Rx_1_1) {
                Rx_1 = Rx_1_1;
            },
            function (service_getandpost_1_1) {
                service_getandpost_1 = service_getandpost_1_1;
            }],
        execute: function() {
            UsersComponent = (function () {
                function UsersComponent(_router, getAndPostService) {
                    this._router = _router;
                    this.getAndPostService = getAndPostService;
                    this.users = [];
                    this.profilePicUrls = [];
                    this.onChange = new core_1.EventEmitter();
                }
                UsersComponent.prototype.ngOnInit = function () {
                    this.userId = localStorage.getItem("USERID");
                    this.getNrOfPages();
                };
                UsersComponent.prototype.getUsers = function (pageNumber) {
                    var _this = this;
                    this.users = null;
                    this.currentPageNr = pageNumber;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/page/' + pageNumber).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.users = res;
                        for (var _i = 0, _a = _this.users; _i < _a.length; _i++) {
                            var user = _a[_i];
                            _this.createImageURL(user);
                        }
                    });
                };
                UsersComponent.prototype.getNrOfPages = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/nrpages').map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.nrOfPages = res;
                        _this.getUsers(0);
                    });
                };
                UsersComponent.prototype.downloadImage = function (url) {
                    return Rx_1.Observable.create(function (observer) {
                        var req = new XMLHttpRequest();
                        req.open('get', url);
                        req.responseType = "arraybuffer";
                        req.onreadystatechange = function () {
                            if (req.readyState == 4 && req.status == 200) {
                                observer.next(req.response);
                                observer.complete();
                            }
                        };
                        req.send();
                    });
                };
                UsersComponent.prototype.createImageURL = function (user) {
                    this.downloadImage(this.getAndPostService.baseUrl + 'user/profilepic/' + user.id).subscribe(function (imageData) {
                        user.profilePicURL = URL.createObjectURL(new Blob([imageData]));
                    });
                };
                UsersComponent.prototype.changeSelectedUser = function (selectedUser) {
                    localStorage.setItem("SELECTEDUSER", selectedUser);
                    this.onChange.emit({ value: selectedUser });
                };
                __decorate([
                    core_1.Output(), 
                    __metadata('design:type', Object)
                ], UsersComponent.prototype, "onChange", void 0);
                UsersComponent = __decorate([
                    core_1.Component({
                        selector: 'users',
                        templateUrl: 'app/templates/users.template.html',
                        directives: [],
                        providers: [service_getandpost_1.GetAndPostService]
                    }), 
                    __metadata('design:paramtypes', [router_1.Router, service_getandpost_1.GetAndPostService])
                ], UsersComponent);
                return UsersComponent;
            }());
            exports_1("UsersComponent", UsersComponent);
        }
    }
});
//# sourceMappingURL=component.users.js.map