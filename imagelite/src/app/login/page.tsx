'use client'
import { Template, InputText, RenderIf, Button, FieldError, useNotification } from '@/components'
import { useState } from 'react'
import { formsScheme, LoginForm, validationScheme } from './FormSchem'
import { useAuth } from '@/resources'
import { useFormik } from 'formik'
import { useRouter } from 'next/navigation'
import { AccessToken, Credentials, User } from '@/resources/user/Users'

export default function Login(){

    const [loading, setLoading] = useState<boolean>(false);
    const [newUser, setNewUser] = useState<boolean>(false);

    const auth = useAuth();
    const notification = useNotification();
    const router = useRouter();

    const formik = useFormik<LoginForm>({
        initialValues: formsScheme,
        validationSchema: validationScheme,
        onSubmit: onSubmit
    })

    async function onSubmit(values: LoginForm){
        if (!newUser){
            const credentials: Credentials = {email: values.email, senha: values.password}
            try{
                const accessTocken: AccessToken = await auth.autheticate(credentials);
                auth.initSession(accessTocken);
                //returno true ou false para sessao valida
                auth.isSessionValide();
                console.log(accessTocken);
                router.push("/galeria")
            } catch(error: any){
                const message = error?.message;
                notification.notify(message, "error");
            }
        }else {
            const user: User = {name: values.name, email: values.email, senha: values.password}
            try{
                await auth.save(user);
                notification.notify("Sucesso ao salvar usuário", "success");
                formik.resetForm();
                setNewUser(false)
            }catch(error: any){
                const message = error?.message;
                notification.notify(message, "error");
            }
        }
    }

    return (
        <Template loading={loading}>
             <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
                <div className="sm:mx-auto sm:w-full sm:max-w-sm">
               
                    <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
                       {newUser ? 'Cadastro' : 'Login'}
                    </h2>

               
                </div>

                <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
                <form className="space-y-6" action="#" method="POST" onSubmit={formik.handleSubmit}>
                    <RenderIf condition={newUser}>

                    <div>
                        <label htmlFor="email" className="block text-sm font-medium leading-6 text-gray-900">
                            Nome
                        </label>
                        <div className="mt-2">
                            <InputText 
                                style="w-full" 
                                id="nome"
                                value={formik.values.name}
                                onChange={formik.handleChange}/>
                            <FieldError error={formik.errors.name}/>
                        </div>
                    </div>
                    </RenderIf>
                    <div>
                    <label htmlFor="email" className="block text-sm font-medium leading-6 text-gray-900">
                        Endereço de email
                    </label>
                    <div className="mt-2">
                        <InputText 
                            style="w-full" 
                            id="email"
                            value={formik.values.email}
                            onChange={formik.handleChange}/>
                        <FieldError error={formik.errors.email}/>
                    </div>
                    </div>

                    <div>
                    <div className="flex items-center justify-between">
                        <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                        Senha
                        </label>
                        <RenderIf condition={!newUser}>

                            <div className="text-sm">
                                <a href="#" className="font-semibold text-indigo-600 hover:text-indigo-500">
                                    Esqueceu senha?
                                </a>
                            </div>
                        </RenderIf>
                    </div>
                    <div className="mt-2">
                        <InputText 
                            style="w-full" 
                            id="password"
                            type='password'
                            value={formik.values.password}
                            onChange={formik.handleChange}/>
                            <FieldError error={formik.errors.password}/>
                    </div>
                    </div>

                    <div>
                        <RenderIf condition={newUser}>
                            <Button type='submit' style='bg-indigo-950 hover:bg-indigo-700' label='salvar'/>
                            <Button type='button' style='bg-red-950 hover:bg-red-700 mx-3' label='cancelar' onClick={event => setNewUser(false)}/>

                    
                        </RenderIf>
                        <RenderIf condition={!newUser}>
                            <Button type='submit' style='bg-indigo-950 hover:bg-indigo-700' label='entrar'/>
                            <Button type='button' style='bg-red-950 hover:bg-red-700 mx-3' label='cadastrar' onClick={event => setNewUser(true)}/>
                            
                        </RenderIf>
                    </div>
                </form>

              
                </div>
            </div>
        </Template>
    )
}