import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HelloWorld from "../components/HelloWorld";
import HelloWorld2 from "../components/HelloWorld2";
import HelloWorld3 from "../components/HelloWorld3";
import { Fab, ThemeProvider, createTheme } from "@mui/material";
import NavBar from "../components/NavBar";
import { blue, green } from "@mui/material/colors";
import WhatsAppIcon from '@mui/icons-material/WhatsApp';

const theme = createTheme({
  typography: {
    allVariants: {
      fontFamily: "judson",
    },
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
      <div className="">
        <NavBar />
        <HelloWorld>
          <Routes>
            <Route path="/" element={<HelloWorld2 />} />
            <Route path="*" element={<HelloWorld3 />} />
          </Routes>
          <Fab
            size='large'
            sx={{
              position: "absolute",
              bottom: 50,
              right: 50,
              color: "common.white",
              bgcolor: green[500],
              "&:hover": {
                bgcolor: green[600],
              },
            }}
            color="primary"
            aria-label="add"
          >
            <WhatsAppIcon style={{
              width:'70%',
              height:'70%'
            }} />
          </Fab>
        </HelloWorld>
      </div>
    </ThemeProvider>
  );
};

export default ClientRouter;
