package xavier.ricardo.softapp.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import xavier.ricardo.softapp.LoginActivity;

public class LoginTask extends AsyncTask<String, Void, String> {
	
	private static final int VERSAO = 6; 
	
	private LoginActivity contexto;
	private ProgressDialog progress;
	private String usuario;
	private String senha;
	
	public LoginTask(LoginActivity contexto, String usuario, String senha) {
		this.contexto = contexto;
		this.usuario = usuario;
		this.senha = senha;
	}
	
	@Override
	protected void onPreExecute() {
		progress = new ProgressDialog((Context) contexto);
		progress.setMessage("Aguarde...");
		progress.show();		
		super.onPreExecute();		
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		progress.dismiss();
		contexto.resultadoLogin(result);
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected String doInBackground(String... params) {
		
		try {
			
			String url = String.format("http://ricardoxavier.no-ip.org" 
				+ "/soft-ws3/softws/login/%s/%s/%s",
				usuario, senha, VERSAO);
			//Log.i("SOFTAPP", url);
			
			HttpClient httpClient = new DefaultHttpClient();
			
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			
			InputStream inputStream = httpResponse.getEntity().getContent();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = "";
			StringBuilder resultStr = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				resultStr.append(line);
			}
			inputStream.close();	
			//Log.i("SOFTAPP", resultStr.toString());
			
			return resultStr.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
