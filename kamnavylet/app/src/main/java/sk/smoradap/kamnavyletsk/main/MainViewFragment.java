package sk.smoradap.kamnavyletsk.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.SearchActivity_;
import sk.smoradap.kamnavyletsk.details.DetailsActivity;
import sk.smoradap.kamnavyletsk.details.DetailsActivity2_;
import sk.smoradap.kamnavyletsk.details.DetailsActivity_;
import sk.smoradap.kamnavyletsk.gui.ItemRecyclerAdapter;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.Item;
import sk.smoradap.kamnavyletsk.utils.SuggestionsUtils;

@EFragment(R.layout.fragment_main_view)
public class MainViewFragment extends Fragment implements MainContract.View {

    private static String TAG = MainViewFragment.class.getSimpleName();

    @Bean(MainPresenter.class)
    MainContract.Presenter presenter;

    private ItemRecyclerAdapter mItemRecyclerAdapter;

    @ViewById(R.id.main_recycler_view)
    RecyclerView recyclerView;

    private ProgressDialog mProgressDialog;
    private SimpleCursorAdapter mSugestionAdapter;

    public MainViewFragment() {
        // Required empty public constructor
    }

    @AfterViews
    void setupPresenter(){
        presenter.setView(this);
    }

    @AfterViews
    public void enableMenu(){
        setHasOptionsMenu(true);
    }

    @AfterViews
    public void addRecyclerDecoration(){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.line_separator));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
        System.out.println("onResume");
        presenter.start();
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
                searchView.onActionViewCollapsed();
                presenter.searchReqested(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() > 1){
                    MatrixCursor c = SuggestionsUtils.createSugestionCursor(getContext().getApplicationContext(), newText);
                    mSugestionAdapter.changeCursor(c);
                }
                return false;
            }

        });

        final String[] from = new String[] {"name"};
        final int[] to = new int[] {android.R.id.text1};
        mSugestionAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1,
                null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        searchView.setSuggestionsAdapter(mSugestionAdapter);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                Cursor c = (Cursor) mSugestionAdapter.getItem(position);
                System.out.println(c.getString(1));
                searchView.onActionViewCollapsed();
                presenter.searchReqested(c.getString(1));
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }
        });
    }





    @UiThread
    @Override
    public void showAttractionDetails(AttractionDetails details) {
        Intent i = new Intent(getContext(), DetailsActivity_.class);
        i.putExtra(DetailsActivity.DETAILS, details);
        startActivity(i);
    }

    @UiThread
    @Override
    public void showAttractionDetails(String url){
        DetailsActivity2_.intent(getContext()).url(url).start();
    }

    @Override
    @UiThread
    public void showNearbyAttractions(List<? extends Item> items) {
        Log.v(TAG, "Setting nearby attraction recycler adapter");
        if(mItemRecyclerAdapter == null){
            mItemRecyclerAdapter = new ItemRecyclerAdapter(getContext(), items, new ItemRecyclerAdapter.OnItemPickedListener() {
                @Override
                public void onItemPicked(Item item) {
                    presenter.attactionPicked(item);
                }
            });
        }
        recyclerView.setAdapter(mItemRecyclerAdapter);
    }

    @UiThread
    @Override
    public void showBusy(boolean busy){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading_data));
        }

        if(busy){
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    @UiThread
    public void performSearch(String query) {
        Intent i = new Intent(getContext(), SearchActivity_.class);
        i.putExtra(SearchActivity_.SEARCH_TERM, query);
        startActivity(i);
    }
}