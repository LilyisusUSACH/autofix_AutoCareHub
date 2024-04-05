import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import HelloWorld from '../components/HelloWorld';
import HelloWorldOnlyClient from '../components/NavBar';
import { ThemeProvider, createTheme } from '@mui/material';
import NavBar from '../components/NavBar';
import NavBarVariant from '../components/NavBarVariant';

const theme = createTheme({
    typography:{
        allVariants:{
            fontFamily:'judson'
        }
    },
  palette: {
    black: {
      main: "#000000",
    },
    yellow: {
      main: "#ffd700",
    },
    grey: {
      main: "#d9d9d9",
    },
    white: {
      main: "#ffffff",
    },
    green: {
      main: "#5cd000",
    },
  },
});

const MyVehicleRouter = () => {
    return (
        <ThemeProvider theme={theme}>
        <div className=''>
            <NavBarVariant/>
            <Routes>
                <Route path='*' element={<HelloWorld/>}/>
            </Routes>
        </div>
        </ThemeProvider>
    );
}

export default MyVehicleRouter;