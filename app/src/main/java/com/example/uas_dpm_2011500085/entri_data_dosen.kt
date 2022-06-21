package com.example.uas_dpm_2011500085

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class entri_data_dosen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entri_data_dosen)

        val modeEdit = intent.hasExtra("NIDN") && intent.hasExtra("nama") &&
                intent.hasExtra("jabatan") && intent.hasExtra("golongan") &&
                intent.hasExtra("pendidikan") && intent.hasExtra("bidang") &&
                intent.hasExtra("program")
        title = if(modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etkdnidn = findViewById<EditText>(R.id.etKdnidn)
        val etNmdosen = findViewById<EditText>(R.id.etNmdosen)
        val spnjabatan = findViewById<Spinner>(R.id.spnjabatan)
        val spnGolPangkat = findViewById<Spinner>(R.id.spnGolPangkat)
        val rdS2 = findViewById<RadioButton>(R.id.rdS2)
        val rdS3 = findViewById<RadioButton>(R.id.rdS3)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val etBidKeahlian = findViewById<EditText>(R.id.etBidKeahlian)
        val etProgStudi = findViewById<EditText>(R.id.etProgStudi)
        val etjabatan = arrayOf("Tenaga Pengajar","Asisten Ahli","Lektor","Lektor Kepala","Guru Besar")
        val golPangkat = arrayOf("III/a - Penata Muda","III/b - Penata Muda Tingkat I","III/c - Penata","III/d - Penata Tingkat I",
            "IV/a - Pembina","IV/b - Pembina Tingkat I","IV/c - Pembina Utama Muda","IV/d - Pembina Utama Madya",
            "IV/e - Pembina Utama")
        val adpGolPangkat = ArrayAdapter(
            this@entri_data_dosen,
            android.R.layout.simple_spinner_dropdown_item,
            golPangkat
        )
        spnGolPangkat.adapter = adpGolPangkat

        val adpjabatan = ArrayAdapter(
            this@entri_data_dosen,
            android.R.layout.simple_spinner_dropdown_item,
            etjabatan
        )
        spnjabatan.adapter = adpjabatan

        if(modeEdit) {
            val kdnidn = intent.getStringExtra("NIDN")
            val nama = intent.getStringExtra("nama")
            val jabatan = intent.getStringExtra("jabatan")
            val GolPangkat = intent.getStringExtra("GolPangkat")
            val pendidikan= intent.getStringExtra("pendidikan")
            val keahlian = intent.getStringExtra("bidang")
            val studi= intent.getStringExtra("program")

            etkdnidn.setText(kdnidn)
            etNmdosen.setText(nama)
            spnjabatan.setSelection(etjabatan.indexOf(jabatan))
            spnGolPangkat.setSelection(golPangkat.indexOf(GolPangkat))
            if(pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etBidKeahlian.setText(keahlian)
            etProgStudi.setText(studi)
        }
        etkdnidn.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if("${etkdnidn.text}".isNotEmpty() && "${etNmdosen.text}".isNotEmpty()
                && "${etBidKeahlian.text}".isNotEmpty() && "${etProgStudi.text}".isNotEmpty() &&
                (rdS2.isChecked || rdS3.isChecked)) {
                val db = Campuss(this@entri_data_dosen)
                db.NIDN = "${etkdnidn.text}"
                db.nmDosen = "${etNmdosen.text}"
                db.jabatan = spnjabatan.selectedItem as String
                db.GolPangkat = spnGolPangkat.selectedItem as String
                db.PendidikanTerakhir = if(rdS2.isChecked) "S2" else "S3"
                db.BidangKeahlian = "${etBidKeahlian.text}"
                db.ProgramStudi = "${etProgStudi.text}"
                if(if(!modeEdit) db.simpan() else db.ubah("${etkdnidn.text}")) {
                    Toast.makeText(
                        this@entri_data_dosen,
                        "Data Dosen pengampu berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }else
                    Toast.makeText(
                        this@entri_data_dosen,
                        "Data Dosen Pengampu kuliah gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }else
                Toast.makeText(
                    this@entri_data_dosen,
                    "Data Dosen Pengampu belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}