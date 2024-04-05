import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import HelloWorld from '../components/HelloWorld';
import HelloWorldOnlyClient from '../components/HelloWorldOnlyClient';
import { ThemeProvider, createTheme } from '@mui/material';

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
            <HelloWorldOnlyClient/>
            <Routes>
                <Route path='/' element={<HelloWorld/>}/>
            </Routes>
        </div>
        </ThemeProvider>
    );
}

export default ClientRouter;