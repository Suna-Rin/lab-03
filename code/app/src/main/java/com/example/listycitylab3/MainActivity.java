package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AddCityFragment.AddCityDialogListener,
        EditCityFragment.EditCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化数据
        String[] cities = {"Edmonton", "Vancouver", "Toronto", "Calgary", "Los Angeles", "Denver", "Hamilton", "Banff"};
        String[] provinces = {"AB", "BC", "ON", "AB", "CA", "CO", "ON", "AB"};
        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        // 适配器 + 列表
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // 点击某一行 -> 编辑
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City selected = dataList.get(position);
            EditCityFragment.newInstance(selected, position)
                    .show(getSupportFragmentManager(), "Edit City");
        });

        // 右下角 + 号 -> 新增
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        if (fab != null) {
            fab.setOnClickListener(v ->
                    new AddCityFragment().show(getSupportFragmentManager(), "Add City")
            );
        }
    }

    // Add 对话框回调：把新城市加入列表
    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    // Edit 对话框回调：刷新显示（对象已在对话框里改过）
    @Override
    public void onCityEdited(int position, City editedCity) {
        cityAdapter.notifyDataSetChanged();
    }
}
