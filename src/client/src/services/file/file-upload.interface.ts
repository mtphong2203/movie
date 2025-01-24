import { Observable } from "rxjs";

export interface IFileUploadService{

    upload(file: File): Observable<any>;
}