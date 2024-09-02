import{ AccessToken, Credentials, User, UserSectionToken } from './Users'
import jwt from 'jwt-decode'


class AuthService{
  
    baseURL: string="http://localhost:8080/users";
    static AUTH_PARAM: string = "_auth";

    async autheticate(credentials: Credentials) : Promise<AccessToken> {
        const response = await fetch(this.baseURL + "/auth", {
            method: 'POST',
            body: JSON.stringify(credentials),
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json"
            }
        });
        if (response.status == 400){
            throw new Error("Usuário ou senha incorretos")
        }
        return await response.json();
    }
    async save(user: User):Promise<void>{
        const response = await fetch(this.baseURL, {
            method: 'POST',
            body: JSON.stringify(user),
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json"
            }
        });

        console.log(response);
        if (response.status == 409){
            throw new Error("Usuário já existe")
        }
       
    }
    initSession(token: AccessToken){
        if(token.accessToken){
            const decodedToken: any = jwt(token.accessToken);
            const userSectionToken: UserSectionToken = {
                accessToken: token.accessToken,
                email: decodedToken.sub,
                name: decodedToken.name,
                expiration: decodedToken.exp
            }
            this.setUserSession(userSectionToken);

        }
    }
    setUserSession(userSectionToken: UserSectionToken){
        try{
            localStorage.setItem(AuthService.AUTH_PARAM, JSON.stringify(userSectionToken));
        }catch(error){
            
        }
    }
    getUserSession() : UserSectionToken | null{
        try{

            const authString = localStorage.getItem(AuthService.AUTH_PARAM);
            if(!authString){
                return null
            }
            const token: UserSectionToken = JSON.parse(authString);
            return token;
        }catch(error){
            return null
        }
    }
    isSessionValide() : boolean {
        const userSession: UserSectionToken|null = this.getUserSession();
        if(!userSession){
            return false;
        }
        const expiration:number | undefined = userSession.expiration;
        if(expiration){
            const expirationDate = expiration * 1000;
            return new Date() < new Date(expirationDate);
         
        }
        return false;
    }
    invalidateSession(): void {
        try{

            localStorage.removeItem(AuthService.AUTH_PARAM);
        }catch(error){}
    }
}

export const useAuth = () => new AuthService();