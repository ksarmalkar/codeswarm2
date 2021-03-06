package com.sample.codeswarm.servicedev;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.codeswarm.R;
import com.sample.codeswarm.model.ManagerContent;

/**
 * A fragment representing a single Developer detail screen.
 * This fragment is either contained in a {@link ManagerListActivity}
 * in two-pane mode (on tablets) or a {@link ManagerDetailActivity}
 * on handsets.
 */
public class ManagerDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private ManagerContent.DeveloperModel mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ManagerDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = ManagerContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_developer_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.developer_name)).setText(mItem.name);
            ((TextView) rootView.findViewById(R.id.developer_description)).setText(mItem.description);
            ((TextView) rootView.findViewById(R.id.developer_languages)).setText(mItem.strength);
            ((TextView) rootView.findViewById(R.id.developer_experience)).setText(mItem.experience);
            ((ImageView) rootView.findViewById(R.id.developer_image)).setImageDrawable(getResources().getDrawable(mItem.imageId));
        }

        return rootView;
    }
}
