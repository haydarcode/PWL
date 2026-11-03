package com.portalpolinema.backend.berita;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.portalpolinema.backend.approvalRequest.ApprovalRequest;
import com.portalpolinema.backend.approvalRequest.ApprovalRequestRepository;
import com.portalpolinema.backend.approvalRequest.dto.ApprovalRequestList;
import com.portalpolinema.backend.shared.*;
import com.portalpolinema.backend.user.*;
import com.portalpolinema.backend.user.dto.UserDto;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeritaService {
    private final ApprovalRequestRepository repo;
    private final BeritaRepository pr;
    private final PayloadParser payloadParser;
    private final UserRepository userRepo;

    public List<Berita> getAll() {
        return pr.findAll();
    }

    public Berita getById(int id) {
        return pr.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Berita tidak ditemukan"));
    }

    public Berita getBySlug(String slug) {
        return pr.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Berita tidak ditemukan"));
    }

    public int incrementView(int id) {
        Berita berita = getById(id);
        berita.setView(berita.getView() + 1);
        pr.save(berita);
        return berita.getView();
    }


    // ===== CRUD langsung (butuh login) =====

    public Berita create(BeritaRequest req) {
        validate(req);
        Berita b = new Berita();
        apply(b, req);
        b.setView(0);
        return pr.save(b);
    }

    public Berita update(int id, BeritaRequest req) {
        validate(req);
        Berita b = getById(id);
        apply(b, req);
        return pr.save(b);
    }

    public void delete(int id) {
        Berita b = getById(id);
        pr.delete(b);
    }

    private void validate(BeritaRequest req) {
        if (req == null || isBlank(req.getJudul()) || isBlank(req.getDeskripsi())
                || req.getKategori() == null || req.getTanggal() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "judul, deskripsi, kategori, dan tanggal wajib diisi");
        }
        if (req.getJudul().trim().length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "judul maksimal 100 karakter");
        }
    }

    private void apply(Berita b, BeritaRequest req) {
        String judul = req.getJudul().trim();
        String slug = SlugUtil.slugifyFinal(judul);
        b.setJudul(judul);
        b.setDeskripsi(req.getDeskripsi());
        b.setKategori(req.getKategori());
        b.setTanggal(req.getTanggal());
        b.setSlug(slug);

        String file = req.getFile();
        if (isBlank(file)) {
            String shortSlug = slug.length() > 80 ? slug.substring(0, 80) : slug;
            file = "/uploads/berita/" + shortSlug + ".jpg";
        }
        b.setFile(file);
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    // get all berita
    public List<ApprovalRequestList> getAllBerita(String search, String date, String category) {
        List<ApprovalRequest> list = repo.findAll();

        list = list.stream().filter(ar -> {
            JsonNode p = payloadParser.parse(ar.getPayload(), ar.getId_approval_request());
            String judul = p.path("judul").asText("").toLowerCase();
            String kategori = p.path("kategori").asText("").toLowerCase();
            String tanggalStr = p.path("tanggal").asText("");

            boolean matchJudul = true;
            boolean matchTanggal = true;
            boolean matchKategori = true;

            if (category != null && !category.isBlank()) {
                matchKategori = kategori.equals(category.toLowerCase());
            }
            if (search != null && !search.isBlank()) {
                matchJudul = judul.contains(search.toLowerCase());
            }

            if (date != null && !date.isBlank()) {
                try {
                    LocalDate filterDate = LocalDate.parse(date);
                    LocalDate tanggal = LocalDate.parse(tanggalStr);
                    matchTanggal = tanggal.equals(filterDate);
                } catch (DateTimeParseException e) {
                    matchTanggal = false;
                }
            }

            return matchJudul && matchTanggal && matchKategori;
        }).toList();

        // filter user + map
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findByEmail(email).orElseThrow();
        Integer idUser = currentUser.getId_user();

        return list.stream()
                .filter(ar -> "berita".equalsIgnoreCase(ar.getNama_tabel()) && idUser.equals(ar.getUser().getId_user()))
                .map(ar -> new ApprovalRequestList(
                        ar.getId_approval_request(),
                        ar.getNama_tabel(),
                        ar.getTargetId(),
                        ar.getAction().name(),
                        ar.getStatus().name(),
                        payloadParser.parse(ar.getPayload(), ar.getId_approval_request()),
                        new UserDto(ar.getUser().getId_user(), ar.getUser().getNama())))
                .toList();
    }

}
