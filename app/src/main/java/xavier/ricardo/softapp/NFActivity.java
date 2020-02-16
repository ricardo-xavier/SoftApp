package xavier.ricardo.softapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import xavier.ricardo.softapp.tasks.AgendaMesTask;
import xavier.ricardo.softapp.tasks.NFTask;

public class NFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nf);
    }

    public void busca(View v) {

        EditText edtNF = findViewById(R.id.edtNF);
        String nf = edtNF.getText().toString();
        if ((nf == null) || nf.trim().equals("")) {
            return;
        }
        new NFTask(this, nf).execute();
    }

    public void resultadoListaNF(Agenda agenda) {

        if (agenda.getCompromissos() == null) {
            return;
        }

        List<String> list = new ArrayList<String>();
        for (Compromisso c : agenda.getCompromissos()) {
            list.add(c.getUsuario() + " " + c.getData());
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        ListView lvResultado = findViewById(R.id.lvResultado);
        lvResultado.setAdapter(adapter);

        lvResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String usuarioData = adapter.getItem(position);
                Intent resp = new Intent();
                resp.putExtra("usuarioData", usuarioData);
                setResult(RESULT_OK, resp);
                finish();
            }
        });

    }
}
