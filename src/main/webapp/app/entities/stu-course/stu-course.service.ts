import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { StuCourse } from './stu-course.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<StuCourse>;

@Injectable()
export class StuCourseService {

    private resourceUrl =  SERVER_API_URL + 'api/stu-courses';

    constructor(private http: HttpClient) { }

    create(stuCourse: StuCourse): Observable<EntityResponseType> {
        const copy = this.convert(stuCourse);
        return this.http.post<StuCourse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(stuCourse: StuCourse): Observable<EntityResponseType> {
        const copy = this.convert(stuCourse);
        return this.http.put<StuCourse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<StuCourse>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<StuCourse[]>> {
        this.buildQueryParamter(req)
        const options = createRequestOption(req);
        return this.http.get<StuCourse[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<StuCourse[]>) => this.convertArrayResponse(res));
    }

    buildQueryParamter(req?:any){
        if(req){
            const query = req.query;
            if(query){
                if (query.student) {
                    req['studentId.equals'] = query.student;
                }
                if (query.phase) {
                    req['phase.contains'] = query.phase;
                }
                if (query.teachCourse) {
                    req['teachCourseId.equals'] = query.teachCourse;
                }
            }
        }
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: StuCourse = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<StuCourse[]>): HttpResponse<StuCourse[]> {
        const jsonResponse: StuCourse[] = res.body;
        const body: StuCourse[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to StuCourse.
     */
    private convertItemFromServer(stuCourse: StuCourse): StuCourse {
        const copy: StuCourse = Object.assign({}, stuCourse);
        return copy;
    }

    /**
     * Convert a StuCourse to a JSON which can be sent to the server.
     */
    private convert(stuCourse: StuCourse): StuCourse {
        const copy: StuCourse = Object.assign({}, stuCourse);
        return copy;
    }
}
