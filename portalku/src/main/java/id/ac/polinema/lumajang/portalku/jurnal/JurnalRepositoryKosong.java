package id.ac.polinema.lumajang.portalku.jurnal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class JurnalRepositoryKosong implements JurnalRepository {

    @Override
    public List<Jurnal> findAll() {
        return List.of();
    }

    @Override
    public Optional<Jurnal> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Jurnal save(Jurnal jurnal) {
        jurnal.setId(0);
        return jurnal;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }
}
