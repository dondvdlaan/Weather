import { Grommet, Header } from 'grommet';

interface Props{

}
/**
 * Simple top bar in the App.tsx main page
 * @param props 
 * @returns 
 */

const AppBar = (props: any) => (
    <Header
       background="brand"
       pad={{ left: "medium", right: "small", vertical: "small" }}
       elevation="medium"
       {...props}
     />
    );


export default AppBar;