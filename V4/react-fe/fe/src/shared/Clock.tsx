import { useEffect, useState } from "react";

type Setter<T> = (data: T) => void;
let dateTimeFormat = new Intl.DateTimeFormat("de-DE",{timeStyle: 'medium' } )

/**
 * Function to show a clock according dateTimeFormat
 * @returns 
 */
export function useClock<String>(): [string, Setter<string>]{

    const [clock, setClock] = useState<string>(""); 

	useEffect(() => { 

		//Implementing the setInterval method 
		const interval = setInterval(() => { 
			setClock(dateTimeFormat.format(new Date())); 
		}, 1000); 

		//Clearing the interval 
		return () => clearInterval(interval); 
	}, [clock]); 

	return [clock, setClock]; 

}
