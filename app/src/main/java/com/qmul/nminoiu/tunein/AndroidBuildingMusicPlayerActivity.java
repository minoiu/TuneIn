package com.qmul.nminoiu.tunein;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;


public class AndroidBuildingMusicPlayerActivity extends Activity implements OnCompletionListener, SeekBar.OnSeekBarChangeListener {

	private ImageButton btnPlay;
	private ImageButton btnForward;
	private ImageButton btnBackward;
	private ImageButton btnNext;
	private ImageButton btnPrevious;
	private ImageButton btnPlaylist;
	private ImageButton btnRepeat;
	private ImageButton btnShuffle;
	private ImageButton downarrow;
	private LinearLayout playerHeader;

	public static SeekBar songProgressBar;
	private TextView songTitleLabel;
	public static TextView songCurrentDurationLabel;
	public static TextView songTotalDurationLabel;
	// Media Player
//	makeprivate MediaPlayer mp;
	// Handler to update UI timer, progress bar etc,.
	private Handler mHandler = new Handler();
	;
	private SongsManager songManager;
	private Utilities utils;
	private int seekForwardTime = 5000; // 5000 milliseconds
	private int seekBackwardTime = 5000; // 5000 milliseconds
	private int currentSongIndex = 0;
	private boolean isShuffle = false;
	private boolean isRepeat = false;
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private String songTitle;
    private String activity;
    private Intent i;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
		setContentView(R.layout.player);

		// All player buttons
		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		btnForward = (ImageButton) findViewById(R.id.btnForward);
		btnBackward = (ImageButton) findViewById(R.id.btnBackward);
		btnNext = (ImageButton) findViewById(R.id.btnNext);
		btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
		btnPlaylist = (ImageButton) findViewById(R.id.btnPlaylist);
		btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
		btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
		songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
		songTitleLabel = (TextView) findViewById(R.id.songTitle);
		songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
		songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
		playerHeader = (LinearLayout) findViewById(R.id.downlayout);
		downarrow = (ImageButton) playerHeader.findViewById(R.id.downarrow);
		downarrow.bringToFront();

//        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.slide_down_info);


        i = getIntent();
		final String songTitle = i.getStringExtra("Song");
		songTitleLabel.setText(UserDetails.playingSongName);

		// Mediaplayer
//		mp = new MediaPlayer();
		songManager = new SongsManager();
		utils = new Utilities();
//
		// Listeners
		songProgressBar.setOnSeekBarChangeListener(this); // Important
		mediaPlayer.setOnCompletionListener(this); // Important
        mediaPlayer.getCurrentPosition();
        songProgressBar.setProgress(mediaPlayer.getCurrentPosition());
        songProgressBar.setMax(100);

