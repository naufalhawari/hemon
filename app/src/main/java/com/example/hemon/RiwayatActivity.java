package com.example.hemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hemon.databinding.ActivityRiwayatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class RiwayatActivity extends AppCompatActivity {

    ActivityRiwayatBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private PreferenceManager preferenceManager;

    private ArrayList<String> jenisList;
    private ArrayList<String> diagnosisList;
    private ArrayList<String> tanggalList;

    private RiwayatAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRiwayatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        jenisList = new ArrayList<>();
        diagnosisList = new ArrayList<>();
        tanggalList = new ArrayList<>();
        adapter = new RiwayatAdapter(jenisList, diagnosisList, tanggalList, getApplicationContext());
        binding.riwayatRecycler.setAdapter(adapter);

        binding.backButton.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            this.finish();
        });

        binding.riwayatRecycler.setLayoutManager(new LinearLayoutManager(RiwayatActivity.this));

        db.collection("asesmen")
//                .whereEqualTo("emailPengguna", preferenceManager.getString("email"))
                .orderBy("tanggalAsesmen", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult().getDocuments().size() > 0){
                            for (int i = 0; i < task.getResult().getDocuments().size(); i++) {

                                DocumentSnapshot data = task.getResult().getDocuments().get(i);

                                if (data.getString("emailPengguna").equals(preferenceManager.getString("email"))) {
                                    jenisList.add(data.getString("jenisAsesmen"));
                                    diagnosisList.add(data.getString("hasilDiagnosis"));

                                    Date d = data.getTimestamp("tanggalAsesmen").toDate();


                                    tanggalList.add(d.getDate() + "-" + d.getMonth() + "-" + d.getHours() +
                                            " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getMinutes());

//                                adapter = new RiwayatAdapter(jenisList, diagnosisList, tanggalList, getApplicationContext());
//                                binding.riwayatRecycler.setAdapter(adapter);

                                    adapter.notifyDataSetChanged();
                                }

                                
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Anda belum melakukan asesmen.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


//        jenisList.add("oi");
//        diagnosisList.add("mantap");
//        tanggalList.add("Oktober");




    }


}