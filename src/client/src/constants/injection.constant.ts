import { InjectionToken } from "@angular/core";
import { IAuthService } from "../services/auth/auth-service.interface";
import { IRoleService } from "../services/role/role-service.interface";
import { IUserService } from "../services/user/user-service.interface";
import { IPermissionService } from "../services/permission/permission-service.interface";
import { IMovie } from "../services/movie/movie.interface";
import { IFileUploadService } from "../services/file/file-upload.interface";
import { IMovietypeService } from "../services/movietype/movietype.interface";
import { ICinemaRoomService } from "../services/cinemaroom/cinemaroom.interface";
import { IScheduleService } from "../services/schedule/schedule.interface";
import { IShowdateService } from "../services/showdate/showdate.interface";

export const AUTH_SERVICE = new InjectionToken<IAuthService>('AUTH_SERVICE');

export const ROLE_SERVICE = new InjectionToken<IRoleService>('ROLE_SERVICE');

export const USER_SERVICE = new InjectionToken<IUserService>('USER_SERVICE');

export const FILE_SERVICE = new InjectionToken<IFileUploadService>('FILE_SERVICE');

export const PERMISSION_SERVICE = new InjectionToken<IPermissionService>('PERMISSION_SERVICE');

export const MOVIE_SERVICE = new InjectionToken<IMovie>('MOVIE_SERVICE');

export const MOVIETYPE_SERVICE = new InjectionToken<IMovietypeService>('MOVIETYPE_SERVICE');

export const CINEMAROOM_SERVICE = new InjectionToken<ICinemaRoomService>('CINEMAROOM_SERVICE');

export const SCHEDULE_SERVICE = new InjectionToken<IScheduleService>('SCHEDULE_SERVICE');

export const SHOWDATE_SERVICE = new InjectionToken<IShowdateService>('SHOWDATE_SERVICE');