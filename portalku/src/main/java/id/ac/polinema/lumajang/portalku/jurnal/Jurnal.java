package id.ac.polinema.lumajang.portalku.jurnal;

public class Jurnal {

    private Integer id;
    private String judul;
    private String penerbit;
    private Integer tahunTerbit;

    public Jurnal() {
    }

    public Jurnal(Integer id, String judul, String penerbit, Integer tahunTerbit) {
        this.id = id;
        this.judul = judul;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getPenerbit() { return penerbit; }
    public void setPenerbit(String penerbit) { this.penerbit = penerbit; }

    public Integer getTahunTerbit() { return tahunTerbit; }
    public void setTahunTerbit(Integer tahunTerbit) { this.tahunTerbit = tahunTerbit; }
}
