import { Height } from "@mui/icons-material";
import {
  Box,
  Divider,
  Grid,
  InputAdornment,
  InputBase,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
  Typography,
} from "@mui/material";
import { FixedSizeList, VariableSizeList } from "react-window";

import SearchIcon from "@mui/icons-material/Search";
import React, { useEffect, useState } from "react";
import workshopService from "../services/workshop.service";
import { repTypes } from "../constants";

const WorkshopPage = () => {
  const [reparations, setReparations] = useState([]);

  //type reparation = {
  //     fechaIngreso : string,
  //      horaIngreso : string,
  //    };

  const init = () => {
    workshopService
      .getActiveReparations()
      .then((response) => {
        setReparations(response.data);
        console.log(response.data);
      })
      .catch((error) => console.log(error));
  };

  const Row2 = ({ index }) => {
    const row = reparations[index];
    return (
      <TableRow key={index}>
        <TableCell align="center" component="th" scope="row">
          {row.vehiculo.patente.toUpperCase()}
        </TableCell>
        <TableCell align="center">{row.vehiculo.marca.toUpperCase()}</TableCell>
        <TableCell align="center">
          {row.vehiculo.modelo.toUpperCase()}
        </TableCell>
        <TableCell align="center">{repTypes[row.typeRep - 1]}</TableCell>
        <TableCell align="center">
          {row.fechaIngreso + " " + row.horaIngreso.slice(0, 5)}
        </TableCell>
        <TableCell align="center">aaa</TableCell>
        <TableCell align="center">Marca</TableCell>
        <TableCell />
      </TableRow>
    );
  };
  const Fila = (props: { row: object }) => {
    const { row } = props;

    return (
        <TableRow
          sx={{
            "& > *": { borderBottom: "unset" },
            background: row.id % 2 != 0 ? "#E7E7E7" : "#FFFFFF",
          }}
        >
          <TableCell align="center" component="th" scope="row">
            {row.vehiculo.patente.toUpperCase()}
          </TableCell>
          <TableCell align="center">
            {" "}
            {row.vehiculo.marca.toUpperCase()}
          </TableCell>
          <TableCell align="center">
            {" "}
            {row.vehiculo.modelo.toUpperCase()}
          </TableCell>
          <TableCell align="center"> {repTypes[row.typeRep - 1]}</TableCell>
          <TableCell align="center">
            {" "}
            {row.fechaIngreso + " " + row.horaIngreso.slice(0, 5)}
          </TableCell>
          <TableCell align="center"> aaa</TableCell>
          <TableCell align="center"> Marca</TableCell>
          <TableCell />
        </TableRow>
    );
  };

  useEffect(() => {
    init();
  }, []);

  return (
    <Grid
      container
      alignContent={"center"}
      justifyContent={"center"}
      width={"100%"}
      height={"90vh"}
    >
      <Paper
        elevation={10}
        square={false}
        sx={{
          width: "95%",
          height: "95%",
          borderRadius: "25px",
        }}
      >
        <Grid
          container
          alignItems={"center"}
          justifyContent={"space-between"}
          width={"95%"}
          margin={"auto"}
          mt={"2%"}
          rowGap={"10px"}
        >
          <Grid item xs={12} sm={5} md={4}>
            <Typography fontWeight={800} variant="h5">
              Reparaciones activas
              <Divider
                variant="fullWidth"
                sx={{
                  opacity: 1,
                  background: "black",
                }}
              ></Divider>
            </Typography>
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField
              id="filled-search"
              label="Buscar reparación"
              type="search"
              size="small"
              variant="outlined"
              sx={{
                width: "100%",
                background: "rgb(240,240,240)",
                borderRadius: "5px",
              }}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <SearchIcon />
                  </InputAdornment>
                ),
              }}
            ></TextField>
          </Grid>
        </Grid>

        <Grid container alignContent={"center"} justifyContent={"center"}>
          <TableContainer>
            <Table>
              <TableHead
                sx={{
                  ".MuiTableCell-root": {
                    fontSize: "20px",
                    fontWeight: "700",
                  },
                }}
              >
                <TableRow>
                  <TableCell align="center"> Patente </TableCell>
                  <TableCell align="center"> Marca </TableCell>
                  <TableCell align="center"> Modelo </TableCell>
                  <TableCell align="center"> Tipo de reparación </TableCell>
                  <TableCell align="center"> Fecha inicio </TableCell>
                  <TableCell align="center"> Completar </TableCell>
                  <TableCell align="center"> Cancelar </TableCell>
                  <TableCell />
                </TableRow>
              </TableHead>
              <TableBody
                sx={{
                  ".MuiTableCell-root": {
                    fontSize: "17px",
                    fontWeight: "100",
                  },
                }}
              >
                {reparations.map( (reparation, index) => {
                    return (
                    <Fila row={reparation} key={index} />
                )
                } )}
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>
      </Paper>
    </Grid>
  );
};
export default WorkshopPage;
