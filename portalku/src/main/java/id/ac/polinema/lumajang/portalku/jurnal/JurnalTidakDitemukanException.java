package id.ac.polinema.lumajang.portalku.jurnal;

public class JurnalTidakDitemukanException extends RuntimeException {

    public JurnalTidakDitemukanException(Integer id) {
        super("Jurnal dengan id " + id + " tidak ditemukan");
    }
}
