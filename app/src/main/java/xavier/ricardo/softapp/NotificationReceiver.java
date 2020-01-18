package xavier.ricardo.softapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

public class NotificationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//Log.i("SOFTAPP", "receiver");
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(3000);
        String usuario = intent.getStringExtra("usuario");
        String data = intent.getStringExtra("data");
        //Log.i("SOFTAPP", "receiver:" + usuario);
        //Log.i("SOFTAPP", "receiver:" + data);
        Intent main = new Intent(context, MainActivity.class);
        main.putExtra("usuario", usuario);
        main.putExtra("data", data);
        main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(main);
	}

}
