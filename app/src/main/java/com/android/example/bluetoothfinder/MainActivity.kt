package com.android.example.bluetoothfinder

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {

    private lateinit var listView : ListView
    private lateinit var statusTextView : TextView
    private lateinit var searchButton : Button
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var bluetoothManager: BluetoothManager
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
                Log.i("Action", intent?.action!!)
        }
    }

    fun searchClicked(view : View){
        statusTextView.text = getString(R.string.searching)
        searchButton.isEnabled = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bluetoothManager = this.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

        listView = findViewById(R.id.listView)
        statusTextView = findViewById(R.id.statusTextView)
        searchButton = findViewById(R.id.searchButton)

        bluetoothAdapter = bluetoothManager.adapter

        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)

        registerReceiver(broadcastReceiver, intentFilter)

        bluetoothAdapter.startDiscovery()
    }
}