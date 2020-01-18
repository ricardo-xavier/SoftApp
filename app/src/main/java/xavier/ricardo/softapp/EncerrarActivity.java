package xavier.ricardo.softapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import xavier.ricardo.softapp.R;
import xavier.ricardo.softapp.tasks.EncerramentoTask;

public class EncerrarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encerrar);
		
		DrawView.setReadOnly(true);
		
		Intent intent = getIntent();
		String chave = intent.getStringExtra("chave");
		System.out.println(chave);
		String[] partes = chave.split(";");
		String observacao = partes.length > 2 ? partes[2] : "";
		String nome = partes.length > 3 ? partes[3] : "";
		String documento = partes.length > 4 ? partes[4] : "";
		String email = partes.length > 5 ? partes[5] : "";
		String json = partes.length > 6 ? partes[6] : "";

		EditText etObservacao = (EditText) findViewById(R.id.etObservacao);
		etObservacao.setText(observacao);
		
		EditText etNome = (EditText) findViewById(R.id.etNome);
		etNome.setText(nome);
		
		EditText etDocumento = (EditText) findViewById(R.id.etDocumento);
		etDocumento.setText(documento);
		
		EditText etEmail = (EditText) findViewById(R.id.etEmail);
		etEmail.setText(email);
		
		Gson gson = new Gson();
		Assinatura assinatura = gson.fromJson(json, Assinatura.class);
		if (assinatura != null) {
			DrawView.setPartes(assinatura.getPartes());
		}
		
		
	}
	
	public void confirma(View v) {
		
		Intent intent = getIntent();
		String chave = intent.getStringExtra("chave");
		System.out.println(chave);
		String[] partes = chave.split(";");
		String usuario = partes[0];
		String data = partes[1];
		
		EditText etObservacao = (EditText) findViewById(R.id.etObservacao);
		String observacao = etObservacao.getText().toString();
		
		EditText etNome = (EditText) findViewById(R.id.etNome);
		String nome = etNome.getText().toString();
		
		EditText etDocumento = (EditText) findViewById(R.id.etDocumento);
		String documento = etDocumento.getText().toString();
		
		EditText etEmail = (EditText) findViewById(R.id.etEmail);
		String email = etEmail.getText().toString();
		
		new EncerramentoTask(this, usuario, data, observacao,
				nome, documento, email).execute();
	}	
	
	public void cancela(View v) {
		finish();
	}

	public void assina(View v) {
		Intent intent = new Intent(this, AssinaturaActivity.class);
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		DrawView assinatura = (DrawView) findViewById(R.id.vwAssinaturaRO);
		assinatura.invalidate();
	}

	public void resultado(String result) {
		
		if (result.equals("ok")) {
			Toast.makeText(this, "Agendamento encerrado com sucesso", Toast.LENGTH_LONG).show();
			Intent intent = getIntent();
			
			String chave = intent.getStringExtra("chave");
			System.out.println(chave);
			String[] partes = chave.split(";");
			String usuario = partes[0];
			String data = partes[1];
			
			EditText etObservacao = (EditText) findViewById(R.id.etObservacao);
			String observacao = etObservacao.getText().toString();
			chave = usuario + ";" + data + ";" + observacao;
			
			Intent resultIntent = new Intent();
			resultIntent.putExtra("chave", chave);
 			
			setResult(RESULT_OK, resultIntent);
			
		} else {
			Toast.makeText(this, "ERRO:" + result, Toast.LENGTH_LONG).show();
			setResult(RESULT_CANCELED);
		}
		
		finish();		
	}	
	

}
