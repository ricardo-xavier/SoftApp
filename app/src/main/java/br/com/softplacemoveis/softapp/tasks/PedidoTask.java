package br.com.softplacemoveis.softapp.tasks;

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

import br.com.softplacemoveis.softapp.DetalhesActivity;
import xavier.ricardo.softapp.Pedido;

public class PedidoTask extends AsyncTask<String, Void, Pedido> {
	
	private DetalhesActivity contexto;
	private ProgressDialog progress;
	private String fornecedor;
	private String data;
	private String codOrcamento;
	private String codPedido;
	
	public PedidoTask(DetalhesActivity contexto, String fornecedor, String data,
			String codOrcamento, String codPedido) {
		this.contexto = contexto;
		this.fornecedor = fornecedor;
		this.data = data;
		this.codOrcamento = codOrcamento;
		this.codPedido = codPedido;
	}
	
	@Override
	protected void onPreExecute() {
		progress = new ProgressDialog((Context) contexto);
		progress.setMessage("Aguarde...");
		progress.show();		
		super.onPreExecute();		
	}
	
	@Override
	protected void onPostExecute(Pedido result) {
		super.onPostExecute(result);
		progress.dismiss();
		contexto.resultado(result);
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Pedido doInBackground(String... params) {
		
		try {
			
			String[] partes = data.split("/");
			int dia = Integer.parseInt(partes[0]);
			int mes = Integer.parseInt(partes[1]);
			int ano = Integer.parseInt(partes[2]);
			String dataYMD = String.format("%04d-%02d-%02d", ano, mes, dia); 
			String url = String.format("http://ricardoxavier.no-ip.org" 
				+ "/soft-ws3/softws/pedido/%s/%s/%s/%s",
				fornecedor, dataYMD, codOrcamento, codPedido);
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
			Pedido pedido = gson.fromJson(resultStr.toString(), Pedido.class);
			if (pedido != null) {
				pedido.setFornecedor(fornecedor);
				pedido.setData(data);
				pedido.setCodOrcamento(Integer.parseInt(codOrcamento));
				pedido.setCodPedido(Integer.parseInt(codPedido));
			}
			return pedido;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
