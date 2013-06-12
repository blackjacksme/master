package com.example.user;

import java.util.List;


import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {

    private NfcAdapter mNfcAdapter;  
    private ImageView pict;
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    String SSID_now;
    
	 @Override  
	 public void onCreate(Bundle savedInstanceState) {  
	   super.onCreate(savedInstanceState);  
	   setContentView(R.layout.activity_home);  
	   mNfcAdapter = NfcAdapter.getDefaultAdapter(this);  
	   if (mNfcAdapter == null) {
		   Toast.makeText(this, "No NFC detected on this phone", Toast.LENGTH_SHORT).show();
	   }
	   mWifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
	   
	   if(mWifiManager.isWifiEnabled()){
		   mWifiInfo = mWifiManager.getConnectionInfo();	   
		   SSID_now = mWifiInfo.getSSID().toString();
	   } else{
		   SSID_now = "Not Connected";
	   }
	   TextView show_SSID = (TextView) findViewById(R.id.SSID);
	   show_SSID.setText(SSID_now);
	   
	   pict = (ImageView) findViewById(R.id.tap_ur_phone);
	   pict.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String networkSSID = "Xirka-WLAN";
     		   	final String networkPass = "SS@XiRKa";
				AlertDialog alertDialog = new AlertDialog.Builder(Home.this).create();
			 	alertDialog.setTitle("Now Connecting...");
		        alertDialog.setMessage("You're about to connect to '"+networkSSID+"'");
		        alertDialog.setIcon(R.drawable.wifi2);
		        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Proceed", new DialogInterface.OnClickListener() {
		        	public void onClick(DialogInterface dialog,int which) {			 
		            // Settings to invoke YES event
	        		// setup a wifi configuration
		        		if(!mWifiManager.isWifiEnabled()){
	        			    mWifiManager.setWifiEnabled(true);
	        			    while (mWifiManager.getWifiState() == mWifiManager.WIFI_STATE_ENABLING ){
	        			    }
	        			  }

	        		   WifiConfiguration conf = new WifiConfiguration();
	        		   conf.SSID = "\"" + networkSSID + "\"";
	        		   conf.preSharedKey = "\""+ networkPass +"\"";
	        		   
	        		   mWifiManager.addNetwork(conf);
	        		   
	        	       // connect to and enable the connection
		        		List<WifiConfiguration> list = mWifiManager.getConfiguredNetworks();
		        		for( WifiConfiguration i : list ) {
		        		    if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
		        		         mWifiManager.disconnect();
		        		         mWifiManager.enableNetwork(i.networkId, true);
		        		         mWifiManager.reconnect();               
		        		         break;
		        		    }           
		        		 }	
		        	TextView confirm_connect = (TextView) findViewById(R.id.SSID);
		        	confirm_connect.setText(networkSSID);
		            Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
		            }
		        });
		 
		        	// Settings to invoke NO event
		        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		            // Write your code here to invoke NO event
		            Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
		            }
		        });
		        // Showing Alert Message
		        alertDialog.show();
		}
	});
 }
}	 