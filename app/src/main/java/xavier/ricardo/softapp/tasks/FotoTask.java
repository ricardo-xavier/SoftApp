package xavier.ricardo.softapp.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import xavier.ricardo.softapp.Imagem;
import xavier.ricardo.softapp.MainActivity;
import xavier.ricardo.softapp.R;

public class FotoTask extends AsyncTask<String, Void, String> {

    private MainActivity context;
    private ProgressDialog progress;
    private String image;
    private String id;
    private String fornecedor;
    private String data;
    private String orcamento;

    public FotoTask(MainActivity context, String fornecedor, String data, String orcamento, String image, String id) {
        this.context = context;
        this.fornecedor = fornecedor;
        this.data = data;
        this.orcamento = orcamento;
        this.image = image;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog((Context) context);
        String wait = context.getString(R.string.wait);
        progress.setMessage(wait);
        progress.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progress.dismiss();
        context.onTaskResult(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            String url = "http://ricardoxavier.no-ip.org/soft-ws3/softws/foto";

            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            Imagem req = new Imagem();
            req.setFornecedor(fornecedor);
            req.setData(data);
            req.setOrcamento(Integer.parseInt(orcamento));
            req.setImage64(image);
            req.setId(id);

            Gson gson = new Gson();
            String json = gson.toJson(req);

            HttpEntity httpEntity = new ByteArrayEntity(json.getBytes());
            httpPost.setEntity(httpEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);

            InputStream inputStream = httpResponse.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder resultStr = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                resultStr.append(line + "\n");
            }
            inputStream.close();

            return resultStr.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
