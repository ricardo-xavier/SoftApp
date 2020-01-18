package xavier.ricardo.softapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xavier.ricardo.softapp.R;

public class AnexoAdapter extends BaseAdapter {
	private AnexoActivity context;
	private List<Anexo> anexos;
	
	public AnexoAdapter(AnexoActivity context, List<Anexo> anexos) {
		this.context = context;
		this.anexos = anexos;
	}
	
	@Override
	public int getCount() {
		return getAnexos().size(); 
	}

	@Override
	public Object getItem(int position) {
		return getAnexos().get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if ((anexos == null) || (anexos.size() <= position)) {
			return null;
		}
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.anexo, null);
		
		Anexo anexo = getAnexos().get(position);
		
		TextView tvAnexo = (TextView) v.findViewById(R.id.tvAnexo);
		tvAnexo.setText(anexo.getCodigo());
		
		return v;		
	}

	public List<Anexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

}
