package xavier.ricardo.softapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import xavier.ricardo.softapp.R;

public class AgendaAdapter extends BaseAdapter {
	private MainActivity context;
	private List<Compromisso> compromissos;
	
	public AgendaAdapter(MainActivity context, List<Compromisso> compromissos) {
		this.context = context;
		this.compromissos = compromissos;
	}
	
	@Override
	public int getCount() {
		return getCompromissos().size(); 
	}

	@Override
	public Object getItem(int position) {
		return getCompromissos().get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if ((compromissos == null) || (compromissos.size() <= position)) {
			return null;
		}
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.agenda, null);

		Compromisso compromisso = getCompromissos().get(position);
		
		if ((compromisso.getParceiro() == null) || (compromisso.getParceiro().trim().equals(""))) {
			ImageButton ibEndereco = (ImageButton) v.findViewById(R.id.ibEndereco);
			ibEndereco.setVisibility(View.INVISIBLE);
		}
		
		/*
		if ((compromisso.getContato() == null) || (compromisso.getContato().trim().equals(""))) {
			ImageButton ibLigar = (ImageButton) v.findViewById(R.id.ibLigar);
			ibLigar.setVisibility(View.INVISIBLE);
		}
		*/
		
		TextView tvTitulo = (TextView) v.findViewById(R.id.tvTitulo);
		if (compromisso.getNatureza().equals("EMPTY")) {
			tvTitulo.setText("Nenhum compromisso");
			return v;
		}
		
		tvTitulo.setText(compromisso.getHora());
		
		TextView tvNatureza = (TextView) v.findViewById(R.id.tvNatureza);
		tvNatureza.setText(compromisso.getNatureza());
		
		TextView tvTexto = (TextView) v.findViewById(R.id.tvTexto);
		tvTexto.setText(compromisso.getPendencia());
		
		TextView tvParceiro = (TextView) v.findViewById(R.id.tvParceiro);
		tvParceiro.setText(compromisso.getParceiro());
		
		TextView tvContato = (TextView) v.findViewById(R.id.tvContato);
		String contato = compromisso.getContato();
		if ((compromisso.getPapel() != null) && !compromisso.getPapel().equals("")) {
			contato += " - " + compromisso.getPapel();
		}
		tvContato.setText(contato);
		
		TextView tvFones = (TextView) v.findViewById(R.id.tvFones);
		
		String fones = "";
		String fone1 = compromisso.getFone1();
		if ((fone1 != null) && !fone1.replace("0", "").trim().equals("")) {
			fones += formata(fone1) + " ";
		}
		String fone2 = compromisso.getFone2();
		if ((fone2 != null) && !fone2.replace("0", "").trim().equals("")) {
			fones += formata(fone2) + " ";
		}
		String celular = compromisso.getCelular();
		if ((celular != null) && !celular.replace("0", "").trim().equals("")) {
			fones += formata(celular);
		}
		
		tvFones.setText(fones);
		
		if (fones.equals("")) {
			ImageButton ibLigar = (ImageButton) v.findViewById(R.id.ibLigar);
			ibLigar.setVisibility(View.INVISIBLE);
		}
		
		ImageButton ibEndereco = (ImageButton) v.findViewById(R.id.ibEndereco);
		ibEndereco.setTag(compromisso.getHora());
		
		Button btDetalhes = (Button) v.findViewById(R.id.btDetalhes);
		if (compromisso.getCodFornecedor() != null) {
			btDetalhes.setVisibility(View.VISIBLE);
			btDetalhes.setText(String.format("%s %s %d-%d", 
					compromisso.getCodFornecedor(),
					compromisso.getDatOrcamento(),
					compromisso.getCodOrcamento(),
					compromisso.getNroPedido()));
		} else {
			btDetalhes.setVisibility(View.INVISIBLE);			
		}
		
