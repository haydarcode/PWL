package id.ac.polinema.lumajang.portalku.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class InfoController {
    private final AppProperties app;
    @Value("${spring.profiles.active:default}")
    private String profilAktif;

    @GetMapping
    public Map<String, Object> info() {
        Map<String, Object> hasil = new LinkedHashMap<>();
        hasil.put("nama", app.getNama());
        hasil.put("versi", app.getVersi());
        hasil.put("profil", profilAktif);
        hasil.put("sambutan", app.getPesanSambutan());
        hasil.put("maksItem", app.getMaksItem());
        return hasil;
    }
}