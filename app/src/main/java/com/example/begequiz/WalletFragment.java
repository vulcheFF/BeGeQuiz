package com.example.begequiz;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.begequiz.databinding.FragmentWalletBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WalletFragment extends Fragment {

    FragmentWalletBinding binding;
    FirebaseFirestore database;
    User user;
    Dialog dialog;

    int images[] =
            {
                    R.drawable.farmer, /*[0]*/
                    R.drawable.jester, /*[1]*/
                    R.drawable.knight,  /*[2]*/
                    R.drawable.magician, /*[3]*/
                    R.drawable.king, /*[4]*/
                    R.drawable.swords /*[5]*/
            };

    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentWalletBinding.inflate(inflater,container,false);

        database = FirebaseFirestore.getInstance();
        dialog = new Dialog(getActivity());

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                binding.currentCoins.setText(String.valueOf(user.getCoins()));
                //binding.currentCoins.setText(user.getCoins()+"");
            }
        });

        binding.rankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.rank_dialog_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                showRank(user.getCoins());

                Button btnOk = dialog.findViewById(R.id.okBtn);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        return binding.getRoot();
    }

    public void showRank(long coins)
    {
        ImageView imageRank = (ImageView) dialog.findViewById(R.id.imageRank);
        TextView rankTextView = (TextView) dialog.findViewById(R.id.rankTextView);

        if(coins >= 0 && coins < 150)
        {
            imageRank.setImageResource(images[0]);
            rankTextView.setText("СЕЛЯНИН");
            dialog.show();
        }

        else if(coins >= 150 && coins < 300)
        {
            imageRank.setImageResource(images[1]);
            rankTextView.setText("ШУТ");
            dialog.show();
        }

        else if(coins >= 300 && coins < 550)
        {
            imageRank.setImageResource(images[2]);
            rankTextView.setText("РИЦАР");
            dialog.show();
        }

        else if(coins >= 550 && coins < 700)
        {
            imageRank.setImageResource(images[3]);
            rankTextView.setText("МАГЬОСНИК");
            dialog.show();
        }

        else if(coins >= 700 && coins < 1000)
        {
            imageRank.setImageResource(images[4]);
            rankTextView.setText("КРАЛ");
            dialog.show();
        }

        else if(coins >= 1000)
        {
            imageRank.setImageResource(images[5]);
            rankTextView.setText("ЗАВОЕВАТЕЛ");
            dialog.show();
        }
    }
}