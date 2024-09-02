

import * as Yup from 'yup'


export interface LoginForm{
    name?: string;
    email: string;
    password: string;
}
export const validationScheme = Yup.object().shape({
    email: Yup.string().trim().required('email é obrigatório').email('email inválido'),
    password: Yup.string().trim().required('senha é obrigatória!').min(8,' senha deve conter no mínimo 8 caracteres')

}) 
export const formsScheme: LoginForm = {name: '', email: '', password: ''}