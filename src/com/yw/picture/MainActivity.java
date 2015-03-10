package com.yw.picture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.yw.picture.OptionPictureListAdapter.OptionPictureCallback;
import com.yw.picture.util.LoadLocalImageUtil;
import com.yw.picture.util.PictureUtil;

/**
 * ����ͼƬ�ϴ�
 * 
 * @author tony
 * 
 */
public class MainActivity extends ActionBarActivity implements
		OptionPictureCallback, OnClickListener {
	private GridView gridView = null;
	private OptionPictureListAdapter adapter = null;
	private List<String> datas = new ArrayList<String>();
	private Button btn_upload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LoadLocalImageUtil.initImageLoader(this);
		setContentView(R.layout.activity_main);
		initViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
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

	private void initViews() {
		btn_upload = (Button) findViewById(R.id.optionpicturelist_btn);
		btn_upload.setOnClickListener(this);
		datas.add("add");
		gridView = (GridView) findViewById(R.id.gridview);
		adapter = new OptionPictureListAdapter(this);
		adapter.setCallback(this);
		adapter.setList(datas);
		gridView.setAdapter(adapter);
	}

	@Override
	public void delete(String url) {
		datas.remove(url);
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void add() {
		Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
		getImage.addCategory(Intent.CATEGORY_OPENABLE);
		getImage.setType("image/jpeg");
		startActivityForResult(getImage, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 0:
			Uri originalUri = data.getData();
			String url = PictureUtil.getPath(this, originalUri);
			datas.add(datas.size() - 1, url);
			for (int i = 0; i < datas.size(); i++) {
				Log.e("optionpricturelist", datas.get(i));
			}
			gridView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			break;
		}
	}

	@Override
	public void onClick(View v) {
		
		
	}
}
