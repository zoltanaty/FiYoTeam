import {Injectable} from 'angular2/core';
import {Http, Headers} from 'angular2/http';

export class User {
  constructor(public id:number, public firstName: string, public lastName: string, public country: string, public city: string, public email: string) { }
}

export class Language {
  constructor(public language: string, public level: number) { }
}

@Injectable()
export class GetAndPostService{

  private ipv4 = 'localhost';
  public baseUrl = 'http://' + this.ipv4 + ':8080/fiyoteam-backend/rest/';

  constructor(private _http: Http){}

  getData(url){
      var headers = new Headers();
      headers.append('Content-Type', 'application/json');

      return this._http.get(url,{headers: headers});
  }

  postData(object, url){
      var headers = new Headers();
      headers.append('Content-Type', 'application/json');
      var json = JSON.stringify(object);

      return this._http.post(url, json, {headers: headers});
  }

}