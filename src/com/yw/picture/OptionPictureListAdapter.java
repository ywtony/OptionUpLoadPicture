package com.yw.picture;

import java.util.ArrayList;
import java.util.List;

import com.yw.picture.util.LoadLocalImageUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class OptionPictureListAdapter extends BaseAdapter {
	private static final int ADD_ITEM = 0;
	private static final int COMMON = 1;
	private static final int TYPE_MAX_COUNT = COMMON + 1;
	private Context context;

	public OptionPictureListAdapter(Context context) {
		this.context = context;
	}

	private List<String> datas = new ArrayList<String>();

	public void setList(List<String> datas) {
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return datas.get(position).equals("add") ? ADD_ITEM : COMMON;
	}

	@Override
	public int getViewTypeCount() {

		return TYPE_MAX_COUNT;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final String url = datas.get(position);
		int type = getItemViewType(position);
		ViewHolder holder = null;
		if (convertView == null) {

			switch (type) {
			case ADD_ITEM:
				convertView = LayoutInflater.from(context).inflate(
						R.layout.optionpicture_item_add, null);
				holder = new ViewHolder(convertView);
				holder.iv_add.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (callback != null) {
							callback.add();
						}
					}
				});
				break;
			case COMMON:
				convertView = LayoutInflater.from(context).inflate(
						R.layout.optionpicturelist_item, null);
				holder = new ViewHolder(convertView);
				holder.iv_del.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (callback != null) {
							callback.delete(url);
						}
					}
				});
				LoadLocalImageUtil.getInstance().displayFromSDCard(
						datas.get(position), holder.iv_display);
				break;
			}
		}

		// ���ñ���ͼƬ
		return convertView;
	}

	private OptionPictureCallback callback;

	public void setCallback(OptionPictureCallback callback) {
		this.callback = callback;
	}

	public interface OptionPictureCallback {
		/**
		 * ɾ��url
		 * 
		 * @param url
		 */
		public void delete(String url);

		/**
		 * ���һ��url��������
		 */
		public void add();
	}

	class ViewHolder {
		public ImageView iv_add;
		public ImageView iv_del;
		public ImageView iv_display;

		public ViewHolder(View view) {
			iv_add = (ImageView) view.findViewById(R.id.iv_add);
			iv_del = (ImageView) view.findViewById(R.id.optiion_del);
			iv_display = (ImageView) view.findViewById(R.id.optiion_iv);
		}
	}
}
