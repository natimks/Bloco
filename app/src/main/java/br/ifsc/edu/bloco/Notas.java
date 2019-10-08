package br.ifsc.edu.bloco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Notas {
    SQLiteDatabase bd;
    Context contexto;
    static String NOME_TABELA = "bloconotas";
    static final String SCRIPT_CRIACAO_TABELA_VEICULOS = "CREATE TABLE IF NOT EXISTS bloconotas "
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "texto TEXT NOT NULL, "
            + "dataCriacao NUMERIC,"
            + "dataAlteracao NUMERIC);";

    public Notas(Context c) {
        this.contexto = c;
        Log.e("CHEGOU", "aEEEE");
        try {
            PersistenceHelper persistenceHelper = PersistenceHelper.getInstance(c);
            bd = persistenceHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.e("CHEGOU", "ERRO" + e.getMessage());
        }

    }

    public List<String> recuperarTodos() {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = bd.rawQuery(queryReturnAll, null);
        List<Nota> notas = construirVeiculoPorCursor(cursor);
        return convertEntitiesForString(notas);
    }

    private List<String> convertEntitiesForString(List<Nota> notas) {
        List<String> stringNotas = new ArrayList<>();
        for (Nota n : notas) {
            stringNotas.add(n.getNome() + "\n CRIAÇÃO: " + n.getDataCriacao() + "\n ALTERAÇÃO: " + n.getDataModificacao());
        }
        return stringNotas;
    }

    public List<Nota> recuperarTodasNotas() {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = bd.rawQuery(queryReturnAll, null);
        List<Nota> notas = construirVeiculoPorCursor(cursor);
        return notas;
    }

    private List<Nota> construirVeiculoPorCursor(Cursor cursor) {
        List<Nota> notas = new ArrayList<Nota>();
        if (cursor == null)
            return notas;

        try {

            if (cursor.moveToFirst()) {
                do {

                    int indexID = cursor.getColumnIndex("_id");
                    int indexMarca = cursor.getColumnIndex("texto");
                    int indexModelo = cursor.getColumnIndex("dataCriacao");
                    int indexPlaca = cursor.getColumnIndex("dataAlteracao");

                    int id = cursor.getInt(indexID);
                    String marca = cursor.getString(indexMarca);
                    Long modelo = cursor.getLong(indexModelo);
                    Long placa = cursor.getLong(indexPlaca);

                    Nota veiculo = new Nota(id, marca, modelo, placa);
                    notas.add(veiculo);

                } while (cursor.moveToNext());
            }

        } finally {
            cursor.close();
        }
        return notas;
    }


    public void salvarNota(String texto) {
        if (texto.trim().equals("")) {
            Toast.makeText(this.contexto, "Nada", Toast.LENGTH_LONG).show();
        } else {
            ContentValues registro = new ContentValues();
            registro.put("texto", texto);
            registro.put("dataCriacao", System.currentTimeMillis());
            registro.put("dataAlteracao", System.currentTimeMillis());
            bd.insert("bloconotas", null, registro);
        }
    }

    public void editarNota(Nota nota) {
        ContentValues valores = gerarContentValeuesNota(nota);
        String[] valoresParaSubstituir = {
                String.valueOf(nota.getId())
        };

        bd.update(NOME_TABELA, valores, "_id" + " = ?", valoresParaSubstituir);
    }

    private ContentValues gerarContentValeuesNota(Nota nota) {
        ContentValues values = new ContentValues();
        values.put("texto", nota.getNome());
        values.put("dataAlteracao", System.currentTimeMillis());

        return values;
    }

    public List<String> recuperarTodosOrderByCriacao() {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA + " order by dataCriacao";
        Cursor cursor = bd.rawQuery(queryReturnAll, null);
        List<Nota> notas = construirVeiculoPorCursor(cursor);
        return convertEntitiesForString(notas);
    }

    public List<String> recuperarTodosOrderByAlteracao() {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA + " order by dataAlteracao";
        Cursor cursor = bd.rawQuery(queryReturnAll, null);
        List<Nota> notas = construirVeiculoPorCursor(cursor);
        return convertEntitiesForString(notas);
    }
}
