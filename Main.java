import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static GoDriveRentalSystem sistem = new GoDriveRentalSystem();

    public static void main(String[] args) {
        sistem.tambahKendaraan(new Mobil("MBL01", "Toyota Avanza", 350000, 7));
        sistem.tambahKendaraan(new Mobil("MBL02", "Daihatsu Sigra", 300000, 7));
        sistem.tambahKendaraan(new Mobil("MBL03", "Honda Brio", 280000, 5));
        sistem.tambahKendaraan(new Motor("MTR01", "Honda Vario", 80000, "Matic"));
        sistem.tambahKendaraan(new Motor("MTR02", "Yamaha NMAX", 100000, "Matic"));
        sistem.tambahKendaraan(new Motor("MTR03", "Kawasaki KLX", 90000, "Manual"));

        int pilihan;

        do {
            tampilMenu();
            System.out.print("Pilih menu: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Input tidak valid. Pilih menu: ");
                scanner.next();
            }

            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tambahKendaraan();
                    break;
                case 2:
                    sistem.tampilkanDaftarKendaraan();
                    break;
                case 3:
                    sewaKendaraan();
                    break;
                case 4:
                    kembalikanKendaraan();
                    break;
                case 5:
                    System.out.println("Terima kasih telah menggunakan GO DRIVE. Sampai jumpa!");
                    break;
                default:
                    System.out.println("[ERROR] Pilihan tidak valid. Silakan pilih 1-5.");
            }

            System.out.println();
        } while (pilihan != 5);

        scanner.close();
    }

    static void tampilMenu() {
        System.out.println("====== MENU GO DRIVE RENTAL SYSTEM ======");
        System.out.println("1. Tambah Kendaraan");
        System.out.println("2. Tampilkan Daftar Armada");
        System.out.println("3. Sewa Kendaraan");
        System.out.println("4. Kembalikan Kendaraan");
        System.out.println("5. Keluar");
    }

    static void tambahKendaraan() {
        System.out.print("Masukkan jenis kendaraan (mobil/motor): ");
        String jenis = scanner.nextLine().trim().toLowerCase();

        System.out.print("Masukkan kode kendaraan: ");
        String kode = scanner.nextLine().trim().toUpperCase();

        System.out.print("Masukkan nama kendaraan: ");
        String nama = scanner.nextLine().trim();

        System.out.print("Masukkan harga sewa per hari: ");
        double harga;

        try {
            harga = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Harga tidak valid.");
            return;
        }

        if (jenis.equals("mobil")) {
            System.out.print("Masukkan kapasitas kursi: ");

            try {
                int kursi = Integer.parseInt(scanner.nextLine().trim());
                sistem.tambahKendaraan(new Mobil(kode, nama, harga, kursi));
                System.out.println("[INFO] Kendaraan berhasil ditambahkan: " + nama + " (" + kode + ")");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Jumlah kursi tidak valid.");
            }

        } else if (jenis.equals("motor")) {
            System.out.print("Masukkan jenis transmisi (Matic/Manual): ");
            String transmisi = scanner.nextLine().trim();

            sistem.tambahKendaraan(new Motor(kode, nama, harga, transmisi));
            System.out.println("[INFO] Kendaraan berhasil ditambahkan: " + nama + " (" + kode + ")");

        } else {
            System.out.println("[ERROR] Jenis tidak dikenal. Gunakan 'mobil' atau 'motor'.");
        }
    }

    static void sewaKendaraan() {
        sistem.tampilkanDaftarKendaraan();

        System.out.print("\nMasukkan kode kendaraan yang ingin disewa: ");
        String kode = scanner.nextLine().trim().toUpperCase();

        System.out.print("Masukkan durasi sewa (dalam hari): ");
        int lama;

        try {
            lama = Integer.parseInt(scanner.nextLine().trim());

            if (lama <= 0) {
                System.out.println("[ERROR] Durasi harus > 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Durasi tidak valid.");
            return;
        }

        System.out.print("Apakah Anda Member VIP? (y/n): ");
        boolean isVIP = scanner.nextLine().trim().equalsIgnoreCase("y");

        try {
            sistem.sewaKendaraan(kode, lama, isVIP);
        } catch (KendaraanTidakTersediaException e) {
            e.printStackTrace();
        }
    }

    static void kembalikanKendaraan() {
        sistem.tampilkanDaftarKendaraan();

        System.out.print("\nMasukkan kode kendaraan yang ingin dikembalikan: ");
        String kode = scanner.nextLine().trim().toUpperCase();

        sistem.kembalikanKendaraan(kode);
    }
}
