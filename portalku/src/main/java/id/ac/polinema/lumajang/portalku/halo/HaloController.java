package id.ac.polinema.lumajang.portalku.halo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/halo")
public class HaloController {

    @GetMapping
    public String halo() {
        return "Layanan Portalku berjalan.";
    }
}
