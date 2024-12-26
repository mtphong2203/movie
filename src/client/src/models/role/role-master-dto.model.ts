import { MasterDto } from "../common/master-dto.model";

export class RoleMasterDto extends MasterDto {
    public name: string;
    public description: string;

    constructor(id: string, active: boolean, createdAt: Date, updatedAt: Date, deletedAt: Date, name: string, description: string) {
        super(id, createdAt, updatedAt, deletedAt, active);
        this.name = name;
        this.description = description;
    }
}