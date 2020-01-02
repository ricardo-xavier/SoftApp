package br.com.softplacemoveis.softapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import br.com.softplacemoveis.softapp.tasks.LoginTask;
import br.com.softplacemoveis.softapp.tasks.NotificadorService;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// teste ca-app-pub-3940256099942544/6300978111
		// real  ca-app-pub-0381609228541841/9233128121
		// app   ca-app-pub-0381609228541841~8494761522
		// novo  ca-app-pub-0381609228541841/8083720641
		MobileAds.initialize(this, "ca-app-pub-0381609228541841~8494761522");
		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);		
		
		SharedPreferences shared = getSharedPreferences("softapp", Context.MODE_PRIVATE);
		
		boolean salvar = shared.getBoolean("salvar", false);
		if (salvar) {
			CheckBox cbSalvar = (CheckBox) findViewById(R.id.cbSalvar);
			cbSalvar.setChecked(true);
			
			String usuario = shared.getString("usuario", "");
			if (!usuario.trim().equals("")) {
				EditText etUsuario = (EditText) findViewById(R.id.etUsuario);
				etUsuario.setText(usuario);
			}
			
			String senha = shared.getString("senha", "");
			if (!senha.trim().equals("")) {
				EditText tpSenha = (EditText) findViewById(R.id.tpSenha);
				tpSenha.setText(senha);
			}
		}			
	}
	
	public void login(View v) {
		
		if (!NetworkUtils.verificaConexao(this)) {
			Toast.makeText(this, "Sem conex�o com a internet", Toast.LENGTH_LONG).show();
			return;
		}		
	
		EditText etUsuario = (EditText) findViewById(R.id.etUsuario);
		String usuario = etUsuario.getText().toString().trim();
		
		if (usuario.equals("")) {
			Toast.makeText(this, "Usu�rio n�o preenchido", Toast.LENGTH_LONG).show();
			etUsuario.requestFocus();
			return;
		}
		EditText tpSenha = (EditText) findViewById(R.id.tpSenha);
		String senha = tpSenha.getText().toString().trim();
		if (senha.equals("")) {
			Toast.makeText(this, "Senha n�o preenchida", Toast.LENGTH_LONG).show();
			tpSenha.requestFocus();
			return;
		}
		
		new LoginTask(this, usuario, senha).execute();
		
	}
	
	public void resultadoLogin(String mensagem) {
		
		if (!mensagem.startsWith("ok")) {
			Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
			return;
		}
		
		boolean administrador = false;
		String[] args = mensagem.split(":");
		if (args.length > 1) {
			administrador = args[1].equalsIgnoreCase("S");
		}
		
		EditText etUsuario = (EditText) findViewById(R.id.etUsuario);
		String usuario = etUsuario.getText().toString().trim();
		
		EditText tpSenha = (EditText) findViewById(R.id.tpSenha);
		String senha = tpSenha.getText().toString().trim();
		
		CheckBox cbSalvar = (CheckBox) findViewById(R.id.cbSalvar);
		boolean salvar = cbSalvar.isChecked();		
		
		SharedPreferences shared = getSharedPreferences("softapp", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = shared.edit();
		editor.putString("usuario", usuario);
		editor.putString("senha", senha);
		editor.putBoolean("salvar", salvar);
		editor.commit();		
		
		boolean rodando = false;
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (service.service.getClassName().contains("NotificadorService")) {
				rodando = true;
				break;
			}
		}
		
		if (!rodando) {
			Intent intentService = new Intent(this, NotificadorService.class);
			intentService.putExtra("usuario", usuario);
			startService(intentService);
		}

		Intent intent = new Intent(this, CalendarActivity.class);
		intent.putExtra("usuario", usuario);
		intent.putExtra("administrador", administrador);
		startActivity(intent);
		
	}

}
