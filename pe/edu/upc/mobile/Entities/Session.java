package pe.edu.upc.mobile.Entities;

public class Session {
    private String CodAlumno;
    private String Token;

    public String getCodAlumno() {
        return this.CodAlumno;
    }

    public void setCodAlumno(String codAlumno) {
        this.CodAlumno = codAlumno;
    }

    public String getToken() {
        return this.Token;
    }

    public void setToken(String token) {
        this.Token = token;
    }
}
