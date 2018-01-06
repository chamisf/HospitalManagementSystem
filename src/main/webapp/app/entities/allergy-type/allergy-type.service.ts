import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AllergyType } from './allergy-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AllergyTypeService {

    private resourceUrl = SERVER_API_URL + 'api/allergy-types';

    constructor(private http: Http) { }

    create(allergyType: AllergyType): Observable<AllergyType> {
        const copy = this.convert(allergyType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(allergyType: AllergyType): Observable<AllergyType> {
        const copy = this.convert(allergyType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AllergyType> {
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
     * Convert a returned JSON object to AllergyType.
     */
    private convertItemFromServer(json: any): AllergyType {
        const entity: AllergyType = Object.assign(new AllergyType(), json);
        return entity;
    }

    /**
     * Convert a AllergyType to a JSON which can be sent to the server.
     */
    private convert(allergyType: AllergyType): AllergyType {
        const copy: AllergyType = Object.assign({}, allergyType);
        return copy;
    }
}
