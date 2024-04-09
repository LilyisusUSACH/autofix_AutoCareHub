import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import HelloWorld from '../components/HelloWorld';
import NavBarVariantWork from '../components/NavBarVariantWork';
import WorkshopPage from '../components/WorkshopPage';
import { ThemeProvider, createTheme } from '@mui/material';


const theme = createTheme({
    typography: {
      allVariants: {
        fontFamily: "judson",
      },
    },
    palette: {
      black:{
        main: "#000000",
      },
      aqua:{
        main: "#28D3C9"
      },yellow: {
        main: "#ffd700",
      },
    },
  });
  

const WorkshopRouter = () => {
    return (
        <ThemeProvider theme={theme}>
            <div className=''>
                <NavBarVariantWork/>
                <Routes>
                    <Route path='/' element={<WorkshopPage/>}/>
                </Routes>
            </div>
        </ThemeProvider>
    );
}

export default WorkshopRouter;