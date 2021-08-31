package com.example.android.findmybook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import com.squareup.picasso.Picasso;



public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Activity context, List<Book> books) {
        super(context,0,books);
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {

        Book currentBook=getItem(position);
        View listItemView=convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        }

        TextView bookname=listItemView.findViewById(R.id.book_name);
        bookname.setText(currentBook.getBookName());

        TextView authorname= listItemView.findViewById(R.id.author_name);
        authorname.setText(currentBook.getAuthorName());

        TextView lang=listItemView.findViewById(R.id.language_view);
        lang.setText(currentBook.getLanguage());

        ImageView img=listItemView.findViewById(R.id.image_view);
        Picasso.get().load(currentBook.getImage()).into(img);

        return listItemView;
    }
}
