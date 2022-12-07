package com.android.example.bluetoothfinder

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private lateinit var listView : ListView
    private lateinit var statusTextView : TextView
    private lateinit var searchButton : Button
    private lateinit var bluetoothAdapter: BluetoothAdapter
    // private lateinit var bluetoothManager: BluetoothManager
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            Log.i("Action", intent?.action!!)
            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED == intent.action){
                statusTextView.text = getString(R.string.finished)
                searchButton.isEnabled = true
            }
        }
    }

    fun searchClicked(view : View){
        statusTextView.text = getString(R.string.searching)
        searchButton.isEnabled = false
        bluetoothAdapter.startDiscovery()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            Log.i("info", "No fine location permissions")

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_SCAN),
                1)
        }
//        bluetoothManager = this.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

        listView = findViewById(R.id.listView)
        statusTextView = findViewById(R.id.statusTextView)
        searchButton = findViewById(R.id.searchButton)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)

        this.registerReceiver(broadcastReceiver, intentFilter)
    }
}