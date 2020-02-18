package xavier.ricardo.softapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import xavier.ricardo.softapp.R;
import xavier.ricardo.softapp.tasks.AgendaMesTask;
import xavier.ricardo.softapp.tasks.UsuariosTask;

@SuppressWarnings("deprecation")
public class CalendarActivity extends Activity {
	
	private static final int MARGEM = 5;
	private int mes;
	private int ano;
	private int largura;
	private String usuario;
	private boolean administrador;
	private List<Integer> diasAgendados;
	private String responsavel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		setTitle(R.string.title_activity_calendar);
		
		Intent intent = getIntent();
		usuario = intent.getStringExtra("usuario");
		responsavel = usuario;
		administrador = intent.getBooleanExtra("administrador", false);
		
		largura = calculaLarguraCelula();
		
		Spinner spResponsavel = (Spinner) findViewById(R.id.spResponsavel);	
		List<String> list = new ArrayList<String>();
		list.add(usuario);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spResponsavel.setAdapter(dataAdapter);		
		
		Calendar hoje = Calendar.getInstance();
		ano = hoje.get(Calendar.YEAR);
		mes = hoje.get(Calendar.MONTH);
		
		final CalendarActivity contexto = this;
		
		NumberPicker npMes = (NumberPicker) findViewById(R.id.npMes);	
		npMes.setMinValue(1);
		npMes.setMaxValue(12);
		npMes.setValue(hoje.get(Calendar.MONTH) + 1);
		npMes.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				mes = newVal - 1;
				Calendar mesAno = Calendar.getInstance();
				mesAno.set(ano, mes, 1);				
				new AgendaMesTask(contexto, responsavel, mesAno).execute();	
			}
		});
		
		NumberPicker npAno = (NumberPicker) findViewById(R.id.npAno);
		npAno.setMinValue(2000);
		npAno.setMaxValue(3000);		
		npAno.setValue(hoje.get(Calendar.YEAR));
		npAno.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				ano = newVal;
				Calendar mesAno = Calendar.getInstance();
				mesAno.set(ano, mes, 1);				
				new AgendaMesTask(contexto, responsavel, mesAno).execute();	
			}
		});
		
		if (administrador) {
			new UsuariosTask(this).execute();
		}
		
		new AgendaMesTask(this, responsavel, hoje).execute();
		
	}
	
	private void montaCabecalho() {
		
		GridLayout glCalendario = (GridLayout) findViewById(R.id.glCalendario);

		// cria o cabe�alho com os dias da semana
		String[] dias = { "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab" };
		for (int i=0; i<dias.length; i++) {

			String dia = dias[i];
			
			Button celula = new Button(this);
			celula.setText(dia);
			celula.setBackgroundColor(Color.RED);
			celula.setPadding(0, 0, 0, 0);
			
			GridLayout.LayoutParams param = new GridLayout.LayoutParams();
			param.height = largura;
			param.width = largura;
			param.setMargins(MARGEM, MARGEM, MARGEM, MARGEM);
			param.columnSpec = GridLayout.spec(i);
			param.rowSpec = GridLayout.spec(0);
			
			celula.setLayoutParams(param);
			glCalendario.addView(celula);

		}
		
	}
	
	private void montaCalendario() {
		
		GridLayout glCalendario = (GridLayout) findViewById(R.id.glCalendario);
		glCalendario.removeAllViews();
		
		montaCabecalho();
		
		Calendar primeiro = Calendar.getInstance();
		primeiro.set(ano, mes, 1);
		System.out.println(primeiro.getTime().toString());
		
		Calendar ultimo = Calendar.getInstance();
		ultimo.setTime(primeiro.getTime());
		ultimo.add(Calendar.MONTH, 1);
		ultimo.add(Calendar.DAY_OF_MONTH, -1);
		
		int diasMes = ultimo.get(Calendar.DAY_OF_MONTH);
		
		int inicio = primeiro.get(Calendar.DAY_OF_WEEK) - 1;
		int fim = inicio + diasMes - 1;
		
		Button[] botoes = new Button[42]; // 6 * 7
		for (int b=0; b<42; b++) {
		
			GridLayout.LayoutParams param = new GridLayout.LayoutParams();
			param.height = largura;
			param.width = largura;		
			param.setMargins(MARGEM, MARGEM, MARGEM, MARGEM);
			int col = b % 7;
			int lin = 1 + b / 7;
			param.columnSpec = GridLayout.spec(col);
			param.rowSpec = GridLayout.spec(lin);

			Button botao = new Button(this);
			String dia = (b < inicio || b > fim) ? "  " : String.valueOf(b - inicio + 1);
			if (dia.equals("  ")) {
				botao.setVisibility(View.INVISIBLE);
			} else {
				if (diasAgendados.contains(Integer.parseInt(dia))) {
					botao.setTextColor(Color.RED);
				}
			}
			
			botao.setText(dia);
			botao.setPadding(0, 0, 0, 0);
			botao.setLayoutParams(param);
			
			glCalendario.addView(botao);
			botoes[b] = botao;
			
			final Context contexto = this;
			
			botao.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Button b = (Button) v;
					
					Calendar cal = Calendar.getInstance();
					cal.set(ano, mes, Integer.parseInt(b.getText().toString()));
					
					Intent intent = new Intent(contexto, MainActivity.class);
					intent.putExtra("usuario", responsavel);
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					intent.putExtra("data", df.format(cal.getTime()));
					startActivity(intent);					
				}
			});
		}
		
	}
	
	private int calculaLarguraCelula() {
		
		Display display = getWindowManager().getDefaultDisplay();
		Point p = new Point();
		display.getSize(p);
		int larguraTelaPx = p.x;
		
		LinearLayout llCalendario = (LinearLayout) findViewById(R.id.llCalendario);

		// 7 celulas com 5 de espa�amento
		int largura =  larguraTelaPx - (16 * MARGEM) - (llCalendario.getPaddingLeft() * 2);
		largura /= 7;		
		
		return largura;
	}
	
	public void resultadoAgendaMes(AgendaMes agenda) {
		this.diasAgendados = agenda.getDias();
		montaCalendario();
	}
	
	public void resultadoUsuarios(Usuarios usuarios) {
		
		Spinner spResponsavel = (Spinner) findViewById(R.id.spResponsavel);	
		List<String> list = new ArrayList<String>();
		list.add(usuario);
		for (String responsavel : usuarios.getUsuarios()) {
			responsavel = responsavel.trim();
			if (!responsavel.equals(usuario)) {
				list.add(responsavel);
			}
		}
		final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spResponsavel.setAdapter(dataAdapter);	
		
		final CalendarActivity contexto = this;

		spResponsavel.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				responsavel = dataAdapter.getItem(position);
				Calendar mesAno = Calendar.getInstance();
				mesAno.set(ano, mes, 1);				
				new AgendaMesTask(contexto, responsavel, mesAno).execute();					
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
	}

	public void nf(View v) {
		Intent intent = new Intent(this, NFActivity.class);
		startActivityForResult(intent, 1);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		CalendarActivity contexto = this;
		if (resultCode == RESULT_OK) {
			String usuarioData = data.getStringExtra("usuarioData");
			String[] partes = usuarioData.split("\\s+");
			if (partes.length > 1) {
				responsavel = partes[0];
				String ymd = partes[1];
				String y = ymd.substring(0, 4);
				String m = ymd.substring(5, 7);
                String d = ymd.substring(8, 10);

                /*
				Calendar mesAno = Calendar.getInstance();
				mesAno.set(Integer.parseInt(y), Integer.parseInt(m), 1);
				new AgendaMesTask(contexto, responsavel, mesAno).execute();
                 */

                Intent intent = new Intent(contexto, MainActivity.class);
                intent.putExtra("usuario", responsavel);
                intent.putExtra("data", d + "/" + m + "/" + y);
                startActivity(intent);
            }
		}
	}
}
