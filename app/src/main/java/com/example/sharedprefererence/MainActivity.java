package com.example.sharedprefererence;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText nama, umur, jurusan, prodi, email;
    public static final String USER_PREF = "USER_PREF";
    public static final String KEY_USER_JSON = "KEY_USER_JSON";
    SharedPreferences sp;
    ListView listView;
    ArrayAdapter<String> jsonAdapter;
    ArrayList<String> jsonDataList = new ArrayList<>();
    int selectedIndex = -1;
    Button btnDelete;
    Button clear;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama = findViewById(R.id.etNama);
        umur = findViewById(R.id.etUmur);
        jurusan = findViewById(R.id.etJurusan);
        prodi = findViewById(R.id.etProdi);
        email = findViewById(R.id.etEmail);

        sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);

        listView = findViewById(R.id.listView);
        jsonAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jsonDataList);

        listView.setAdapter(jsonAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;

            String json = jsonAdapter.getItem(position);
            Gson gson = new Gson();
            User user = gson.fromJson(json, User.class);

            nama.setText(user.getName());
            umur.setText(String.valueOf(user.getAge()));
            jurusan.setText(user.getJurusan());
            prodi.setText(user.getProdi());
            email.setText(user.getEmail());

            btnDelete.setEnabled(true);
        });

        clear = findViewById(R.id.btnClear);
        save = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setEnabled(false);
        btnDelete.setOnClickListener(v -> deleteSelectedItem());

        save.setOnClickListener(v -> save(v));
        clear.setOnClickListener(v -> clear(v));

        showStoredData();
    }

    public void save(View v) {
        String namaValue = nama.getText().toString();
        int umurValue = Integer.parseInt(umur.getText().toString());
        String jurusanValue = jurusan.getText().toString();
        String prodiValue = prodi.getText().toString();
        String emailValue = email.getText().toString();

        User user = new User(namaValue, umurValue, jurusanValue, prodiValue, emailValue);
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_USER_JSON, userJson);
        editor.apply();

        Toast.makeText(this, "Data saved!", Toast.LENGTH_SHORT).show();

        if (selectedIndex >= 0) {
            jsonDataList.remove(selectedIndex);
            jsonAdapter.notifyDataSetChanged();
            selectedIndex = -1;
            btnDelete.setEnabled(false);
        }

        addJsonDataToListView(userJson);
    }

    private void deleteSelectedItem() {
        if (selectedIndex >= 0) {
            jsonDataList.remove(selectedIndex);
            jsonAdapter.notifyDataSetChanged();

            SharedPreferences.Editor editor = sp.edit();
            editor.remove(KEY_USER_JSON);
            editor.apply();

            selectedIndex = -1;
            clear(null);

            Toast.makeText(this, "Data deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    public void clear(View v) {
        nama.setText("");
        umur.setText("");
        jurusan.setText("");
        prodi.setText("");
        email.setText("");
    }

    private void addJsonDataToListView(String jsonData) {
        jsonDataList.add(jsonData);
        jsonAdapter.notifyDataSetChanged();
    }

    private void showStoredData() {
        String savedJson = sp.getString(KEY_USER_JSON, "");

        if (!savedJson.isEmpty()) {
            addJsonDataToListView(savedJson);
        }
    }
}