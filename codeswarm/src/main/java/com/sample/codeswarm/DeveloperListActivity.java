package com.sample.codeswarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sample.codeswarm.receivers.ScreenBroadCastReceiver;
import com.sample.codeswarm.servicedev.ManagerListActivity;
import com.sample.codeswarm.services.SimpleIntentService;

import com.sample.codeswarm.tasks.FetchNetworkTimeAsyncTask;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * An activity representing a list of Developers. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DeveloperDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link DeveloperListFragment} and the item details
 * (if present) is a {@link DeveloperDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link DeveloperListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class DeveloperListActivity extends FragmentActivity
        implements DeveloperListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_list);

        if (findViewById(R.id.developer_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((DeveloperListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.developer_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    private ScreenBroadCastReceiver screenBroadCastReceiver = new ScreenBroadCastReceiver();
    private VolumeReceiver volumeBroadCastReceiver = new VolumeReceiver();
    private ResponseReceiver responseReceiver = new ResponseReceiver();

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(screenBroadCastReceiver, filter);

        IntentFilter volumeFilter = new IntentFilter();
        volumeFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(volumeBroadCastReceiver, volumeFilter);

        IntentFilter serviceFilter = new IntentFilter();
        serviceFilter.addAction("com.sample.intent.action.MESSAGE_PROCESSED");
        serviceFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(responseReceiver, serviceFilter);
    }

    class VolumeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int volume = (Integer)intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
            Crouton.makeText(DeveloperListActivity.this, "Volume Changed to " + volume, Style.INFO).show();
        }
    }

    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP =
                "com.sample.intent.action.MESSAGE_PROCESSED";
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra(SimpleIntentService.PARAM_OUT_MSG);
            Crouton.makeText(DeveloperListActivity.this, text, Style.INFO).show();

        }
    }

    @Override
    protected void onPause() {
        unregisterReceiver(screenBroadCastReceiver);
        unregisterReceiver(volumeBroadCastReceiver);
        unregisterReceiver(responseReceiver);
        super.onPause();
    }

    /**
     * Callback method from {@link DeveloperListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(DeveloperDetailFragment.ARG_ITEM_ID, id);
            DeveloperDetailFragment fragment = new DeveloperDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.developer_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, DeveloperDetailActivity.class);
            detailIntent.putExtra(DeveloperDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.call_intent_service);
        if (item != null) {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    String strInputMsg = "Ping from Server : ";
                    Intent msgIntent = new Intent(DeveloperListActivity.this, SimpleIntentService.class);
                    msgIntent.putExtra(SimpleIntentService.PARAM_IN_MSG, strInputMsg);
                    startService(msgIntent);
                    return true;
                }
            });
        }

        item = menu.findItem(R.id.call_binding_service);
        if (item != null) {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Intent intent = new Intent(DeveloperListActivity.this, ManagerListActivity.class);
                    startActivity(intent);
                    return true;
                }
            });
        }

        item = menu.findItem(R.id.call_async_task);
        if (item != null) {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    new FetchNetworkTimeAsyncTask(new FetchNetworkTimeAsyncTask.FetchNetworkTimeCallable() {
                        @Override
                        public void onSuccess(String time) {
                            Crouton.makeText(DeveloperListActivity.this, "Server Time : " + time, Style.INFO).show();
                        }
                    }).execute();
                    return true;
                }
            });
        }
        return true;
    }
}
