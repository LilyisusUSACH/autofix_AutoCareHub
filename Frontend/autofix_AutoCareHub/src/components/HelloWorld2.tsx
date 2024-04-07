import React, { useState } from "react";
import {
  Box,
  Button,
  Card,
  CardContent,
  CardMedia,
  Grid,
  Typography,
} from "@mui/material";
import "./a.css";
import Carousel from "react-material-ui-carousel";
import "../style/Example.scss";
import Img from "../assets/imgs/ElectricidadAuto.jpg";
import { Link } from "react-router-dom";

type Item = {
  Name: string;
  Caption: string;
  ButtonText: string;
  ButtonLink: string;
  BackgroundColor: string;
  TextColor: string;
  contentPosition: "left" | "right" | "middle";
  Items: { Name: string; Image: string }[];
};

interface BannerProps {
  item: Item;
  contentPosition: "left" | "right" | "middle";
  length?: number;
}

const Banner = (props: BannerProps) => {
  const contentPosition = props.contentPosition
    ? props.contentPosition
    : "left";
  const totalItems: number = props.length ? props.length : 3;
  const mediaLength = totalItems - 1;

  const items = [];
  const content = (
    <Grid item xs={4} key="content">
      <CardContent
        sx={{ backgroundColor: `${props.item.BackgroundColor}!important` }}
        className="CardContent"
      >
        <Typography
          className="Title"
          sx={{
            color: `${props.item.TextColor}!important`,
          }}
        >
          {props.item.Name}
        </Typography>

        <Typography
          className="Caption"
          sx={{
            color: `${props.item.TextColor}!important`,
          }}
        >
          {props.item.Caption}
        </Typography>
        <Link to={props.item.ButtonLink}>
          <Button
            sx={{
              color: `${props.item.TextColor}!important`,
              border: `3px solid ${props.item.TextColor}!important`,
            }}
            variant="outlined"
            className="ViewButton"
          >
            {props.item.ButtonText}
          </Button>
        </Link>
      </CardContent>
    </Grid>
  );

  for (let i = 0; i < mediaLength; i++) {
    const item = props.item.Items[i];

    const media = (
      <Grid item xs={4} key={item.Name}>
        <CardMedia
          className="Media"
          component="img"
          image={item.Image}
          title={item.Name}
        ></CardMedia>
      </Grid>
    );

    items.push(media);
  }

  if (contentPosition === "left") {
    items.unshift(content);
  } else if (contentPosition === "right") {
    items.push(content);
  } else if (contentPosition === "middle") {
    items.splice(items.length / 2, 0, content);
  }

  return (
    <Card raised className="Banner">
      <Grid container spacing={0} className="BannerGrid">
        {items}
      </Grid>
    </Card>
  );
};

const items: Item[] = [
  {
    Name: "Tu auto en el taller",
    Caption: "Si tienes tu vehiculo en el taller",
    contentPosition: "left",
    ButtonText: "Haz click aqui!",
    ButtonLink: "/myVehicle",
    BackgroundColor: "rgb(0,0,0)",
    TextColor: "red",
    Items: [
      {
        Name: "Taller Vehiculos",
        Image: "src/assets/imgs/VehiculosTaller.jpg",
      },
      {
        Name: "iPhone",
        Image: "src/assets/imgs/ReparandoMotor.jpg",
      },
    ],
  },
  {
    Name: "Home Appliances",
    Caption: "Say no to manual home labour!",
    contentPosition: "middle",
    ButtonText: "",
    ButtonLink: "",
    BackgroundColor: "rgb(255,0,0)",
    TextColor: "rgb(0,255,255)",
    Items: [
      {
        Name: "Washing Machine WX9102",
        Image: "https://source.unsplash.com/featured/?washingmachine",
      },
      {
        Name: "Learus Vacuum Cleaner",
        Image: "https://source.unsplash.com/featured/?vacuum,cleaner",
      },
    ],
  },
  {
    Name: "Decoratives",
    Caption: "Give style and color to your living room!",
    contentPosition: "right",
    ButtonText: "",
    ButtonLink: "",
    BackgroundColor: "rgb(0,0,255)",
    TextColor: "rgb(255,255,0)",
    Items: [
      {
        Name: "Living Room Lamp",
        Image: "https://source.unsplash.com/featured/?lamp",
      },
      {
        Name: "Floral Vase",
        Image: "https://source.unsplash.com/featured/?vase",
      },
    ],
  },
];

const HelloWorld2 = () => {
  return (
    <Grid
      container
      direction="column"
      justifyContent="space-evenly"
      alignItems={"center"}
      style={{
        minHeight: "100%",
      }}
    >
      <Typography variant="h3">
        Somos los N°1 en Reparaciones Automovilisticas
      </Typography>
      <Carousel
        className="Example"
        autoPlay={true}
        animation="fade"
        indicators={true}
        duration={500}
        navButtonsAlwaysInvisible={false}
        navButtonsAlwaysVisible={false}
        cycleNavigation={true}
        fullHeightHover={true}
        swipe={true}
        sx={{
          minWidth: "100%",
        }}

        // next={(now: any, previous:any) => console.log(`Next User Callback: Now displaying child ${now}. Previously displayed child ${previous}`)}
        // prev={(now, previous) => console.log(`Prev User Callback: Now displaying child ${now}. Previously displayed child ${previous}`)}
        // onChange={(now, previous) => console.log(`OnChange User Callback: Now displaying child ${now}. Previously displayed child ${previous}`)}

        // navButtonsProps={{style: {backgroundColor: 'cornflowerblue', borderRadius: 0}}}
        // navButtonsWrapperProps={{style: {bottom: '0', top: 'unset', }}}
        // indicatorContainerProps={{style: {margin: "20px"}}}
        // NextIcon='next'
      >
        {items.map((item, index) => {
          return (
            <Banner
              item={item}
              key={index}
              contentPosition={item.contentPosition}
            />
          );
        })}
      </Carousel>
      <Typography align="center" variant="h5" sx={{ fontStyle: "italic" }}>
        Si tu vehiculo esta en el taller ingresa a la opcion Mi Vehiculo <br />
        <br />
        Si quieres conocer mas acerca de nuestros servicios ingresa a Servicios
      </Typography>
    </Grid>
  );
};
export default HelloWorld2;
