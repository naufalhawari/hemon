package com.example.hemon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    private ArrayList<String> jenisList;
    private ArrayList<String> diagnosisList;
    private ArrayList<String> tanggalList;
    private Context context;

    public RiwayatAdapter(ArrayList<String> jenisList, ArrayList<String> diagnosisList, ArrayList<String> tanggalList, Context context) {
        this.jenisList = jenisList;
        this.diagnosisList = diagnosisList;
        this.tanggalList = tanggalList;
        this.context = context;
    }

    @androidx.annotation.NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_riwayat, parent, false);


        return new RiwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull RiwayatViewHolder holder, int position) {
        holder.jenisAsesmen.setText(jenisList.get(position));
        holder.diagnosis.setText(diagnosisList.get(position));
        holder.tanggal.setText(tanggalList.get(position));
    }

    @Override
    public int getItemCount() {
        return jenisList.size();
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder {
        private TextView jenisAsesmen, diagnosis, tanggal;

        public RiwayatViewHolder(@NonNull View itemView) {
            super(itemView);

            jenisAsesmen = itemView.findViewById(R.id.jenis_asesmen);
            diagnosis = itemView.findViewById(R.id.diagnosis);
            tanggal = itemView.findViewById(R.id.tanggal);
        }
    }
}
