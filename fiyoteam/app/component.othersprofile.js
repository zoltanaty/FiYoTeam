System.register(['angular2/core', 'angular2/router', './service.getandpost', './component.otherslanguages', './component.othersskills', './component.othersprojects', './component.othersrating', './component.othersprojectsappliedfor'], function(exports_1, context_1) {
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
    var core_1, router_1, service_getandpost_1, component_otherslanguages_1, component_othersskills_1, component_othersprojects_1, component_othersrating_1, component_othersprojectsappliedfor_1;
    var OthersProfileComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (service_getandpost_1_1) {
                service_getandpost_1 = service_getandpost_1_1;
            },
            function (component_otherslanguages_1_1) {
                component_otherslanguages_1 = component_otherslanguages_1_1;
            },
            function (component_othersskills_1_1) {
                component_othersskills_1 = component_othersskills_1_1;
            },
            function (component_othersprojects_1_1) {
                component_othersprojects_1 = component_othersprojects_1_1;
            },
            function (component_othersrating_1_1) {
                component_othersrating_1 = component_othersrating_1_1;
            },
            function (component_othersprojectsappliedfor_1_1) {
                component_othersprojectsappliedfor_1 = component_othersprojectsappliedfor_1_1;
            }],
        execute: function() {
            OthersProfileComponent = (function () {
                function OthersProfileComponent(_router, getAndPostService) {
                    this._router = _router;
                    this.getAndPostService = getAndPostService;
                    this.user = new service_getandpost_1.User(null, '', '', '', '', '', '', '', '', null);
                }
                OthersProfileComponent.prototype.ngOnInit = function () {
                    this.userId = localStorage.getItem("SELECTEDUSER");
                    this.getUser();
                };
                OthersProfileComponent.prototype.getUser = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.user = res;
                    });
                };
                OthersProfileComponent = __decorate([
                    core_1.Component({
                        selector: 'othersprofile',
                        templateUrl: 'app/templates/othersprofile.template.html',
                        directives: [component_otherslanguages_1.OthersLanguageTemplateComponent, component_othersskills_1.OthersSkillTemplateComponent, component_othersprojects_1.OthersProjectsComponent, component_othersprojectsappliedfor_1.OthersProjectsAppliedForComponent, component_othersrating_1.OthersRatingComponent],
                        providers: [service_getandpost_1.GetAndPostService]
                    }), 
                    __metadata('design:paramtypes', [router_1.Router, service_getandpost_1.GetAndPostService])
                ], OthersProfileComponent);
                return OthersProfileComponent;
            }());
            exports_1("OthersProfileComponent", OthersProfileComponent);
        }
    }
});
//# sourceMappingURL=component.othersprofile.js.map