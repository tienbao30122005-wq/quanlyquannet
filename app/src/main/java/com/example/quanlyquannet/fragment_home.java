package com.example.quanlyquannet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseUser;
public class fragment_home extends Fragment {

    private TableLayout layoutAdmin;
    private LinearLayout layoutUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_home,
                container,
                false
        );

        TextView txtUserId = view.findViewById(R.id.textView22);

        FirebaseUser user =
                FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(user.getUid())
                    .child("userId")
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        txtUserId.setText("ID: " + snapshot.getValue());
                    });
        }

        layoutAdmin = view.findViewById(R.id.layoutAdmin);
        layoutUser = view.findViewById(R.id.layoutUser);

        // Ẩn cả 2 trước khi load dữ liệu
        layoutAdmin.setVisibility(View.GONE);
        layoutUser.setVisibility(View.GONE);

        if (user != null) {

            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(user.getUid())
                    .child("role")
                    .get()
                    .addOnSuccessListener(snapshot -> {

                        String role = snapshot.getValue(String.class);

                        if ("admin".equals(role)) {

                            layoutAdmin.setVisibility(View.VISIBLE);
                            layoutUser.setVisibility(View.GONE);

                        } else {

                            layoutAdmin.setVisibility(View.GONE);
                            layoutUser.setVisibility(View.VISIBLE);

                        }

                    })
                    .addOnFailureListener(e -> {

                        layoutAdmin.setVisibility(View.GONE);
                        layoutUser.setVisibility(View.VISIBLE);

                    });
        }

        return view;
    }
}