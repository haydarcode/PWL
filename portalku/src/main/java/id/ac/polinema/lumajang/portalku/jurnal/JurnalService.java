package id.ac.polinema.lumajang.portalku.jurnal;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JurnalService {

    private final JurnalRepository repository;

    public JurnalService(JurnalRepository repository) {
        this.repository = repository;
    }

    public List<Jurnal> cariSemua() {
        return repository.findAll();
    }

    public Jurnal cariSatu(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new JurnalTidakDitemukanException(id));
    }

    public Jurnal tambah(Jurnal jurnal) {
        jurnal.setId(null);
        return repository.save(jurnal);
    }

    public void hapus(Integer id) {
        if (!repository.deleteById(id)) {
            throw new JurnalTidakDitemukanException(id);
        }
    }
}
