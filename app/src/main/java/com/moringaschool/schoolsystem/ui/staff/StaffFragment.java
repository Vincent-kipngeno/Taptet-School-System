package com.moringaschool.schoolsystem.ui.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.ui.StaffDetailsActivity;
import com.moringaschool.schoolsystem.ui.StaffRegistrationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StaffFragment extends Fragment {

    private StaffViewModel staffViewModel;

    @BindView(R.id.staff_list) RecyclerView staffList;

    private DatabaseReference StaffRef, UsersRef;
    private FirebaseAuth mAuth;
    private String currentUserID="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        staffViewModel = ViewModelProviders.of(this).get(StaffViewModel.class);
        View view = inflater.inflate(R.layout.fragment_staff, container, false);

        ButterKnife.bind(this,view);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        StaffRef = FirebaseDatabase.getInstance().getReference().child("Staff");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        staffList.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.students_fragment_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_addStudent) {
            Intent intent = new Intent(getActivity(), StaffRegistrationActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    public void fillStudentsRecyclerView () {
        FirebaseRecyclerOptions<Boolean> options =
                new FirebaseRecyclerOptions.Builder<Boolean>()
                        .setQuery(StaffRef, Boolean.class)
                        .build();


        FirebaseRecyclerAdapter<Boolean, StaffViewHolder> adapter =
                new FirebaseRecyclerAdapter<Boolean, StaffViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final StaffViewHolder holder, int position, @NonNull Boolean model)
                    {
                        final String staffID = getRef(position).getKey();

                        UsersRef.child(staffID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot)
                            {
                                if (dataSnapshot.exists())
                                {
                                    final String name = dataSnapshot.child("name").getValue().toString();
                                    final String sTscNo = dataSnapshot.child("tscNo").getValue().toString();
                                    final String type = dataSnapshot.child("type").getValue().toString();

                                    holder.staffName.setText(name);
                                    holder.tscNo.setText(sTscNo);
                                    holder.staffType.setText(type);
                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Intent staffIntent = new Intent(getContext(), StaffDetailsActivity.class);
                                            staffIntent.putExtra("visit_user_id", staffID);
                                            startActivity(staffIntent);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stutdent_custom_layout, viewGroup, false);
                        return new StaffViewHolder(view);
                    }
                };

        staffList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class  StaffViewHolder extends RecyclerView.ViewHolder
    {
        TextView tscNo, staffName, staffType;


        public StaffViewHolder(@NonNull View itemView)
        {
            super(itemView);

            staffName = itemView.findViewById(R.id.studentName);
            tscNo = itemView.findViewById(R.id.className);
            staffType = itemView.findViewById(R.id.attendance);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            fillStudentsRecyclerView();
        }
    }
}