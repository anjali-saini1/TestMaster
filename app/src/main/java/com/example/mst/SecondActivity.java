package com.example.mst;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuactivity_main);
        Button mobileInfoButton = findViewById(R.id.Mobile_info);
        Button sensorsButton = findViewById(R.id.button2);
        Button storageButton = findViewById(R.id.storage);
        mobileInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the ThirdActivity (MobileInfoActivity) when the Mobile Info button is clicked
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        sensorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });
        Button storage = findViewById(R.id.storage);

        storageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, StorageActivity.class);
                startActivity(intent);
            }
        });

    }

}
