System.register(['angular2/core', 'angular2/router', './service.getandpost'], function(exports_1, context_1) {
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
    var HomePageComponent;
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
            }],
        execute: function() {
            HomePageComponent = (function () {
                function HomePageComponent(_router, getAndPostService) {
                    this._router = _router;
                    this.getAndPostService = getAndPostService;
                    this.onChange = new core_1.EventEmitter();
                }
                HomePageComponent.prototype.ngOnInit = function () {
                    this.userId = localStorage.getItem("USERID");
                    this.getTeamsImLeading();
                    this.getTeamsImParticipating();
                };
                HomePageComponent.prototype.getTeamsImLeading = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'collaboration/teamsimleading/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.teamsImLeading = res;
                    });
                };
                HomePageComponent.prototype.getTeamsImParticipating = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'collaboration/teamsimparticipating/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.teamsImParticipating = res;
                    });
                };
                HomePageComponent.prototype.changeSelectedUser = function (selectedUser) {
                    localStorage.setItem("SELECTEDUSER", selectedUser);
                    this.onChange.emit({ value: selectedUser });
                };
                __decorate([
                    core_1.Output(), 
                    __metadata('design:type', Object)
                ], HomePageComponent.prototype, "onChange", void 0);
                HomePageComponent = __decorate([
                    core_1.Component({
                        selector: 'homepage',
                        templateUrl: 'app/templates/homepage.template.html',
                        directives: [],
                        providers: [service_getandpost_1.GetAndPostService]
                    }), 
                    __metadata('design:paramtypes', [router_1.Router, service_getandpost_1.GetAndPostService])
                ], HomePageComponent);
                return HomePageComponent;
            }());
            exports_1("HomePageComponent", HomePageComponent);
        }
    }
});
//# sourceMappingURL=component.skills.admin.js.map