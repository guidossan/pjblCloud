import Login from "@/app/login/page";
import { useAuth } from "@/resources"

interface AutheticatedPageProps{
    children: React.ReactNode
}
export const AutheticatedPage: React.FC<AutheticatedPageProps> =(
    {children}
)=>{

    const auth = useAuth();
    if(!auth.isSessionValide()){
        return <Login/>
    }
    return (
        <>
            {children}
        </>
    )

}