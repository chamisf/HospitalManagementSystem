import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { GynaecologicalHistory } from './gynaecological-history.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GynaecologicalHistoryService {

    private resourceUrl = SERVER_API_URL + 'api/gynaecological-histories';

    constructor(private http: Http) { }

    create(gynaecologicalHistory: GynaecologicalHistory): Observable<GynaecologicalHistory> {
        const copy = this.convert(gynaecologicalHistory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(gynaecologicalHistory: GynaecologicalHistory): Observable<GynaecologicalHistory> {
        const copy = this.convert(gynaecologicalHistory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<GynaecologicalHistory> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    findByPatientId(id: number): Observable<GynaecologicalHistory> {
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
     * Convert a returned JSON object to GynaecologicalHistory.
     */
    private convertItemFromServer(json: any): GynaecologicalHistory {
        const entity: GynaecologicalHistory = Object.assign(new GynaecologicalHistory(), json);
        return entity;
    }

    /**
     * Convert a GynaecologicalHistory to a JSON which can be sent to the server.
     */
    private convert(gynaecologicalHistory: GynaecologicalHistory): GynaecologicalHistory {
        const copy: GynaecologicalHistory = Object.assign({}, gynaecologicalHistory);
        return copy;
    }
}
