package com.pakos.lcw;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;


public class ledControlv2 extends AppCompatActivity {


    Button btnSend, btnDis,image,timeBtn;
    SeekBar brightness;
    TextView lumn;
    String address = null;
    EditText command;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Context mContext;
    int font;

    //Bitmap bMap = BitmapFactory.decodeFile("file:///com/led/led/car.bmp");
    //Drawable myImg=getResources().getDrawable(R.drawable.tsiakas);
    Bitmap bMap;// = BitmapFactory.decodeResource(getResources(), R.drawable.tsiakas);

    public void SetContext(Context context)
    {
        mContext = context;
    }

    private RadioGroup radioGroup;
    private RadioButton small, medium, silent;


    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
       /* radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.silent) {
                    Toast.makeText(getApplicationContext(), "choice: Silent",
                            Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.sound) {
                    Toast.makeText(getApplicationContext(), "choice: Sound",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "choice: Vibration",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        small = (RadioButton) findViewById(R.id.sound);
        medium = (RadioButton) findViewById(R.id.vibration);
        silent = (RadioButton) findViewById(R.id.silent);
*/
        //bMap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_drawable_image);

        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //view of the ledControl
        setContentView(R.layout.activity_led_control);

        //call the widgtes

        btnSend = (Button)findViewById(R.id.sendBtn);
        btnDis = (Button)findViewById(R.id.button4);
        command = (EditText)findViewById(R.id.commandTxt);
        image = (Button)findViewById(R.id.imgBtn);
        timeBtn = (Button)findViewById(R.id.timeBtn);


        /*radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //int selectedId = radioGroup.getCheckedRadioButtonId();
                // find which radioButton is checked by id
                if(checkedId == small.getId()) {
                    font=1;
                } else if(checkedId == medium.getId()) {
                    font=2;
                } else {
                    font=3;
                }
            }
        });
*/
        new ConnectBT().execute(); //Call the class to connect

        //commands to be sent to bluetooth




        btnDis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });

        timeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (btSocket!=null)
                {
                    try
                    {
                        DateFormat df = new SimpleDateFormat("HHmmss");
                        String date = df.format(Calendar.getInstance().getTime());
                        //SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
                        //String currentDateandTime = sdf.format(new Date());
                        btSocket.getOutputStream().write(("time"+date).getBytes());
                    }
                    catch (IOException e)
                    {
                        msg("Error");
                    }
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendText();
            }
        });


        image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendImage(mContext); //send image stream
            }
        });

    }

    @Override
    public void onBackPressed() {
        Disconnect();
    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
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
                btSocket.getOutputStream().write(command.getText().toString().getBytes());

                /*btSocket.getOutputStream().write("img".getBytes());
                if (command.getText().toString()=="image"){
                    for (int i=0; i<160; i++){

                        int[] row = new int[128];
                        bMap.getPixels(row,0, bMap.getWidth(), i, 0, 128, 1);

                        byte[] flex = new byte[4*128];

                        for(int j=0; j <128; j++)
                        {
                            flex[j]   = (byte) (row[j] >> 24);
                            flex[j+1] = (byte) (row[j] >> 16);
                            flex[j+2] = (byte) (row[j] >> 8);
                            flex[j+3] = (byte) (row[j] >> 0);
                        }
                        btSocket.getOutputStream().write(flex);
                    }
                    btSocket.getOutputStream().write("img".getBytes());

                }
                */


            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void sendImage(Context context)
    {
        if (btSocket!=null)
        {
            try
            {
                //Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.tsiakas);
                //bMap=drawableToBitmap(drawable);
                bMap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_drawable_image);

                btSocket.getOutputStream().write("IMG_ST".getBytes());

                for (int i=0; i<160; i++){

                    // row contains 4byte for each pixel
                    int[] row = new int[128];
                    bMap.getPixels(row,0, 128, i, 0, 128, 1);

                    // rowL will contain one byte for each ARGB for each pixel
                    byte[] rowL = new byte[128*4];

                    for (int j=0; j < 128; j++)
                    {
                        rowL[4*j]   = (byte) (row[j] >> 24);
                        rowL[4*j+1] = (byte) (row[j] >> 16);
                        rowL[4*j+2] = (byte) (row[j] >> 8 );
                        rowL[4*j+3] = (byte) (row[j] >> 0 );
                    }


                    // rowD will contain one byte for each value of each ARGB of each pixel divided by 16
                    // so that it fits in half a byte
                    byte[] rowD = new byte[128*4];

                    for (int j = 0; j<128; j++)
                    {
                        rowD[4*j]   = (byte) (rowL[j]   / ((byte)16));
                        rowD[4*j+1] = (byte) (rowL[j+1] / ((byte)16));
                        rowD[4*j+2] = (byte) (rowL[j+2] / ((byte)16));
                        rowD[4*j+3] = (byte) (rowL[j+3] / ((byte)16));
                    }

                    // rowS is what will be sent. 2 bytes for each ARGB
                    byte[] rowS = new byte[128*2];

                    for(int j=0; j <128; j++)
                    {
                        byte byte1 = (byte) (rowD[4*j]   << 4);
                        byte byte2 = (byte) (rowD[4*j+2] << 4);

                        rowS[2*j]     = (byte) (byte1 | rowD[4*j+1]);
                        rowS[2*j+1]   = (byte) (byte2 | rowD[4*j+3]);
                    }

                    btSocket.getOutputStream().write(rowS);

                    // Delete this
                    //break;
                }
                //btSocket.getOutputStream().write("endimg".getBytes());


            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }



    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ledControlv2.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                 myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                 BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                 btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                 BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                 btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
