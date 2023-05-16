package com.edusoft.model;

public class ResultadoAluno {
    private String COD;
    private String RESULTADO;
    private double MEDIA;

    public ResultadoAluno() {
    }

    public String getCOD() {
        return COD;
    }

    public void setCOD(String COD) {
        this.COD = COD;
    }

    public String getRESULTADO() {
        return RESULTADO;
    }

    public void setRESULTADO(String RESULTADO) {
        this.RESULTADO = RESULTADO;
    }

    public double getMEDIA() {
        return MEDIA;
    }

    public void setMEDIA(double MEDIA) {
        this.MEDIA = MEDIA;
    }
}
