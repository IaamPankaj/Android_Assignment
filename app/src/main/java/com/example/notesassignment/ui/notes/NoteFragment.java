package com.example.notesassignment.ui.notes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.notesassignment.custom_rv_adapters.NoteRVAdapter;
import com.example.notesassignment.databinding.FragmentHomeBinding;
import com.example.notesassignment.models.Note;
import com.example.notesassignment.mydb.MyDatabase;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class NoteFragment extends Fragment {

    MyDatabase myDatabase;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        myDatabase = new MyDatabase(requireContext());

        if (myDatabase.readAll().size()!=0)
        {
            Log.d("TAG", "readAll: "+myDatabase.readAll().size());
            binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
            NoteRVAdapter noteRVAdapter = new NoteRVAdapter((ArrayList<Note>) myDatabase.readAll(), requireContext());
            binding.recyclerview.setAdapter(noteRVAdapter);
            // Add a new item to the data source

// Notify the adapter of the data change
            noteRVAdapter.notifyItemInserted(myDatabase.readAll().size() - 1);

// Optionally scroll to the newly added item
//            binding.recyclerview.scrollToPosition(myDatabase.readAll().size() - 1);

        }
        else {
            Toast.makeText(requireContext(), "No Data", Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    //run
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}