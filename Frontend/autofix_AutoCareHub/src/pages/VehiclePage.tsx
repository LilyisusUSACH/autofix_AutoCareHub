import Checkbox from "@mui/material/Checkbox";
import HighlightOffIcon from "@mui/icons-material/HighlightOff";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import { formatCurrency, formatPhrase } from "../utils/utils";
import {
  Autocomplete,
  Box,
  Button,
  Dialog,
  DialogTitle,
  Divider,
  Grid,
  IconButton,
  InputBase,
  List,
  ListItem,
  ListItemButton,
  ListItemText,
  Paper,
  Popover,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
  Typography,
} from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";
import { light } from "@mui/material/styles/createPalette";
import { useEffect, useState } from "react";
import { Label, Translate } from "@mui/icons-material";
import SendIcon from "@mui/icons-material/Send";
import receiptService from "../services/receipt.service";
import { Bono, Details, Receipt, Vehicle } from "../types/types";
import { repTypes } from "../constants";

const VehiclePage = () => {
  const { id } = useParams();

  const [isPaid, setIsPaid] = useState(true);
  const [uncomplete, setUncomplete] = useState(false);
  const [datos, setDatos] = useState<Receipt>();
  const [mostrarPantallaCompleta, setMostrarPantallaCompleta] = useState(false);
  const [checked, setChecked] = useState(false);
  const [bonosDisponibles, setBonosDisponibles] = useState<Bono[]>([]);

  const handleAbrirPantallaCompleta = () => {
    if (!(isPaid || uncomplete)) {
      receiptService.getBonosByMarca(datos?.patente?.marca).then((response) => {
        setBonosDisponibles(response.data);
      });
      setMostrarPantallaCompleta(true);
    }
  };

  const navigate = useNavigate();

  const handleCerrarPantallaCompleta = () => {
    setMostrarPantallaCompleta(false);
  };

  const handleBoleta = () => {
    setSelectBoleta(true);
    receiptService
      .getReceiptsByPatente(datos?.patente?.patente?.toLocaleLowerCase())
      .then((response) => {
        setBoletasDisponibles(response.data);
      });
  };

  const cerrarSelectBoleta = () => {
    setSelectBoleta(false);
  };

  const handleCheck = () => {
    return;
  };

  const init = () => {
    receiptService.getReceipt(id).then((response) => {
      setIsPaid(response.data.pagado);
      setChecked(response.data.bono != null);
      if (response.data.pagado) setDatos(response.data);
      else
        receiptService
          .postCalculate(id, false, null)
          .then((response2) => {
            setDatos(response2.data);
            if (response2.data.bono != null) {
              setChecked(true);
            }
          })
          .catch((error) => {
            setDatos(response.data);
            setUncomplete(true);
          });

      //console.log(datos);
    });
  };

  useEffect(() => {
    init();
  }, []);

  return (
    <>
      <Grid container minHeight={"89vh"}>
        <Grid item xs={12} md={5} minHeight={"80vh"}  marginBlock={"40px"}>
          <Paper
            sx={{
              minHeight: "80vh",
              width: "80%",
              margin: "auto",
              borderRadius: "25px",
              textAlign: "center",
              overflow: "auto",
            }}
          >
            <Typography variant="h4"> Vehiculo </Typography>
            <Divider
              variant="middle"
              sx={{
                bgcolor: "black",
              }}
            />
            <Grid container width={"90%"} height={"70vh"} justifyContent={'space-around'} margin={"auto"}>
              <Grid
                item
                sx={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  width: "100%",
                  paddingBlock: "10px",
                }}
              >
                <Typography variant="h6">Patente:</Typography>
                <TextField
                  margin="dense"
                  variant="filled"
                  size="small"
                  InputProps={{
                    readOnly: true,
                    inputProps: { style: { padding: 7 } },
                  }}
                  fullWidth
                  sx={{
                    marginInline: "40px",
                  }}
                ></TextField>
              </Grid>
              <Grid
                item
                sx={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "space-between",
                  width: "100%",
                  paddingBlock: "10px",
                }}
              >
                <Typography variant="h6">Modelo:</Typography>
                <TextField
                  margin="dense"
                  variant="filled"
                  size="small"
                  InputProps={{
                    readOnly: true,
                    inputProps: { style: { padding: 7 } },
                  }}
                  sx={{
                    width: "100%",
                    marginInline: "10px",
                  }}
                ></TextField>
                <Typography variant="h6">Marca:</Typography>
                <TextField
                  margin="dense"
                  variant="filled"
                  size="small"
                  InputProps={{
                    readOnly: true,
                    inputProps: { style: { padding: 7 } },
                  }}
                  sx={{
                    marginRight: "40px",
                    marginLeft: "10px",
                    width: "100%",
                  }}
                ></TextField>
              </Grid>
              <Grid
                item
                sx={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "space-between",
                  width: "100%",
                  paddingBlock: "10px",
                }}
              >
                <Typography variant="h6">N° asientos</Typography>
                <TextField
                  margin="dense"
                  variant="filled"
                  size="small"
                  InputProps={{
                    readOnly: true,
                    inputProps: { style: { padding: 7 } },
                  }}
                  sx={{
                    width: "50%",
                    marginInline: "10px",
                  }}
                ></TextField>
                <Typography variant="h6">Año:</Typography>
                <TextField
                  margin="dense"
                  variant="filled"
                  size="small"
                  InputProps={{
                    readOnly: true,
                    inputProps: { style: { padding: 7 } },
                  }}
                  sx={{
                    marginRight: "40px",
                    marginLeft: "10px",
                    width: "100%",
                  }}
                ></TextField>
              </Grid>
              <Grid
                item
                sx={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "space-between",
                  width: "100%",
                  paddingBlock: "10px",
                }}
              >
                <Typography variant="h6">Tipo de motor:</Typography>
                <TextField
                  margin="dense"
                  variant="filled"
                  size="small"
                  InputProps={{
                    readOnly: true,
                    inputProps: { style: { padding: 7 } },
                  }}
                  sx={{
                    marginInline: "40px",
                  }}
                ></TextField>
              </Grid>
              <Grid
                item
                sx={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "space-between",
                  width: "100%",
                  paddingBlock: "10px",
                }}
              >
                <Typography variant="h6">Tipo de vehiculo:</Typography>
                <TextField
                  margin="dense"
                  variant="filled"
                  size="small"
                  InputProps={{
                    readOnly: true,
                    inputProps: { style: { padding: 7 } },
                  }}
                  sx={{
                    marginInline: "40px",
                  }}
                ></TextField>
              </Grid>
            </Grid>
          </Paper>
        </Grid>
        <Grid item xs={12} md={7} minHeight={"80vh"} marginBlock={"40px"}>
          <Paper
            sx={{
              minHeight: "80vh",
              width: "80%",
              margin: "auto",
              borderRadius: "25px",
              textAlign: "center",
              overflow: "auto",
            }}
          >
            <Typography variant="h4">Boletas asociadas</Typography>
            <Divider
              variant="middle"
              sx={{
                bgcolor: "black",
              }}
            />
            <TableContainer
              sx={{
                maxHeight: "73vh",
              }}
            >
              <Table stickyHeader aria-label="a dense table">
                <TableHead>
                  <TableRow>
                    <TableCell width={"35%"}>Nombre</TableCell>
                    <TableCell width={"20%"}>Fecha Ingreso</TableCell>
                    <TableCell width={"20%"}>Fecha Salida</TableCell>
                    <TableCell width={"20%"}>Fecha Retiro</TableCell>
                    <TableCell width={"10%"} align="right">
                      Monto
                    </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody></TableBody>
              </Table>
            </TableContainer>
          </Paper>
        </Grid>
      </Grid>
    </>
  );
};
export default VehiclePage;
