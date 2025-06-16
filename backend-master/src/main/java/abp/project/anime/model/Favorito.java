package abp.project.anime.model;

public class Favorito {
    private int iduser;

    private int idanime;

    public Favorito() {
    }

    public Favorito(int iduser, int idanime) {
        this.iduser = iduser;
        this.idanime = idanime;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdanime() {
        return idanime;
    }

    public void setIdanime(int idanime) {
        this.idanime = idanime;
    }

    @Override
    public String toString() {
        return "Favorito{" +
                "iduser=" + iduser +
                ", idanime=" + idanime +
                '}';
    }
}
