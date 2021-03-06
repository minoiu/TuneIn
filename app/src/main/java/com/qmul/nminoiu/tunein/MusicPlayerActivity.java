package com.qmul.nminoiu.tunein;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;


/**
 * created by nicoleta on 11/11/2017
 */
public class MusicPlayerActivity extends Activity implements OnCompletionListener, SeekBar.OnSeekBarChangeListener {

	private ImageButton btnPlay;
    public String ID;
	private ImageButton btnForward;
	private ImageButton btnBackward;
	private ImageButton btnNext;
	private ImageButton btnPrevious;
	private ImageButton btnPlaylist;
	private ImageButton btnRepeat;
	private ImageButton btnShuffle;
	private ImageButton downarrow;
	private LinearLayout playerHeader;
	private LinearLayout coverLay;
	private LinearLayout lldwn;
	private RelativeLayout layout;
    public static SeekBar songProgressBar;
    public static TextView songTitleLabel;
    public static TextView songCurrentDurationLabel;
    public static TextView songTotalDurationLabel;
	private Handler mHandler = new Handler();
	private SongsManager songManager;
	public static Utilities utils;
	private int seekForwardTime = 5000; // 5000 milliseconds
	private int seekBackwardTime = 5000; // 5000 milliseconds
	private int currentSongIndex = 0;
	private boolean isShuffle = false;
	private boolean isRepeat = false;
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    public static ArrayList<String> songs = new ArrayList<>();
    public static ArrayList<String> urls = new ArrayList<>();
    private String songTitle;
    private String activity;
    private Intent i;
	private List myFollowers;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);

		// All player buttons
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		ID = firebaseAuth.getCurrentUser().getUid();
		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		btnForward = (ImageButton) findViewById(R.id.btnForward);
		btnBackward = (ImageButton) findViewById(R.id.btnBackward);
		btnNext = (ImageButton) findViewById(R.id.btnNext);
		btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
		btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
		btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
		songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
		songTitleLabel = (TextView) findViewById(R.id.songTitle);
		songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
		songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
		downarrow = (ImageButton) findViewById(R.id.downarrow);
		lldwn = (LinearLayout) findViewById(R.id.lldown);
		myFollowers = new ArrayList<>();
		layout = (RelativeLayout) findViewById(R.id.layout);

		DatabaseReference reqdb = FirebaseDatabase.getInstance().getReference().child("TimeRequest").child(ID);
		reqdb.addListenerForSingleValueEvent(
				new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						dataSnapshot.getRef().removeValue();
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {
						Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
					}
				});

		DatabaseReference reqdb1 = FirebaseDatabase.getInstance().getReference().child("TimeAnswer");
		reqdb1.addListenerForSingleValueEvent(
				new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
							String reqId = snapshot.getKey().toString();
							if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
								dataSnapshot.child(reqId).getRef().removeValue();
							}
						}
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {
						Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
					}
				});


		//retrieve current song from Firebase
		DatabaseReference songTitleRef = FirebaseDatabase.getInstance().getReference().child("CurrentSong");
		songTitleRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				if(dataSnapshot.child(ID).exists()){
					String song = dataSnapshot.child(ID).child("Song").getValue().toString();
					songTitleLabel.setText(song);
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

		songManager = new SongsManager();
		utils = new Utilities();

		// Listeners
		songProgressBar.setOnSeekBarChangeListener(this);
		mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.getCurrentPosition();
        mediaPlayer.getDuration();
        songProgressBar.setProgress(mediaPlayer.getCurrentPosition()-mediaPlayer.getDuration());
//        songProgressBar.setMax(100);

        //handle slide down player page into previous activity
        i = getIntent();
        lldwn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (i != null) {
					if (i.hasExtra("Uniqid")) {
						String uniqid = i.getStringExtra("Uniqid");
						if (uniqid.equals("FromSettings")) {
							Intent intent = new Intent(MusicPlayerActivity.this, RealTimeActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromLibrary")) {
							Intent intent = new Intent(MusicPlayerActivity.this, LibraryActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								SongSingleton.getInstance().setSongName(songTitleLabel.getText().toString());
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSearch")) {
							Intent intent = new Intent(MusicPlayerActivity.this, RealTimeActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
								intent.putExtra("ID", "FromPlayer");
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSongs")) {
							Intent intent = new Intent(MusicPlayerActivity.this, Songs.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromPlaylistSongs")) {
							Intent intent = new Intent(MusicPlayerActivity.this, PlaylistSongs.class);
							String oldP = getIntent().getStringExtra("OldPlaylist");
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								intent.putExtra("OldPlaylist", oldP);
								intent.putExtra("Name", oldP);
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromDownloads")) {
							Intent intent = new Intent(MusicPlayerActivity.this, Downloads.class);
							intent.putExtra("UniqId", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromChat")) {
							Intent intent = new Intent(MusicPlayerActivity.this, Chat.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromFavourites")) {
							Intent intent = new Intent(MusicPlayerActivity.this, Favourites.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSearch")) {
							Intent intent = new Intent(MusicPlayerActivity.this, RealTimeActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromMyPlaylists")) {
							Intent intent = new Intent(MusicPlayerActivity.this, MyPlaylists.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSharedPlSongs")) {
							Intent intent = new Intent(MusicPlayerActivity.this, MyPlaylists.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						}
						else {
							Intent intent = new Intent(MusicPlayerActivity.this, RealTimeActivity.class);
							intent.putExtra("ID", "FromMP");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						}
						overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);
					}
				}
			}
		});

        //handle click on song title to slide down layout
		songTitleLabel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (i != null) {
					if (i.hasExtra("Uniqid")) {
						String uniqid = i.getStringExtra("Uniqid");
						if (uniqid.equals("FromSettings")) {
							Intent intent = new Intent(MusicPlayerActivity.this, RealTimeActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromLibrary")) {
							Intent intent = new Intent(MusicPlayerActivity.this, LibraryActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								SongSingleton.getInstance().setSongName(songTitleLabel.getText().toString());
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSearch")) {
							Intent intent = new Intent(MusicPlayerActivity.this, RealTimeActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
								intent.putExtra("ID", "FromPlayer");
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSongs")) {
							Intent intent = new Intent(MusicPlayerActivity.this, Songs.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromPlaylistSongs")) {
							Intent intent = new Intent(MusicPlayerActivity.this, PlaylistSongs.class);
							String oldP = getIntent().getStringExtra("OldPlaylist");
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								intent.putExtra("OldPlaylist", oldP);
								intent.putExtra("Name", oldP);
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromDownloads")) {
							Intent intent = new Intent(MusicPlayerActivity.this, Downloads.class);
							intent.putExtra("UniqId", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromChat")) {
							Intent intent = new Intent(MusicPlayerActivity.this, Chat.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromFavourites")) {
							Intent intent = new Intent(MusicPlayerActivity.this, Favourites.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSearch")) {
							Intent intent = new Intent(MusicPlayerActivity.this, RealTimeActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromMyPlaylists")) {
							Intent intent = new Intent(MusicPlayerActivity.this, MyPlaylists.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSharedPlSongs")) {
							Intent intent = new Intent(MusicPlayerActivity.this, MyPlaylists.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						}
						else {
							Intent intent = new Intent(MusicPlayerActivity.this, RealTimeActivity.class);
							intent.putExtra("ID", "FromMP");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						}
						overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);
					}
				}
			}
		});

		//handle click on down arrow to slide down player layout
		downarrow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (i != null) {
					if (i.hasExtra("Uniqid")) {
						String uniqid = i.getStringExtra("Uniqid");
						if (uniqid.equals("FromSettings")) {
							Intent intent = new Intent(MusicPlayerActivity.this, RealTimeActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromLibrary")) {
							Intent intent = new Intent(MusicPlayerActivity.this, LibraryActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								SongSingleton.getInstance().setSongName(songTitleLabel.getText().toString());
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSearch")) {
							Intent intent = new Intent(MusicPlayerActivity.this, RealTimeActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
								intent.putExtra("ID", "FromPlayer");
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSongs")) {
							Intent intent = new Intent(MusicPlayerActivity.this, Songs.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromPlaylistSongs")) {
							Intent intent = new Intent(MusicPlayerActivity.this, PlaylistSongs.class);
							String oldP = getIntent().getStringExtra("OldPlaylist");
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								intent.putExtra("OldPlaylist", oldP);
								intent.putExtra("Name", oldP);
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromDownloads")) {
							Intent intent = new Intent(MusicPlayerActivity.this, Downloads.class);
							intent.putExtra("UniqId", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromChat")) {
							Intent intent = new Intent(MusicPlayerActivity.this, Chat.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromFavourites")) {
							Intent intent = new Intent(MusicPlayerActivity.this, Favourites.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSearch")) {
							Intent intent = new Intent(MusicPlayerActivity.this, RealTimeActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromMyPlaylists")) {
							Intent intent = new Intent(MusicPlayerActivity.this, MyPlaylists.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSharedPlSongs")) {
							Intent intent = new Intent(MusicPlayerActivity.this, MyPlaylists.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						}
						else {
							Intent intent = new Intent(MusicPlayerActivity.this, RealTimeActivity.class);
							intent.putExtra("ID", "FromMP");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						}
						overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);
					}
				}
			}
		});

        // Updating progress bar
        updateProgressBar();

        //Play button click event
        //plays a song and changes button to pause image
        //pauses a song and changes button to play image
		btnPlay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// check for already playing
				if (mediaPlayer.isPlaying()) {
					if (mediaPlayer != null) {
						mediaPlayer.pause();
						// Changing button image to play button
						btnPlay.setImageResource(R.drawable.btn_play);
						eraseFromFirebase();
					}
				} else {
					// Resume song
					if (mediaPlayer != null) {
						mediaPlayer.start();
						// Changing button image to pause button
						String songtitle = songTitleLabel.getText().toString();
						getFollowers(UserDetails.fullname, songtitle);
						btnPlay.setImageResource(R.drawable.btn_pause);
					}
				}
			}
		});


		/**
		 * Forward button click event
		 * Forwards song specified seconds
		 * */
		btnForward.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// get current song position
				int currentPosition = mediaPlayer.getCurrentPosition();
				// check if seekForward time is lesser than song duration
				if (currentPosition + seekForwardTime <= mediaPlayer.getDuration()) {
					// forward song
					mediaPlayer.seekTo(currentPosition + seekForwardTime);
				} else {
					// forward to end position
					mediaPlayer.seekTo(mediaPlayer.getDuration());
				}
			}
		});

		/**
		 * Backward button click event
		 * Backward song to specified seconds
		 * */
		btnBackward.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// get current song position
				int currentPosition = mediaPlayer.getCurrentPosition();
				// check if seekBackward time is greater than 0 sec
				if (currentPosition - seekBackwardTime >= 0) {
					//forward song
					mediaPlayer.seekTo(currentPosition - seekBackwardTime);
				} else {
					// backward to starting position
					mediaPlayer.seekTo(0);
				}
			}
		});

        //Next button click event
        //Plays next song by taking currentSongIndex + 1
		btnNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// check if next song is there or not
				if(currentSongIndex < (songs.size() - 1)){
					playSong(currentSongIndex + 1);
					currentSongIndex = currentSongIndex + 1;
				}else{
					// play first song
					playSong(0);
					currentSongIndex = 0;
				}
			}
		});

		/**
		 * Back button click event
		 * Plays previous song by currentSongIndex - 1
		 * */
		btnPrevious.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String currentSong = songTitleLabel.getText().toString();
				currentSongIndex = songs.indexOf(currentSong);
				if (currentSongIndex > 0) {
					playSong(currentSongIndex - 1);
					currentSongIndex = currentSongIndex - 1;
				} else {
					// play last song
					playSong(songs.size() - 1);
					currentSongIndex = songs.size() - 1;
				}

			}
		});

		/**
		 * Button Click event for Repeat button
		 * Enables repeat flag to true
		 * */
		btnRepeat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (isRepeat) {
					isRepeat = false;
					Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
					songTitle = songTitleLabel.getText().toString();
					btnRepeat.setImageResource(R.drawable.btn_repeat);
				} else {
					// make repeat to true
					isRepeat = true;
					Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
					// make shuffle to false
					isShuffle = false;
					btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
					btnShuffle.setImageResource(R.drawable.btn_shuffle);
				}
			}
		});

        //Button Click event for Shuffle button
        //Enables shuffle flag to true
		btnShuffle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (isShuffle) {
					isShuffle = false;
					Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
					btnShuffle.setImageResource(R.drawable.btn_shuffle);
				} else {
					// make repeat to true
					isShuffle = true;
					Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
					// make shuffle to false
					isRepeat = false;
					btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
					btnRepeat.setImageResource(R.drawable.btn_repeat);
				}
			}
		});
	}

    /**
     * Function to play a song
     *
     * @param songIndex - index of song
     */
    public void playSong(final int songIndex) {

		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(urls.get(songIndex));
			mediaPlayer.prepare();
			mediaPlayer.start();
			Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
			Map<String, Object> uinfo = new HashMap<>();
			uinfo.put("Song", songs.get(songIndex));
			refsong.updateChildren(uinfo);
			Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
			Firebase songRef = ref.child("URL").child(songs.get(songIndex));
			songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
				@Override
				public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
					for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
						getFollowers(UserDetails.fullname, songs.get(songIndex));
					}
				}

				@Override
				public void onCancelled(FirebaseError firebaseError) {

				}
			});
			// Changing Button Image to pause image
			btnPlay.setImageResource(R.drawable.btn_pause);
			// set Progress bar values
			songProgressBar.setProgress(0);
			songProgressBar.setMax(100);
			// Updating progress bar
			updateProgressBar();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * Gets followers.
     *
     * @param fullname the fullname
     * @param mysong   the mysong
     */
    public void getFollowers(String fullname, final String mysong) {
        final ArrayAdapter<String> fadapter;
		UserDetails.mysong = mysong;
		fadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myFollowers);
		DatabaseReference fdb;
		fdb = FirebaseDatabase.getInstance().getReference().child("Followers").child(fullname);
		fdb.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
					String value = snapshot.getKey();
					myFollowers.add(value);
					UserDetails.myFollowers.add(value);
					fadapter.notifyDataSetChanged();
				}
				eraseFromRecents(mysong);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}

    /**
     * Erase from recents.
     *
     * @param mysong the mysong
     */
    public void eraseFromRecents(String mysong) {
		DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("FriendsActivity");
		mDatabase1.addListenerForSingleValueEvent(
				new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						String v;
						for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
							v = snapshot.getKey();
							if (dataSnapshot.child(v).hasChild(getMyFullname(ID))) {
                                dataSnapshot.child(v).child(getMyFullname(ID)).getRef().removeValue();
							}
						}
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {

					}
				});
		addToHome(UserDetails.myFollowers, mysong);
	}

    /**
     * Gets fullname.
     *
     * @param id the id
     * @return the my fullname
     */
    public String getMyFullname(String id) {

		DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(id).child("Name");

		mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				UserDetails.fullname = dataSnapshot.getValue().toString();
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}

		});
		return UserDetails.fullname;
	}


    /**
     * Add to home.
     *
     * @param myvalue the myvalue
     * @param mysong  the mysong
     */
    public void addToHome(List<String> myvalue, final String mysong) {

		String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		DatabaseReference mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");

		mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				UserDetails.myname = dataSnapshot.getValue().toString();
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

		for (int i = 0; i <= myvalue.size() - 1; i++) {
			Firebase ref4 = new Firebase("https://tunein-633e5.firebaseio.com/Homepage/" + myvalue.get(i));
			Map<String, Object> uinfo = new HashMap<>();
			if(!RealTimeActivity.checkBox.isChecked()) {
				uinfo.put("Song", mysong);
				if (!UserDetails.picturelink.equals("")) {
					uinfo.put("Picture", UserDetails.picturelink);
				} else {
					uinfo.put("Picture", "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/ProfilePictures%2Fdefault-user.png?alt=media&token=98996406-225b-4572-a494-b6306ce9a288");
				}
				ref4.child(UserDetails.fullname).updateChildren(uinfo);
			} else {
				eraseFromFirebase();
			}
		}
	}

    /**
     * Add to friend activity.
     *
     * @param myvalue the myvalue
     * @param mysong  the mysong
     */
    public void addToFriendActivity(List<String> myvalue, final String mysong) {

		String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		DatabaseReference mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");
		mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				UserDetails.myname = dataSnapshot.getValue().toString();
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

		for (int i = 0; i <= myvalue.size() - 1; i++) {
			Firebase refAct = new Firebase("https://tunein-633e5.firebaseio.com/FriendsActivity/" + myvalue.get(i));
			Map<String, Object> udet = new HashMap<>();
			if(!RealTimeActivity.checkBox.isChecked()) {
				udet.put("Song", mysong);
				udet.put("Time", System.currentTimeMillis());
				if (!UserDetails.picturelink.equals("")) {
					udet.put("Picture", UserDetails.picturelink);

				} else {
					udet.put("Picture", "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/ProfilePictures%2Fdefault-user.png?alt=media&token=98996406-225b-4572-a494-b6306ce9a288");
				}
				refAct.child(UserDetails.fullname).updateChildren(udet);
			}
		}
	}


    /**
     * Repeat song.
     *
     * @param songIndex the song index
     * @param songName  the song name
     */
    public void repeatSong(int songIndex, String songName) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(urls.get(songIndex));
			mediaPlayer.prepare();
			mediaPlayer.start();
			Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
			Map<String, Object> uinfo = new HashMap<>();
			uinfo.put("Song", songName);
			refsong.updateChildren(uinfo);
			btnPlay.setImageResource(R.drawable.btn_pause);

			// set Progress bar values
			songProgressBar.setProgress(0);
			songProgressBar.setMax(100);

			// Updating progress bar
			updateProgressBar();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * Update timer on seekbar
     */
    public void updateProgressBar() {
		mHandler.postDelayed(mUpdateTimeTask, 100);
	}

	/**
	 * Background Runnable thread
	 */
	public Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
				long totalDuration = mediaPlayer.getDuration();
				long currentDuration = mediaPlayer.getCurrentPosition();

				// Displaying Total Duration time
				songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
				// Displaying time completed playing
				songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));
				// Updating progress bar
				int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
				//Log.d("Progress", ""+progress);
				songProgressBar.setProgress(progress);
				// Running this thread after 100 milliseconds
				mHandler.postDelayed(this, 100);
			}
	};



    /**
	 * On Song Playing completed
	 * if repeat is ON play same song again
	 * if shuffle is ON play random song
	 * */
	@Override
	public void onCompletion(MediaPlayer arg0) {

		// check for repeat is ON or OFF
		if(isRepeat){
			// repeat is on play same song again
			repeatSong(currentSongIndex, songTitle);
		} else if(isShuffle){
			// shuffle is on - play a random song
			Random rand = new Random();
			currentSongIndex = rand.nextInt((songs.size() - 1) - 0 + 1) + 0;
			playSong(currentSongIndex);
		} else{
			// no repeat or shuffle ON - play next song
			if(currentSongIndex < (songs.size() - 1)){
				playSong(currentSongIndex + 1);
				currentSongIndex = currentSongIndex + 1;
			}else{
				playSong(0);
				currentSongIndex = 0;
			}
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	// remove message Handler from updating progress bar
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	//	/**
	//	 * When user stops moving the progress hanlder
	//	 * */
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		int totalDuration = mediaPlayer.getDuration();
		int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

		// forward or backward to certain seconds
		mediaPlayer.seekTo(currentPosition);

		// update timer progress again
		updateProgressBar();
	}

    @Override
    protected void onPause() {
        super.onPause();
    }

	//erase from homepage when song stops
	public void eraseFromFirebase() {
		DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Homepage");
		mDatabase1.addListenerForSingleValueEvent(
				new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						String v;
						for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
							v = snapshot.getKey();
							if (dataSnapshot.child(v).hasChild(getMyFullname(ID))) {
								dataSnapshot.child(v).child(getMyFullname(ID)).getRef().removeValue();
							}
						}
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {

					}
				});
		addToFriendActivity(UserDetails.myFollowers, UserDetails.mysong, ID);
	}

	//add to friend activity
	public void addToFriendActivity(List<String> myvalue, final String mysong, final String myid) {

		DatabaseReference mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");

		mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				UserDetails.myname = dataSnapshot.getValue().toString();
				String me = dataSnapshot.getValue().toString();
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

		for (int i = 0; i <= myvalue.size() - 1; i++) {

			Firebase refAct = new Firebase("https://tunein-633e5.firebaseio.com/FriendsActivity/" + myvalue.get(i));
			Map<String, Object> udet = new HashMap<>();

			if (!RealTimeActivity.checkBox.isChecked()) {
				udet.put("Song", mysong);
				udet.put("Time", System.currentTimeMillis());
				if (!UserDetails.picturelink.equals("")) {
					udet.put("Picture", UserDetails.picturelink);

				} else {
					udet.put("Picture", "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/ProfilePictures%2Fdefault-user.png?alt=media&token=98996406-225b-4572-a494-b6306ce9a288");
				}
				refAct.child(UserDetails.fullname).updateChildren(udet);
			}
		}

	}


}

