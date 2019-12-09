package model;

public class Aula {

//    LISTAR
    private int COD_DET_AUL;
    private String EST_DETAUL;
    private String SEMESTRE;
    private String MODULO;
    private String PERSONA;
    private String TIPO;

//    LISTAR AULA
    private int COD_AULA;
    private String EST_AULA;
    private String SEM_AULA;
    private String ABRMOD;
//    LISTAR DOCUMENTO
    private String IDDOC;
    private String CODTIPDOC;
    private String CODDETEMP;
    private String FETDOC;
    
    public int getCOD_DET_AUL() {
        return COD_DET_AUL;
    }

    public void setCOD_DET_AUL(int COD_DET_AUL) {
        this.COD_DET_AUL = COD_DET_AUL;
    }

    public String getEST_DETAUL() {
        return EST_DETAUL;
    }

    public void setEST_DETAUL(String EST_DETAUL) {
        this.EST_DETAUL = EST_DETAUL;
    }

    public String getSEMESTRE() {
        return SEMESTRE;
    }

    public void setSEMESTRE(String SEMESTRE) {
        this.SEMESTRE = SEMESTRE;
    }

    public String getMODULO() {
        return MODULO;
    }

    public void setMODULO(String MODULO) {
        this.MODULO = MODULO;
    }

    public String getPERSONA() {
        return PERSONA;
    }

    public void setPERSONA(String PERSONA) {
        this.PERSONA = PERSONA;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    public int getCOD_AULA() {
        return COD_AULA;
    }

    public void setCOD_AULA(int COD_AULA) {
        this.COD_AULA = COD_AULA;
    }

    public String getEST_AULA() {
        return EST_AULA;
    }

    public void setEST_AULA(String EST_AULA) {
        this.EST_AULA = EST_AULA;
    }

    public String getSEM_AULA() {
        return SEM_AULA;
    }

    public void setSEM_AULA(String SEM_AULA) {
        this.SEM_AULA = SEM_AULA;
    }

    public String getABRMOD() {
        return ABRMOD;
    }

    public void setABRMOD(String ABRMOD) {
        this.ABRMOD = ABRMOD;
    }

    public String getFETDOC() {
        return FETDOC;
    }

    public void setFETDOC(String FETDOC) {
        this.FETDOC = FETDOC;
    }

    public String getIDDOC() {
        return IDDOC;
    }

    public void setIDDOC(String IDDOC) {
        this.IDDOC = IDDOC;
    }

    public String getCODDETEMP() {
        return CODDETEMP;
    }

    public void setCODDETEMP(String CODDETEMP) {
        this.CODDETEMP = CODDETEMP;
    }

    public String getCODTIPDOC() {
        return CODTIPDOC;
    }

    public void setCODTIPDOC(String CODTIPDOC) {
        this.CODTIPDOC = CODTIPDOC;
    }

}
