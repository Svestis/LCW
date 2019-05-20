package com.pakos.lcw;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.util.UUID;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class microphone extends AppCompatActivity {
    String command = null;
    private PulsatorLayout mPulsator;
    Button listen;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microphone);
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);
        new ConnectBT().execute();
        mPulsator = findViewById(R.id.pulsator);
        listen = findViewById(R.id.startlistening);
        listen.setOnClickListener(new View.OnClickListener() {
            Boolean clicked = true;
            @Override
            public void onClick(View view) {
                if (clicked){
                    listen.setText(R.string.stoplistening);
                    listen.setBackgroundColor(getResources().getColor(R.color.transparentv2));
                    clicked = false;
                    mPulsator.setCount(4);
                    mPulsator.setDuration(5000);
                    mPulsator.setInterpolator(0);
                    command = "equal";
                    mPulsator.start();
                    sendText();
                }
                else{
                    listen.setText(R.string.startlistening);
                    listen.setBackgroundResource(android.R.drawable.btn_default);
                    command = "b000000";
                    mPulsator.stop();
                    clicked = true;
                    sendText();
                }
            }
        });
    }

    private void sendText()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(command.getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }


    public void onBackPressed(){
        Disconnect();
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
        finish();

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
            progress = ProgressDialog.show(microphone.this, "Loading...", "Please wait!");
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
                msg("Connection Interrupted");
                finish();
            }
            else
            {
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
