import {Component} from 'angular2/core';
import 'rxjs/Rx';
import {GetAndPostService} from './service.getandpost'

@Component({
  selector: 'rating',
  providers: [GetAndPostService],
  templateUrl: 'app/templates/rating.template.html'
})

export class RatingComponent {

  constructor(private getAndPostService: GetAndPostService){}

  ngOnInit(){
   this.userId = localStorage.getItem("USERID");
 }

}