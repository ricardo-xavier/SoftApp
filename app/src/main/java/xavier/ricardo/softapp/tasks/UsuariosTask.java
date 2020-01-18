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

import xavier.ricardo.softapp.CalendarActivity;
import xavier.ricardo.softapp.Usuarios;

public class UsuariosTask extends AsyncTask<String, Void, Usuarios> {
	
	private CalendarActivity contexto;
	private ProgressDialog progress;
	
	public UsuariosTask(CalendarActivity contexto) {
		this.contexto = contexto;
	}
	
	@Override
	protected void onPreExecute() {
		progress = new ProgressDialog((Context) contexto);
		progress.setMessage("Aguarde...");
		progress.show();		
		super.onPreExecute();		
	}
	
	@Override
	protected void onPostExecute(Usuarios result) {
		super.onPostExecute(result);
		progress.dismiss();
		contexto.resultadoUsuarios(result);
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Usuarios doInBackground(String... params) {
		
		try {
			
			String url = "http://ricardoxavier.no-ip.org" 
				+ "/soft-ws3/softws/usuarios";
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
			Usuarios usuarios = gson.fromJson(resultStr.toString(), Usuarios.class);	
			return usuarios;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
