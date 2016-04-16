package com.sketchproject.infogue.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sketchproject.infogue.R;
import com.sketchproject.infogue.adapters.FollowerRecyclerViewAdapter;
import com.sketchproject.infogue.fragments.dummy.DummyFollowerContent;
import com.sketchproject.infogue.models.Contributor;
import com.sketchproject.infogue.modules.EndlessRecyclerViewScrollListener;
import com.sketchproject.infogue.modules.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FollowerFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_RELATED_ID = "contributor-id";
    private static final String ARG_RELATED_USERNAME = "contributor-username";
    private static final String ARG_TYPE = "screen-type";

    private int mColumnCount = 1;
    private int mLoggedId;
    private int mId;
    private String mUsername;
    private String mType;
    private boolean isFirstCall = true;
    private boolean isEndOfPage = false;
    private boolean isEmptyPage = false;

    private List<Contributor> allFollowers;
    private FollowerRecyclerViewAdapter followerAdapter;
    private OnListFragmentInteractionListener mListener;
    private SessionManager session;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FollowerFragment() {
    }

    @SuppressWarnings("unused")
    public static FollowerFragment newInstance(int columnCount) {
        FollowerFragment fragment = new FollowerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public static FollowerFragment newInstance(int columnCount, int id, String username, String type) {
        FollowerFragment fragment = new FollowerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_RELATED_ID, id);
        args.putString(ARG_RELATED_USERNAME, username);
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getContext());
        mLoggedId = session.getSessionData(SessionManager.KEY_ID, 0);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mId =  getArguments().getInt(ARG_RELATED_ID);
            mUsername =  getArguments().getString(ARG_RELATED_USERNAME);
            mType = getArguments().getString(ARG_TYPE);
        }

        double random = Math.random();
        isEmptyPage = random > 0.5;
        Log.i("INFOGUE/random", isEmptyPage+" "+String.valueOf(random));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follower_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            LinearLayoutManager linearLayoutManager;
            if (mColumnCount <= 1) {
                linearLayoutManager = new LinearLayoutManager(context);
            } else {
                linearLayoutManager = new GridLayoutManager(context, mColumnCount);
            }

            allFollowers = new ArrayList<>();
            followerAdapter = new FollowerRecyclerViewAdapter(allFollowers, mListener, mType);
            recyclerView.setAdapter(followerAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(final int page, int totalItemsCount) {
                    if(!isFirstCall){
                        loadFollowers(page, totalItemsCount);
                    }
                }
            });

            if(isFirstCall){
                isFirstCall = false;
                loadFollowers(0, 0);
            }
        }
        return view;
    }

    /**
     * @param page starts at 0
     * @param totalItemsCount total of article row view
     */
    private void loadFollowers(final int page, int totalItemsCount) {
        if (!isEndOfPage) {
            allFollowers.add(null);
            followerAdapter.notifyItemInserted(allFollowers.size() - 1);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    allFollowers.remove(allFollowers.size() - 1);
                    followerAdapter.notifyItemRemoved(allFollowers.size());

                    List<Contributor> moreFollowers = !isEmptyPage ? DummyFollowerContent.generateDummy(page) : new ArrayList<Contributor>();
                    int curSize = followerAdapter.getItemCount();
                    allFollowers.addAll(moreFollowers);

                    if (allFollowers.size() <= 0) {
                        isEndOfPage = true;
                        Log.i("INFOGUE/Contributor", "EMPTY on page " + page);
                        Contributor emptyContributor = new Contributor(0, null);
                        allFollowers.add(emptyContributor);
                    } else if (allFollowers.size() >= 100) {
                        isEndOfPage = true;
                        Log.i("INFOGUE/Contributor", "END on page " + page);
                        Contributor endContributor = new Contributor(-1, null);
                        allFollowers.add(endContributor);
                    }

                    followerAdapter.notifyItemRangeInserted(curSize, allFollowers.size() - 1);
                    Log.i("INFOGUE/Contributor", "Load More page " + page);
                }
            }, 3000);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Contributor contributor);
    }
}
