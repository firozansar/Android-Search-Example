package info.firozansari.searchdemo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amsen.par.searchview.AutoCompleteSearchView;
import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;
import com.amsen.par.searchview.prediction.view.DefaultPredictionPopupWindow;
import com.amsen.par.searchview.util.ViewUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private AutoCompleteSearchView searchView;
    public MenuItem searchViewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addInitialFragment();

        //;InitSearchLayout(toolbar);
    }

    private void addInitialFragment() {
        FragmentManager frgManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = frgManager.beginTransaction();
        Fragment f = frgManager.findFragmentByTag("MainFragment");

        if (f == null) {
            Fragment fragment = new MainFragment();
            fragmentTransaction.add(R.id.main_content_frame, fragment, "MainFragment").commit();
            frgManager.executePendingTransactions();
        }
    }


    /**
     * Method creates fragment transaction and replace current fragment with new one.
     *
     * @param newFragment    new fragment used for replacement.
     * @param transactionTag text identifying fragment transaction.
     */
    public void replaceFragment(Fragment newFragment, String transactionTag) {
        if (newFragment != null) {
            FragmentManager frgManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = frgManager.beginTransaction();
            fragmentTransaction.addToBackStack(transactionTag);
            fragmentTransaction.replace(R.id.main_content_frame, newFragment).commitAllowingStateLoss();
            frgManager.executePendingTransactions();
        } else {
            //Timber.e(new RuntimeException(), "Replace fragments with null newFragment parameter.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        searchViewItem = menu.findItem(R.id.action_search);
        searchView = (AutoCompleteSearchView) searchViewItem.getActionView();
        searchView.setUseDefaultProgressBar(true);
        searchView.setUseDefaultPredictionPopupWindow(false);
        searchView.setPredictionPopupWindow(new DefaultPredictionPopupWindow(MainActivity.this, new CustomPredictionAdapter()));

        searchView.setOnPredictionClickListener(new OnPredictionClickListener() {

            @Override
            public void onClick(int position, Prediction prediction) {
                Toast.makeText(getApplicationContext(), String.format("clicked [position:%d, value:%s, displayString:%s]", position, prediction.value, prediction.displayString), Toast.LENGTH_SHORT).show();

                searchViewItem.collapseActionView();
            }

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    searchView.showProgressBar();

                    //fakeNetworkPredictions(newText);
                }

                return true;
            }
        });
        return true;
    }



    private void InitSearchLayout(Toolbar toolbar) {
        final SearchViewLayout searchViewLayout = (SearchViewLayout) findViewById(R.id.search_view_container);
        searchViewLayout.setExpandedContentSupportFragment(this, new SearchStaticListSupportFragment());
        searchViewLayout.handleToolbarAnimation(toolbar);
        searchViewLayout.setCollapsedHint("Collapsed Hint");
        searchViewLayout.setExpandedHint("Expanded Hint");
//        searchViewLayout.setHint("Global Hint");

        ColorDrawable collapsed = new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary));
        ColorDrawable expanded = new ColorDrawable(ContextCompat.getColor(this, R.color.default_color_expanded));
        searchViewLayout.setTransitionDrawables(collapsed, expanded);
        searchViewLayout.setSearchListener(new SearchViewLayout.SearchListener() {
            @Override
            public void onFinished(String searchKeyword) {
                searchViewLayout.collapse();
                Snackbar.make(searchViewLayout, "Start Search for - " + searchKeyword, Snackbar.LENGTH_LONG).show();
            }
        });
        searchViewLayout.setOnToggleAnimationListener(new SearchViewLayout.OnToggleAnimationListener() {
            @Override
            public void onStart(boolean expanding) {
                if (expanding) {
                   // TODO when expanded
                } else {
                    // TODO when collapsed
                }
            }

            @Override
            public void onFinish(boolean expanded) {

            }
        });
        searchViewLayout.setSearchBoxListener(new SearchViewLayout.SearchBoxListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: " + s + "," + start + "," + count + "," + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: " + s + "," + start + "," + before + "," + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: " + s);
            }
        });
    }

    public void searchVisible(boolean value){
        if(value)
        {
            searchViewItem.setVisible(true);
        }
        else
        {
            searchViewItem.setVisible(false);
        }
    }
}