		playerHeader.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (i != null) {
					if (i.hasExtra("Uniqid")) {
						String uniqid = i.getStringExtra("Uniqid");
						if (uniqid.equals("FromSettings")) {
							Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, SettingsActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromLibrary")) {
							Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, LibraryActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
							//overridePendingTransition(R.anim.pull_up_from_bottom, R.anim.push_out_to_bottom);
							//startActivity(intent);
						} else if (uniqid.equals("FromSearch")) {
							Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, SettingsActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();
								intent.putExtra("ID", "FromPlayer");
							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSongs")) {
							Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, Songs.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromPlaylistSongs")) {
							Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, PlaylistSongs.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromDownloads")) {
							Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, Downloads.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromChat")) {
							Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, Chat.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromFavourites")) {
							Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, Favourites.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSearch")) {
							Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, SettingsActivity.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromMyPlaylists")) {
							Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, MyPlaylists.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						} else if (uniqid.equals("FromSharedPlSongs")) {
							Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, MyPlaylists.class);
							intent.putExtra("ID", "FromPlayer");
							if (mediaPlayer.isPlaying()) {
								intent.putExtra("Song", songTitleLabel.getText().toString());
								UserDetails.playingSongName = songTitleLabel.getText().toString();

							}
							finish();
							overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
						}
						else {
							Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, SettingsActivity.class);
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

//		// Getting all songs list
//		songsList = songManager.getPlayList();
//
//		// By default play first song
		//playSong(0);
//
//		/**
//		 * Play button click event
//		 * plays a song and changes button to pause image
//		 * pauses a song and changes button to play image
//		 * */
		btnPlay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// check for already playing
				if (mediaPlayer.isPlaying()) {
					if (mediaPlayer != null) {
						mediaPlayer.pause();
						// Changing button image to play button
						btnPlay.setImageResource(R.drawable.btn_play);
					}
				} else {
					// Resume song
					if (mediaPlayer != null) {
						mediaPlayer.start();
						// Changing button image to pause button
						btnPlay.setImageResource(R.drawable.btn_pause);
					}
				}

			}
		});

        downarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i != null) {
                    if (i.hasExtra("Uniqid")) {
                        String uniqid = i.getStringExtra("Uniqid");
                        if (uniqid.equals("FromSettings")) {
                            Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, SettingsActivity.class);
                            intent.putExtra("ID", "FromPlayer");
                            if (mediaPlayer.isPlaying()) {
                                intent.putExtra("Song", songTitleLabel.getText().toString());
                            }
                            finish();
                            overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
                        } else if (uniqid.equals("FromLibrary")) {
                            Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, LibraryActivity.class);
                            intent.putExtra("ID", "FromPlayer");
                            if (mediaPlayer.isPlaying()) {
                                intent.putExtra("Song", songTitleLabel.getText().toString());
                            }
                            finish();
                            overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
                            //overridePendingTransition(R.anim.pull_up_from_bottom, R.anim.push_out_to_bottom);
                            //startActivity(intent);
                        } else if (uniqid.equals("FromSearch")) {
                            Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, SettingsActivity.class);
                            intent.putExtra("ID", "FromPlayer");
                            if (mediaPlayer.isPlaying()) {
                                intent.putExtra("Song", songTitleLabel.getText().toString());
                                intent.putExtra("ID", "FromPlayer");
                            }
                            startActivity(intent);
                        } else if (uniqid.equals("FromSongs")) {
                            Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, LibraryActivity.class);
                            intent.putExtra("ID", "FromPlayer");
                            if (mediaPlayer.isPlaying()) {
                                intent.putExtra("Song", songTitleLabel.getText().toString());
                            }
                            startActivity(intent);
                        } else if (uniqid.equals("FromPlaylistSongs")) {
                            Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, PlaylistSongs.class);
                            intent.putExtra("ID", "FromPlayer");
                            if (mediaPlayer.isPlaying()) {
                                intent.putExtra("Song", songTitleLabel.getText().toString());
                            }
                            startActivity(intent);
                        } else if (uniqid.equals("FromDownloaded")) {
                            Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, LibraryActivity.class);
                            intent.putExtra("ID", "FromPlayer");
                            if (mediaPlayer.isPlaying()) {
                                intent.putExtra("Song", songTitleLabel.getText().toString());
                            }
                            startActivity(intent);
                        } else if (uniqid.equals("FromChat")) {
                            Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, Chat.class);
                            intent.putExtra("ID", "FromPlayer");
                            if (mediaPlayer.isPlaying()) {
                                intent.putExtra("Song", songTitleLabel.getText().toString());
                            }
                            finish();
                            overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
                        } else if (uniqid.equals("FromFavourites")) {
                            Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, LibraryActivity.class);
                            intent.putExtra("ID", "FromPlayer");
                            if (mediaPlayer.isPlaying()) {
                                intent.putExtra("Song", songTitleLabel.getText().toString());
                            }
                            startActivity(intent);
                        } else if (uniqid.equals("FromSearch")) {
                            Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, SettingsActivity.class);
                            intent.putExtra("ID", "FromPlayer");
                            if (mediaPlayer.isPlaying()) {
                                intent.putExtra("Song", songTitleLabel.getText().toString());
                            }
                            startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(AndroidBuildingMusicPlayerActivity.this, SettingsActivity.class);
                        intent.putExtra("ID", "FromMP");
                        if (mediaPlayer.isPlaying()) {
                            intent.putExtra("Song", songTitleLabel.getText().toString());
                        }
                        startActivity(intent);
                    }
                   // overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);
                }
            }
        });
