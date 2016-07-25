package com.example.nasi.wifiscandemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listWifi;
    Button btn;
    ArrayList<ScanResult> dataWifi=new ArrayList<ScanResult>();
    ArrayList<String> nameWifi=new ArrayList<String>();
    WifiManager wifi;
    List<ScanResult> results;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
        listWifi=(ListView)findViewById(R.id.listView);
        btn=(Button)findViewById(R.id.button);
        wifi =(WifiManager) getSystemService(Context.WIFI_SERVICE);
        final Intent manhinh2=new Intent(MainActivity.this,Main2Activity.class);

        if (wifi.isWifiEnabled() == false)
        {
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }

        //xu ly bam nut scan
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataWifi.clear();
                nameWifi.clear();
                wifi.startScan();
                results = wifi.getScanResults();
                Toast.makeText(getBaseContext(), "Scanning.... with " + results.size() + " result", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < results.size(); i++) {
                    dataWifi.add(results.get(i));
                    nameWifi.add(results.get(i).SSID+"\n"+results.get(i).level+" db");
                    adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,nameWifi);
                }
                listWifi.setAdapter(adapter);

            }
        });
        //xu ly chon mot item tren list
        listWifi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                manhinh2.putExtra("data",
                        "SSID: " + dataWifi.get(position).SSID + "\n"
                                + "BSSID: " + dataWifi.get(position).BSSID +"\n"
                                + "Capabilities: " + dataWifi.get(position).capabilities+"\n"
                                + "Frequency: " + dataWifi.get(position).frequency+"\n"
                                + "Level: " + dataWifi.get(position).level
                );
                startActivity(manhinh2);
            }
        });
    }


}
