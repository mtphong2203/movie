import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { IFileUploadService } from "./file-upload.interface";

@Injectable({
    providedIn: 'root'
})
export class FileUploadService implements IFileUploadService {

    private readonly apiUrl: string = 'http://localhost:8080/api/v1/files/upload-file';

    constructor(private readonly httpClient: HttpClient) { }

    upload(file: File): Observable<any> {
        const formData = new FormData();

        formData.append('file', file, file.name);

        return this.httpClient.post(this.apiUrl, formData, {
            responseType: 'text',
        });
    }
}