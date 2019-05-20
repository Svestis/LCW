package com.pakos.lcw;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.util.UUID;

import yuku.ambilwarna.AmbilWarnaDialog;

public class sendText extends AppCompatActivity {
    Button btnSend,bColor,tColor;
    int bColorint,tColorint;
    View text, background;
    String address = null;
    String bColorHtml = "#FFFFFF",msg;
    String tColorHtml = "#000000";
    EditText command;
    private ProgressDialog progress;
    public static String EXTRA_ADDRESS = "device_address";
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);
        new ConnectBT().execute();
        btnSend = findViewById(R.id.sendBtn);
        command = findViewById(R.id.commandTxt);
        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bColorHtml = String.format("#%06X", (0xFFFFFF & bColorint));
                tColorHtml = String.format("#%06X", (0xFFFFFF & tColorint));
                msg="text"+bColorHtml.substring(bColorHtml.length()-6)+
                        tColorHtml.substring(tColorHtml.length()-6)+command.getText();
                sendText();
            }
        });
        text = findViewById(R.id.colorshowtext);
        background = findViewById(R.id.colorshowbackground);
        bColorint = ContextCompat.getColor(sendText.this,android.R.color.white);
        tColorint = ContextCompat.getColor(sendText.this, android.R.color.black);
        bColor = findViewById(R.id.pickbackcolor);
        tColor = findViewById(R.id.picktextcolor);
        bColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openColorPickerback();
            }
        });

        tColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPickertext();
            }
        });
    }

    public void openColorPickertext(){
        AmbilWarnaDialog colorPicker2 = new AmbilWarnaDialog(this, tColorint, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                tColorint = color;
                text.setBackgroundColor(tColorint);
            }
        });
        colorPicker2.show();
    }

    public void openColorPickerback(){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, bColorint, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                bColorint = color;
                background.setBackgroundColor(bColorint);
            }
        });
        colorPicker.show();
    }

    @Override
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

    private void sendText()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(msg.getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(sendText.this, "Loading...", "Please wait!");
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
