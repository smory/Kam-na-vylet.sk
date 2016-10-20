package sk.smoradap.kamnavyletsk.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.SearchActivity_;
import sk.smoradap.kamnavyletsk.details.DetailsActivity;
import sk.smoradap.kamnavyletsk.details.DetailsActivity_;
import sk.smoradap.kamnavyletsk.gui.ItemRecyclerAdapter;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.Item;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_main_view)
public class MainViewFragment extends Fragment implements MainContract.View {

    private static String TAG = "MainViewFragment";
    private MainContract.Presenter mPresenter;
    private ItemRecyclerAdapter mItemRecyclerAdapter;

    @ViewById(R.id.main_recycler_view)
    RecyclerView mRecyclerView;

    public MainViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MainViewFragment.
     */
    public static MainViewFragment newInstance() {
        MainViewFragment fragment = new MainViewFragment();
        return fragment;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter){
        mPresenter = presenter;
    }

    @AfterViews
    public void enableMenu(){
        setHasOptionsMenu(true);
    }

    /*@AfterViews
    public void setFab(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    } */

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
        System.out.println("onResume");
        mPresenter.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.menu_simple_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("Query: " + query );
                searchView.onActionViewCollapsed();
                Intent i = new Intent(getContext(), SearchActivity_.class);
                i.putExtra(SearchActivity_.SEARCH_TERM, query);
                startActivity(i);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void registerForSingleLocationUpdate(OnLocationUpdatedListener listener) {
        SmartLocation.with(getContext())
                .location()
                .oneFix()
                .start(listener);
    }


    @Override
    public void showAttractionDetails(AttractionDetails details) {
        Intent i = new Intent(getContext(), DetailsActivity_.class);
        i.putExtra(DetailsActivity.DETAILS, details);
        startActivity(i);
    }

    @Override
    public void showAttractionDetails(String url){
        Intent i = new Intent(getContext(), DetailsActivity_.class);
        i.putExtra(DetailsActivity.URL, url);
        startActivity(i);
    }

    @Override
    @UiThread
    public void showNearbyAttractions(List<? extends Item> items) {
        Log.v(TAG, "Setting nearby attraction recycler adapter");
        if(mItemRecyclerAdapter == null){
            mItemRecyclerAdapter = new ItemRecyclerAdapter(getContext(), items);
        }
        mRecyclerView.setAdapter(mItemRecyclerAdapter);
    }
}