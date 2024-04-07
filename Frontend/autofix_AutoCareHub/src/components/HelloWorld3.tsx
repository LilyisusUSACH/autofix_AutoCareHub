import React, { useState, MouseEvent } from "react";
import { Box, Grid, Typography } from "@mui/material";
import backImg from "../assets/engranaje.png";
import "./a.css";

const HelloWorld3= () => {
  const [isDowning, setIsDowning] = useState(false);

  const handleOnMove = () => {
    setIsDowning(!isDowning);
  };

  return (
    <Typography>
              {[...new Array(10)]
                .map(
                  () => `Cras mattis consectetur purus sit amet fermentum.
Cras justo odio, dapibus ac facilisis in, egestas eget quam.
Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
Praesent commodo cursus magna, vel scelerisque nisl consectetur et.`
                )
                .join("\n")}
            </Typography>
  )
};
export default HelloWorld3;

