package savern.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BooksLoader extends AsyncTaskLoader<List<Book>> {

    /** Query URL */
    private String mUrl;
    public BooksLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }
    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null.
        if(mUrl==null){
            return null;
        }
        // Perform the network request, parse the response, and extract a list of books.
        List<Book> listOfBooks = QueryUtils.fetchEarthquakeData(mUrl);

        return listOfBooks;
    }
}
