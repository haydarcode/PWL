package id.ac.polinema.lumajang.portalku.jurnal;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class InMemoryJurnalRepository implements JurnalRepository {

    private final ConcurrentHashMap<Integer, Jurnal> data = new ConcurrentHashMap<>();
    private final AtomicInteger sequence = new AtomicInteger(0);

    public InMemoryJurnalRepository() {
        save(new Jurnal(null, "JISEBI", "Universitas Airlangga", 2015));
        save(new Jurnal(null, "Jurnal Informatika Polinema", "Politeknik Negeri Malang", 2015));
    }

    @Override
    public List<Jurnal> findAll() {
        return data.values().stream().toList();
    }

    @Override
    public Optional<Jurnal> findById(Integer id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Jurnal save(Jurnal jurnal) {
        if (jurnal.getId() == null) {
            jurnal.setId(sequence.incrementAndGet());
        } else {
            sequence.updateAndGet(current -> Math.max(current, jurnal.getId()));
        }
        data.put(jurnal.getId(), jurnal);
        return jurnal;
    }

    @Override
    public boolean deleteById(Integer id) {
        return data.remove(id) != null;
    }
}
