import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { FamilyHistory } from './family-history.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {GynaecologicalHistory} from "../gynaecological-history/gynaecological-history.model";

@Injectable()
export class FamilyHistoryService {

    private resourceUrl = SERVER_API_URL + 'api/family-histories';

    constructor(private http: Http) { }

    create(familyHistory: FamilyHistory): Observable<FamilyHistory> {
        const copy = this.convert(familyHistory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(familyHistory: FamilyHistory): Observable<FamilyHistory> {
        const copy = this.convert(familyHistory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FamilyHistory> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    findByPatientId(id: number): Observable<FamilyHistory> {
        return this.http.get(`${this.resourceUrl}/by-patient/${id}`).map((res: Response) => {
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
     * Convert a returned JSON object to FamilyHistory.
     */
    private convertItemFromServer(json: any): FamilyHistory {
        const entity: FamilyHistory = Object.assign(new FamilyHistory(), json);
        return entity;
    }

    /**
     * Convert a FamilyHistory to a JSON which can be sent to the server.
     */
    private convert(familyHistory: FamilyHistory): FamilyHistory {
        const copy: FamilyHistory = Object.assign({}, familyHistory);
        return copy;
    }
}
