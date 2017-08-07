package com.alexstyl.specialdates.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.alexstyl.android.Version;

import java.util.List;

public class MementoActivity extends AppCompatActivity {

    /**
     * Override this method in order to let the activity handle the up button.
     * When pressed it will navigate the user to the parent of the activity
     */
    protected boolean shouldUseHomeAsUp() {
        return false;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (shouldUseHomeAsUp()) {
            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setHomeButtonEnabled(true);
                bar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return handleUp();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    private boolean handleUp() {
        if (!shouldUseHomeAsUp()) {
            return false;
        }
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }

    protected Context context() {
        return this;
    }

    protected boolean supportsTransitions() {
        return Version.hasKitKat();
    }

    protected Activity thisActivity() {
        return this;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return onKeyMenuPressed();
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public boolean onKeyMenuPressed() {
        return false;
    }

}
