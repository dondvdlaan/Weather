import { useState, useEffect } from 'react';

export const Timer = () => {

    const [isLoading, setIsLoading] = useState(true);
    const [data, setData]           = useState("");

    useEffect(() => {

        // Creating a timeout within the useEffect hook 
        setTimeout(() => {
            setData("Welcome to gfg!");
            setIsLoading(false);
        }, 5000);
    }, []);

    if (isLoading) {
        return <div className='spinner'>
            Loading.....</div>;
    }

    return (
        <div className='container'>
            {data}
        </div>
    );
}
