import { MasterDto } from "../common/master-dto.model";

export class UserMasterDto extends MasterDto {
    public firstName: string;
    public lastName: string;
    public username: string;
    public email: string;
    public gender: string;
    public address: string;
    public phoneNumber: string;
    public dateOfBirth: Date;

    constructor(id: string, active: boolean, createdAt: Date, updatedAt: Date, deletedAt: Date,
        firstName: string, lastName: string, username: string, email: string, gender: string, address: string, phoneNumber: string, dateOfBirth: Date
    ) {
        super(id, createdAt, updatedAt, deletedAt, active);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }
}