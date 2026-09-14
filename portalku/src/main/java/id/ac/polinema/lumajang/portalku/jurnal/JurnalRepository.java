package id.ac.polinema.lumajang.portalku.jurnal;

import java.util.List;
import java.util.Optional;

public interface JurnalRepository {
    List<Jurnal> findAll();
    Optional<Jurnal> findById(Integer id);
    Jurnal save(Jurnal jurnal);
    boolean deleteById(Integer id);
}
