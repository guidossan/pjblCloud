import * as Yup from 'yup'

export  interface FormProps {
    name: string;
    tags: string;
    file: string | Blob;
}


export const formsScheme: FormProps ={ name: '', tags: '', file: ''}

export const formValidationScheme  = Yup.object().shape({
    name: Yup.string().trim().required('Nome é obrigatório!').max(50, "tamanho máximo de 50"),
    tags: Yup.string().trim().required('Passe ao menos uma tag!').max(50, "tamanho máximo de 50"),
    file: Yup.mixed<Blob>().required('Selecione imagem para upload').test('size', 'tamanho da imagem não pode ser maior que 4MB', (file) => {return file.size < 4000000;})
    .test('type', 'Formatos aceitos: JPEG, GIF e PNG', (file) => {return file.type ==='image/jpeg' || file.type ==='image/png'|| file.type ==='image/gif'})
}) 