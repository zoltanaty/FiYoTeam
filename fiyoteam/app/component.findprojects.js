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
    var FindProjectsComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (service_getandpost_1_1) {
                service_getandpost_1 = service_getandpost_1_1;
            }],
        execute: function() {
            FindProjectsComponent = (function () {
                function FindProjectsComponent(getAndPostService) {
                    this.getAndPostService = getAndPostService;
                    this.nrOfPages = [];
                    this.searchCriteria = "";
                    this.userId = localStorage.getItem("USERID");
                    this.onChange = new core_1.EventEmitter();
                }
                FindProjectsComponent.prototype.ngOnInit = function () {
                    this.getNrOfPages();
                };
                FindProjectsComponent.prototype.getUserProjects = function (pageNumber) {
                    var _this = this;
                    this.projects = null;
                    this.currentPageNr = pageNumber;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'project/' + pageNumber + '/' + this.searchCriteria).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.projects = res;
                    });
                };
                FindProjectsComponent.prototype.getNrOfPages = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'project/nrpages/' + this.searchCriteria).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.nrOfPages = res;
                        _this.getUserProjects(0);
                    });
                };
                FindProjectsComponent.prototype.requestCollaboration = function (userId, projectId, ownerId, project) {
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'collaboration/' + userId + '/' + projectId + '/' + ownerId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        project.collaborationRequestResponse = res;
                    });
                };
                FindProjectsComponent.prototype.changeSelectedUser = function (selectedUser) {
                    localStorage.setItem("SELECTEDUSER", selectedUser);
                    this.onChange.emit({ value: selectedUser });
                };
                __decorate([
                    core_1.Output(), 
                    __metadata('design:type', Object)
                ], FindProjectsComponent.prototype, "onChange", void 0);
                FindProjectsComponent = __decorate([
                    core_1.Component({
                        selector: 'findprojects',
                        templateUrl: 'app/templates/findprojects.template.html',
                        directives: [],
                        providers: [service_getandpost_1.GetAndPostService]
                    }), 
                    __metadata('design:paramtypes', [service_getandpost_1.GetAndPostService])
                ], FindProjectsComponent);
                return FindProjectsComponent;
            }());
            exports_1("FindProjectsComponent", FindProjectsComponent);
        }
    }
});
//# sourceMappingURL=component.findprojects.js.map