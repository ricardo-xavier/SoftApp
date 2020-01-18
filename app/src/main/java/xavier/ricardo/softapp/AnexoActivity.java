package xavier.ricardo.softapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import xavier.ricardo.softapp.R;
import xavier.ricardo.softapp.tasks.AnexoTask;

public class AnexoActivity extends Activity {
	
	private Compromisso compromisso;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anexo);
		
		Intent intent = getIntent();
		String fornecedor = intent.getStringExtra("fornecedor");
		String data = intent.getStringExtra("data");
		String orcamento = intent.getStringExtra("orcamento");
		
		new AnexoTask(this, fornecedor, data, orcamento).execute();
		
	}
	
	public void abrirAnexo(View v) {
		
		View p = (View) v.getParent();
		TextView tvAnexo = (TextView) p.findViewById(R.id.tvAnexo);
		String codigo = tvAnexo.getText().toString();
		
		for (Anexo anexo : compromisso.getAnexos()) {
			if (anexo.getCodigo().equals(codigo)) {
					
				String pdf = String.format("http://ricardoxavier.no-ip.org/soft/%s%d%s.pdf",
						compromisso.getCodFornecedor(), compromisso.getCodOrcamento(), codigo.replace(" ", ""));					
					
				Uri uri = Uri.parse(pdf);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		}
		
	}	

	public void resultado(Compromisso _compromisso) {
		
		compromisso = _compromisso;
		ListView lvAnexos = (ListView) findViewById(R.id.lvAnexos);
		AnexoAdapter adapter = new AnexoAdapter(this, compromisso.getAnexos());
		lvAnexos.setAdapter(adapter);
		
	}
}
