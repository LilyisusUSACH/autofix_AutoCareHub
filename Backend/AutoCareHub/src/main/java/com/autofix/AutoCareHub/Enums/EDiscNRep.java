package com.autofix.AutoCareHub.Enums;

public enum EDiscNRep {
    GASOLINA(new float[] {-1 , 0.5f, 0.5f, 0.10f, 0.10f, 0.10f, 0.15f, 0.15f, 0.15f, 0.15f, 0.20f}),
    DIESEL(new float[] {-1 , 0.7f, 0.7f, 0.12f, 0.12f, 0.12f, 0.17f, 0.17f, 0.17f, 0.17f, 0.22f}),
    HIBRIDO(new float[] {-1 , 0.10f, 0.10f, 0.15f, 0.15f, 0.15f, 0.20f, 0.20f, 0.20f, 0.20f, 0.25f}),
    ELECTRICO(new float[] {-1 , 0.8f, 0.8f, 0.13f, 0.13f, 0.13f, 0.18f, 0.18f, 0.18f, 0.18f, 0.23f});

    private final float[] values;

    private EDiscNRep(float[] values){
        this.values = values;
    }

    private float[] getValues(){
        return values;
    }
}
