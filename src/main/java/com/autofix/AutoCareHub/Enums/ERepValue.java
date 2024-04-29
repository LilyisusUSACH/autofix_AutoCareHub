package com.autofix.AutoCareHub.Enums;

// idea: pasar esto a un excel

import java.lang.reflect.Array;
import java.util.List;

public enum ERepValue {
    GASOLINA(new int[] {-1 , 120000, 130000, 350000, 210000, 150000,
            100000, 100000, 180000, 150000, 130000, 80000}),
    DIESEL(new int[] {-1 , 120000, 130000, 450000, 210000, 150000,
            120000, 100000, 180000, 150000, 140000, 80000}),
    HIBRIDO(new int[] {-1 , 180000, 190000, 700000, 300000, 200000,
            450000, 100000, 210000, 180000, 220000, 80000}),
    ELECTRICO(new int[] {-1 , 220000, 230000, 800000, 300000, 250000,
            0, 100000, 250000, 180000, 0, 80000});

    private final int[] values;

    private ERepValue(int[] values){
        this.values = values;
    }

    public int[] getValues(){
        return values;
    }
}
