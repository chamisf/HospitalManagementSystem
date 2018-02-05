import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Medicine } from './medicine.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MedicineService {

    private resourceUrl = SERVER_API_URL + 'api/medicines';

    constructor(private http: Http) { }

    create(medicine: Medicine): Observable<Medicine> {
        const copy = this.convert(medicine);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(medicine: Medicine): Observable<Medicine> {
        const copy = this.convert(medicine);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Medicine> {
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
     * Convert a returned JSON object to Medicine.
     */
    private convertItemFromServer(json: any): Medicine {
        const entity: Medicine = Object.assign(new Medicine(), json);
        return entity;
    }

    /**
     * Convert a Medicine to a JSON which can be sent to the server.
     */
    private convert(medicine: Medicine): Medicine {
        const copy: Medicine = Object.assign({}, medicine);
        return copy;
    }
}
