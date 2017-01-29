package savern.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BooksAdapter extends ArrayAdapter<Book> {


    public BooksAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        final Book currentBook = getItem(position);

        String author = currentBook.getAuthor();
        // Find the TextView with view ID author
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        // Display the author of the current book in that TextView
        authorView.setText(author);

        String title = currentBook.getTitle();
        // Find the TextView with view ID title
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        // Display the tile of the current book in that TextView
        titleView.setText(title);

        String description = currentBook.getDescription();
        // Find the TextView with view ID description
        TextView descriptionView = (TextView) listItemView.findViewById(R.id.description);
        // Display the description of the current book in that TextView
        descriptionView.setText(description);

        return listItemView;
    }
}