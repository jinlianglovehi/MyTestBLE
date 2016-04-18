package com.doudou.cn.mytestble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jinliang on 16-4-18.
 */
public class DeviceInfoRecyclerAdapter extends RecyclerView.Adapter<DeviceInfoRecyclerAdapter.DeviceViewHolder> {



    private Context context;
    private List<BluetoothDevice> listData;

    private LayoutInflater layoutInflater;

    public  DeviceInfoRecyclerAdapter(Context contet, List<BluetoothDevice> listData) {
        this.context = contet;
        this.listData = listData;
        // 获取inflater 数据
        layoutInflater = LayoutInflater.from(context);

    }


    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //返回item
        return new DeviceViewHolder(layoutInflater.inflate(R.layout.adapter_item_device_info, parent, false));
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        BluetoothDevice  devide =  listData.get(position);
        holder.deviceName.setText(devide.getName());


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    //注册控件
    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.device_name)
        TextView deviceName;
        public DeviceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
