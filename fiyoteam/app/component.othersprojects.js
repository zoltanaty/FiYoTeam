System.register(['angular2/core', './service.getandpost'], function(exports_1, context_1) {
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
    var OthersProjectsComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (service_getandpost_1_1) {
                service_getandpost_1 = service_getandpost_1_1;
            }],
        execute: function() {
            OthersProjectsComponent = (function () {
                function OthersProjectsComponent(getAndPostService) {
                    this.getAndPostService = getAndPostService;
                }
                OthersProjectsComponent.prototype.ngOnInit = function () {
                    this.userId = localStorage.getItem("SELECTEDUSER");
                    this.getUserProjects();
                };
                OthersProjectsComponent.prototype.getUserProjects = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/projects/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.projects = res;
                    });
                };
                OthersProjectsComponent = __decorate([
                    core_1.Component({
                        selector: 'othersprojects',
                        templateUrl: 'app/templates/othersprojects.template.html',
                        directives: [],
                        providers: [service_getandpost_1.GetAndPostService]
                    }), 
                    __metadata('design:paramtypes', [service_getandpost_1.GetAndPostService])
                ], OthersProjectsComponent);
                return OthersProjectsComponent;
            }());
            exports_1("OthersProjectsComponent", OthersProjectsComponent);
        }
    }
});
//# sourceMappingURL=component.othersprojects.js.map