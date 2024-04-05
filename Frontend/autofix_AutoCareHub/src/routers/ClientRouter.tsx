import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import HelloWorld from '../components/HelloWorld';
import { ThemeProvider, createTheme } from '@mui/material';
import NavBar from '../components/NavBar';

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

const ClientRouter = () => {
    return (
        <ThemeProvider theme={theme}>
        <div className=''>
            <NavBar/>
            <Routes>
                <Route path='*' element={<HelloWorld/>}/>
            </Routes>
        </div>
        </ThemeProvider>
    );
}

export default ClientRouter;