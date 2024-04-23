export interface Vehicle {
    carType: number;
    fabricationYear: string;
    id: number | null;
    kmRecorridos: number;
    marca: string;
    modelo: string;
    motorType: number;
    nasientos: number;
    patente: string;
  }
  
export interface Reparation {
    fechaIngreso: string;
    fechaRetiro: string;
    fechaSalida: string;
    horaIngreso: string;
    horaRetiro: string;
    horaSalida: string;
    id: number;
    montoTotal: number;
    receipt_id: number;
    typeRep: number;
    vehiculo: Vehicle;
  }
  
export interface ColumnData {
    label: string;
    width: number;
  }