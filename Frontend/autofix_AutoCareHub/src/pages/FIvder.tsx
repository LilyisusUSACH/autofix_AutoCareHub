import {
  Grid,
  Paper,
  IconButton,
  TextField,
  Typography,
  Divider,
} from "@mui/material";
import SendIcon from "@mui/icons-material/Send";

const PosPage = () => {
  return (
    <Grid
      container
      alignContent={"center"}
      min-height={"86vh"}
      height={"86vh"}
      marginTop={"20px"}
      paddingInline={"5%"}
    >
      <Grid
        item
        xs={12}
        md={8}
        container
        direction={"column"}
        height={"100%"}
        justifyContent={"space-around"}
      >
        <Grid item xs={6} padding={"20px"}>
          <Paper
            elevation={8}
            sx={{
              height: "100%",
              width: "70%",
              alignItems: "center",
              justifyItems: "center",
              borderRadius: "30px",
            }}
          >
            <Grid
              container
              direction={"column"}
              justifyContent={"space-evenly"}
              height={"100%"}
            >
              <Grid item>
                <Typography textAlign={"center"} variant="h4">
                  {"Consultar cliente"}
                </Typography>
                <Divider
                  variant="fullWidth"
                  sx={{
                    mt: "2%",
                    borderBottomWidth: "1px",
                    opacity: 1,
                    background: "black",
                  }}
                ></Divider>
              </Grid>
              <Grid item>
                <Typography
                  textAlign={"center"}
                  variant="h6"
                  sx={{
                    lineHeight: "1.2",
                  }}
                >
                  {"Ingresa Patente del cliente para conocer su deuda actual"}
                </Typography>
              </Grid>
              <Grid item textAlign={"center"}>
                <TextField
                  // TODO: validar
                  required
                  sx={{
                    width: "50%",
                    letterSpacing: "200px",
                  }}
                  InputProps={{
                    style: { letterSpacing: "20px" },
                  }}
                  //onChange={handleChange}
                  //value={pat}
                  label="Patente"
                  variant="filled"
                  size="small"
                />
                <IconButton
                  //onClick={getStatus}
                  sx={{
                    ml: "1rem",
                    mt: "0.2rem",
                    border: "0.5px solid black",
                    backgroundColor: "#1EBD96",
                    "&:hover": {
                      backgroundColor: "#1EBD96",
                      filter: "brightness(110%)",
                    },
                  }}
                >
                  <SendIcon
                    sx={{
                      color: "white",
                      strokeOpacity: "1",
                      strokeWidth: "0.5px",
                      stroke: "black",
                    }}
                  />
                </IconButton>
              </Grid>
            </Grid>
          </Paper>
        </Grid>
        <Grid item xs={6} padding={"20px"}>
          <Paper
            sx={{
              height: "100%",
              borderRadius: "20px",
            }}
          >
            aaaa
          </Paper>
        </Grid>
      </Grid>
      <Grid
        item
        xs={4}
        height={"100%"}
        padding={"20px"}
        alignContent={"center"}
      >
        <Paper
          elevation={8}
          sx={{
            height: "100%",
            borderRadius: "30px",
          }}
        >
          <Grid
            container
            direction={"column"}
            width={"100%"}
            height={"100%"}
            textAlign={"center"}
            justifyContent={"start"} rowGap={'3%'}
            pt={'10px'}
          >
            <Grid item>
              <Typography variant="h4">Reportes</Typography>
              <Divider></Divider>
            </Grid>
            <Grid item>
              <Typography
                sx={{
                  fontStyle: "italic",
                  fontWeight:'300',
                  color:'gray'
                }}
              >
                Aqui puedes ver los reportes mas frencuentes y necesarios
              </Typography>
              <Divider></Divider>
            </Grid>
            <Grid item display={"flex"} alignItems={"center"}>
              <Typography width={"80%"}>
                {"Reporte 1"} <br /> {"Detalle de costos por cada vehiculo"}
              </Typography>
              <IconButton
                //onClick={getStatus}
                sx={{
                  width: "40px",
                  height: "40px",
                  border: "0.5px solid black",
                  backgroundColor: "#1EBD96",
                  "&:hover": {
                    backgroundColor: "#1EBD96",
                    filter: "brightness(110%)",
                  },
                }}
              >
                <SendIcon
                  sx={{
                    color: "white",
                    strokeOpacity: "1",
                    strokeWidth: "0.5px",
                    stroke: "black",
                  }}
                />
              </IconButton>
            </Grid>
            <Grid item display={"flex"} alignItems={"center"}>
              <Typography width={"80%"}>
                {"Reporte 1"} <br /> {"Detalle de costos por cada vehiculo"}
              </Typography>
              <IconButton
                //onClick={getStatus}
                sx={{
                  width: "40px",
                  height: "40px",
                  border: "0.5px solid black",
                  backgroundColor: "#1EBD96",
                  "&:hover": {
                    backgroundColor: "#1EBD96",
                    filter: "brightness(110%)",
                  },
                }}
              >
                <SendIcon
                  sx={{
                    color: "white",
                    strokeOpacity: "1",
                    strokeWidth: "0.5px",
                    stroke: "black",
                  }}
                />
              </IconButton>
            </Grid>
            <Grid item display={"flex"} alignItems={"center"}>
              <Typography width={"80%"}>
                {"Reporte 1"} <br /> {"Detalle de costos por cada vehiculo"}
              </Typography>
              <IconButton
                //onClick={getStatus}
                sx={{
                  width: "40px",
                  height: "40px",
                  border: "0.5px solid black",
                  backgroundColor: "#1EBD96",
                  "&:hover": {
                    backgroundColor: "#1EBD96",
                    filter: "brightness(110%)",
                  },
                }}
              >
                <SendIcon
                  sx={{
                    color: "white",
                    strokeOpacity: "1",
                    strokeWidth: "0.5px",
                    stroke: "black",
                  }}
                />
              </IconButton>
            </Grid>
            <Grid item display={"flex"} alignItems={"center"}>
              <Typography width={"80%"}>
                {"Reporte 1"} <br /> {"Detalle de costos por cada vehiculo"}
              </Typography>
              <IconButton
                //onClick={getStatus}
                sx={{
                  width: "40px",
                  height: "40px",
                  border: "0.5px solid black",
                  backgroundColor: "#1EBD96",
                  "&:hover": {
                    backgroundColor: "#1EBD96",
                    filter: "brightness(110%)",
                  },
                }}
              >
                <SendIcon
                  sx={{
                    color: "white",
                    strokeOpacity: "1",
                    strokeWidth: "0.5px",
                    stroke: "black",
                  }}
                />
              </IconButton>
            </Grid>
          </Grid>
        </Paper>
      </Grid>
    </Grid>
  );
};

export default PosPage;
