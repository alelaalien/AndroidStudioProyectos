package com.example.conversordemonedas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=(Button) findViewById(R.id.btConvertir);
       final EditText etDolar=(EditText) findViewById(R.id.etDolares);
       final EditText etEuro=(EditText) findViewById(R.id.etEuros);
        final RadioButton rbADolar= (RadioButton) findViewById(R.id.rbDolar);
       final RadioButton rbAEuro=(RadioButton) findViewById(R.id.rbEuro);

        final EditText etCambio=(EditText) findViewById(R.id.etCambio);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sDolar= etDolar.getText().toString();
                String sEuro= etEuro.getText().toString();

                float dolar= Float.parseFloat(sDolar);
                float euro= Float.parseFloat(sEuro);

                if (rbADolar.isSelected()&&!etEuro.getText().equals("")){
                    float valor=euro* 1.10f;
                    String valorCambio= Float.toString(valor);
                    etCambio.setText(valorCambio);
                }
                if (rbAEuro.isSelected()&&!etDolar.getText().equals("")){

                    float valor=dolar*0.91f;
                    String valorCambio= Float.toString(valor);
                    etCambio.setText(valorCambio);
                }



            }
        });
    }
}
