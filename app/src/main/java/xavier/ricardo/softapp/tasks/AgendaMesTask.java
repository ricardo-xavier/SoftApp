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
import java.util.Calendar;

import xavier.ricardo.softapp.AgendaMes;
import xavier.ricardo.softapp.CalendarActivity;

public class AgendaMesTask extends AsyncTask<String, Void, AgendaMes> {
	
	private CalendarActivity contexto;
	private ProgressDialog progress;
	private String usuario;
	private Calendar data;
	
	public AgendaMesTask(CalendarActivity contexto, String usuario, Calendar data) {
		this.contexto = contexto;
		this.usuario = usuario;
		this.data = data;
	}
	
	@Override
	protected void onPreExecute() {
		progress = new ProgressDialog((Context) contexto);
		progress.setMessage("Aguarde...");
		progress.show();		
		super.onPreExecute();		
	}
	
	@Override
	protected void onPostExecute(AgendaMes result) {
		super.onPostExecute(result);
		progress.dismiss();
		contexto.resultadoAgendaMes(result);
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected AgendaMes doInBackground(String... params) {
		
		try {
			
			String dataStr = String.format("%04d-%02d-%02d", 
					data.get(Calendar.YEAR), data.get(Calendar.MONTH)+1, data.get(Calendar.DATE));
			String url = String.format("http://ricardoxavier.no-ip.org" 
				+ "/soft-ws3/softws/listames/%s/%s",
				usuario.toLowerCase(), dataStr);
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
			AgendaMes agenda = gson.fromJson(resultStr.toString(), AgendaMes.class);	
			
			return agenda;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
