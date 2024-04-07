import React from 'react';
import { Scrollbar } from 'react-scrollbars-custom';

function CustomScrollbar(props) {
  return (
    <Scrollbar
      style={{ width: '100vw', height: '100vh' }} // Ajusta el ancho y alto según tus necesidades
      rtl={false} // Cambia a true si necesitas soporte de escritura de derecha a izquierda
      {...props}
    />
  );
}

export default CustomScrollbar;
