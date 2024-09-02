'use client'

import React from "react"

interface PrimeiroComponentProps{
    menssagem?: string;
    menssagemDoBotao?: string;

}
export const PrimeiroComponent: React.FC<PrimeiroComponentProps> = (props: PrimeiroComponentProps) =>{

    function handleClick(){
        console.log(props.menssagemDoBotao)
       
    }
    return (
        <div>
            {props.menssagem}
           
            <button onClick={handleClick}>Clique aqui</button>
        </div>
    )
}

export const ArrowFunction = () => {
    return(
        <h2>ArrowFunction</h2>
    )
}