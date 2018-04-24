import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';

import {Student} from './student.model';
import {createRequestOption} from '../../shared';

export type EntityResponseType = HttpResponse<Student>;

@Injectable()
export class StudentService {

    private resourceUrl = SERVER_API_URL + 'api/students';

    constructor(private http:HttpClient) {
    }

    create(student:Student):Observable<EntityResponseType> {
        const copy = this.convert(student);
        return this.http.post<Student>(this.resourceUrl, copy, {observe: 'response'})
            .map((res:EntityResponseType) => this.convertResponse(res));
    }

    update(student:Student):Observable<EntityResponseType> {
        const copy = this.convert(student);
        return this.http.put<Student>(this.resourceUrl, copy, {observe: 'response'})
            .map((res:EntityResponseType) => this.convertResponse(res));
    }

    find(id:number):Observable<EntityResponseType> {
        return this.http.get<Student>(`${this.resourceUrl}/${id}`, {observe: 'response'})
            .map((res:EntityResponseType) => this.convertResponse(res));
    }

    query(req?:any):Observable<HttpResponse<Student[]>> {


        this.buildQueryParamter(req);

        const options = createRequestOption(req);
        return this.http.get<Student[]>(this.resourceUrl, {params: options, observe: 'response'})
            .map((res:HttpResponse<Student[]>) => this.convertArrayResponse(res));
    }

    buildQueryParamter(req?:any){
        if(req) {
            const studentQuery = req.studentQuery;
            if (studentQuery){
                if (studentQuery.stuCode) {
                    req['stuCode.equals'] = studentQuery.stuCode;
                }

                if (studentQuery.chnName) {
                    req['chnName.contains'] = studentQuery.chnName;
                }

                if (studentQuery.engName) {
                    req['engName.contains'] = studentQuery.engName;
                }

                if (studentQuery.school) {
                    req['school.contains'] = studentQuery.school;
                }

                if (studentQuery.grade) {
                    req['grade.equals'] = studentQuery.grade;
                }

                if (studentQuery.isAfterSchool) {
                    req['isAfterSchool.equals'] = studentQuery.isAfterSchool;
                }
            }
        }
    }

    delete(id:number):Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    private convertResponse(res:EntityResponseType):EntityResponseType {
        const body:Student = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res:HttpResponse<Student[]>):HttpResponse<Student[]> {
        const jsonResponse:Student[] = res.body;
        const body:Student[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Student.
     */
    private convertItemFromServer(student:Student):Student {
        const copy:Student = Object.assign({}, student);
        return copy;
    }

    /**
     * Convert a Student to a JSON which can be sent to the server.
     */
    private convert(student:Student):Student {
        const copy:Student = Object.assign({}, student);
        return copy;
    }
}
