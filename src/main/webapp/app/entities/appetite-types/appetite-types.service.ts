import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AppetiteTypes } from './appetite-types.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AppetiteTypesService {

    private resourceUrl = SERVER_API_URL + 'api/appetite-types';

    constructor(private http: Http) { }

    create(appetiteTypes: AppetiteTypes): Observable<AppetiteTypes> {
        const copy = this.convert(appetiteTypes);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(appetiteTypes: AppetiteTypes): Observable<AppetiteTypes> {
        const copy = this.convert(appetiteTypes);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AppetiteTypes> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to AppetiteTypes.
     */
    private convertItemFromServer(json: any): AppetiteTypes {
        const entity: AppetiteTypes = Object.assign(new AppetiteTypes(), json);
        return entity;
    }

    /**
     * Convert a AppetiteTypes to a JSON which can be sent to the server.
     */
    private convert(appetiteTypes: AppetiteTypes): AppetiteTypes {
        const copy: AppetiteTypes = Object.assign({}, appetiteTypes);
        return copy;
    }
}
