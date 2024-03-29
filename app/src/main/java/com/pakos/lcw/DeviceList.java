package com.pakos.lcw;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Set;

public class DeviceList extends AppCompatActivity
{
    Button btnPaired;
    ListView devicelist;
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";

    public void alert_Bluetooth(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(DeviceList.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.enable_bluetooth);
        builder.setMessage(R.string.enable_bluetooth_sub);
        builder.setPositiveButton(R.string.enable, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myBluetooth.enable();
            }
        });
        builder.setNegativeButton(R.string.dontenable,null);
        builder.show();
    }

    public String device_type(int i){
        String x;
        if(i==1024){
            x="\uD83D\uDD0A" +" | " + "\uD83D\uDCF9"; //audio-video
        }
        else if (i==256){
            x="\uD83D\uDCBB"; //computer
        }
        else if (i==2304){
            x="\u2695"; //health
        }
        else if (i==1536){
            x="\uD83D\uDDBC"; //imaging
        }
        else if (i==768){
            x="\uD83D\uDDA7";//networking
        }
        else if (i==1280){
            x="\uD83D\uDDAE"; //peripheral
        }
        else if (i==512){
            x="\uD83D\uDCF1"; //phone
        }
        else if (i==2048){
            x="\uD83C\uDFAE"; //toy
        }
        else if (i==1792){
            x="\u231A"; //wearable
        }
        else {
            x="\u003F"; //miscellaneous
        }
        return x;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_list_slide);
        btnPaired = findViewById(R.id.buttonpairdevices);
        devicelist = findViewById(R.id.swipe_device_list_view);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if(myBluetooth == null)
        {
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            finish();
        }
        else if(!myBluetooth.isEnabled())
        {
            alert_Bluetooth();
        }

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (myBluetooth.isEnabled()) {
                    showpaired();
                }
                else {
                    alert_Bluetooth();
                }
            }
        });

        if (myBluetooth.isEnabled()){
            showpaired();
        }
    }

    private void showpaired(){
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();
        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                list.add(device_type(bt.getBluetoothClass().getMajorDeviceClass()) + " " + bt.getName() + "\n" + bt.getAddress());
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_device_list, menu);
        return true;
    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length()-17);
            Intent blt_menu = new Intent(DeviceList.this, AppMenu.class);
            blt_menu.putExtra(EXTRA_ADDRESS, address);
            startActivity(blt_menu);
        }
    };
}
