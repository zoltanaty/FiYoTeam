System.register(['angular2/core', 'angular2/router', 'rxjs/Rx', './service.getandpost', './component.languages', './component.skills', './component.rating'], function(exports_1, context_1) {
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
    var core_1, router_1, Rx_1, service_getandpost_1, component_languages_1, component_skills_1, component_rating_1;
    var UserProfileComponent;
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
            },
            function (component_languages_1_1) {
                component_languages_1 = component_languages_1_1;
            },
            function (component_skills_1_1) {
                component_skills_1 = component_skills_1_1;
            },
            function (component_rating_1_1) {
                component_rating_1 = component_rating_1_1;
            }],
        execute: function() {
            UserProfileComponent = (function () {
                function UserProfileComponent(_router, getAndPostService) {
                    this._router = _router;
                    this.getAndPostService = getAndPostService;
                    this.user = new service_getandpost_1.User(null, '', '', '', '', '', '', '', '', '');
                    this.filesToUpload = [];
                    this.profilePicURL = null;
                    this.showUpdatedUserMessage = false;
                }
                UserProfileComponent.prototype.ngOnInit = function () {
                    this.userId = localStorage.getItem("USERID");
                    this.getUser();
                    this.createImageURL();
                };
                UserProfileComponent.prototype.getUser = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.user = res;
                    });
                };
                UserProfileComponent.prototype.updateUser = function () {
                    var _this = this;
                    this.getAndPostService.postData(this.user, this.getAndPostService.baseUrl + 'user/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.user = res;
                        _this.showUpdatedMessage();
                    });
                };
                UserProfileComponent.prototype.showUpdatedMessage = function () {
                    this.showUpdatedUserMessage = true;
                };
                UserProfileComponent.prototype.hideUpdatedMessage = function () {
                    this.showUpdatedUserMessage = false;
                };
                UserProfileComponent.prototype.upload = function () {
                    var _this = this;
                    this.makeFileRequest(this.getAndPostService.baseUrl + 'user/profilepic/' + this.userId, this.filesToUpload).then(function (result) {
                        _this.createImageURL();
                    }, function (error) {
                        console.error(error);
                    });
                };
                UserProfileComponent.prototype.fileChangeEvent = function (fileInput) {
                    this.filesToUpload = fileInput.target.files;
                };
                UserProfileComponent.prototype.makeFileRequest = function (url, files) {
                    return new Promise(function (resolve, reject) {
                        var formData = new FormData();
                        var xhr = new XMLHttpRequest();
                        for (var i = 0; i < files.length; i++) {
                            formData.append("file", files[i], files[i].name);
                        }
                        xhr.onreadystatechange = function () {
                            if (xhr.readyState == 4) {
                                if (xhr.status == 200) {
                                    resolve(JSON.parse(xhr.response));
                                }
                                else {
                                    reject(xhr.response);
                                }
                            }
                        };
                        xhr.open("POST", url, true);
                        xhr.send(formData);
                    });
                };
                UserProfileComponent.prototype.downloadImage = function (url) {
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
                UserProfileComponent.prototype.createImageURL = function () {
                    var _this = this;
                    this.downloadImage(this.getAndPostService.baseUrl + 'user/profilepic/' + this.userId).subscribe(function (imageData) {
                        _this.profilePicURL = URL.createObjectURL(new Blob([imageData]));
                    });
                };
                UserProfileComponent = __decorate([
                    core_1.Component({
                        selector: 'userprofile',
                        templateUrl: 'app/templates/userprofile.template.html',
                        directives: [component_languages_1.LanguageTemplateComponent, component_skills_1.SkillTemplateComponent, component_rating_1.RatingComponent],
                        providers: [service_getandpost_1.GetAndPostService]
                    }), 
                    __metadata('design:paramtypes', [router_1.Router, service_getandpost_1.GetAndPostService])
                ], UserProfileComponent);
                return UserProfileComponent;
            }());
            exports_1("UserProfileComponent", UserProfileComponent);
        }
    }
});
//# sourceMappingURL=component.userprofile.js.map