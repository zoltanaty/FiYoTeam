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
    var OthersSkillTemplateComponent;
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
            OthersSkillTemplateComponent = (function () {
                function OthersSkillTemplateComponent(getAndPostService) {
                    this.getAndPostService = getAndPostService;
                }
                OthersSkillTemplateComponent.prototype.ngOnInit = function () {
                    this.userId = localStorage.getItem("SELECTEDUSER");
                    this.getUserSkills();
                };
                OthersSkillTemplateComponent.prototype.getUserSkills = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/skills/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.skills = res;
                    });
                };
                OthersSkillTemplateComponent.prototype.getAvailableSkills = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'skill/').map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.availableSkills = res;
                    });
                };
                OthersSkillTemplateComponent = __decorate([
                    core_1.Component({
                        selector: 'othersskill-template',
                        providers: [service_getandpost_1.GetAndPostService],
                        templateUrl: 'app/templates/othersskill.template.html'
                    }), 
                    __metadata('design:paramtypes', [service_getandpost_1.GetAndPostService])
                ], OthersSkillTemplateComponent);
                return OthersSkillTemplateComponent;
            }());
            exports_1("OthersSkillTemplateComponent", OthersSkillTemplateComponent);
        }
    }
});
//# sourceMappingURL=component.othersskills.js.map