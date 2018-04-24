import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TeachCourseComponent } from './teach-course.component';
import { TeachCourseDetailComponent } from './teach-course-detail.component';
import { TeachCoursePopupComponent } from './teach-course-dialog.component';
import { TeachCourseDeletePopupComponent } from './teach-course-delete-dialog.component';

@Injectable()
export class TeachCourseResolvePagingParams implements Resolve<any> {

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

export const teachCourseRoute: Routes = [
    {
        path: 'teach-course',
        component: TeachCourseComponent,
        resolve: {
            'pagingParams': TeachCourseResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'courseosApp.teachCourse.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'teach-course/:id',
        component: TeachCourseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'courseosApp.teachCourse.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const teachCoursePopupRoute: Routes = [
    {
        path: 'teach-course-new',
        component: TeachCoursePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'courseosApp.teachCourse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'teach-course/:id/edit',
        component: TeachCoursePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'courseosApp.teachCourse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'teach-course/:id/delete',
        component: TeachCourseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'courseosApp.teachCourse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
