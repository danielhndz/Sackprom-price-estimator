import { Role } from './role';

export class UserPrinciple {

    private id: number;
    private name: string;
    private username: string;
    private email: string;
    private authorities: Array<Role>;
    private isAdmin: boolean;

    constructor(
        id: number, 
        name: string, 
        username: string, 
        email: string, 
        authorities: Array<Role>
    ) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.authorities = authorities;
        
        this.setIsAdmin(this.authorities);
    }

    getName(): string {
        return this.name;
    }

    getUsername(): string {
        return this.username;
    }

    getIsAdmin(): boolean {
        return this.isAdmin;
    }

    setIsAdmin(authorities: Array<Role>): void {
        for (let i = 0; i < authorities.length; i++) {
            const auth = authorities[i];
            if (auth.isAdmin()) {
                this.isAdmin = true;
                break;
            }
        }
        this.isAdmin = false;
    }
}
