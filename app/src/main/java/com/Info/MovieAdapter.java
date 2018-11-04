package com.Info;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.aamirkalimi.popularmoviestage1.MainActivity;
import com.example.aamirkalimi.popularmoviestage1.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<MovieData> mMovieArrayList;
    private Context mContext;
    private MovieAdapterOnClickHandler mClickHandler;

    public MovieAdapter(MainActivity mainActivity) {
        mContext = mainActivity.getApplicationContext();
        mClickHandler = mainActivity;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView posterImageView;
        private MovieViewHolder(View view) {
            super(view);
            posterImageView = (ImageView) view.findViewById(R.id.movie_images);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            MovieData movie = mMovieArrayList.get(adapterPosition);
            mClickHandler.onClickItem(movie);
        }
    }

    public interface MovieAdapterOnClickHandler {
        void onClickItem(MovieData movie);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.movie_grid_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForGridItem, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieData movie = mMovieArrayList.get(position);
        String posterUrl = movie.getPosterURL();
        Picasso.with(mContext)
                .load(Uri.parse(posterUrl))
                .fit()
                .centerInside()
                .into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        if (mMovieArrayList == null) return 0;
        return mMovieArrayList.size();
    }

    public void setMovieArrayList(ArrayList<MovieData> movieArrayList) {
        mMovieArrayList = movieArrayList;
        notifyDataSetChanged();
    }
}
