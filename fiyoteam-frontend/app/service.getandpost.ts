import {Injectable} from 'angular2/core';
import {Http, Headers} from 'angular2/http';

export class User {
  constructor(public id:number, public firstName: string, public lastName: string, public country: string, public city: string, public email: string, public password: string, public passwordAgain: string, public profilePicURL: string) { }
}

export class Language {
  constructor(public id: number, public language: string, public level: number) { }
}

export class Skill {
  constructor(public id: number, public skill: string, public level: number) { }
}

@Injectable()
export class GetAndPostService{

  private ipv4 = '192.168.96.55';
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

  putData(object, url){
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    var json = JSON.stringify(object);

    return this._http.put(url, json, {headers: headers});
  }

  delete(url){
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this._http.delete(url, {headers: headers});
  }

  getFile(url){
    var headers = new Headers();
    headers.append('Content-Type', 'image/jpg');

    this._http.get(url,  { headers: headers })
    .map(res => {
        return new Blob([res], {
        type: res.headers.get("Content-Type")
      });
    })
    .map(blob => {
      var urlCreator = window.URL;
      var url = urlCreator.createObjectURL(blob);
      console.log("THE URL IS: " + url);
      return url;
    })
  }

  /*uploadFile(fileToUpload, url){
    var headers = new Headers();
    headers.append('Content-Type', 'image/jpeg');

    let formData = new FormData();
    formData.append("file", fileToUpload);
    var json = JSON.stringify(formData);

    console.log(fileToUpload);

    return this._http.post(url, json, {headers: headers});
  }*/

}

}