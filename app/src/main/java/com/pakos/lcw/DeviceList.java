package com.pakos.lcw;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.circularreveal.CircularRevealRelativeLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import java.util.ArrayList;
import java.util.Set;


public class DeviceList extends AppCompatActivity
{
    Button btnPaired;
    SwipeMenuListView devicelist;
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";


    public void alert_Bluetooth(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(DeviceList.this);
        final View customAlert = getLayoutInflater().inflate(R.layout.action_request_enable_bluetooth, null);
        builder.setCancelable(false);
        ImageButton donotenable = customAlert.findViewById(R.id.cancel_enable_blt);
        ImageButton enable = customAlert.findViewById(R.id.enable_blt);
        builder.setView(customAlert);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        donotenable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myBluetooth.enable();
                dialog.dismiss();
            }
        });
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
                    pairedDevicesList();
                }
                else {
                    alert_Bluetooth();
                }
            }
        });
    }
    private void pairedDevicesList()
    {
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

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem connect = new SwipeMenuItem(
                        getApplicationContext());
                connect.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                connect.setWidth((90));
                connect.setIcon(R.drawable.ic_bluetooth);
                menu.addMenuItem(connect);
            }
        };
        devicelist.setMenuCreator(creator);
        devicelist.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        String address = myBluetooth.getAddress();
                        Intent blt_menu = new Intent(DeviceList.this, ledControl.class);
                        blt_menu.putExtra(EXTRA_ADDRESS, address);
                        startActivity(blt_menu);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_device_list, menu);
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
}
