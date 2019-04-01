package com.flaiserapps.quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {
    private EditText email, password, nombre, apellidos, telefono;
    private Button cancelar, registrar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mAuth = FirebaseAuth.getInstance();
        email=(EditText) this.findViewById(R.id.registro_email);
        password=(EditText) this.findViewById(R.id.registro_password);
        nombre=(EditText) this.findViewById(R.id.registro_nombre);
        apellidos=(EditText) this.findViewById(R.id.registro_apellidos);
        telefono=(EditText) this.findViewById(R.id.registro_telf);

        cancelar=(Button) this.findViewById(R.id.registro_cancel);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        registrar=(Button) this.findViewById(R.id.registro_ok);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (email.getText().toString().isEmpty()){
                email.setError("El email no puede estar en blanco");
                email.requestFocus();
            }
            else{
                if (!isValidEmail(email.getText().toString())){
                    email.setError("Direccón de correo no válida");
                    email.requestFocus();
                }else{
                    if(password.getText().toString().isEmpty()){
                        password.setError("La contraseña no puede estar en blanco");
                        password.requestFocus();
                    }else{
                        if(password.getText().toString().length()<6){
                            password.setError("Longitud mínima de 6 caracteres");
                            password.requestFocus();
                        }else{

                            registrarUsuario(email.getText().toString(), password.getText().toString());
                        }

                    }

                }

            }
            }
        });
    }
    public void registrarUsuario(final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("hectorr", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid=user.getUid();
                            Usuario useraux=new Usuario(email, password, nombre.getText().toString(), apellidos.getText().toString(), telefono.getText().toString());
                            //Apuntamos al nodo usuarios
                            DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("usuarios");
                            Log.d("hecotrr", "Database Reference: "+dbr.toString());
                            //Si no existe lo crea, si existe lo machaca
                            dbr.child(uid).setValue(useraux);
                            setResult(RESULT_OK);
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("hectorr", "createUserWithEmail:failure", task.getException());
                        }

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FirebaseAuthException feAux = (FirebaseAuthException) e;
                        String errcode=feAux.getErrorCode();

                    }
                });

    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
