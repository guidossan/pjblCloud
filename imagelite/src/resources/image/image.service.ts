import { Image } from './image.resources'
import { useAuth } from '@/resources'
class ImageService {
    baseUrl: string = 'http://localhost:8080/images';
    auth = useAuth();
    async buscar(query: string='', extencion: string ="") : Promise<Image[]> {
        const userSession = this.auth.getUserSession();
        const url = `${this.baseUrl}?query=${query}&extencion=${extencion}`
        const response = await fetch(url, {
            headers: {
                "Authorization": `Bearer ${userSession?.accessToken}`
            }
        });
        return await response.json();
    }
    async salvar(dados: FormData): Promise<string | null> {
        const userSession = this.auth.getUserSession();
        const response = await fetch(this.baseUrl, {
            method: 'POST', 
            body: dados,
            headers: {
                "Authorization": `Bearer ${userSession?.accessToken}`
            }
        }) 
        return response.headers.get('location')
    }

}


export const useImagesService = () => new ImageService();