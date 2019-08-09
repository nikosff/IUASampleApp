package com.nicolasfanin.IUASampleApp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.nicolasfanin.IUASampleApp.fragments.ActivityWithFragments;

public class MyMainActivity extends AppCompatActivity {

    static final int PICK_CONTACT_REQUEST = 1;  // Request code que me permite hacer multiples startActivityForResult.

    private Button selectContactButton;
    private Button listActivityButton;
    private Button activityWithFragmentsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_main_activity);

        selectContactButton = findViewById(R.id.select_contact_button);
        listActivityButton = findViewById(R.id.list_activity_button);
        activityWithFragmentsButton = findViewById(R.id.activity_with_fragments);

        selectContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickContact();
            }
        });
        listActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToListActivity();
            }
        });
        activityWithFragmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivityWithFragments();
            }
        });

    }

    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(Phone.CONTENT_TYPE); // Mostrar solo los contactos del usuario con sus nombres
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Verificamos que se corresponda con nuestra solicitud mediante
        // el codigo que nosotros mismos creamos
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                // el argumento data contiene información del contacto seleccionado
                Uri selectedContact = data.getData();
                String[] projection = {Phone.NUMBER};
                Cursor cursor = getContentResolver().query(selectedContact, projection, null, null, null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(Phone.NUMBER);
                String number = cursor.getString(column);
                Toast.makeText(this, "Telefono Seleccionado: " + number, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void navigateToListActivity() {
        Intent listIntent = new Intent(MyMainActivity.this, ListActivity.class);
        startActivity(listIntent);
    }

    private void navigateToActivityWithFragments() {
        startActivity(new Intent(MyMainActivity.this, ActivityWithFragments.class));
    }
}
