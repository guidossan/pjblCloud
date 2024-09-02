'use client'

import { ImageCard, Template, Button, InputText, useNotification, AutheticatedPage } from '@/components'
import { useState } from 'react'
import { useImagesService }from '@/resources'
import {Image } from "@/resources/image/image.resources"

import Link from 'next/link';


export default function GaleriaPage(){
   
    const useService = useImagesService();
    const notification = useNotification();
    const [images, setImage] = useState<Image[]>([])
    const [query, setQuery] = useState<string>('')
    const [extencion, setExtencion] = useState<string>('')
    const [loading, setLoading]  = useState<boolean>(false)


    async function searchImages(){
        setLoading(true)
        const result = await useService.buscar(query, extencion);
        setImage(result); 
        setLoading(false)
        if (!result.length){
            notification.notify('Não encontrou resultados', 'warning')
        }

    }

    function renderImageCard(image: Image) {
        return (
            <ImageCard 
                    key={image.url}
                    nome={image.name} 
                    src={image.url} 
                    tamanho={image.size} 
                    dataUpload={image.uploadDate} 
                    extencion={image.extencion}/>
        )
    }

    function renderImageCards(){
        return images.map(image => renderImageCard(image))
    }

    return (
        <AutheticatedPage>

            <Template loading={loading}>
                <section className='flex flex-col items-center justify-center my-5'>
                    <div className='flex space-x-4'>
                        <InputText onChange={event => setQuery(event.target.value)} placeHolder='Digite nome ou tag'/>
                        <select onChange={event => setExtencion(event.target.value)} className='border px-4 py-2 rounded-lg text-grey-900'>
                            <option value=''>All Formats</option>
                            <option value='PNG'>PNG</option>
                            <option value='JPEG'>JPEG</option>
                            <option value='GIF'>GIF</option>
                        </select>
                        <Button style='bg-blue-500 hover:bg-blue-300' label='Search' onClick={searchImages}></Button>
                        <Link href="/formulario">
                        
                            <Button style='bg-yellow-500 hover:bg-yellow-300' label='Add New'></Button>
                        </Link>
                    </div>
                </section>

                <section className="grid grid-cols-4 gap-8">
                    {

                        renderImageCards()
                    }
                </section>
            </Template>
        </AutheticatedPage>



    )
    
}