		Button btEncerrar = (Button) v.findViewById(R.id.btEncerrar);
		btEncerrar.setVisibility(View.VISIBLE);
		String chave = String.format("%s;%s;%s;%s;%s;%s;%s", 
				compromisso.getUsuario(), compromisso.getData(), 
				compromisso.getEncerramento() != null ? compromisso.getEncerramento() : "",
				compromisso.getNome() != null ? compromisso.getNome() : "",
				compromisso.getDocumento() != null ? compromisso.getDocumento() : "",
				compromisso.getEmail() != null ? compromisso.getEmail() : "",
				compromisso.getJson() != null ? compromisso.getJson() : "");
		btEncerrar.setTag(chave);

		Button btAnexos = (Button) v.findViewById(R.id.btAnexos);
		if ((compromisso.getAnexos() != null)
				&& (compromisso.getAnexos().size() > 0)) {
			btAnexos.setVisibility(View.VISIBLE);
			btAnexos.setText(String.format("Anexos(%d)", 
					compromisso.getAnexos().size()));
		} else {
			btAnexos.setVisibility(View.INVISIBLE);			
		}

		TextView tvPedido = (TextView) v.findViewById(R.id.tvPedido);
		ImageButton ivPedido = (ImageButton) v.findViewById(R.id.ivPedido);
		tvPedido.setVisibility(View.INVISIBLE);
		ivPedido.setVisibility(View.INVISIBLE);

		TextView tvOrcamento = (TextView) v.findViewById(R.id.tvOrcamento);
		ImageButton ivOrcamento = (ImageButton) v.findViewById(R.id.ivOrcamento);
		tvOrcamento.setVisibility(View.INVISIBLE);
		ivOrcamento.setVisibility(View.INVISIBLE);

		/*
		if (compromisso.getPedido() == null) {
			tvPedido.setVisibility(View.INVISIBLE);
			ivPedido.setVisibility(View.INVISIBLE);
			
		} else {
			tvPedido.setVisibility(View.VISIBLE);
			ivPedido.setVisibility(View.VISIBLE);
			// /usr/local/tomcat/webapps/ROOT/soft/pedidos/TECNOFLEX2017994_1.pdf
			String[] partes = compromisso.getPedido().split("/");
			String pdf = partes[partes.length-1];
			tvPedido.setText(pdf);
		}

		if (compromisso.getOrcamento() == null) {
			tvOrcamento.setVisibility(View.INVISIBLE);
			ivOrcamento.setVisibility(View.INVISIBLE);
		} else {
			tvOrcamento.setVisibility(View.VISIBLE);
			ivOrcamento.setVisibility(View.VISIBLE);
			String[] partes = compromisso.getOrcamento().split("/");
			String pdf = partes[partes.length-1];
			tvOrcamento.setText(pdf);
		}
		*/
		
		return v;		
	}

	private String formata(String fone) {
		fone = fone.trim();
		
		switch (fone.length()) {
		
		case 10:
			// 3133789526
			// 01234567890
			return "(" + fone.substring(0, 2) + ")" 
					+ fone.substring(2, 6) + "-" + fone.substring(6);				
		
		case 11:
			// 31988749526
			// 03133789526
			// 012345678901
			if (fone.charAt(0) == '0') {
				return "(" + fone.substring(1, 3) + ")" 
						+ fone.substring(3, 7) + "-" + fone.substring(7);								
			} else {
				return "(" + fone.substring(0, 2) + ")" 
						+ fone.substring(2, 7) + "-" + fone.substring(7);				
			}
		}
		
		if (fone.length() < 10) {
			int pad = 10 - fone.length();
			fone = "0000000000".substring(0, pad) + fone;
		}
		return "(" + fone.substring(0, 2) + ")" 
				+ fone.substring(2, 6) + "-" + fone.substring(6);			
	}

	public List<Compromisso> getCompromissos() {
		return compromissos;
	}

	public void setCompromissos(List<Compromisso> compromissos) {
		this.compromissos = compromissos;
	}

}
