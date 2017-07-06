package info.firozansari.searchdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by topcashback on 06/07/2017.
 */

public class MainFragment extends Fragment {

    CardView searchCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        searchCardView = (CardView) view.findViewById(R.id.search_box);
        searchCardView.setOnClickListener(mSearchViewOnClickListener);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private final View.OnClickListener mSearchViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (getActivity() != null && getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).searchVisible(true);
                ((MainActivity) getActivity()).replaceFragment(new PredictiveSearchFragment(), PredictiveSearchFragment.class.getSimpleName());
            }
        }
    };
}
