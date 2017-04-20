System.register(['angular2/core', './service.getandpost', './component.languages'], function(exports_1, context_1) {
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
    var core_1, service_getandpost_1, component_languages_1;
    var MyProjectsComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (service_getandpost_1_1) {
                service_getandpost_1 = service_getandpost_1_1;
            },
            function (component_languages_1_1) {
                component_languages_1 = component_languages_1_1;
            }],
        execute: function() {
            MyProjectsComponent = (function () {
                function MyProjectsComponent(getAndPostService) {
                    this.getAndPostService = getAndPostService;
                    this.newProject = new service_getandpost_1.ProjectResponse(new service_getandpost_1.Project(null, '', '', 'active'), new Array());
                    this.projectToEdit = new service_getandpost_1.Project(null, '', '', '');
                }
                MyProjectsComponent.prototype.ngOnInit = function () {
                    this.userId = localStorage.getItem("USERID");
                    this.getProjectSkills();
                    this.getAvailableSkills();
                };
                MyProjectsComponent.prototype.getProjectSkills = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/projects/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.projects = res;
                    });
                };
                MyProjectsComponent.prototype.addNewProject = function () {
                    var _this = this;
                    this.getAndPostService.putData(this.newProject, this.getAndPostService.baseUrl + 'user/projects/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.projects = res;
                    });
                };
                MyProjectsComponent.prototype.addSkillToNewProject = function (skillId) {
                    var existsAlready = false;
                    for (var _i = 0; _i < this.newProject.skills.length; _i++) {
                        var skill = this.availableSkills[_i];
                        if (skill.id == skillId) {
                            existsAlready = true;
                            break;
                        }
                    }
                    if (existsAlready == false) {
                        var selectedSkill = new service_getandpost_1.Skill(null, '', null);
                        for (var _i = 0; _i < this.availableSkills.length; _i++) {
                            var skill = this.availableSkills[_i];
                            if (skill.id == skillId) {
                                selectedSkill.id = skill.id;
                                selectedSkill.skill = skill.skill;
                                selectedSkill.level = 100;
                            }
                        }
                        this.newProject.skills.push(selectedSkill);
                    }
                };
                MyProjectsComponent.prototype.removeSkillFromNewProject = function (skill) {
                    var index = this.newProject.skills.indexOf(skill);
                    if (index !== -1) {
                        this.newProject.skills.splice(index, 1);
                    }
                };
                MyProjectsComponent.prototype.resetNewProject = function () {
                    this.newProject = new service_getandpost_1.ProjectResponse(new service_getandpost_1.Project(null, '', '', 'active'), new Array());
                    this.newProject.project.name = "";
                    this.newProject.project.description = "";
                    this.newProject.skills = [];
                };
                MyProjectsComponent.prototype.getAvailableSkills = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'skill/').map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.availableSkills = res;
                    });
                };
                MyProjectsComponent.prototype.setProjectToEdit = function (prj) {
                    this.projectToEdit = prj;
                };
                MyProjectsComponent = __decorate([
                    core_1.Component({
                        selector: 'myprojects',
                        templateUrl: 'app/templates/myprojects.template.html',
                        directives: [component_languages_1.LanguageTemplateComponent],
                        providers: [service_getandpost_1.GetAndPostService]
                    }), 
                    __metadata('design:paramtypes', [service_getandpost_1.GetAndPostService])
                ], MyProjectsComponent);
                return MyProjectsComponent;
            }());
            exports_1("MyProjectsComponent", MyProjectsComponent);
        }
    }
});
//# sourceMappingURL=component.myprojects.js.map