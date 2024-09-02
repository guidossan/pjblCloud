'use client'
interface ImageCardProps {
    nome?: string;
    tamanho?: number;
    dataUpload?: string;
    src?: string;
    extencion?: string;
}

export const ImageCard: React.FC<ImageCardProps> = ({ nome, tamanho, dataUpload, src, extencion }: ImageCardProps) => {
    function donwload(){
        window.open(src, '_blank')
    }
   
    
    return(
        <div className='card relative bg-white rounted-md shadow-md transition-transform ease-in duration-300 trasnform hover: shadow-lg hover:-translate-y-2'>
            <img onClick={donwload} src={src} className='h-56 w-full object-cover rounded-t-md' alt=''/>
            <div className="card-body p-4">
                <h5 className="text-x1 font-semibold mb-2 text-gray-600">{nome}</h5>
                <p className="text-gray-600">{formatBytes(tamanho)}</p>
                <p className="text-gray-600">{dataUpload}</p>
                <p className="text-gray-600">{extencion}</p>
            </div>
        </div>
    )
}

function formatBytes(bytes: number = 0,decimals = 2) {
    if(bytes == 0) return '0 Bytes';
    const k = 1024
    const dm = decimals || 2

    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']

    const i = Math.floor(Math.log(bytes) / Math.log(k));
    
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
 }