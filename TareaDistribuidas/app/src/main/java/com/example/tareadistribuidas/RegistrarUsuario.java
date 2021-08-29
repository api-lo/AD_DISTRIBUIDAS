package com.example.tareadistribuidas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tareadistribuidas.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrarUsuario extends AppCompatActivity {
    TextView txtNombre, txtApellido, txtCorreo, txtFechaNacimiento, txtGenero, txtUser, txtContrasenia;
    CircleImageView imgImagen;
    RequestQueue requestQueue;
    Button btnRegistrarse;
    private static final int COD_SELECTION = 10;
    private static final int COD_FOTO = 20;
    String URL="";
    Uri uri;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        inicializarFirebase();
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtGenero = findViewById(R.id.txtGenero);
        txtUser = findViewById(R.id.txtUser);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        imgImagen = findViewById(R.id.imgImagen);
        requestQueue = Volley.newRequestQueue(this);
    }
    public void btnRegistrar(View view){
//        requestRegitrar(user);
        addFirebaseUsuario();
    }

    public void RegistrarUsuario(String local) {

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("Fotos");
    }

    public void mostrarOpciones(View view) {
        mostrarDialogo();
    }

    private void mostrarDialogo() {
        CharSequence[] opciones = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarUsuario.this);
        builder.setTitle("Elige una opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (opciones[i].equals("Tomar Foto")) {

                } else {
                    if (opciones[i].equals("Elegir de galeria")) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_SELECTION);

                    } else {
                        dialog.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case COD_SELECTION:
                uri = data.getData();
                imgImagen.setImageURI(uri);
//                addFirebaseUsuario(miPath);
                break;
        }
    }

    private void requestRegitrar(User user) {
        String url = "https://aplicaciones.uteq.edu.ec/WenServisPA/webresources/generic/singup";
        HashMap<String, String> hash = new HashMap<>();
        hash.put("idUsuario", "0");
        hash.put("firstName", user.getFirstName());
        hash.put("lastName", user.getLastName());
        hash.put("birthdate", user.getBirthdate());
        hash.put("gender", user.getGender());
        hash.put("phone", user.getPhone());
        hash.put("username", user.getUsername());
        hash.put("mail", user.getMail());
        hash.put("password", user.getPassword());
        hash.put("image", user.getImage());
        hash.put("state", "true");

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hash), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(RegistrarUsuario.this, response.getString("message"), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistrarUsuario.this, MainActivity.class);
                    Bundle b = new Bundle();
                    intent.putExtras(b);
                    startActivity(intent);
                } catch (JSONException e) {
                    Toast.makeText(RegistrarUsuario.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError ex) {
                Toast.makeText(RegistrarUsuario.this, "Incorrect: \n" + ex.getMessage(), Toast.LENGTH_LONG).show();

                System.out.println(ex.toString());
            }
        });
        requestQueue.add(jsonRequest);
    }

    public void addFirebaseUsuario() {

        StorageReference reference = storageReference.child(nombreAleatodio());
        UploadTask uploadTask = reference.putFile(uri);

        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(Task<Uri> task) {
                Uri dUri = task.getResult();
                URL=dUri.toString();
                user = new User(0, txtNombre.getText().toString(), txtApellido.getText().toString(),
                        txtFechaNacimiento.getText().toString(), txtGenero.getText().toString(),
                        "0990618108", txtUser.getText().toString(), txtCorreo.getText().toString(),
                        (txtContrasenia.getText().toString()),
                        URL, true);

                requestRegitrar(user);
                /*RegistrarUsuario(URL);
                Toast.makeText(RegistrarUsuario.this, "Todo bien ", Toast.LENGTH_SHORT);*/
            }
        });

    }

    public String nombreAleatodio() {

        int p = (int) (Math.random() * 25 + 1);
        int s = (int) (Math.random() * 25 + 1);
        int t = (int) (Math.random() * 25 + 1);
        int c = (int) (Math.random() * 25 + 1);
        int numero1 = (int) (Math.random() * 1012 + 2111);
        int numero2 = (int) (Math.random() * 1012 + 2111);

        String[] elementos = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        final String aleatorio = elementos[p] + elementos[s] + numero1 + elementos[t] + elementos[c] + numero2 + "comprimido.jpg";
        return aleatorio;
    }


}