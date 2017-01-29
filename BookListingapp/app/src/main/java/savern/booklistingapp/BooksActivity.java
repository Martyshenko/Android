package savern.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class BooksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private String request = "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=f49ec6ddb21c4fc084f53cdf0b51a2f9&q=";

    /**
     * Adapter for the list of books
     */
    private BooksAdapter mAdapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private String mInput;
    private TextView mEmptyView;
    private View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView booksListView = (ListView) findViewById(R.id.list);

        mEmptyView = (TextView) findViewById(R.id.empty_list_item);
        booksListView.setEmptyView(mEmptyView);

        loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyView.setText(R.string.empty_view);

        // Create a new adapter that takes an empty list of books as mInput
        mAdapter = new BooksAdapter(
                this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        booksListView.setAdapter(mAdapter);

        final EditText searchField = (EditText) findViewById(R.id.search);

        final Button button = (Button) findViewById(R.id.submit);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, BooksActivity.this);

        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mInput = searchField.getText().toString();
                mEmptyView.setText("");
                loadingIndicator.setVisibility(View.VISIBLE);
                // Get a reference to the ConnectivityManager to check state of network connectivity
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {

                    //restart loader
                    getLoaderManager().restartLoader(0, null, BooksActivity.this);
                } else {
                    //Otherwise, display error
                    //First, hide loading indicator so error message will be visible
                    View loadingIndicator = findViewById(R.id.loading_spinner);
                    loadingIndicator.setVisibility(View.GONE);
                    // Update empty state with no connection error message
                    mEmptyView.setText(R.string.no_internet);
                }
            }
        });

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BooksLoader(this, request + mInput);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> listOfBooks) {
        // Hide loading indicator because the data has been loaded
        loadingIndicator.setVisibility(View.GONE);
        // Clear the adapter of previous books data
        mAdapter.clear();

        // If there is a valid list of {@link listOfBooks}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (listOfBooks != null && !listOfBooks.isEmpty()) {
            mAdapter.addAll(listOfBooks);
        } else if (mInput != null)
            mEmptyView.setText(R.string.no_matches_found);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        //Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }


}