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
	private rater;
	private rated;
	private rate;
	private canIRateHim: boolean;


	constructor(private getAndPostService: GetAndPostService){
	}

	ngOnInit(){
		
		this.userId = localStorage.getItem("SELECTEDUSER");
		this.rater = localStorage.getItem("USERID");
		this.rated = localStorage.getItem("SELECTEDUSER");
		
		this.canIRateHim();
		this.listenAndSendRating();

		
	}

	listenAndSendRating(){
		$('.rating,.kv-fa').on(
			'change', this.sendRating);	
	}

	sendRating () {
		this.rate = $(this).val();

		$.ajax({
			url: 'https://fiyoteam-backend.herokuapp.com/rest/' + this.rated + '/' + this.rater + '/' + this.rate,
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

			}
			);
	}

	canIRateHim(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'rating/' + this.rater + '/' + this.rated).map(res => res.json())

		.subscribe(
			(res) => {
				this.canIRateHim = res;
				this.getRatingForUser();
			}
			);
	}

}