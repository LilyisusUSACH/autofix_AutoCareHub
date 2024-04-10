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

import SearchIcon from "@mui/icons-material/Search";
import React, { useEffect, useState } from "react";
import workshopService from "../services/workshop.service";
import { repTypes } from "../constants";
import { TableComponents, TableVirtuoso } from "react-virtuoso";

interface Vehicle {
  carType: string,
  fabricationYear: string,
  id:number,
  kmRecorridos:number,
  marca:string,
  modelo:string,
  motorType:string,
  nasientos:number,
  patente:string
}

interface Reparation{
  fechaIngreso:string,
  fechaRetiro:string,
  fechaSalida:string,
  horaIngreso:string,
  horaRetiro:string,
  horaSalida:string,
  id:number,
  montoTotal:number,
  receipt_id:number,
  typeRep:number,
  vehiculo: Vehicle,
}

interface ColumnData {
  dataKey: keyof Reparation;
  label: string;
  width: number;
}

const VirtuosoTableComponents: TableComponents<Reparation> = {
  Scroller: React.forwardRef<HTMLDivElement>((props, ref) => (
    <TableContainer component={Box} {...props} ref={ref} />
  )),
  Table: (props) => (
    <Table {...props} sx={{ borderCollapse: 'separate', tableLayout: 'auto' }} />
  ),
  TableHead,
  TableRow: ({ item: _item, ...props }) => <TableRow {...props} />,
  TableBody: React.forwardRef<HTMLTableSectionElement>((props, ref) => (
    <TableBody {...props} ref={ref} />
  )),
};

const columns: ColumnData[] = [
  {
    width: 200,
    label: "Dessert",
    dataKey: "dessert",
  },
  {
    width: 120,
    label: "Calories\u00A0(g)",
    dataKey: "calories",
    numeric: true,
  },
  {
    width: 120,
    label: "Fat\u00A0(g)",
    dataKey: "fat",
    numeric: true,
  },
  {
    width: 120,
    label: "Carbs\u00A0(g)",
    dataKey: "carbs",
    numeric: true,
  },
  {
    width: 120,
    label: "Protein\u00A0(g)",
    dataKey: "protein",
    numeric: true,
  },
];

const Fila = (props: { row: Reparation }) => {
  const { row } = props;
  return (
    <>
      <TableCell align="center" component="th" scope="row">
        {row.vehiculo.patente.toUpperCase()}
      </TableCell>
      <TableCell align="center">
        {row.vehiculo.marca.toUpperCase()}
      </TableCell>
      <TableCell align="center">
        {row.vehiculo.modelo.toUpperCase()}
      </TableCell>
      <TableCell align="center"> {repTypes[row.typeRep - 1]}</TableCell>
      <TableCell align="center">
        {row.fechaIngreso + " " + row.horaIngreso.slice(0, 5)}
      </TableCell>
      <TableCell align="center"> aaa</TableCell>
      <TableCell align="center"> Marca</TableCell>
    </>
  );
};

const WorkshopPage = () => {
  const [reparations, setReparations] = useState([]);

  const init = () => {
    workshopService
      .getActiveReparations()
      .then((response) => {
        setReparations(response.data);
        console.log(response.data);
      })
      .catch((error) => console.log(error));
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

        <Box alignContent={"center"} height={'89%'} justifyContent={"center"}>
          <TableVirtuoso
            style={{ height: "100%", borderRadius: '0 0 25px 25px' }}
            data={reparations}
            components={VirtuosoTableComponents}
            fixedHeaderContent={() => (
              <TableRow
              style={{
                background:'white'
              }}
              >
                <TableCell variant="head" align="center"> Patente </TableCell>
                <TableCell variant="head" align="center"> Marca </TableCell>
                <TableCell variant="head" align="center"> Modelo </TableCell>
                <TableCell variant="head" align="center"> Tipo de reparación </TableCell>
                <TableCell variant="head" align="center"> Fecha inicio </TableCell>
                <TableCell variant="head" align="center"> Completar </TableCell>
                <TableCell variant="head" align="center"> Cancelar </TableCell>
              </TableRow>
            )}
            itemContent={(index, reparation) => {
              return <Fila key={index} row={reparation}></Fila>;
            }}
          ></TableVirtuoso>
        </Box>
      </Paper>
    </Grid>
  );
};
export default WorkshopPage;
