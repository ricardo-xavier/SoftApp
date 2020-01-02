package br.com.softplacemoveis.softapp.tasks;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import br.com.softplacemoveis.softapp.Agenda;
import xavier.ricardo.softapp.NotificationReceiver;

public class NotificadorService extends IntentService {
	
	private int diaUltimaNofificacao = -1;
	private int qtdUltimaNotificacao;
	private static int TEMPO_SLEEP = 1000 * 60 * 60;

	public NotificadorService() {
		super("NotificadorService");
	}

	public NotificadorService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {

			String usuario = intent.getStringExtra("usuario");
			//Log.i("SOFTAPP", "service:" + usuario);
			
			while (true) {
				
				Calendar data = Calendar.getInstance();
				int hoje = data.get(Calendar.DAY_OF_MONTH);
				
				// nao notifica antes das 08:00
				if (data.get(Calendar.HOUR_OF_DAY) < 8) {
					Thread.sleep(TEMPO_SLEEP);
					continue;
				}
				
				// busca a agenda do dia seguinte
				data.add(Calendar.DATE, 1);
				Agenda agenda = getAgenda(usuario, data);
				
				if ((agenda.getCompromissos() == null) || (agenda.getCompromissos().size() == 0)) {
					// sem compromissos
					Thread.sleep(TEMPO_SLEEP);
					continue;					
				}
				
				// notifica se ainda nao notificou no dia ou se o numero de compromissos mudou
				boolean notifica = false;
				int qtdCompromissos = agenda.getCompromissos().size();
				if (diaUltimaNofificacao != hoje)  {
					notifica = true;
				} else if (qtdCompromissos != qtdUltimaNotificacao) {
					notifica = true;
				}
				if (!notifica) {
					Thread.sleep(TEMPO_SLEEP);
					continue;
				}
				
				// notifica
				qtdUltimaNotificacao = qtdCompromissos;
				diaUltimaNofificacao = hoje;
				AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
				Intent notificationIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
				notificationIntent.putExtra("usuario", usuario);
				String dataStr = String.format("%02d/%02d/%04d", 
						data.get(Calendar.DATE),
						data.get(Calendar.MONTH)+1,
						data.get(Calendar.YEAR));
				notificationIntent.putExtra("data", dataStr);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, 0);
				Calendar calendar = Calendar.getInstance();
				alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
				
				Thread.sleep(TEMPO_SLEEP);
				
			}
		} catch (Exception e) {
			
		}
			
	}
	
	private Agenda getAgenda(String usuario, Calendar data) throws ClientProtocolException, IOException {
		
		String dataStr = String.format("%04d-%02d-%02d", 
				data.get(Calendar.YEAR), data.get(Calendar.MONTH)+1, data.get(Calendar.DATE));
		String url = String.format("http://ricardoxavier.no-ip.org" 
			+ "/soft-ws3/softws/lista/%s/%s",
			usuario.toLowerCase(), dataStr);
		//Log.i("SOFTAPP", "service:" + url);
		
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
		//Log.i("SOFTAPP", "service:" + resultStr.toString());
		
		Gson gson = new Gson();
		Agenda agenda = gson.fromJson(resultStr.toString(), Agenda.class);	
		return agenda;

	}

}
