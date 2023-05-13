package com.example.notesassignment.custom_rv_adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesassignment.R;
import com.example.notesassignment.models.Note;
import com.example.notesassignment.mydb.MyDatabase;

import java.util.ArrayList;

public class NoteRVAdapter extends RecyclerView.Adapter<NoteRVAdapter.NoteViewHolder> {
    ArrayList<Note> arrayList;
    Context context;
    MyDatabase myDatabase;

    public NoteRVAdapter(ArrayList<Note> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.note_desgin,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bindNote(arrayList.get(position));
        myDatabase = new MyDatabase(context);
        Log.d("TAG", "List Size: "+arrayList.size());
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                myDatabase.delete(arrayList.get(holder.getAdapterPosition()));
                notifyItemRemoved(holder.getAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView text1,text2,text3,text4;
        LinearLayout parent;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            text3 = itemView.findViewById(R.id.text3);
            text4 = itemView.findViewById(R.id.text4);
        }

        public void bindNote(Note note)
        {
            text1.setText(String.valueOf(note.getId()));
            text2.setText(note.getTitle());
            text3.setText(note.getDate());
            text4.setText(note.getDescription());
        }
    }
}
