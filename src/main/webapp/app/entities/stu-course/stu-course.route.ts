import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { StuCourseComponent } from './stu-course.component';
import { StuCourseDetailComponent } from './stu-course-detail.component';
import { StuCoursePopupComponent } from './stu-course-dialog.component';
import { StuCourseDeletePopupComponent } from './stu-course-delete-dialog.component';

@Injectable()
export class StuCourseResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const stuCourseRoute: Routes = [
    {
        path: 'stu-course',
        component: StuCourseComponent,
        resolve: {
            'pagingParams': StuCourseResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'courseosApp.stuCourse.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'stu-course/:id',
        component: StuCourseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'courseosApp.stuCourse.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stuCoursePopupRoute: Routes = [
    {
        path: 'stu-course-new',
        component: StuCoursePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'courseosApp.stuCourse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stu-course/:id/edit',
        component: StuCoursePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'courseosApp.stuCourse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stu-course/:id/delete',
        component: StuCourseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'courseosApp.stuCourse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
