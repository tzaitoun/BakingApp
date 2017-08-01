package com.zaitoun.talat.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.zaitoun.talat.bakingapp.R;

import static com.zaitoun.talat.bakingapp.ui.RecipeStepViewActivity.*;


public class RecipeStepVideoFragment extends Fragment {

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;

    public RecipeStepVideoFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* Inflate the view from the layout */
        final View view = inflater.inflate(R.layout.fragment_recipe_step_video, container, false);

        Bundle bundle = getArguments();

        /* Check if the bundle is valid */
        if (bundle != null && bundle.containsKey(RECIPE_STEP_VIDEO_URL_BUNDLE_KEY)) {

            /* Initialize the player view */
            mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.simple_exo_player_view);

            /* Get the url string of the video */
            String urlString = bundle.getString(RECIPE_STEP_VIDEO_URL_BUNDLE_KEY);

            /* If it exists, play the video */
            if (urlString != null && !urlString.isEmpty()) {

                Uri.Builder builder = Uri.parse(urlString).buildUpon();
                Uri uri = builder.build();

                initializePlayer(uri);
            }

            else {
                // do something
            }
        }

        return view;
    }

    /* Sets up the Exo player to play the video from the url.
     * Author: Udacity Android Development Nanodegree Program
     */
    public void initializePlayer(Uri mediaUri) {

        Context context = getContext();

        if (mExoPlayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(context, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /* Releases and dereferences the exo player when the fragment/activity is destroyed
    *  Author: Udacity Android Development Nanodegree Program
    */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}