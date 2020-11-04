package com.moringaschool.schoolsystem.ui.students;

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
import androidx.lifecycle.Observer;
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
import com.moringaschool.schoolsystem.models.Student;
import com.moringaschool.schoolsystem.ui.StudentRegistrationActivity;
import com.moringaschool.schoolsystem.ui.StudentRegistrationActivity_ViewBinding;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class StudentsFragment extends Fragment {

    private StudentsViewModel studentsViewModel;

    @BindView(R.id.students_list) RecyclerView studentsList;

    private DatabaseReference StudentsRef, UsersRef;
    private FirebaseAuth mAuth;
    private String currentUserID="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        studentsViewModel = ViewModelProviders.of(this).get(StudentsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_students, container, false);

        ButterKnife.bind(this,view);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        StudentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        studentsList.setLayoutManager(new LinearLayoutManager(getContext()));

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
            Intent intent = new Intent(getActivity(), StudentRegistrationActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    public void fillStudentsRecyclerView () {
        FirebaseRecyclerOptions<Student> options =
                new FirebaseRecyclerOptions.Builder<Student>()
                        .setQuery(StudentsRef, Student.class)
                        .build();


        FirebaseRecyclerAdapter<Student, StudentsViewHolder> adapter =
                new FirebaseRecyclerAdapter<Student, StudentsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final StudentsViewHolder holder, int position, @NonNull Student model)
                    {
                        final String studentsIDs = getRef(position).getKey();

                        UsersRef.child(studentsIDs).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot)
                            {
                                if (dataSnapshot.exists())
                                {
                                    final String name = dataSnapshot.child("name").getValue().toString();
                                    final String sClass = dataSnapshot.child("grade").getValue().toString();

                                    holder.studentName.setText(name);
                                    holder.className.setText(sClass);
                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Intent studentIntent = new Intent(getContext(), StudentDetailsActivity.class);
                                            studentIntent.putExtra("visit_user_id", studentsIDs);
                                            startActivity(studentIntent);
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
                    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stutdent_custom_layout, viewGroup, false);
                        return new StudentsViewHolder(view);
                    }
                };

        studentsList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class  StudentsViewHolder extends RecyclerView.ViewHolder
    {
        TextView className, studentName, dayAttendance;


        public StudentsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            studentName = itemView.findViewById(R.id.studentName);
            className = itemView.findViewById(R.id.className);
            dayAttendance = itemView.findViewById(R.id.attendance);
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