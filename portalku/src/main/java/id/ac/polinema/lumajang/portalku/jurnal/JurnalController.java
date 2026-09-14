package id.ac.polinema.lumajang.portalku.jurnal;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jurnal")
public class JurnalController {

    private final JurnalService service;

    public JurnalController(JurnalService service) {
        this.service = service;
    }

    @GetMapping
    public List<Jurnal> semua() {
        return service.cariSemua();
    }

    @GetMapping("/{id}")
    public Jurnal satu(@PathVariable Integer id) {
        return service.cariSatu(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Jurnal tambah(@RequestBody Jurnal jurnal) {
        return service.tambah(jurnal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hapus(@PathVariable Integer id) {
        service.hapus(id);
    }
}
