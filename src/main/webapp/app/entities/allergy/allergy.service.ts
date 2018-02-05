import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Allergy } from './allergy.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AllergyService {

    private resourceUrl = SERVER_API_URL + 'api/allergies';

    constructor(private http: Http) { }

    create(allergy: Allergy): Observable<Allergy> {
        const copy = this.convert(allergy);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(allergy: Allergy): Observable<Allergy> {
        const copy = this.convert(allergy);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Allergy> {
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
     * Convert a returned JSON object to Allergy.
     */
    private convertItemFromServer(json: any): Allergy {
        const entity: Allergy = Object.assign(new Allergy(), json);
        return entity;
    }

    /**
     * Convert a Allergy to a JSON which can be sent to the server.
     */
    private convert(allergy: Allergy): Allergy {
        const copy: Allergy = Object.assign({}, allergy);
        return copy;
    }
}
