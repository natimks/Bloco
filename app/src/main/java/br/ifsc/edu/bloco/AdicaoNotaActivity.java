package br.ifsc.edu.bloco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class AdicaoNotaActivity extends AppCompatActivity {
    Notas notas;
    EditText textoEd;
    Nota nota;
    boolean editarNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nota);
        notas = new Notas(this);
        nota = new Nota();
        textoEd = (EditText) this.findViewById(R.id.texto_edit_text);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            String texto = b.getString("texto");
            int id = b.getInt("id");
            if (Objects.nonNull(id) && Objects.nonNull(texto)) {
                textoEd.setText(texto);
                nota.setId(id);
                editarNota = true;
            }
        }

    }

    public void salvarNota(View view) {
        String texto = textoEd.getText().toString();
        if (Objects.nonNull(textoEd)) {
            if (editarNota) {
                nota.setNome(texto);
                notas.editarNota(nota);
            } else {
                notas.salvarNota(texto);
            }
            Intent it = new Intent(AdicaoNotaActivity.this, MainActivity.class);
            startActivity(it);
        } else {
            Toast.makeText(this, "ERRO AO SALVAR", Toast.LENGTH_LONG).show();
        }
    }
}
