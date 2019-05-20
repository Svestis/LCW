package com.pakos.lcw;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.IOException;
import java.util.UUID;

public class AppMenu extends AppCompatActivity {
    ImageButton button1, button2, button3, button4, button5, button6;
    Button disconnect;
    final String image = "logo.bmp";
    public static String EXTRA_ADDRESS = "device_address";
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_menu);
        button1 = findViewById(R.id.menu_act1);
        button2 = findViewById(R.id.menu_act2);
        button3 = findViewById(R.id.menu_act3);
        button4 = findViewById(R.id.menu_act4);
        button5 = findViewById(R.id.menu_act5);
        button6 = findViewById(R.id.menu_act6);
        disconnect = findViewById(R.id.disc);
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);
        new ConnectBT().execute();
        final Button disconnect = findViewById(R.id.disc);
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Disconnect();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent color = new Intent(AppMenu.this, colorPicker.class);
                color.putExtra(EXTRA_ADDRESS, address);
                Disconnect();
                startActivity(color);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent img = new Intent(AppMenu.this, imageGallery.class);
                img.putExtra(EXTRA_ADDRESS, address);
                Disconnect();
                startActivity(img);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mic = new Intent(AppMenu.this, microphone.class);
                mic.putExtra(EXTRA_ADDRESS, address);
                startActivity(mic);
                Disconnect();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendText = new Intent(AppMenu.this, sendText.class);
                sendText.putExtra(EXTRA_ADDRESS, address);
                startActivity(sendText);
                Disconnect();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent time = new Intent(AppMenu.this, time.class);
                time.putExtra(EXTRA_ADDRESS, address);
                startActivity(time);
                Disconnect();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendText();
            }
        });

        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Disconnect();
                finish();
            }
        });
    }

    private void sendText()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(image.getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void Disconnect()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.close();
            }
            catch (IOException e)
            { msg("Error");}
        }
    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
           progress = ProgressDialog.show(AppMenu.this, "Connecting...", "Please wait!");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed.");
                finish();
            }
            else
            {
                msg("Connected");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

}
