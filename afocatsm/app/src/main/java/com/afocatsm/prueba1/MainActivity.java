package com.afocatsm.prueba1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView textViewPlaca,textViewFinV, textViewinicioV, textViewCat, textViewAsociado, textViewmaterno, textViewpaterno;
    EditText textPlacaVehiculo, editTextPhone ;
    Button buttonReportarSiniestro;
    FloatingActionButton floatingActionButton2;

    //String url= Webservice.RAIZ + Webservice.BUSCARPLACA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlaca = findViewById(R.id.textViewPlaca);
        textViewinicioV= findViewById(R.id.textViewinicioV);
        textViewFinV = findViewById(R.id.textViewFinV);
        textViewCat = findViewById(R.id.textViewCat);
        textViewAsociado = findViewById(R.id.textViewAsociado);
        textViewpaterno = findViewById(R.id.textViewpaterno);
        textViewmaterno = findViewById(R.id.textViewmaterno);
        textPlacaVehiculo = findViewById(R.id.textPlacaVehiculo);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonReportarSiniestro = findViewById(R.id.buttonReportarSiniestro);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);

        buttonReportarSiniestro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarPlaca();
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarSniestro();
                enviarMensaje("936088164","Hola Soy"+ textPlacaVehiculo+ " te estoy enviando un Mensaje pues sufri un accidente");

                enviarCorreo();
            }
        });

    }

    public void buscarPlaca() {
        String placa1 = textPlacaVehiculo.getText().toString();
        if (placa1.equals("")) {
            Toast.makeText(this, "Escribir Placa !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            textPlacaVehiculo.requestFocus();
        } else {

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Webservice.RAIZ + Webservice.BUSCARPLACA + "?placa=" + textPlacaVehiculo.getText();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    //Toast.makeText(getApplicationContext(), "Placa " + textPlacaVehiculo.getText(), Toast.LENGTH_LONG).show();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            textViewPlaca.setText(jsonObject.getString("cPlaca_V"));
                            textViewinicioV.setText(jsonObject.getString("dFechaVigenciaInicio"));
                            textViewFinV.setText(jsonObject.getString("dFechaVigenciaTermino"));
                            textViewCat.setText(jsonObject.getString("cNumeroCat"));
                            textViewAsociado.setText(jsonObject.getString("cPerNombre"));
                            textViewpaterno.setText(jsonObject.getString("cPerApePaterno"));
                            textViewmaterno.setText(jsonObject.getString("cPerApeMaterno"));
                        } catch (JSONException e) {
                            // Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "No pertence a Afocat San Martin", Toast.LENGTH_LONG).show();
                }
            });
            queue.add(jsonArrayRequest);
        }
    }

    public void registrarSniestro(){
        String url1= Webservice.RAIZ + Webservice.REGISTRARSINIESTRO ;//+ "?placa="+ textPlacaVehiculo.getText();
        String nombre = textPlacaVehiculo.getText().toString();

        if (nombre.equals("")){
            Toast.makeText(this, "Escribir Placa !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            textPlacaVehiculo.requestFocus();
        } else {
            final ProgressDialog loading = ProgressDialog.show( this, "guardando...","Espere" );
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            mostrarDialogoBasico();
                            limpiarcampos();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String placa = textPlacaVehiculo.getText().toString().trim();
                    String telefono = editTextPhone.getText().toString().trim();
                    String mydate1 = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    String numeroSiniestroApp = textPlacaVehiculo.getText().toString().trim() + mydate1;

                    siniestros sini= new siniestros(placa,telefono, numeroSiniestroApp);
                    Map<String,String> params=new Hashtable<>();
                    params.put("placa",sini.getPlaca());
                    params.put("telefono",sini.getCelular());
                    params.put("numeroSiniestroApp", sini.getNumeroSiniestroApp());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void mostrarDialogoBasico(){
        String numeroSiniestrosApp = textPlacaVehiculo.getText().toString();
        // String date = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()));
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String numeroContactoApp = numeroSiniestrosApp + " - "+mydate;

        AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this); //, R.style.DialogoBasico
        builder.setTitle("Afocat San Martin");
        builder.setMessage("Muchas Gracias su numero de siniestro es: "+ numeroContactoApp).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                Toast.makeText(getApplicationContext(),textPlacaVehiculo.getText(),Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        }).show(); //
    }

    public void mostrarNosocomios(View view) {
       Intent reportarsiniestro1= new Intent( this, listaHositales.class);
       startActivity(reportarsiniestro1);
    }

    public void limpiarcampos (){
        textViewPlaca.setText("Placa");
        textViewFinV.setText("Inicio de Vigencia");
        textViewinicioV.setText("Fin de Vigencia");
        textViewCat.setText("Numero de CAT");
        textViewAsociado.setText("Nombre del Asociado");
        textViewmaterno.setText("Apellido Paterno");
        textViewpaterno.setText("Apellido Materno");
        textPlacaVehiculo.setText("");
        editTextPhone.setText("");
        textPlacaVehiculo.requestFocus();
    }

    private void   enviarCorreo(){
        String mail = "perezdiazevenronald@gmail.com";
        String Mesage="uted esta cpmsdflmsdlmsdgdf";
        String Subject= "sfsdfsdfsdfsdf";

        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,Subject,Mesage);
        javaMailAPI.execute();
    }

    private void enviarMensaje (String numero, String mensaje){
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero,null,mensaje,null,null);
            Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}