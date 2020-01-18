package xavier.ricardo.softapp.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import xavier.ricardo.softapp.AnexoActivity;
import xavier.ricardo.softapp.Compromisso;

public class AnexoTask extends AsyncTask<String, Void, Compromisso> {
	
	private AnexoActivity contexto;
	private ProgressDialog progress;
	private String fornecedor;
	private String data;
	private String codOrcamento;
	
	public AnexoTask(AnexoActivity contexto, String fornecedor, String data,
			String codOrcamento) {
		this.contexto = contexto;
		this.fornecedor = fornecedor;
		this.data = data;
		this.codOrcamento = codOrcamento;
	}
	
	@Override
	protected void onPreExecute() {
		progress = new ProgressDialog((Context) contexto);
		progress.setMessage("Aguarde...");
		progress.show();		
		super.onPreExecute();		
	}
	
	@Override
	protected void onPostExecute(Compromisso result) {
		super.onPostExecute(result);
		progress.dismiss();
		contexto.resultado(result);
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Compromisso doInBackground(String... params) {
		
		try {
			
			String[] partes = data.split("/");
			int dia = Integer.parseInt(partes[0]);
			int mes = Integer.parseInt(partes[1]);
			int ano = Integer.parseInt(partes[2]);
			String dataYMD = String.format("%04d-%02d-%02d", ano, mes, dia); 
			String url = String.format("http://ricardoxavier.no-ip.org" 
				+ "/soft-ws3/softws/anexos/%s/%s/%s",
				fornecedor, dataYMD, codOrcamento);
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
			
			Gson gson = new Gson();
			Compromisso compromisso = gson.fromJson(resultStr.toString(), Compromisso.class);
			compromisso.setCodFornecedor(fornecedor);
			compromisso.setCodOrcamento(Integer.parseInt(codOrcamento));
			return compromisso;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
