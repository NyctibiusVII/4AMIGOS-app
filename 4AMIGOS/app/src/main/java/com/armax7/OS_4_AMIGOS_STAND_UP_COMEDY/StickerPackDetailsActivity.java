/*
 * Copyright (c) WhatsApp Inc. and its affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.armax7.OS_4_AMIGOS_STAND_UP_COMEDY;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class StickerPackDetailsActivity extends AddStickerPackActivity {

    /**
     * Do not change below values of below 3 lines as this is also used by WhatsApp
     */
    public static final String EXTRA_STICKER_PACK_ID = "sticker_pack_id";
    public static final String EXTRA_STICKER_PACK_AUTHORITY = "sticker_pack_authority";
    public static final String EXTRA_STICKER_PACK_NAME = "sticker_pack_name";

    public static final String EXTRA_STICKER_PACK_WEBSITE = "sticker_pack_website";
    public static final String EXTRA_STICKER_PACK_EMAIL = "sticker_pack_email";
    public static final String EXTRA_STICKER_PACK_PRIVACY_POLICY = "sticker_pack_privacy_policy";
    public static final String EXTRA_STICKER_PACK_LICENSE_AGREEMENT = "sticker_pack_license_agreement";
    public static final String EXTRA_STICKER_PACK_TRAY_ICON = "sticker_pack_tray_icon";
    public static final String EXTRA_SHOW_UP_BUTTON = "show_up_button";
    public static final String EXTRA_STICKER_PACK_DATA = "sticker_pack";

    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_pack_details);
        boolean showUpButton = getIntent().getBooleanExtra(EXTRA_SHOW_UP_BUTTON, false);
        mViewHolder.stickerPack = getIntent().getParcelableExtra(EXTRA_STICKER_PACK_DATA);
        TextView packNameTextView = findViewById(R.id.pack_name);
        TextView packPublisherTextView = findViewById(R.id.author);
        ImageView packTrayIcon = findViewById(R.id.tray_image);
        TextView packSizeTextView = findViewById(R.id.pack_size);

        mViewHolder.addButton = findViewById(R.id.add_to_whatsapp_button);
        mViewHolder.alreadyAddedText = findViewById(R.id.already_added_text);
        mViewHolder.layoutManager = new GridLayoutManager(this, 1);
        mViewHolder.recyclerView = findViewById(R.id.sticker_list);
        mViewHolder.recyclerView.setLayoutManager(mViewHolder.layoutManager);
        mViewHolder.recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(pageLayoutListener);
        mViewHolder.recyclerView.addOnScrollListener(dividerScrollListener);
        mViewHolder.divider = findViewById(R.id.divider);
        if (mViewHolder.stickerPreviewAdapter == null) {
            mViewHolder.stickerPreviewAdapter = new StickerPreviewAdapter(getLayoutInflater(), R.drawable.sticker_error, getResources().getDimensionPixelSize(R.dimen.sticker_pack_details_image_size), getResources().getDimensionPixelSize(R.dimen.sticker_pack_details_image_padding), mViewHolder.stickerPack);
            mViewHolder.recyclerView.setAdapter(mViewHolder.stickerPreviewAdapter);
        }
        packNameTextView.setText(mViewHolder.stickerPack.name);
        packPublisherTextView.setText(mViewHolder.stickerPack.publisher);
        packTrayIcon.setImageURI(StickerPackLoader.getStickerAssetUri(mViewHolder.stickerPack.identifier, mViewHolder.stickerPack.trayImageFile));
        packSizeTextView.setText(Formatter.formatShortFileSize(this, mViewHolder.stickerPack.getTotalSize()));
        this.mViewHolder.addButton.setOnClickListener(v -> addStickerPackToWhatsApp(mViewHolder.stickerPack.identifier, mViewHolder.stickerPack.name));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showUpButton);
            getSupportActionBar().setTitle(showUpButton ? getResources().getString(R.string.title_activity_sticker_pack_details_multiple_pack) : getResources().getQuantityString(R.plurals.title_activity_sticker_packs_list, 1));
        }
    }

    private void launchInfoActivity(String publisherWebsite, String publisherEmail, String privacyPolicyWebsite, String licenseAgreementWebsite, String trayIconUriString) {
        Intent intent = new Intent(StickerPackDetailsActivity.this, StickerPackInfoActivity.class);
        intent.putExtra(StickerPackDetailsActivity.EXTRA_STICKER_PACK_ID, mViewHolder.stickerPack.identifier);
        intent.putExtra(StickerPackDetailsActivity.EXTRA_STICKER_PACK_WEBSITE, publisherWebsite);
        intent.putExtra(StickerPackDetailsActivity.EXTRA_STICKER_PACK_EMAIL, publisherEmail);
        intent.putExtra(StickerPackDetailsActivity.EXTRA_STICKER_PACK_PRIVACY_POLICY, privacyPolicyWebsite);
        intent.putExtra(StickerPackDetailsActivity.EXTRA_STICKER_PACK_LICENSE_AGREEMENT, licenseAgreementWebsite);
        intent.putExtra(StickerPackDetailsActivity.EXTRA_STICKER_PACK_TRAY_ICON, trayIconUriString);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info && mViewHolder.stickerPack != null) {
            Uri trayIconUri = StickerPackLoader.getStickerAssetUri(mViewHolder.stickerPack.identifier, mViewHolder.stickerPack.trayImageFile);
            launchInfoActivity(mViewHolder.stickerPack.publisherWebsite, mViewHolder.stickerPack.publisherEmail, mViewHolder.stickerPack.privacyPolicyWebsite, mViewHolder.stickerPack.licenseAgreementWebsite, trayIconUri.toString());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final ViewTreeObserver.OnGlobalLayoutListener pageLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            setNumColumns(mViewHolder.recyclerView.getWidth() / mViewHolder.recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.sticker_pack_details_image_size));
        }
    };

    private void setNumColumns(int numColumns) {
        if (this.mViewHolder.numColumns != numColumns) {
            mViewHolder.layoutManager.setSpanCount(numColumns);
            this.mViewHolder.numColumns = numColumns;
            if (mViewHolder.stickerPreviewAdapter != null) {
                mViewHolder.stickerPreviewAdapter.notifyDataSetChanged();
            }
        }
    }

    private final RecyclerView.OnScrollListener dividerScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull final RecyclerView recyclerView, final int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            updateDivider(recyclerView);
        }

        @Override
        public void onScrolled(@NonNull final RecyclerView recyclerView, final int dx, final int dy) {
            super.onScrolled(recyclerView, dx, dy);
            updateDivider(recyclerView);
        }

        private void updateDivider(RecyclerView recyclerView) {
            boolean showDivider = recyclerView.computeVerticalScrollOffset() > 0;
            if (mViewHolder.divider != null) {
                mViewHolder.divider.setVisibility(showDivider ? View.VISIBLE : View.INVISIBLE);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mViewHolder.whiteListCheckAsyncTask = new WhiteListCheckAsyncTask(this);
        mViewHolder.whiteListCheckAsyncTask.execute(mViewHolder.stickerPack);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mViewHolder.whiteListCheckAsyncTask != null && !mViewHolder.whiteListCheckAsyncTask.isCancelled()) {
            mViewHolder.whiteListCheckAsyncTask.cancel(true);
        }
    }

    private void updateAddUI(Boolean isWhitelisted) {
        if (isWhitelisted) {
            mViewHolder.addButton.setVisibility(View.GONE);
            mViewHolder.alreadyAddedText.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.addButton.setVisibility(View.VISIBLE);
            mViewHolder.alreadyAddedText.setVisibility(View.GONE);
        }
    }

    static class WhiteListCheckAsyncTask extends AsyncTask<StickerPack, Void, Boolean> {
        private final WeakReference<StickerPackDetailsActivity> stickerPackDetailsActivityWeakReference;

        WhiteListCheckAsyncTask(StickerPackDetailsActivity stickerPackListActivity) {
            this.stickerPackDetailsActivityWeakReference = new WeakReference<>(stickerPackListActivity);
        }

        @Override
        protected final Boolean doInBackground(StickerPack... stickerPacks) {
            StickerPack stickerPack = stickerPacks[0];
            final StickerPackDetailsActivity stickerPackDetailsActivity = stickerPackDetailsActivityWeakReference.get();
            if (stickerPackDetailsActivity == null) {
                return false;
            }
            return WhitelistCheck.isWhitelisted(stickerPackDetailsActivity, stickerPack.identifier);
        }

        @Override
        protected void onPostExecute(Boolean isWhitelisted) {
            final StickerPackDetailsActivity stickerPackDetailsActivity = stickerPackDetailsActivityWeakReference.get();
            if (stickerPackDetailsActivity != null) {
                stickerPackDetailsActivity.updateAddUI(isWhitelisted);
            }
        }
    }
    private static class ViewHolder {
        private RecyclerView recyclerView;
        private GridLayoutManager layoutManager;
        private StickerPreviewAdapter stickerPreviewAdapter;
        private int numColumns;
        private View addButton;
        private View alreadyAddedText;
        private StickerPack stickerPack;
        private View divider;
        private WhiteListCheckAsyncTask whiteListCheckAsyncTask;
    }
}
