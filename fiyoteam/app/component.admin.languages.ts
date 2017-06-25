import {Component} from 'angular2/core';
import {Observable} from 'rxjs/Rx';
import {GetAndPostService, Language} from './service.getandpost'

@Component({
	selector: 'admin-languages',
	templateUrl: 'app/templates/admin-languages.template.html',
	directives: [],
	providers: [GetAndPostService]

})

export class AdminLanguagesComponent { 

	private userId;
	private availableLanguages: Language[];
	private newLanguage = "";

	constructor(private getAndPostService: GetAndPostService) {}

	ngOnInit(){
		this.userId = localStorage.getItem("USERID");
		this.getAvailableLanguages();
	}

	getAvailableLanguages(){
		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'language/').map(res => res.json())

		.subscribe(
			(res) => {
				this.availableLanguages = res;
			}
			);
	}

	addNewLanguage(){

		this.getAndPostService.getData(this.getAndPostService.baseUrl + 'language/' + this.newLanguage).map(res => res.json())

		.subscribe(
			(res) => {
				this.availableLanguages = res;
			}
			);
	}

	deleteLanguage(languageId: number){
		this.getAndPostService.delete(this.getAndPostService.baseUrl + 'language/' + languageId).map(res => res.json())

		.subscribe(
			(res) => {
				this.availableLanguages = res;
			}
			);
	}

}