package com.example.tesi.AppPavone;

public class Json {
    private String ita;
    private String fra;
    private String eng;
    private String sug1;
    private String sug2;
    private String sug3;
    private String img;
    private Boolean svolto;

    private String tipo;

    public Json() {
    }

    public void setIta(String ita) {
        this.ita = ita;
    }

    public void setFra(String fra) {
        this.fra = fra;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public void setSug1(String sug1) {
        this.sug1 = sug1;
    }

    public void setSug2(String sug2) {
        this.sug2 = sug2;
    }

    public void setSug3(String sug3) {
        this.sug3 = sug3;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setSvolto(Boolean svolto) {
        this.svolto = svolto;
    }

    @Override
    public String toString() {
        return "Json{" +
                "ita='" + ita + '\'' +
                ", fra='" + fra + '\'' +
                ", eng='" + eng + '\'' +
                ", sug1='" + sug1 + '\'' +
                ", sug2='" + sug2 + '\'' +
                ", sug3='" + sug3 + '\'' +
                ", img='" + img + '\'' +
                ", svolto=" + svolto +
                '}';
    }



    public Json(String ita, String fra, String eng, String sug1, String sug2, String sug3, String img, Boolean svolto) {
        this.ita = ita;
        this.fra = fra;
        this.eng = eng;
        this.sug1 = sug1;
        this.sug2 = sug2;
        this.sug3 = sug3;
        this.img = img;
        this.svolto = svolto;
    }

    public Json(String ita, String fra, String eng, String tipo, String img) {
        this.ita = ita;
        this.fra = fra;
        this.eng = eng;
        this.tipo = tipo;
        this.img = img;
    }

    public String getTipo() {
        return tipo;
    }

    public String getIta() {
        return ita;
    }

    public String getFra() {
        return fra;
    }

    public String getEng() {
        return eng;
    }

    public String getSug1() {
        return sug1;
    }

    public String getSug2() {
        return sug2;
    }

    public String getSug3() {
        return sug3;
    }

    public String getImg() {
        return img;
    }

    public Boolean getSvolto() {
        return svolto;
    }
}
