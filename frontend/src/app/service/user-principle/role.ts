export class Role {

    private authority: string;

    constructor(
        authority: string
    ) {
        this.authority = authority;
    }

    isAdmin(): boolean {
        return (this.authority === 'ROLE_ADMIN');
    }
}