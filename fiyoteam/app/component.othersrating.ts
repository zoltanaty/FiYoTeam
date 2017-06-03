import {Component} from 'angular2/core';
import 'rxjs/Rx';
import {GetAndPostService, Rating} from './service.getandpost'

@Component({
	selector: 'othersrating',
	providers: [GetAndPostService],
	templateUrl: 'app/templates/othersrating.template.html'
})

export class OthersRatingComponent {

	private rating = new Rating(null, null, 0, 0, 0, 0, 0, 0);
	private rate;
	private rater;
	private rated;
	private canIRateHim: boolean;


	constructor(private getAndPostService: GetAndPostService){
	}

	ngOnInit(){
		this.userId = localStorage.getItem("SELECTEDUSER");
		this.rater = localStorage.getItem("USERID");
		this.rated = localStorage.getItem("SELECTEDUSER");
		
		this.canIRateHim();

		
	}

	listenAndSendRating(){
		$('.rating,.kv-fa').on(
			'change', this.sendRating);	
	}

	sendRating () {
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
	}

	getRatingForUser(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'rating/' + this.userId).map(res => res.json())

		.subscribe(
			(res) => {
				this.rating = res;

				setTimeout(() => {
					$('.kv-fa').rating({
						theme: 'krajee-fa',
						filledStar: '<i class="fa fa-star"></i>',
						emptyStar: '<i class="fa fa-star-o"></i>'
					});

				}, 0)

				this.listenAndSendRating();

			}
			);
	}

	canIRateHim(){
		var rater = localStorage.getItem("USERID");
		var rated = localStorage.getItem("SELECTEDUSER");

		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'rating/' + rater + '/' + rated).map(res => res.json())

		.subscribe(
			(res) => {
				this.canIRateHim = res;
				this.getRatingForUser();

			}
			);
	}

}