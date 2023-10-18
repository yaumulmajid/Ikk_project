package id.go.lan.ikk.seeder;

import id.go.lan.ikk.module.policy.entity.InstrumentAnswerEntity;
import id.go.lan.ikk.module.policy.repository.InstrumentAnswerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 5)
@Slf4j
public class InstrumentAnswerSeeder implements CommandLineRunner {

    @Autowired
    private InstrumentAnswerRepository instrumentAnswerRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            seed();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void seed() {
        if (instrumentAnswerRepository.count() == 0) {
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1aa", "Penentuan masalah berasal dari atas (top down) maupun dari bawah (bottom up), dengan melibatkan stakeholder eksternal dan partisipasi kelompok sasaran"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1ab", "Penentuan masalah dari atas (top down) maupun dari bawah (bottom up)  dengan mempertimbangkan masukan stakeholder eksternal"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1ac", "Penentuan masalah dari atas (top down), melibatkan stakeholder eksternal pemerintah, tanpa partisipasi kelompok sasaran"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1ad", "Penentuan masalah dari atas (top down), hanya ditentukan dari internal instansi pemerintah, tanpa partisipasi stakeholder eksternal dan kelompok sasaran"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1ba", "Berkaitan secara langsung dengan prioritas nasional jangka pendek dan jangka menengah"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1bb", "Berkaitan secara langsung dengan prioritas nasional jangka pendek atau jangka menengah"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1bc", "Berkaitan secara tidak langsung dengan prioritas nasional jangka pendek atau jangka menengah"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1bd", "Tidak berkaitan dengan prioritas nasional dalam jangka pendek atau jangka menengah"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1ca", "Masalah kebijakan berkaitan langsung dengan  kepentingan  masyarakat umum dan kepentingan kelompok tertentu yang memiliki kerentanan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1cb", "Masalah kebijakan berkaitan langsung dengan kepentingan  masyarakat umum atau kepentingan kelompok tertentu yang memiliki kerentanan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1cc", "Masalah berkaitan secara tidak langsung dengan kepentingan masyarakat umum atau kepentingan kelompok tertentu yang memiliki kerentanan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1cd", "Masalah tidak berkaitan dengan kepentingan  masyarakat umum atau kepentingan kelompok tertentu yang memiliki kerentanan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1da", "Perhatian stakeholder/publik sangat intensif terhadap masalah, menimbulkan konflik, dan mendesak instansi mengambil kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1db", "Perhatian stakeholder/publik cukup intensif terhadap masalah, menimbulkan polemik tanpa konlfik, dan mendorong instansi mengambil kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1dc", "Perhatian stakeholder/publik kurang intensif terhadap masalah, polemik rendah, dan mengharapkan instansi mengambil kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a1dd", "Perhatian stakeholder/publik tidak ada terhadap masalah, tidak ada polemik, dan tidak urgen bagi instansi untuk mengambil kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a2aa", "Isu ditentukan dengan konsensus aspirasi seluruh stakeholder dan dilakukan analisis kebijakan terkait"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a2ab", "Isu ditentukan dengan konsensus aspirasi beberapa stakeholder dan dilakukan analisis kebijakan terkait"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a2ac", "Isu ditentukan dengan konsensus aspirasi beberapa stakeholder namun tanpa dilakukan analisis kebijakan terkait"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a2ad", "Isu ditentukan tanpa dengan konsensus aspirasi stakeholder dan tanpa dilakukan analisis kebijakan terkait"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a2ba", "Belum ada kebijakan yang menjadi acuan untuk mengatasi masalah"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a2bb", "Ada kebijakan namun masih bersifat umum dan belum mampu mengatasi masalah"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a2bc", "Ada kebijakan yang masih berlaku, tetapi belum secara optimal diimplementasikan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a2bd", "Ada kebijakan yang masih berlaku, namun tidak dapat diimplementasikan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a2ca", "Tekanan dari kelompok legislatif atau kelompok berpengaruh di luar instansi"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a2cb", "Adanya temuan dari suatu kajian atau publikasi ilmiah terkait masalah"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a2cc", "Terjadi berulang dalam kurun waktu tertentu sebelum adanya kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("a2cd", "Muncul dalam arus utama di media massa saat wacana kebijakan berkembang"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b1aa", "Tujuan kebijakan dinyatakan secara eksplisit dan mudah dipahami"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b1ab", "Tujuan kebijakan dinyatakan secara eksplisit  namun kurang dapat dipahami"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b1ac", "Tujuan kebijakan dinyatakan secara eksplisit namun tidak dapat dipahami"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b1ad", "Tidak terdapat tujuan kebijakan secara eksplisit"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b1ba", "Semua Opsi kebijakan telah dikaji kemanfaatannya"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b1bb", "Sebagian opsi kebijakan telah dikaji kemanfaatannya"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b1bc", "Hanya satu opsi kebijakan yang dikaji kemanfaatannya"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b1bd", "Tidak ada opsi yang dikaji kemanfaatannya"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b2aa", "Seluruh isi kebijakan dapat menjawab permasalahan dan tantangan saat ini dan ke depan dengan baik"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b2ab", "Sebagian besar isi kebijakan dapat menjawab permasalahan dan tantangan saat ini dan ke depan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b2ac", "Hanya sebagian kecil isi kebijakan dapat menjawab permasalahan dan tantangan saat ini dan kedepan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b2ad", "Kebijakan tidak dapat menjawab permasalahan saat ini maupun ke depan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b2ba", "Terdapat strategi penangan terhadap semua resiko yang telah dipetakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b2bb", "Terdapat strategi penanganan terhadap sebagian besar resiko yang telah dipetakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b2bc", "Terdapat strategi penanganan terhadap beberapa resiko yang telah dipetakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b2bd", "Tidak terdapat analisis strategi penanganan resiko kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b3aa", "Rumusan kebijakan memenuhi seluruh kebutuhan stakeholder dan mempertimbangkan seluruh lingkungan eksternal kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b3ab", "Rumusan kebijakan memenuhi sebagian besar kebutuhan stakeholder dan mempertimbangkan sebagian besar lingkungan eksternal kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b3ac", "Rumusan kebijakan memenuhi sebagian kecil kebutuhan stakeholder dan mempertimbangkan sebagian kecil lingkungan eksternal kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b3ad", "Rumusan kebijakan tidak memenuhi kebutuhan stakeholder dan tidak mempertimbangkan lingkungan eksternal kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b3ba", "Seluruh materi rancangan kebijakan dilakukan konsultasi kepada seluruh stakeholder dan pihak yang akan terdampak"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b3bb", "Seluruh materi rancangan kebijakan dilakukan konsultasi kepada sebagian besar stakeholder dan pihak yang akan terdampak"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b3bc", "Beberapa bagian materi rancangan kebijakan dilakukan konsultasi kepada beberapa stakeholder dan pihak yang akan terkena dampak"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b3bd", "Rancangan kebijakan tidak dilakukan konsulstasi dengan stakeholder dan pihak yang akan terdampak"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b3ca", "Semua kelompok rentan dipertimbangkan dalam rancangan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b3cb", "Sebagian besar kelompok rentan dipertimbangkan dalam rancangan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b3cc", "Sebagian kecil kelompok rentan dipertimbangan dalam rancangan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b3cd", "Tidak mempertimbangkan kelompok rentan dalam rancangan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b4aa", "Perumusan kebijakan didukung adanya kajian dan analisis yang sangat memadai"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b4ab", "Perumusan kebijakan didukung adanya kajian dan analisis yang cukup memadai"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b4ac", "Perumusan kebijakan didukung adanya kajian dan analisis yang kurang memadai"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b4ad", "Perumusan kebijakan didukung adanya kajian dan analisis yang kurang memadai"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b4ba", "Kajian dan analisis untuk perumusan kebijakan didukung bukti yang sangat memadai"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b4bb", "Kajian dan analisis untuk perumusan kebijakan didukung bukti yang cukup memadai"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b4bc", "Kajian dan analisis untuk perumusan kebijakan didukung sedikit bukti"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b4bd", "Kajian dan analisis untuk perumusan kebijakan tanpa ada dukungan bukti sama sekali"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b4ca", "Semua terdokumentasi dengan baik"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b4cb", "Sebagian besar terdokumentasi dengan baik"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b4cc", "Sebagian kecil terdokumentasi dengan baik"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b4cd", "Tidak ada dokumentasi"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b5aa", ">80%  alternatif merupakan solusi baru terhadap permasalahan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b5ab", ">40% - 80%  alternatif merupakan solusi baru terhadap permasalahan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b5ac", "<40% alternatif merupakan solusi baru terhadap permasalahan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b5ad", "Tidak ada alternatif solusi baru terhadap permasalahan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b5ba", ">5 aspek yang memberikan nilai tambah/manfaat baru bagi stakeholder dari kebijakan yang ada"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b5bb", "2-5 aspek yang memberikan nilai tambah/manfaat baru bagi stakeholder dari kebijakan yang ada"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b5bc", "Hanya 1 aspek yang memberikan nilai tambah/manfaat baru bagi stakeholder dari kebijakan yang ada"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b5bd", "Tidak ada nilai tambah/manfaat baru bagi stakeholder dari kebijakan yang ada"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b5ca", ">80% Cara/metode implementasi  kebijakan berbeda sama sekali dengan yang lainnya"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b5cb", "Terdapat cukup banyak unsur kebaruan dalam cara/metode implementasi"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b5cc", "Terdapat beberapa unsur kebaruan dalam cara/metode implementasi"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("b5cd", "Tidak ada unsur kebaruan dalam cara/metode implementasi"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1aa", "Dilakukan uji coba/piloting dengan lingkup relatif lebih luas dan waktu yang lama"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1ab", "Dilakukan uji coba/piloting dengan lingkup  dan waktu yang cukup"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1ac", "Dilakukan uji coba/piloting dengan lingkup dan waktu yang sempit/terbatas"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1ad", "Tidak dilakukan uji coba/piloting"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1ba", "Terdapat kejelasan unit kerja/kelompok kerja/tim  yang  berwenang dan bertanggungjawab terhadap implementasi kebijakan dan sesuai dengan kebutuhan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1bb", "Terdapat  unit kerja/kelompok kerja/tim  yang  berwenang dan bertanggungjawab terhadap implementasi kebijakan namun tidak sesuai dengan kebutuhan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1bc", "Terdapat  unit kerja/kelompok kerja/tim  yang mengimplementasikan kebijakan namun tidak jelas kewenangan dan tanggungjawabnya"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1bd", "Tidak terdapat unit kerja/kelompok kerja/tim yang  berwenang dan bertanggungjawab terhadap implementasi kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1ca", "Terdapat strategi implementasi kebijakan yang jelas dengan memuat indikator target keberhasilan implementasi"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1cb", "Terdapat strategi implementasi kebijakan yang jelas, namun belum memuat indikator target keberhasilan implementasi"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1cc", "Terdapat strategi implementasi kebijakan namun tidak jelas dan belum memuat indikator target keberhasilan implementasi"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1cd", "Tidak terdapat strategi implementasi kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1da", "Rencana alokasi sumber daya manusia dan anggaran yang sangat sesuai dengan kebutuhan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1db", "Tersedia rencana alokasi SDM dan anggaran yang cukup sesuai dengan kebutuhan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1dc", "Tersedia rencana alokasi sumber daya manusia dan anggaran namun hanya beberapa yang sesuai dengan kebutuhan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c1dd", "Tidak tersedia rencana alokasi sumber daya manusia dan anggaran"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c2aa", "Terdapat strategi komunikasi kebijakan dengan mempertimbangkan seluruh target audience/Pemangku kepentingan yang terkait"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c2ab", "Terdapat strategi komunikasi kebijakan dengan mempertimbangkan beberapa target audience/Pemangku kepentingan yang terkait"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c2ac", "Terdapat strategi komunikasi kebijakan namun tidak mempertimbangkan target audience/Pemangku kepentingan yang terkait"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c2ad", "Tidak terdapat strategi komunikasi kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c2ba", ">9 kali"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c2bb", "7-9 kali"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c2bc", "4-6 kali"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c2bd", "1-3 kali"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c2ca", "Terdapat 6 atau lebih  jenis media komunikasi"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c2cb", "Terdapat 4-5 jenis media komunikasi"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c2cc", "Terdapat 2-3 jenis media komunikasi kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c2cd", "Terdapat 1 (satu) jenis media komunikasi kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c3aa", "Setiap bulan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c3ab", "Setiap triwulan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c3ac", "Setiap semester"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c3ad", "Setiap tahun"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c3ba", "Kegiatan monitoring mencakup seluruh ruang lingkup dalam strategi implementasi yang disusun, termasuk alokasi SDM dan anggaran"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c3bb", "Kegiatan monitoring mencakup sebagian besar ruang lingkup dalam  strategi implementasi yang disusun"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c3bc", "Kegiatan monitoring mencakup sebagaian kecil ruang lingkup strategi implementasi yang disusun"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c3bd", "Tidak dilakukan kegiatan monitoring implementasi kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c3ca", ">80% hasil monitoring implementasi kebijakan ditindaklanjuti"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c3cb", ">40% - 80%  hasil monitoring implementasi kebijakan ditindaklanjuti"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c3cc", "<40% hasil monitoring implementasi kebijakan ditindaklanjuti"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("c3cd", "Tidak ada tindak lanjut"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d1aa", "Evaluasi terhadap pencapaian seluruh tujuan kebijakan secara terukur"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d1ab", "Evaluasi terhadap pencapaian sebagian besar tujuan kebijakan secara terukur"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d1ac", "Evaluasi terhadap pencapaian sebagian kecil tujuan kebijakan secara terukur"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d1ad", "Belum dilakukan evaluasi terhadap pencapaian tujuan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d1ba", "Telah dilakukan evaluasi terhadap seluruh metode/strategi implementasi kebijakan dalam mencapai tujuan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d1bb", "Telah dilakukan evaluasi terhadap sebagian besar metode/strategi implementasi kebijakan dalam mencapai tujuan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d1bc", "Telah dilakukan evaluasi terhadap sebagian kecil metode/strategi implementasi kebijakan dalam mencapai tujuan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d1bd", "Tidak dilakukan evaluasi terhadap kesesuaian metode/strategi implementasi kebijakan dengan tujuan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d2aa", "Evaluasi atas efisiensi kebijakan terukur disertai dengan kejelasan tindak lanjut yang diperlukan untuk perbaikan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d2ab", "Evaluasi atas efisiensi kebijakan terukur namun tidak disertai kejelasan tindaklanjut yang diperlukan untuk perbaikan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d2ac", "Evaluasi atas efisiensi kebijakan tidak terukur"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d2ad", "Belum dilakukan evaluasi atas efisiensi kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d2ba", "Dilakukan upaya efisiensi terhadap seluruh aspek sumber daya dalam pencapaian tujuan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d2bb", "Dilakukan upaya efisiensi terhadap beberapa aspek sumber daya dalam pencapaian tujuan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d2bc", "Dilakukan upaya efisiensi terhadap salah satu aspek sumber daya dalam pencapaian tujuan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d2bd", "Tidak dilakukan upaya efisiensi sumberdaya dalam pencapaian tujuan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3aa", "Seluruh atau sebagian besar kelompok sasaran, termasuk kelompok rentan, memiliki persepsi positif atas keberadaan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3ab", "Sebagian besar kelompok sasaran memiliki persepsi positif atas keberadaan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3ac", "Hanya sebagian kecil kelompok sasaran yang memiliki persepsi positif atas keberadaan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3ad", "Belum dilakukan evaluasi terhadap penerimaan kelompok sasaran atas keberadaan kebijakan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3ba", "Sebagian besar anggota kelompok sasaran menilai kebijakan telah selaras dengan kebutuhan mereka dan telah mendorong lahirnya nilai-nilai positif baru"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3bb", "Sebagian besar anggota kelompok sasaran menilai kebijakan telah selaras dengan kebutuhan mereka namun tidak mendorong lahirnya nilai-nilai positif baru"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3bc", "Sebagian kecil anggota kelompok sasaran menilai kebijakan telah selaras dengan kebutuhan mereka"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3bd", "Belum dilakukan evaluasi terhadap responsivitas kebijakan atas kebutuhan kelompok sasaran"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3ca", "Hasil kebijakan telah memenuhi preferensi kelompok sasaran dimana seluruh kelompok sasaran mengalami peningkatan utilitas/welfare"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3cb", "Hasil kebijakan telah memenuhi preferensi kelompok sasaran dimana sebagian besar kelompok sasaran mengalami peningkatan utilitas/welfare"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3cc", "Hasil kebijakan telah memenuhi preferensi kelompok sasaran dimana sebagian kecil kelompok sasaran mengalami peningkatan utilitas/welfare kelompok sasaran"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3cd", "Belum dilakukan evaluasi atas pengaruh hasil kebijakan terhadap peningkatan utilitas/welfare kelompok sasaran"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3da", "Hasil kebijakan telah memberikan dampak terhadap seluruh kelompok rentan sesuai dengan tujuan yang telah ditetapkan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3db", "Hasil kebijakan memberikan dampak terhadap sebagian kelompok rentan sesuai dengan tujuan yang telah ditetapkan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3dc", "Hasil kebijakan belum memberikan dampak terhadap kelompok rentan sesuai dengan tujuan yang telah ditetapkan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3dd", "Belum dilakukan evaluasi atas pengaruh hasil kebijakan terhadap kelompok rentan"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3ea", "Kebijakan telah meningkatkan seluruh kualitas penggunaan sumber daya dan proses kerja penyelesaian masalah menjadi lebih efisien"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3eb", "Kebijakan telah meningkatkan sebagian besar kualitas penggunaan sumber daya dan proses kerja penyelesaian masalah menjadi lebih efisien"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3ec", "Kebijakan telah meningkatkan sebagian kecil kualitas penggunaan sumber daya dan proses kerja penyelesaian masalah menjadi lebih efisien"));
            instrumentAnswerRepository.save(new InstrumentAnswerEntity("d3ed", "Belum dilakukan evaluasi atas pengaruh kebijakan terhadap peningkatan kualitas penggunaan sumberdaya dan proses kerja"));
        }
    }
}
