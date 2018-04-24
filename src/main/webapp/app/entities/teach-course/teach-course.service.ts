import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TeachCourse } from './teach-course.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TeachCourse>;

@Injectable()
export class TeachCourseService {

    private resourceUrl =  SERVER_API_URL + 'api/teach-courses';

    constructor(private http: HttpClient) { }

    create(teachCourse: TeachCourse): Observable<EntityResponseType> {
        const copy = this.convert(teachCourse);
        return this.http.post<TeachCourse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(teachCourse: TeachCourse): Observable<EntityResponseType> {
        const copy = this.convert(teachCourse);
        return this.http.put<TeachCourse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TeachCourse>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TeachCourse[]>> {
        this.buildQueryParamter(req);
        const options = createRequestOption(req);
        return this.http.get<TeachCourse[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TeachCourse[]>) => this.convertArrayResponse(res));
    }

    buildQueryParamter(req?:any){
        if(req){
            const query = req.query;
            if(query){
                if (query.teachCourseCode) {
                    req['teachCourseCode.equals'] = query.teachCourseCode;
                }
                if (query.teachCourseType) {
                    req['teachCourseType.equals'] = query.teachCourseType;
                }
                if (query.teacher) {
                    req['teacherId.equals'] = query.teacher;
                }
                if (query.course) {
                    req['courseId.equals'] = query.course;
                }
            }
        }
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TeachCourse = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TeachCourse[]>): HttpResponse<TeachCourse[]> {
        const jsonResponse: TeachCourse[] = res.body;
        const body: TeachCourse[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TeachCourse.
     */
    private convertItemFromServer(teachCourse: TeachCourse): TeachCourse {
        const copy: TeachCourse = Object.assign({}, teachCourse);
        return copy;
    }

    /**
     * Convert a TeachCourse to a JSON which can be sent to the server.
     */
    private convert(teachCourse: TeachCourse): TeachCourse {
        const copy: TeachCourse = Object.assign({}, teachCourse);
        return copy;
    }
}
