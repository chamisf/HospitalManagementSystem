import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { SleepTypes } from './sleep-types.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SleepTypesService {

    private resourceUrl = SERVER_API_URL + 'api/sleep-types';

    constructor(private http: Http) { }

    create(sleepTypes: SleepTypes): Observable<SleepTypes> {
        const copy = this.convert(sleepTypes);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(sleepTypes: SleepTypes): Observable<SleepTypes> {
        const copy = this.convert(sleepTypes);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SleepTypes> {
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
     * Convert a returned JSON object to SleepTypes.
     */
    private convertItemFromServer(json: any): SleepTypes {
        const entity: SleepTypes = Object.assign(new SleepTypes(), json);
        return entity;
    }

    /**
     * Convert a SleepTypes to a JSON which can be sent to the server.
     */
    private convert(sleepTypes: SleepTypes): SleepTypes {
        const copy: SleepTypes = Object.assign({}, sleepTypes);
        return copy;
    }
}
