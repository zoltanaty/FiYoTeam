import {Injectable} from 'angular2/core';
import {Http, Headers} from 'angular2/http';

export class User {
  constructor(public id:number, public email: string, public password: string, public firstName: string, public lastName: string) { }
}

@Injectable()
export class GetAndPostService{

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