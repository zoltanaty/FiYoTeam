import {Injectable} from 'angular2/core';
import {Http, Headers} from 'angular2/http';

export class User {
  constructor(public id:number, public firstName: string, public lastName: string, public country: string, public city: string, public email: string, public description: string, public password: string, public passwordAgain: string, public profilePicURL: string) { }
}

export class Language {
  constructor(public id: number, public language: string, public level: number) { }
}

export class Skill {
  constructor(public id: number, public skill: string, public level: number) { }
}

export class Project {
  constructor(public id: number, public name: string, public description: string, public status: string) { }
}

export class ProjectResponse {
  constructor(public project: Project, public skills: Skill[]) {}
}

@Injectable()
export class GetAndPostService{

  //private ipv4 = 'localhost';
  //public baseUrl = 'http://' + this.ipv4 + ':8080/fiyoteam-backend/rest/'

  private ipv4 = 'https://fiyoteam-backend.herokuapp.com';
  public baseUrl = this.ipv4 + '/rest/';

  constructor(private _http: Http){}

  getData(url){
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('authorization', localStorage.getItem("TOKEN"));
    headers.append('identifier', localStorage.getItem("USERID"));

    return this._http.get(url,{headers: headers});
  }

  postData(object, url){
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('authorization', localStorage.getItem("TOKEN"));
    headers.append('identifier', localStorage.getItem("USERID"));
    var json = JSON.stringify(object);

    return this._http.post(url, json, {headers: headers});
  }

  putData(object, url){
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('authorization', localStorage.getItem("TOKEN"));
    headers.append('identifier', localStorage.getItem("USERID"));
    var json = JSON.stringify(object);

    return this._http.put(url, json, {headers: headers});
  }

  delete(url){
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('authorization', localStorage.getItem("TOKEN"));
    headers.append('identifier', localStorage.getItem("USERID"));

    return this._http.delete(url, {headers: headers});
  }

  getFile(url){
    var headers = new Headers();
    headers.append('Content-Type', 'image/jpg');
    headers.append('authorization', localStorage.getItem("TOKEN"));
    headers.append('identifier', localStorage.getItem("USERID"));

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

}

}