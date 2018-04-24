import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Teacher } from './teacher.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Teacher>;

@Injectable()
export class TeacherService {

    private resourceUrl =  SERVER_API_URL + 'api/teachers';

    constructor(private http: HttpClient) { }

    create(teacher: Teacher): Observable<EntityResponseType> {
        const copy = this.convert(teacher);
        return this.http.post<Teacher>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(teacher: Teacher): Observable<EntityResponseType> {
        const copy = this.convert(teacher);
        return this.http.put<Teacher>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Teacher>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Teacher[]>> {
        this.buildQueryParamter(req);
        const options = createRequestOption(req);
        return this.http.get<Teacher[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Teacher[]>) => this.convertArrayResponse(res));
    }

    buildQueryParamter(req?:any){
        if(req){
            const teacherQuery = req.teacherQuery;
            if(teacherQuery){
                if (teacherQuery.id) {
                    req['id.equals'] = teacherQuery.id;
                }
                if (teacherQuery.teacherCode) {
                    req['teacherCode.equals'] = teacherQuery.teacherCode;
                }
                if (teacherQuery.name) {
                    req['name.contains'] = teacherQuery.name;
                }

                if (teacherQuery.subject) {
                    req['subject.contains'] = teacherQuery.subject;
                }

                if (teacherQuery.college) {
                    req['college.contains'] = teacherQuery.college;
                }

                if (teacherQuery.isOnJob) {
                    req['isOnJob.equals'] = teacherQuery.isOnJob;
                }
            }
        }
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Teacher = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Teacher[]>): HttpResponse<Teacher[]> {
        const jsonResponse: Teacher[] = res.body;
        const body: Teacher[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Teacher.
     */
    private convertItemFromServer(teacher: Teacher): Teacher {
        const copy: Teacher = Object.assign({}, teacher);
        return copy;
    }

    /**
     * Convert a Teacher to a JSON which can be sent to the server.
     */
    private convert(teacher: Teacher): Teacher {
        const copy: Teacher = Object.assign({}, teacher);
        return copy;
    }
}
