package com.doudou.cn.mytestble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 蓝牙的搜索过程
 */


public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getName();

    @Bind(R.id.recyclear)
    RecyclerView recyclear;
    @Bind(R.id.btn_seatch)
    TextView btnSeatch;

    private List<BluetoothDevice> deviceList ;
    private DeviceInfoRecyclerAdapter adapter ;

    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 100;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView();

    }

    // 初始化recyclerView 列表数据
    private void initRecyclerView() {
        // 设置线性布局内容
        deviceList = new ArrayList<>();
        adapter = new DeviceInfoRecyclerAdapter(MainActivity.this,deviceList);
        recyclear.setLayoutManager(new LinearLayoutManager(this));

    }

    /**
     * 搜索蓝牙设备
     */
    @OnClick(R.id.btn_seatch)
    public void btnSeartch() {

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, " 很抱歉, 你的设备不支持蓝牙设备", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "btnSeartch: 设备不支持蓝牙BLE");
            return;
        }

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        // 获取蓝牙adapter
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            Log.i(TAG, "btnSeartch: 设备不支持蓝牙  adapter为空");
            return;

        }

        if (!mBluetoothAdapter.isEnabled()) {
            Log.i(TAG, "btnSeartch:  打开蓝牙的开关的设备 ");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // 搜索蓝牙设备
        initSearChDevice();

    }

    // 搜索蓝牙设备
    private void initSearChDevice() {

        // 设置搜索的设备的回调函数
        mBluetoothAdapter.startLeScan(scanCallBack);

    }

    // 蓝牙搜索的回调函数
    private BluetoothAdapter.LeScanCallback scanCallBack = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {

            Log.i(TAG, "onLeScan: 搜索到的蓝牙设备：Name:" + device.getName() + "-Address:" + device.getAddress()
                    + "---uuid:" +device.getUuids() + "----bondService :" + device.getBondState()
            );
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    deviceList.add(device);
                    adapter.notifyDataSetChanged();
                }
            });

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


}
