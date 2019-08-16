package com.metis.myapplication;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.libreria.metis.Cedula;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button scanBtn;
    private TextView doc_field, pasajero_field;
    String res = null;

    Cedula  cedula;

    public IntentIntegrator scanIntegrator;

  /*  public MainActivity() {
        cedula = new  Cedula();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se Instancia el botón de Scan
        scanBtn = (Button) findViewById(R.id.scan_button);
        //Se Instancia el Campo de Texto para el nombre del formato de código de barra
        doc_field = (TextView) findViewById(R.id.doc_field);
        //Se Instancia el Campo de Texto para el contenido  del código de barra
        pasajero_field = (TextView) findViewById(R.id.pasajero_field);
        //Se agrega la clase MainActivity.java como Listener del evento click del botón de Scan
        scanBtn.setOnClickListener(this);


    }

    public void onClick(View v){
        //Se responde al evento click
        if(v.getId()==R.id.scan_button){
            //Se instancia un objeto de la clase IntentIntegrator
            scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            //Se procede con el proceso de scaneo
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //Se obtiene el resultado del proceso de scaneo y se parsea
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            //Quiere decir que se obtuvo resultado pro lo tanto:
            //Desplegamos en pantalla el contenido del código de barra scaneado
            String scanContent = result.getContents();

            Log.d("data", scanContent);

            if (scanContent == null) {
                Toast.makeText(getApplicationContext(), "No fue posible leer la cedula", Toast.LENGTH_LONG).show();
            } else {
                res = result.getContents();
                cedula = new Cedula(res);
                if (this.cedula == null) {

                    Toast.makeText(getApplicationContext(), "onActivityResult - Read PDF417: Cedula mal formada", Toast.LENGTH_SHORT).show();
                    return;
                }

                pasajero_field.setText(this.cedula.getNombre());
                Log.d("data", this.cedula.getNombre());
                int cedulaInt = Integer.parseInt(this.cedula.getId());
                doc_field.setText(String.valueOf(cedulaInt));
            }

        }else{

            //super.onActivityResult(requestCode, resultCode, data);
            //Quiere decir que NO se obtuvo resultado
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}

