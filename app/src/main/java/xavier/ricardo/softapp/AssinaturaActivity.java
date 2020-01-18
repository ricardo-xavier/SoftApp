package xavier.ricardo.softapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import xavier.ricardo.softapp.R;

public class AssinaturaActivity extends Activity {
	
	private List<List<Ponto>> partes = new ArrayList<List<Ponto>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assinatura);
		DrawView.setReadOnly(false);
		
		partes = null;
		if (DrawView.getPartes() != null) {
			partes = new ArrayList<List<Ponto>>();
			for (List<Ponto> parte : DrawView.getPartes()) {
				List<Ponto> pontos = new ArrayList<Ponto>();
				for (Ponto ponto : parte) {
					pontos.add(ponto);
				}
				partes.add(pontos);
			}
		}
	}

	public void limpa(View v) {
		DrawView assinatura = (DrawView) findViewById(R.id.vwAssinatura);
		assinatura.limpa();
	}

	public void cancela(View v) {
		limpa(v);
		DrawView.setPartes(partes);
		DrawView.setReadOnly(true);
		finish();
	}

	public void grava(View v) {
		DrawView.setReadOnly(true);
		finish();
	}
	
}
