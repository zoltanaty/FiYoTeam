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
    var OthersRatingComponent;
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
            OthersRatingComponent = (function () {
                function OthersRatingComponent(getAndPostService) {
                    this.getAndPostService = getAndPostService;
                    this.rating = new service_getandpost_1.Rating(null, null, 0, 0, 0, 0, 0, 0);
                }
                OthersRatingComponent.prototype.ngOnInit = function () {
                    this.userId = localStorage.getItem("SELECTEDUSER");
                    this.rater = localStorage.getItem("USERID");
                    this.rated = localStorage.getItem("SELECTEDUSER");
                    this.canIRateHim();
                };
                OthersRatingComponent.prototype.listenAndSendRating = function () {
                    $('.rating,.kv-fa').on('change', this.sendRating);
                };
                OthersRatingComponent.prototype.sendRating = function () {
                    this.rate = $(this).val();
                    var rater = localStorage.getItem("USERID");
                    var rated = localStorage.getItem("SELECTEDUSER");
                    $.ajax({
                        //url: 'http://localhost:8080/fiyoteam-backend/rest/rating/' + rated + '/' + rater + '/' + this.rate,
                        url: 'https://fiyoteam-backend.herokuapp.com/rest/rating/' + rated + '/' + rater + '/' + this.rate,
                        type: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                            'authorization': localStorage.getItem("TOKEN"),
                            'identifier': localStorage.getItem("USERID")
                        },
                        success: function (result) {
                            this.rating = result;
                        }
                    });
                };
                OthersRatingComponent.prototype.getRatingForUser = function () {
                    var _this = this;
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'rating/' + this.userId).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.rating = res;
                        setTimeout(function () {
                            $('.kv-fa').rating({
                                theme: 'krajee-fa',
                                filledStar: '<i class="fa fa-star"></i>',
                                emptyStar: '<i class="fa fa-star-o"></i>'
                            });
                        }, 0);
                        _this.listenAndSendRating();
                    });
                };
                OthersRatingComponent.prototype.canIRateHim = function () {
                    var _this = this;
                    var rater = localStorage.getItem("USERID");
                    var rated = localStorage.getItem("SELECTEDUSER");
                    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'rating/' + rater + '/' + rated).map(function (res) { return res.json(); })
                        .subscribe(function (res) {
                        _this.canIRateHim = res;
                        _this.getRatingForUser();
                    });
                };
                OthersRatingComponent = __decorate([
                    core_1.Component({
                        selector: 'othersrating',
                        providers: [service_getandpost_1.GetAndPostService],
                        templateUrl: 'app/templates/othersrating.template.html'
                    }), 
                    __metadata('design:paramtypes', [service_getandpost_1.GetAndPostService])
                ], OthersRatingComponent);
                return OthersRatingComponent;
            }());
            exports_1("OthersRatingComponent", OthersRatingComponent);
        }
    }
});
//# sourceMappingURL=component.othersrating.js.map