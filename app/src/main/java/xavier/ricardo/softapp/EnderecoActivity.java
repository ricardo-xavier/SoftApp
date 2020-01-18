package xavier.ricardo.softapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import xavier.ricardo.softapp.R;

@SuppressWarnings("deprecation")
public class EnderecoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_endereco);
		setTitle(R.string.title_activity_endereco);
		
		Intent intent = getIntent();
		String rua = intent.getStringExtra("rua");
		String bairro = intent.getStringExtra("bairro");
		
		TextView tvRua = (TextView) findViewById(R.id.tvRua);
		tvRua.setText(rua);
		
		TextView tvBairro = (TextView) findViewById(R.id.tvBairro);
		tvBairro.setText(bairro);
		
		String endereco = (rua + "," + bairro).replace(' ', '+');
		
		WebView wvMapa = (WebView) findViewById(R.id.wvMapa);
		wvMapa.getSettings().setJavaScriptEnabled(true);
		wvMapa.loadUrl("https://www.google.com/maps/search/?api=1&query=" + endereco);		
		
	}

}
