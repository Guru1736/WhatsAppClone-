package com.guruprasad.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.guruprasad.whatsappclone.Adapters.FragmentAdapter;
import com.guruprasad.whatsappclone.databinding.ActivityHomeBinding;
import com.guruprasad.whatsappclone.databinding.ActivityMainBinding;

public class Home extends AppCompatActivity {

    FirebaseAuth firebaseAuth ;
    ActivityHomeBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();

        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.Logout:
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),sign_in.class));
                finish();

                break;

            case R.id.Setting:
                Toast.makeText(this, "You clicked Setting ", Toast.LENGTH_SHORT).show();
                break;

            case R.id.groupchat:
                startActivity(new Intent(getApplicationContext(),Groupchat.class));


        }
        return true ;
    }
}