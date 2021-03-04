package com.example.bottomsheetandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomsheetandroid.models.Student;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomSheet extends BottomSheetDialogFragment {
    public BottomSheet()
    {

    }
    private RecyclerView recyclerView;
    private AdapterRecyclerView adapterRecyclerView;
    private List<Student> studentList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.row_add_item, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        studentList = new ArrayList<>();
        studentList.add(new Student(1, "Đỗ Văn Nam"));
        studentList.add(new Student(2, "Lê Thị Hà"));
        studentList.add(new Student(3, "Trần Quang Hiếu"));
        adapterRecyclerView =new AdapterRecyclerView(studentList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterRecyclerView);

        return  view;

    }
}
