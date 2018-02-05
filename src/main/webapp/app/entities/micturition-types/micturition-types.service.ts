import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { MicturitionTypes } from './micturition-types.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MicturitionTypesService {

    private resourceUrl = SERVER_API_URL + 'api/micturition-types';

    constructor(private http: Http) { }

    create(micturitionTypes: MicturitionTypes): Observable<MicturitionTypes> {
        const copy = this.convert(micturitionTypes);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(micturitionTypes: MicturitionTypes): Observable<MicturitionTypes> {
        const copy = this.convert(micturitionTypes);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MicturitionTypes> {
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
     * Convert a returned JSON object to MicturitionTypes.
     */
    private convertItemFromServer(json: any): MicturitionTypes {
        const entity: MicturitionTypes = Object.assign(new MicturitionTypes(), json);
        return entity;
    }

    /**
     * Convert a MicturitionTypes to a JSON which can be sent to the server.
     */
    private convert(micturitionTypes: MicturitionTypes): MicturitionTypes {
        const copy: MicturitionTypes = Object.assign({}, micturitionTypes);
        return copy;
    }
}
