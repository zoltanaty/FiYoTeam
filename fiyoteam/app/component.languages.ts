import {Component} from 'angular2/core';
import 'rxjs/Rx';
import {GetAndPostService, Language} from './service.getandpost'

@Component({
  selector: 'language-template',
  providers: [GetAndPostService],
  templateUrl: 'app/templates/languages.template.html'
})

export class LanguageTemplateComponent {
  private userId;
  private languages: Language[];
  private availableLanguages: Language[];
  private newLanguage = new Language(null, '', 50);

  constructor(private getAndPostService: GetAndPostService){}

  ngOnInit(){
    this.userId = localStorage.getItem("USERID");

    this.getUsersLanguages();
    this.getAvailableLanguages();

  }

  getUsersLanguages(){
    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'user/languages/' + this.userId).map(res => res.json())

    .subscribe(
      (res) => {
        this.languages = res;
      }
      );
  }

  getAvailableLanguages(){
    this.getAndPostService.getData(this.getAndPostService.baseUrl + 'language/').map(res => res.json())

    .subscribe(
      (res) => {
        this.availableLanguages = res;
      }
      );
  }

  updateLanguages() {

    this.getAndPostService.postData(this.languages, this.getAndPostService.baseUrl + 'user/languages/' + this.userId).map(res => res.json())

    .subscribe(
      (res) => {
        this.languages = res;
      }
      );
  }

  addNewLanguage() {

    this.getAndPostService.putData(this.newLanguage, this.getAndPostService.baseUrl + 'user/languages/' + this.userId).map(res => res.json())

    .subscribe(
      (res) => {
        this.languages = res;
      }
      );
  }

  deleteLanguage(language){

    this.getAndPostService.delete(this.getAndPostService.baseUrl + 'user/languages/' + this.userId + '/' + language.id).map(res => res.json())

    .subscribe(
      (res) => {
        this.languages = res;
      }
      );
  }
}