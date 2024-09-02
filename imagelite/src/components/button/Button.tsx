import React from "react";

interface ButtonProps {
    style?: string;
    label?: string;
    onClick?: (event: any) => void;
    type?: "submit" | "button" | undefined

}
export const Button: React.FC<ButtonProps> = ({
        onClick, style, label, type
}) => {
    return (
        <button type={type} className={`${style} text-white px-4 py-2 rounded-lg `} onClick={onClick}>
            
            { label }
        </button>
    )
}