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
    var ProjectsAppliedForComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (service_getandpost_1_1) {
                service_getandpost_1 = service_getandpost_1_1;
            }],
        execute: function() {
            ProjectsAppliedForComponent = (function () {
                function ProjectsAppliedForComponent(getAndPostService) {
                    this.getAndPostService = getAndPostService;
                    this.onChange = new core_1.EventEmitter();
                }
                ProjectsAppliedForComponent.prototype.ngOnInit = function () {
                    this.userId = localStorage.getItem("USERID");
                    this.getAppliedProjects();
                };
                ProjectsAppliedForComponent.prototype.getAppliedProjects = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'collaboration/projects/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.projects = res;
                    });
                };
                ProjectsAppliedForComponent.prototype.changeSelectedUser = function (selectedUser) {
                    localStorage.setItem("SELECTEDUSER", selectedUser);
                    this.onChange.emit({ value: selectedUser });
                };
                ProjectsAppliedForComponent.prototype.getCollaborationsForProject = function (projectId, ownerId) {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'collaboration/requests/' + projectId + '/' + ownerId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.projectCollaborations = res;
                    });
                };
                __decorate([
                    core_1.Output(), 
                    __metadata('design:type', Object)
                ], ProjectsAppliedForComponent.prototype, "onChange", void 0);
                ProjectsAppliedForComponent = __decorate([
                    core_1.Component({
                        selector: 'projectsappliedfor',
                        templateUrl: 'app/templates/projectsappliedfor.template.html',
                        directives: [],
                        providers: [service_getandpost_1.GetAndPostService]
                    }), 
                    __metadata('design:paramtypes', [service_getandpost_1.GetAndPostService])
                ], ProjectsAppliedForComponent);
                return ProjectsAppliedForComponent;
            }());
            exports_1("ProjectsAppliedForComponent", ProjectsAppliedForComponent);
        }
    }
});
//# sourceMappingURL=component.projectsappliedfor.js.map