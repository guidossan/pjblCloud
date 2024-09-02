import { HtmlContext } from "next/dist/server/future/route-modules/app-page/vendored/contexts/entrypoints";
import React from "react";


interface InputTextProps {
    style?: string;
    onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void
    placeHolder?: string;
    id?: string;
    value?: string;
    type?: string;
}
export const InputText: React.FC<InputTextProps> = ({
    onChange, style, placeHolder, id, type="text"
}) =>{
    return (
        <input
            
            id={id}
            type={type} 
            onChange={onChange}
            placeholder={placeHolder}
            className={`${style} border flex-1 px-5 py-2 rounded-lg text-gray-900 border-0 pl-1 py-1.5 sm:leading-6`} />
        
    )
}