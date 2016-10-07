package com.edu.sangfan.swiperefreshlayoutdemo;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.edu.sangfan.swiperefreshlayoutdemo.adapter.MyAdapter;
import com.edu.sangfan.swiperefreshlayoutdemo.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout mLayout;
    private RecyclerView mRecyclerView;
    private List<String> datas = new ArrayList<>();
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        mLayout = (SwipeRefreshLayout) findViewById(R.id.mLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    private void initRecyclerView() {
        mAdapter = new MyAdapter(datas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        //设置默认的动画效果
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setOnItemClickListener(new MyAdapter.onItemClickListener(){

            @Override
            public void onClick(View v, int position, String city) {
                Toast.makeText(MainActivity.this, "city:"+city+",position:"+position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSwipeRefreshLayout(){
        //设置刷新圈的颜色
        mLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置刷新圈的下拉值
        mLayout.setDistanceToTriggerSync(100);
        //设置刷新圈的背景色
//        mLayout.setProgressBackgroundColorSchemeColor(
//                getResources().getColor(R.color.item_press));
        //设置刷新圈的大小
        mLayout.setSize(SwipeRefreshLayout.LARGE);

        mLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0; i<5; i++){
                            mAdapter.addData(i, "new City:"+i);
                        }
                        mAdapter.notifyItemRangeChanged(0, 10);
                        mRecyclerView.scrollToPosition(0);
                        //刷新状态，默认停止
                        mLayout.setRefreshing(false);
//                        mLayout.isRefreshing();
                    }
                }, 2000);
            }
        });
    }

    private void initDatas() {
        for(int i=1; i<=10; i++){
            datas.add("city"+i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_action_add:
                mAdapter.addData(0, "new City");
                break;
            case R.id.id_action_delete:
                mAdapter.removeData(1);
                break;
            case R.id.id_action_gridview://网格图效果
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.id_action_staview: //瀑布流效果
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        return true;
    }


}
