'use client'

import { InputText, Template, Button, RenderIf, useNotification, FieldError, AutheticatedPage } from "@/components"
import { useImagesService } from '@/resources/image/image.service'
import { useFormik } from 'formik';
import { useState } from "react";
import { FormProps, formValidationScheme, formsScheme } from './FormSchem'
import Link from 'next/link';




export default function FormularioPage(){
  
    const useService = useImagesService();
    const notification = useNotification();
    const [ImagePreview, setImagePreview] = useState<string>();
    const [Loading, setLoading] = useState<boolean>(false);
   
    const formik = useFormik<FormProps>({
        initialValues: formsScheme,
        onSubmit: handleSubmit,
        validationSchema: formValidationScheme
    })
    async function handleSubmit(dados: FormProps){
        setLoading(true);
        const formData = new FormData();
        formData.append("file", dados.file);
        formData.append("name", dados.name);
        formData.append("tags", dados.tags);

        await useService.salvar(formData);

        formik.resetForm();
        setImagePreview('')
        setLoading(false);
        notification.notify("Upload enviado com sucesso", 'success');
    }


    function onFileUpload(event: React.ChangeEvent<HTMLInputElement>){
        if (event.target.files){
            /**
             * seta o valor "file" 
             */
            const file = event.target.files[0];
            formik.setFieldValue("file", file)
            /**
             * seta a imagem para mostrar qnd for selecionada
             */
            const currentUrl = URL.createObjectURL(file)
            setImagePreview(currentUrl)
        }
    }
    return (
        <AutheticatedPage>
            <Template loading={Loading}>
                <section className="flex flex-col items-center justify-center my-5">
                    <h5 className="mt-3 mb-10 text-3x1 font-extrabold tracking-tight text-gray-900">Nova Image</h5>
                    <form onSubmit={formik.handleSubmit}>  
                        <div className="mt-5 grig grid-cols-1">
                            <label className="block text-sm font-medium leading-6 text-gray-500">Nome: *</label>
                            <InputText id="name" onChange={formik.handleChange} placeHolder="Nome da imagem" value={formik.values.name}/>
                            <FieldError error={formik.errors.name}/>
                        </div>
                        <div className="mt-5 grig grid-cols-1">
                            <label className="block text-sm font-medium leading-6 text-gray-500">Tags: *</label>
                            <InputText id="tags" onChange={formik.handleChange} placeHolder="Digite o nome das tegs separado por vírgula" value={formik.values.tags} />
                            <FieldError error={formik.errors.tags}/>
                        </div>
                        <div className="mt-5 grig grid-cols-1">
                            <label className="block text-sm font-medium leading-6 text-gray-500">Image: *</label>
                            <FieldError error={formik.errors.file}/>
                            <div className="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10"> 
                                <div className="text-center">
                                    <RenderIf condition={!ImagePreview}>

                                        <svg className="mx-auto h-12 w-12 text-gray-300" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                                            <path fillRule="evenodd" d="M1.5 6a2.25 2.25 0 012.25-2.25h16.5A2.25 2.25 0 0122.5 6v12a2.25 2.25 0 01-2.25 2.25H3.75A2.25 2.25 0 011.5 18V6zM3 16.06V18c0 .414.336.75.75.75h16.5A.75.75 0 0021 18v-1.94l-2.69-2.689a1.5 1.5 0 00-2.12 0l-.88.879.97.97a.75.75 0 11-1.06 1.06l-5.16-5.159a1.5 1.5 0 00-2.12 0L3 16.061zm10.125-7.81a1.125 1.125 0 112.25 0 1.125 1.125 0 01-2.25 0z" clipRule="evenodd" />
                                        </svg>
                                    </RenderIf>
                                    <div className="mt-4 flex text-sm leading-6 text-gray-600"> 
                                        <label className="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600">
                                            <RenderIf condition={!ImagePreview}>
                                                <span>Click to upload</span>

                                            </RenderIf>
                                            <RenderIf condition={!!ImagePreview}>
                                                <img src={ImagePreview} width={250} className="rounded-md"/>
                                            </RenderIf>
                                            <input onChange={onFileUpload} type="file" className="sr-only"/>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="mt-5 flex items-center justify-end gap-x-4">   
                            <Button type="submit" style="bg-blue-500 hover:bg-blue-300" label="Save"/>
                            <Link href='/galeria'>
                                <Button type="button" style="bg-red-500 hover:bg-red-300" label="Cancelar"/>
                            </Link>
                        </div>
                    </form>
                </section>
            </Template>
        </AutheticatedPage>
    )
    
}