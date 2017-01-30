package savern.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BooksAdapter extends ArrayAdapter<Book> {


    public BooksAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    static class ViewHolder {
        @BindView(R.id.author) TextView authorText;
        @BindView(R.id.title) TextView titleText;
        @BindView(R.id.description) TextView descriptionText;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
            holder = new ViewHolder(listItemView);
            listItemView.setTag(holder);
        }else {
            //if view already exists, get the holder instance from the view
            holder = (ViewHolder) listItemView.getTag();
        }
        final Book currentBook = getItem(position);

        String author = currentBook.getAuthor();
        // Display the author of the current book in the TextView
        holder.authorText.setText(author);

        String title = currentBook.getTitle();
        // Display the tile of the current book in the TextView
        holder.titleText.setText(title);

        String description = currentBook.getDescription();
        // Display the description of the current book in the TextView
        holder.descriptionText.setText(description);

        return listItemView;
    }
}