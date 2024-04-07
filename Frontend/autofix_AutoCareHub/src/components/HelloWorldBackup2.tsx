import React, { useState } from 'react';
import './a.css';
import { Height } from '@mui/icons-material';

const HelloWolrd = () => {
  const [isScrolled, setIsScrolled] = useState(false);

  const handleScroll = () => {
    const scrollPosition = window.scrollY;

    // Hacer algo con la posición del scroll si es necesario
    console.log("Posición de scroll:", scrollPosition);

    // Cambiar el estado según sea necesario
    if (scrollPosition > 0) {
      setIsScrolled(true);
    } else {
      setIsScrolled(false);
    }
  };

  // Agregar event listener al montar el componente
  React.useEffect(() => {
    window.addEventListener('scroll', handleScroll);
    // Remover event listener al desmontar el componente
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  return (
    <div className={`mi-div ${isScrolled ? 'scrolled' : ''}`
    } style={{height:'20000px'}}>
      ¡Pasa el cursor aquí!
    </div>
  );
};

export default HelloWolrd;