//
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
//
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
					// forward song
					mediaPlayer.seekTo(currentPosition - seekBackwardTime);
				} else {
					// backward to starting position
					mediaPlayer.seekTo(0);
				}

			}
		});
//
//		/**
//		 * Next button click event
//		 * Plays next song by taking currentSongIndex + 1
//		 * */
//		btnNext.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// check if next song is there or not
//				if(currentSongIndex < (songsList.size() - 1)){
//					playSong(currentSongIndex + 1);
//					currentSongIndex = currentSongIndex + 1;
//				}else{
//					// play first song
//					playSong(0);
//					currentSongIndex = 0;
//				}
//			}
//		});
//
		/**
		 * Back button click event
		 * Plays previous song by currentSongIndex - 1
		 * */
//		btnPrevious.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				if (currentSongIndex > 0) {
//					playSong(currentSongIndex - 1);
//					currentSongIndex = currentSongIndex - 1;
//				} else {
//					// play last song
//					playSong(songsList.size() - 1);
//					currentSongIndex = songsList.size() - 1;
//				}
//
//			}
//		});
//
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


//		/**
//		 * Button Click event for Shuffle button
//		 * Enables shuffle flag to true
//		 * */
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
//
//		/**
//		 * Button Click event for Play list click event
//		 * Launches list activity which displays list of songs
//		 * */
//		btnPlaylist.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				Intent i = new Intent(getApplicationContext(), PlayListActivity.class);
//				startActivityForResult(i, 100);
//			}
//		});
//
//	}
//
//	/**
//	 * Receiving song index from playlist view
//	 * and play the song
//	 * */
//	@Override
//    protected void onActivityResult(int requestCode,
//                                     int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == 100){
//         	 currentSongIndex = data.getExtras().getInt("songIndex");
//         	 // play selected song
//             playSong(currentSongIndex);
//        }
//    }

		/**
		 * Function to play a song
		 * @param songIndex - index of song
		 * */

	public void playSong(int songIndex) {

		//btnPlay.setImageResource(R.drawable.btn_pause);

		// Play song
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(SettingsActivity.url);
			mediaPlayer.prepare();
			mediaPlayer.start();

			// Displaying Song title
			String songTitle = SettingsActivity.song;
			songTitleLabel.setText(songTitle);
//
			// Changing Button Image to pause image
			btnPlay.setImageResource(R.drawable.btn_pause);
//
			// set Progress bar values
			songProgressBar.setProgress(0);
			songProgressBar.setMax(100);
//
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
	private Runnable mUpdateTimeTask = new Runnable() {
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
			playSong(currentSongIndex);
		} else if(isShuffle){
			// shuffle is on - play a random song
			Random rand = new Random();
			currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
			playSong(currentSongIndex);
		} else{
			// no repeat or shuffle ON - play next song
			if(currentSongIndex < (songsList.size() - 1)){
				playSong(currentSongIndex + 1);
				currentSongIndex = currentSongIndex + 1;
			}else{
				// play first song
				playSong(0);
				currentSongIndex = 0;
			}
		}
	}

//	@Override
//	 public void onDestroy(){
//	 super.onDestroy();
//		mediaPlayer.release();
//}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
//		// remove message Handler from updating progress bar
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

	public void showSong(){

	}

    @Override
    protected void onPause()
    {
        super.onPause();
//        finish();
//        overridePendingTransition(R.anim.stay, R.anim.slide_down_info);
       // overridePendingTransition(R.anim.pull_up_from_bottom, R.anim.push_out_to_bottom);
    }
}

