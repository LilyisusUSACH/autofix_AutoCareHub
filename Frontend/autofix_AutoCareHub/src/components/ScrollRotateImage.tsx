import React from 'react';
import { useScrollTrigger } from '@mui/material';

const ScrollRotateImage = ({children}) => {
  const trigger = useScrollTrigger();
  return (
    <div
      style={{
        transition: 'transform 0.5s ease-in-out', // CSS transition for smooth rotation
        transform: trigger ? 'rotate(90deg)' : 'rotate(0deg)', // Rotate image when trigger is true
      }}
    >
      {children}
    </div>
  );
};

export default ScrollRotateImage;