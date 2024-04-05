import { Box, Slide, Typography, useScrollTrigger } from "@mui/material";
import backImg from "../assets/engranaje.png";
import ScrollRotateImage from "./ScrollRotateImage";
import './a.css'

interface Props {
  /**
   * Injected by the documentation to work in an iframe.
   * You won't need it on your project.
   */
  window?: () => Window;
  children: React.ReactElement;
}

function HideOnScroll(props: Props) {
  const { children, window } = props;
  const trigger = useScrollTrigger({
    target: window ? window() : undefined,
  });

  return (
    <Slide appear={false} direction="down" in={!trigger}>
      {children}
    </Slide>
  );
}

const HelloWorld = () => {
  const sentido = false;
  return (<div>
    <HideOnScroll>
      <Box
        sx={{
          zIndex: "modal",
          position: "absolute",
          pointerEvents: "none",
          left: "-15rem",
          top: "20%",
        }}
      >
        <img className="lala" src={backImg} />
      </Box>
    </HideOnScroll>
    <Box sx={{ my: 2 }}>
        <Typography>
          {[...new Array(15)]
            .map(
              () => `Cras mattis consectetur purus sit amet fermentum.
Cras justo odio, dapibus ac facilisis in, egestas eget quam.
Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
Praesent commodo cursus magna, vel scelerisque nisl consectetur et.`,
            )
            .join('\n')}
            </Typography>
        </Box>
    </div>
  );
}
export default HelloWorld;
