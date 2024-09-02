
export class User{
    name?: string;
    email?: string;
    senha?: string;
}

export class Credentials {
    email?: string;
    senha?: string;
}

export class AccessToken {
    accessToken?: string;
}

export class UserSectionToken{
    name?: string;
    email?: string;
    accessToken?: string;
    expiration?: number;
}