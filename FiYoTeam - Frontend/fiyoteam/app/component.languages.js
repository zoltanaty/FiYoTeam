System.register(['angular2/core', 'rxjs/Rx', './service.getandpost'], function(exports_1, context_1) {
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
    var core_1, service_getandpost_1;
    var LanguageTemplateComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (_1) {},
            function (service_getandpost_1_1) {
                service_getandpost_1 = service_getandpost_1_1;
            }],
        execute: function() {
            LanguageTemplateComponent = (function () {
                function LanguageTemplateComponent(getAndPostService) {
                    this.getAndPostService = getAndPostService;
                    this.newLanguage = new service_getandpost_1.Language(null, '', 50);
                }
                LanguageTemplateComponent.prototype.ngOnInit = function () {
                    this.userId = localStorage.getItem("USERID");
                    this.getUsersLanguages();
                    this.getAvailableLanguages();
                };
                LanguageTemplateComponent.prototype.getUsersLanguages = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/languages/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.languages = res;
                    });
                };
                LanguageTemplateComponent.prototype.getAvailableLanguages = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'language/').map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.availableLanguages = res;
                    });
                };
                LanguageTemplateComponent.prototype.updateLanguages = function () {
                    var _this = this;
                    this.getAndPostService.postData(this.languages, this.getAndPostService.baseUrl + 'user/languages/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.languages = res;
                    });
                };
                LanguageTemplateComponent.prototype.addNewLanguage = function () {
                    var _this = this;
                    this.getAndPostService.putData(this.newLanguage, this.getAndPostService.baseUrl + 'user/languages/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.languages = res;
                    });
                };
                LanguageTemplateComponent.prototype.deleteLanguage = function (language) {
                    var _this = this;
                    this.getAndPostService.delete(this.getAndPostService.baseUrl + 'user/languages/' + this.userId + '/' + language.id).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.languages = res;
                    });
                };
                LanguageTemplateComponent = __decorate([
                    core_1.Component({
                        selector: 'language-template',
                        providers: [service_getandpost_1.GetAndPostService],
                        templateUrl: 'app/templates/languages.template.html'
                    }), 
                    __metadata('design:paramtypes', [service_getandpost_1.GetAndPostService])
                ], LanguageTemplateComponent);
                return LanguageTemplateComponent;
            }());
            exports_1("LanguageTemplateComponent", LanguageTemplateComponent);
        }
    }
});
//# sourceMappingURL=component.languages.js.map