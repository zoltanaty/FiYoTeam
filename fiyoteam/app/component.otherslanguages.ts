import {Component} from 'angular2/core';
import 'rxjs/Rx';
import {GetAndPostService, Language} from './service.getandpost'

@Component({
  selector: 'otherslanguage-template',
  providers: [GetAndPostService],
  templateUrl: 'app/templates/otherslanguages.template.html'
})

export class OthersLanguageTemplateComponent {

  private private userId;
  private languages: Language[];

  constructor(private getAndPostService: GetAndPostService){}

  ngOnInit(){
   this.userId = localStorage.getItem("SELECTEDUSER");

   this.getUsersLanguages();
 }

 getUsersLanguages(){
  this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/languages/' + this.userId).map(res => res.json())

  .subscribe(
    (res) => {
      this.languages = res;
    }
    );
}

}