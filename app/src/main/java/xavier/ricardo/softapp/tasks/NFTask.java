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

import xavier.ricardo.softapp.Agenda;
import xavier.ricardo.softapp.NFActivity;

public class NFTask extends AsyncTask<String, Void, Agenda> {

    private NFActivity contexto;
    private ProgressDialog progress;
    private String nf;

    public NFTask(NFActivity contexto, String nf) {
        this.contexto = contexto;
        this.nf = nf;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog((Context) contexto);
        progress.setMessage("Aguarde...");
        progress.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Agenda result) {
        super.onPostExecute(result);
        progress.dismiss();
        contexto.resultadoListaNF(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Agenda doInBackground(String... params) {

        try {

            String url = String.format("http://ricardoxavier.no-ip.org"
                            + "/soft-ws3/softws/listaNF/%s", nf);
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
            Agenda agenda = gson.fromJson(resultStr.toString(), Agenda.class);

            return agenda;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
