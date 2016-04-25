package com.beaconsucc.beaconcity.ui.activities.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.beaconsucc.beaconcity.R;
import com.beaconsucc.beaconcity.ui.adapters.NavDrawerListAdapter;

public abstract class DrawerMenuActivity extends AppCompatActivity implements
		OnItemClickListener {

	private DrawerLayout mDrawerLayout;
	private int mSelectedOptionDrawerIndex;
	private ActionBarDrawerToggle mDrawerToggle;

	private Toolbar toolbar;
	private ListView mRecycler;
	private NavDrawerListAdapter mAdapter;

	private int mSelectedIndex;

	// ------- Life Cycle ------- //

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String TITLES[] = { getString(R.string.lab_menu1), getString(R.string.lab_menu2), getString(R.string.lab_menu3) };
		int ICONS[] = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher };

		setContentView(R.layout.activity_drawer_menu);

		toolbar = (Toolbar) findViewById(R.id.tool_bar);
		setSupportActionBar(toolbar);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
				R.string.app_name, R.string.app_name) {

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				// mSectionTitle = (String) getActionBar().getTitle();
				// getActionBar().setTitle(R.string.app_name);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				// getActionBar().setTitle(mSectionTitle);
			}

		};

		mSelectedIndex = 0;

		mRecycler = (ListView) findViewById(R.id.recycler_side_menu);
		mAdapter = new NavDrawerListAdapter(this, TITLES, ICONS);
		mRecycler.setAdapter(mAdapter);
		mRecycler.setOnItemClickListener(this);

		// mDrawerToggle.setDrawerIndicatorEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();

		// getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActionBar().setHomeButtonEnabled(true);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mDrawerLayout.closeDrawer(Gravity.START);
		selectItem(position);

	}

	public void selectItem(int idDrawerMenuOption) {
		switch (idDrawerMenuOption) {
		case 0:
			mSelectedOptionDrawerIndex = 0;
			break;
		case 1:
			mSelectedOptionDrawerIndex = 1;
			break;
		case 2:
			mSelectedOptionDrawerIndex = 2;
			break;

		default:
			break;
		}

		// if (mSelectedOptionDrawerIndex != 0) {
		// if (mBtnSelected != null) {
		// mBtnSelected.setSelected(false);
		// ImageButton nowSelected = (ImageButton)
		// findViewById(idDrawerMenuOption);
		// nowSelected.setSelected(true);
		// mBtnSelected = nowSelected;
		// } else {
		// mBtnSelected = (ImageButton) findViewById(idDrawerMenuOption);
		// mBtnSelected.setSelected(true);
		// }
		// }

	}

	public int getSelectedSectionIndex() {
		return mSelectedOptionDrawerIndex;
	}

	public void setSelectedSectionIndex(int mSelectedOptionDrawerIndex) {
		this.mSelectedOptionDrawerIndex = mSelectedOptionDrawerIndex;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// setters and getters
	public DrawerLayout getDrawerLayout() {
		return mDrawerLayout;
	}

	@Override
	public void onBackPressed() {
		if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
			mDrawerLayout.closeDrawer(Gravity.START);
		} else {
			super.onBackPressed();
		}

	}
}
