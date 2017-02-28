import {Component} from 'angular2/core';
import 'rxjs/Rx';
import {GetAndPostService, Language} from './service.getandpost'

@Component({
  selector: 'language-template',
  providers: [GetAndPostService],
  templateUrl: 'app/templates/languages.template.html'
})

export class LanguageTemplateComponent {

  private private userId;
  private languages: Language[];
  //public newLanguage = new Language(null,null,'');
  //public languagesfromadmins;

  constructor(private getAndPostService: GetAndPostService){}

  ngOnInit(){
   this.userId = localStorage.getItem("USERID");

   /*this.getandpostservice.getData('http://localhost:8080/addressbook_rest/api/v1/languages/getAllLanguages/').map(res => res.json())

   .subscribe(
    (res) => {
      this.languagesfromadmins = res;
    }
    );*/


   this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/languages/' + this.userId).map(res => res.json())

    .subscribe(
      (res) => {
        this.languages = res;    
      }
      );
 }

 /*updateLanguages() {

   this.getandpostservice.postData(this.languages,'http://localhost:8080/addressbook_rest/api/v1/languagehasuser/updateslanguagehasuser').map(res => res.json())

   .subscribe(
    (res) => {
      this.languages = res;
    }
    );
 }
 restoreLanguages() {

 }

 addNewLanguage() {

  this.newLanguage.userId = this.id;

  this.getandpostservice.postData(this.newLanguage,'http://localhost:8080/addressbook_rest/api/v1/languagehasuser/addlanguagehasuser').map(res => res.json())

  .subscribe(
    (res) => {
      this.languages = res;
    }
    );
}

deleteLanguage(language){

  this.getandpostservice.postData(language,'http://localhost:8080/addressbook_rest/api/v1/languagehasuser/deletelanguagehasuser').map(res => res.json())

  .subscribe(
    (res) => {
      this.languages = res;
    }
    );
}*/

}