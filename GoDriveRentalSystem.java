import java.util.ArrayList;

public class GoDriveRentalSystem {
    private ArrayList<Kendaraan> daftarKendaraan;

    public GoDriveRentalSystem() {
        daftarKendaraan = new ArrayList<>();
    }

    public void tambahKendaraan(Kendaraan k) {
        daftarKendaraan.add(k);
    }

    public void tampilkanDaftarKendaraan() {
        if (daftarKendaraan.isEmpty()) {
            System.out.println("[INFO] Belum ada kendaraan dalam armada.");
            return;
        }
        System.out.println("\n=== DAFTAR ARMADA GODRIVE ===");
        int nomor = 1;
        for (Kendaraan k : daftarKendaraan) {
            System.out.print(nomor + ". ");
            k.tampilInfo();
            nomor++;
        }
    }

    public void sewaKendaraan(String kode, int lamaSewa, boolean isVIP) throws KendaraanTidakTersediaException {
        Kendaraan target = cariKendaraan(kode);

        if (target == null || !target.isTersedia()) {
            throw new KendaraanTidakTersediaException(
                    "Kendaraan dengan kode " + kode + " gagal disewa. Alasan: Kendaraan sedang disewa atau tidak ditemukan!");
        }

        target.setTersedia(false);

        System.out.println("\n=== TRANSAKSI SEWA GODRIVE ===");
        System.out.println("Kendaraan Berhasil Disewa!");
        System.out.printf("%-20s: %s (%s)%n", "Unit", target.getNamaKendaraan(), target.getKodeKendaraan());
        System.out.printf("%-20s: %d hari%n", "Lama Sewa", lamaSewa);

        double biayaDasar = lamaSewa * target.getHargaSewaPerHari();
        double totalBiaya = target.hitungBiayaDasar(lamaSewa);

        System.out.printf("%-20s: Rp %,.0f%n", "Biaya Dasar Harian", biayaDasar);

        if (target instanceof Mobil mobil) {
            double tambahan = mobil.getTambahanKursi();
            if (tambahan > 0)
                System.out.printf("%-20s: Rp %,.0f%n", "Tambahan Kursi (>5)", tambahan);
        } else if (target instanceof Motor motor) {
            double asuransi = motor.getTambahanAsuransi(lamaSewa);
            if (asuransi > 0)
                System.out.printf("%-20s: Rp %,.0f%n", "Asuransi Matik", asuransi);
        }

        double diskon = 0;
        if (isVIP) {
            diskon = totalBiaya * 0.10;
            System.out.printf("%-20s: -Rp %,.0f%n", "Diskon VIP (10%)", diskon);
        }

        double diskonLama = 0;
        if (lamaSewa > 7) {
            diskonLama = totalBiaya * 0.05;
            System.out.printf("%-20s: -Rp %,.0f%n", "Diskon > 7 Hari (5%)", diskonLama);
        }

        System.out.println("------------------------------------------");
        System.out.printf("%-20s: Rp %,.0f%n", "TOTAL BIAYA AKHIR", totalBiaya - diskon - diskonLama);
    }

    public void kembalikanKendaraan(String kode) {
        Kendaraan target = cariKendaraan(kode);
        if (target == null) {
            System.out.println("[ERROR] Kendaraan dengan kode " + kode + " tidak ditemukan.");
            return;
        }
        if (target.isTersedia()) {
            System.out.println("[ERROR] Kendaraan " + target.getNamaKendaraan() + " belum disewa.");
            return;
        }
        target.setTersedia(true);
        System.out.println("[INFO] Kendaraan " + target.getNamaKendaraan() + " (" + kode + ") berhasil dikembalikan. Status: Tersedia.");
    }

    private Kendaraan cariKendaraan(String kode) {
        for (Kendaraan k : daftarKendaraan)
            if (k.getKodeKendaraan().equalsIgnoreCase(kode))
                return k;
        return null;
    }
}