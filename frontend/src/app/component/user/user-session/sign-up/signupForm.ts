export class SignupForm {
    constructor(
        public name: string,
        public username: string,
        public email: string,
        public password: string,
        public roles: string[]
    ) {}
}