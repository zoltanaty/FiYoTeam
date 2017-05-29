System.register(['angular2/core', 'angular2/http'], function(exports_1, context_1) {
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
    var core_1, http_1;
    var User, Language, Skill, Project, ProjectResponse, GetAndPostService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            }],
        execute: function() {
            User = (function () {
                function User(id, firstName, lastName, country, city, email, description, password, passwordAgain, profilePicURL) {
                    this.id = id;
                    this.firstName = firstName;
                    this.lastName = lastName;
                    this.country = country;
                    this.city = city;
                    this.email = email;
                    this.description = description;
                    this.password = password;
                    this.passwordAgain = passwordAgain;
                    this.profilePicURL = profilePicURL;
                }
                return User;
            }());
            exports_1("User", User);
            Language = (function () {
                function Language(id, language, level) {
                    this.id = id;
                    this.language = language;
                    this.level = level;
                }
                return Language;
            }());
            exports_1("Language", Language);
            Skill = (function () {
                function Skill(id, skill, level) {
                    this.id = id;
                    this.skill = skill;
                    this.level = level;
                }
                return Skill;
            }());
            exports_1("Skill", Skill);
            Project = (function () {
                function Project(id, name, description, status) {
                    this.id = id;
                    this.name = name;
                    this.description = description;
                    this.status = status;
                }
                return Project;
            }());
            exports_1("Project", Project);
            ProjectResponse = (function () {
                function ProjectResponse(project, skills, authorName, authorId, createdAt) {
                    this.project = project;
                    this.skills = skills;
                    this.authorName = authorName;
                    this.authorId = authorId;
                    this.createdAt = createdAt;
                }
                return ProjectResponse;
            }());
            exports_1("ProjectResponse", ProjectResponse);
            GetAndPostService = (function () {
                //private ipv4 = 'https://fiyoteam-backend.herokuapp.com';
                //public baseUrl = this.ipv4 + '/rest/';
                function GetAndPostService(_http) {
                    this._http = _http;
                    this.ipv4 = 'localhost';
                    this.baseUrl = 'http://' + this.ipv4 + ':8080/fiyoteam-backend/rest/';
                }
                GetAndPostService.prototype.getData = function (url) {
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'application/json');
                    headers.append('authorization', localStorage.getItem("TOKEN"));
                    headers.append('identifier', localStorage.getItem("USERID"));
                    return this._http.get(url, { headers: headers });
                };
                GetAndPostService.prototype.postData = function (object, url) {
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'application/json');
                    headers.append('authorization', localStorage.getItem("TOKEN"));
                    headers.append('identifier', localStorage.getItem("USERID"));
                    var json = JSON.stringify(object);
                    return this._http.post(url, json, { headers: headers });
                };
                GetAndPostService.prototype.putData = function (object, url) {
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'application/json');
                    headers.append('authorization', localStorage.getItem("TOKEN"));
                    headers.append('identifier', localStorage.getItem("USERID"));
                    var json = JSON.stringify(object);
                    return this._http.put(url, json, { headers: headers });
                };
                GetAndPostService.prototype.delete = function (url) {
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'application/json');
                    headers.append('authorization', localStorage.getItem("TOKEN"));
                    headers.append('identifier', localStorage.getItem("USERID"));
                    return this._http.delete(url, { headers: headers });
                };
                GetAndPostService.prototype.getFile = function (url) {
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'image/jpg');
                    headers.append('authorization', localStorage.getItem("TOKEN"));
                    headers.append('identifier', localStorage.getItem("USERID"));
                    this._http.get(url, { headers: headers })
                        .map(function (res) {
                        return new Blob([res], {
                            type: res.headers.get("Content-Type")
                        });
                    })
                        .map(function (blob) {
                        var urlCreator = window.URL;
                        var url = urlCreator.createObjectURL(blob);
                        console.log("THE URL IS: " + url);
                        return url;
                    });
                };
                GetAndPostService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [http_1.Http])
                ], GetAndPostService);
                return GetAndPostService;
            }());
            exports_1("GetAndPostService", GetAndPostService);
        }
    }
});
//# sourceMappingURL=service.getandpost.js.map