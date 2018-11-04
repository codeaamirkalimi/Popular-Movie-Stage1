package com.Info;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieData implements Parcelable {
    private final String mTitle;
    private final String mPosterUrl;
    private final String mSynopsis;
    private final String mVoteAverage;
    private final String mReleaseDate;

    public MovieData(String title, String releaseDate, String posterURL, String synopsis, String voteAverage) {
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterUrl = posterURL;
        mSynopsis = synopsis;
        mVoteAverage = voteAverage;
    }

    private MovieData(Parcel parcel) {
        mTitle = parcel.readString();
        mReleaseDate = parcel.readString();
        mPosterUrl = parcel.readString();
        mSynopsis = parcel.readString();
        mVoteAverage = parcel.readString();
    }

    public String getPosterURL() {
        return mPosterUrl;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mPosterUrl);
        parcel.writeString(mSynopsis);
        parcel.writeString(mVoteAverage);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel parcel) {
            return new MovieData(parcel);
        }

        @Override
        public MovieData[] newArray(int i) {
            return new MovieData[i];
        }
    };
}
