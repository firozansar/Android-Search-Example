package info.firozansari.searchdemo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //;InitSearchLayout(toolbar);
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
}
