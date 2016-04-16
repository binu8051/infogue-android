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
import com.sketchproject.infogue.adapters.ArticleRecyclerViewAdapter;
import com.sketchproject.infogue.fragments.dummy.DummyArticleContent;
import com.sketchproject.infogue.models.Article;
import com.sketchproject.infogue.modules.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnArticleFragmentInteractionListener}
 * interface.
 */
public class ArticleFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_CATEGORY_ID = "category-id";
    private static final String ARG_CATEGORY = "category";
    private static final String ARG_SUBCATEGORY_ID = "subcategory-id";
    private static final String ARG_SUBCATEGORY = "subcategory";
    private static final String ARG_FEATURED = "featured";
    private static final String ARG_AUTHOR_ID = "author-id";

    private int mColumnCount = 1;
    private int mCategoryId = 0;
    private int mSubcategoryId = 0;
    private String mCategory;
    private String mSubcategory;
    private String mFeatured;
    private int mAuthor;
    private boolean hasHeader = false;
    private boolean isFirstCall = true;
    private boolean isEndOfPage = false;
    private boolean isEmptyPage = false;

    private List<Article> allArticles;
    private ArticleRecyclerViewAdapter articleAdapter;
    private OnArticleFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleFragment() {
    }

    public static ArticleFragment newInstance(int columnCount) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings("unused")
    public static ArticleFragment newInstance(int columnCount, int id) {
        ArticleFragment fragment = new ArticleFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_AUTHOR_ID, id);

        fragment.setArguments(args);

        return fragment;
    }

    @SuppressWarnings("unused")
    public static ArticleFragment newInstance(int columnCount, String featured) {
        ArticleFragment fragment = new ArticleFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_FEATURED, featured);

        fragment.setArguments(args);

        return fragment;
    }

    @SuppressWarnings("unused")
    public static ArticleFragment newInstance(int columnCount, int categoryId, String category) {
        ArticleFragment fragment = new ArticleFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_CATEGORY_ID, categoryId);
        args.putString(ARG_CATEGORY, category);

        fragment.setArguments(args);

        return fragment;
    }

    @SuppressWarnings("unused")
    public static ArticleFragment newInstance(int columnCount, int categoryId, String category, int subcategoryId, String subcategory) {
        ArticleFragment fragment = new ArticleFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_CATEGORY_ID, categoryId);
        args.putString(ARG_CATEGORY, category);
        args.putInt(ARG_SUBCATEGORY_ID, subcategoryId);
        args.putString(ARG_SUBCATEGORY, subcategory);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);
            mSubcategoryId = getArguments().getInt(ARG_SUBCATEGORY_ID);
            mCategory = getArguments().getString(ARG_CATEGORY);
            mSubcategory = getArguments().getString(ARG_SUBCATEGORY);
            mFeatured = getArguments().getString(ARG_FEATURED);
            mAuthor = getArguments().getInt(ARG_AUTHOR_ID);
        }

        if(mSubcategoryId > 0 && mSubcategory != null){
            Log.i("ARTICLE SUB CAT", mSubcategory+" id : "+mSubcategoryId);
        }
        else  if(mCategoryId > 0 && mCategory != null){
            Log.i("ARTICLE", mCategory+" id : "+mCategoryId);
        }
        else if(mFeatured != null){
            hasHeader = true;
            Log.i("ARTICLE FEATURED", mFeatured);
        }
        else if(mAuthor != 0){
            Log.i("ARTICLE CONTRIBUTOR", String.valueOf(mAuthor));
        }
        else{
            Log.i("ARTICLE", "DEFAULT");
        }

        double random = Math.random();
        isEmptyPage = random > 0.9;
        Log.i("INFOGUE/random", isEmptyPage+" "+String.valueOf(random));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);

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

            allArticles = new ArrayList<>();
            articleAdapter = new ArticleRecyclerViewAdapter(allArticles, mListener, hasHeader);
            recyclerView.setAdapter(articleAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(final int page, int totalItemsCount) {
                    if(!isFirstCall){
                        loadArticles(page);
                    }
                }
            });

            if(isFirstCall){
                isFirstCall = false;
                loadArticles(0);
            }
        }
        return view;
    }

    /**
     * @param page starts at 0
     */
    private void loadArticles(final int page){
        if(!isEndOfPage){
            allArticles.add(null);
            articleAdapter.notifyItemInserted(allArticles.size() - 1);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    allArticles.remove(allArticles.size() - 1);
                    articleAdapter.notifyItemRemoved(allArticles.size());

                    List<Article> moreArticles = !isEmptyPage ? DummyArticleContent.generateDummy(page) : new ArrayList<Article>();
                    int curSize = articleAdapter.getItemCount();
                    allArticles.addAll(moreArticles);

                    if (allArticles.size() <= 0) {
                        isEndOfPage = true;
                        Log.i("INFOGUE/Article", "EMPTY on page " + page);
                        Article emptyArticle = new Article(0, null, "Empty page");
                        allArticles.add(emptyArticle);
                    } else if (allArticles.size() >= 100) {
                        isEndOfPage = true;
                        Log.i("INFOGUE/Article", "END on page " + page);
                        Article endArticle = new Article(-1, null, "End of page");
                        allArticles.add(endArticle);
                    }

                    articleAdapter.notifyItemRangeInserted(curSize, allArticles.size() - 1);
                    Log.i("INFOGUE/Article", "Load More page " + page);
                }
            }, 3000);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnArticleFragmentInteractionListener) {
            mListener = (OnArticleFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnArticleFragmentInteractionListener");
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
    public interface OnArticleFragmentInteractionListener {
        void onArticleFragmentInteraction(View view, Article article);

        void onArticlePopupInteraction(View view, Article article);

        void onArticleLongClickInteraction(View view, Article article);
    }
}
