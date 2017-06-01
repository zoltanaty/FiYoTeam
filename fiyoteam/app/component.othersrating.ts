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

	constructor(private getAndPostService: GetAndPostService){}

	ngOnInit(){
		
		this.userId = localStorage.getItem("SELECTEDUSER");
		this.getRatingForUser();
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

					$('.rating,.kv-fa').on(
						'change', function () {
							console.log('Rating selected: ' + $(this).val());
						});
				}, 0)

			}
			);
	}

}