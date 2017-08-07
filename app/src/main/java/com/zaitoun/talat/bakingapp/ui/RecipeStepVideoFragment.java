package com.zaitoun.talat.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import static com.zaitoun.talat.bakingapp.ui.RecipeStepSelectionActivity.RECIPE_STEP_VIDEO_URL_BUNDLE_KEY;

/**
 * A Fragment that displays a recipe step's video.
 */
public class RecipeStepVideoFragment extends Fragment {

    public static final String PLAYER_POSITION_KEY = "PLAYER_POSITION";
    public static final String PLAYER_STATE_KEY = "PLAYER_STATE";

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;

    private String mVideoUriString;
    private long mPosition;

    public RecipeStepVideoFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* Inflate the layout of the fragment */
        final View view = inflater.inflate(R.layout.fragment_recipe_step_video, container, false);

        Bundle bundle = getArguments();

        /* Check if the bundle is valid */
        if (bundle != null && bundle.containsKey(RECIPE_STEP_VIDEO_URL_BUNDLE_KEY)) {

            /* Get a reference to the view */
            mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.simple_exo_player_view);

            TextView mTextView = (TextView) view.findViewById(R.id.tv_no_media);

            /* Get the url string of the video */
            String urlString = bundle.getString(RECIPE_STEP_VIDEO_URL_BUNDLE_KEY);

            mVideoUriString = urlString;

            /* If it exists, play the video */
            if (urlString != null && !urlString.isEmpty()) {

                Uri.Builder builder = Uri.parse(urlString).buildUpon();
                Uri uri = builder.build();
                initializePlayer(uri);
            }

            /* If it doesn't, show a message */
            else {
                mPlayerView.setVisibility(View.GONE);
                mTextView.setVisibility(View.VISIBLE);
            }
        }

        return view;
    }

    /**
     * Sets up the Exo player to play the video from the url.
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

    /**
     *  Releases and dereferences the Exo player when the fragment/activity is destroyed.
     *  Author: Udacity Android Development Nanodegree Program
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    /* Releases player when either the activity get destroyed or when the activity is not in
     * the foreground (pressing home button for example).
     */
    @Override
    public void onStop() {
        super.onStop();
        /* Before releasing th player, get the position so if the user comes back, we can start it from there */
        if (mExoPlayer != null) {
            mPosition = mExoPlayer.getCurrentPosition();
        }
        releasePlayer();
    }

    /* Initializes the player if the user pressed the home button and came back to the activity */
    @Override
    public void onStart() {
        super.onStart();

        /* Check if mExoPlayer does not exist: 1) If the user presses home button, then the player
         * would be released and null. 2) If the Exoplayer was not created in onCreateView because
         * there was no valid uri, so it would be null. These are the only 2 cases where it should
         * enter the first if statement.
         */
        if (mExoPlayer == null) {

            /* If it exists, play the video */
            if (mVideoUriString != null && !mVideoUriString.isEmpty()) {

                Uri.Builder builder = Uri.parse(mVideoUriString).buildUpon();
                Uri uri = builder.build();
                initializePlayer(uri);
                /* Move to the position before home button was pressed and pause the video */
                mExoPlayer.seekTo(mPosition);
                mExoPlayer.setPlayWhenReady(false);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        /* Save the player position and state*/
        if (mExoPlayer != null) {
            long position = mExoPlayer.getCurrentPosition();
            outState.putLong(PLAYER_POSITION_KEY, position);

            int playerState = mExoPlayer.getPlaybackState();

            if (playerState == SimpleExoPlayer.STATE_READY) {
                outState.putBoolean(PLAYER_STATE_KEY, mExoPlayer.getPlayWhenReady());
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* Get the player position */
        if (savedInstanceState != null && mExoPlayer != null) {
            mExoPlayer.seekTo(savedInstanceState.getLong(PLAYER_POSITION_KEY));

            /* If the video was paused before rotation, pause it, else just play it */
            if (!savedInstanceState.getBoolean(PLAYER_STATE_KEY)) {
                mExoPlayer.setPlayWhenReady(false);
            }
        }
    }
}