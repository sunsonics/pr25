package com.example.pr25;
import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCatSound, mChickenSound, mCowSound, mDogSound, mDuckSound, mSheepSound;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            // Для устройств до Android 5
//            createOldSoundPool();
//        } else {
//            // Для новых устройств
//            createNewSoundPool();
//        }

//        mAssetManager = getAssets();
//
//        // получим идентификаторы
//        mCatSound = loadSound("cat.ogg");
//        mChickenSound = loadSound("chicken.ogg");
//        mCowSound = loadSound("cow.ogg");
//        mDogSound = loadSound("dog.ogg");
//        mDuckSound = loadSound("duck.ogg");
//        mSheepSound = loadSound("sheep.ogg");

        ImageView cowImageButton = findViewById(R.id.imageButton);
        cowImageButton.setOnClickListener(onClickListener);

        ImageView chickenImageButton = findViewById(R.id.imageButton2);
        chickenImageButton.setOnClickListener(onClickListener);

        ImageView catImageButton = findViewById(R.id.imageButton3);
        catImageButton.setOnClickListener(onClickListener);

        ImageView duckImageButton = findViewById(R.id.imageButton4);
        duckImageButton.setOnClickListener(onClickListener);

        ImageView sheepImageButton = findViewById(R.id.imageButton5);
        sheepImageButton.setOnClickListener(onClickListener);

        ImageView dogImageButton = findViewById(R.id.imageButton6);
        dogImageButton.setOnClickListener(onClickListener);


        cowImageButton.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                if (eventAction == MotionEvent.ACTION_UP) {
                    // Отпускаем палец
                    if (mStreamID > 0)
                        mSoundPool.stop(mStreamID);
                }
                if (eventAction == MotionEvent.ACTION_DOWN) {
                    // Нажимаем на кнопку
                    mStreamID = playSound(mCowSound);
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    mSoundPool.stop(mStreamID);
                }
                return true;
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.imageButton) {
                playSound(mCowSound);
            } else if (id == R.id.imageButton2) {
                playSound(mChickenSound);
            } else if (id == R.id.imageButton3) {
                playSound(mCatSound);
            } else if (id == R.id.imageButton4) {
                playSound(mDuckSound);
            } else if (id == R.id.imageButton5) {
                playSound(mSheepSound);
            } else if (id == R.id.imageButton6) {
                playSound(mDogSound);
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mCatSound = loadSound("jivotnoe-odna-korova-myichit-odin-raz-31987.ogg");
        mChickenSound = loadSound("koza-bleet.ogg");
        mCowSound = loadSound("gromkiy-krik-vzroslogo-petuha.ogg");
        mDogSound = loadSound("loshad-rjet-i-skachet-galopom.ogg");
        mDuckSound = loadSound("tsyiplyata-37374.ogg");
        mSheepSound = loadSound("zvuk41.ogg");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}
