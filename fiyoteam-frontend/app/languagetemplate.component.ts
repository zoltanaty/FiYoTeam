import {Component} from 'angular2/core';
import {GetAndPostService, Language} from './service.getandpost'

@Component({
  selector: 'language-template',
  providers: [GetAndPostService],
  templateUrl: 'app/templates/languages.template.html'
})


export class LanguageTemplateComponent {

  public languages: Language[];
  public newLanguage = new Language(null,null,'');
  public id;
  public languagesfromadmins;

  constructor(private getandpostservice: GetAndPostService){}

  ngOnInit(){
   this.id = localStorage.getItem("user");

   this.getandpostservice.getData('http://localhost:8080/addressbook_rest/api/v1/languages/getAllLanguages/').map(res => res.json())

   .subscribe(
    (res) => {
      this.languagesfromadmins = res;
    }
    );


   this.getandpostservice.getData('http://localhost:8080/addressbook_rest/api/v1/languagehasuser/getlanguagehasuser/' + this.id).map(res => res.json())

   .subscribe(
    (res) => {
      this.languages = res;
    }
    );
 }

 updateLanguages() {

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
}

}