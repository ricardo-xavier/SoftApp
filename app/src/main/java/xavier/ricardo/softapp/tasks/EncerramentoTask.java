package xavier.ricardo.softapp.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import xavier.ricardo.softapp.Assinatura;
import xavier.ricardo.softapp.DrawView;
import xavier.ricardo.softapp.Encerramento;
import xavier.ricardo.softapp.EncerrarActivity;

public class EncerramentoTask extends AsyncTask<String, Void, String> {
	
	private EncerrarActivity contexto;
	private ProgressDialog progress;
	private String usuario;
	private String data;
	private String observacao;
	private String nome;
	private String documento;
	private String email;
	
	public EncerramentoTask(EncerrarActivity contexto, String usuario, String data, String observacao,
			String nome, String documento, String email) {
		this.contexto = contexto;
		this.usuario = usuario;
		this.observacao = observacao;
		this.data = data;
		this.nome = nome;
		this.documento = documento;
		this.email = email;
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
		contexto.resultado(result);
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected String doInBackground(String... params) {
		
		try {
			
			String url = "http://ricardoxavier.no-ip.org/soft-ws3/softws/encerra";
			//Log.i("SOFTAPP", url);
			
			HttpClient httpClient = new DefaultHttpClient();
			 
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("content-type", "application/json");
			
			Encerramento encerramento = new Encerramento();
			encerramento.setUsuario(usuario);
			encerramento.setData(data);
			encerramento.setObservacao(observacao);
			encerramento.setNome(nome);
			encerramento.setDocumento(documento);
			encerramento.setEmail(email);
			Assinatura assinatura = new Assinatura();
			assinatura.setPartes(DrawView.getPartes());
			encerramento.setAssinatura(assinatura);
			
			Gson gson = new Gson();
			String json = gson.toJson(encerramento, Encerramento.class);
			
            httpPost.setEntity(new ByteArrayEntity(json.getBytes()));			
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
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
