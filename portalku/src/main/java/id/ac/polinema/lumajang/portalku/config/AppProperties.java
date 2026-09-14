package id.ac.polinema.lumajang.portalku.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String nama;
    private String versi;
    private Integer maksItem;
    private String pesanSambutan;

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getVersi() { return versi; }
    public void setVersi(String versi) { this.versi = versi; }

    public Integer getMaksItem() { return maksItem; }
    public void setMaksItem(Integer maksItem) { this.maksItem = maksItem; }

    public String getPesanSambutan() { return pesanSambutan; }
    public void setPesanSambutan(String pesanSambutan) { this.pesanSambutan = pesanSambutan; }
}
