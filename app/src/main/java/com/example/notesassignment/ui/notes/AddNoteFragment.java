package com.example.notesassignment.ui.notes;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.notesassignment.R;
import com.example.notesassignment.databinding.FragmentAddNoteBinding;
import com.example.notesassignment.models.Note;
import com.example.notesassignment.mydb.MyDatabase;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;


public class AddNoteFragment extends Fragment {
    FragmentAddNoteBinding binding;
    MyDatabase database;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_note,container,false);

        database = new MyDatabase(requireContext());

//        will data to the local database

        binding.btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });

        Note note = new Note();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            note.setDate(String.valueOf(LocalDate.now()));
        }
        binding.setModel(note);
        // Inflate the layout for this fragment

        return binding.getRoot();
    }

    public void addNote(){
        if (validateNoteDetail())
        {
            database.create(binding.getModel());
            Navigation.findNavController(requireView()).navigate(R.id.nav_notes);
        }
        else Snackbar.make(requireView(), "Please add note",Snackbar.LENGTH_LONG).show();
    }

    private boolean validateNoteDetail() {

        return binding.getModel()!=null;
    }
}