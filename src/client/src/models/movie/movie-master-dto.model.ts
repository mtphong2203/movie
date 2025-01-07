import { MasterDto } from "../common/master-dto.model";

export class MovieMasterDTO extends MasterDto{
    public name!: string;
    public fromDate!: Date;
    public movieCompany!: string;
    public duration!: number;
    public version!: string;
}
