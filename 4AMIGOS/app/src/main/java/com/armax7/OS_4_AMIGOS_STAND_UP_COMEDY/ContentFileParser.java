/*
 * Copyright (c) WhatsApp Inc. and its affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.armax7.OS_4_AMIGOS_STAND_UP_COMEDY;

import android.text.TextUtils;
import android.util.JsonReader;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class ContentFileParser {

    private static final int LIMIT_EMOJI_COUNT = 3;

    @NonNull
    static List<StickerPack> parseStickerPacks(@NonNull InputStream contentsInputStream) throws IOException, IllegalStateException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(contentsInputStream))) {
            return readStickerPacks(reader);
        }
    }

    @NonNull
    private static List<StickerPack> readStickerPacks(@NonNull JsonReader reader) throws IOException, IllegalStateException {
        List<StickerPack> stickerPackList = new ArrayList<>();
        String androidPlayStoreLink = null;
        String iosAppStoreLink = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String key = reader.nextName();
            if ("android_play_store_link".equals(key)) {
                androidPlayStoreLink = reader.nextString();
            } else if ("ios_app_store_link".equals(key)) {
                iosAppStoreLink = reader.nextString();
            } else if ("sticker_packs".equals(key)) {
                reader.beginArray();
                while (reader.hasNext()) {
                    StickerPack stickerPack = readStickerPack(reader);
                    stickerPackList.add(stickerPack);
                }
                reader.endArray();
            } else {
                throw new IllegalStateException("campo desconhecido em json: " + key);
            }
        }
        reader.endObject();
        if (stickerPackList.size() == 0) {
            throw new IllegalStateException("a lista de pacotes de adesivos não pode estar vazia");
        }
        for (StickerPack stickerPack : stickerPackList) {
            stickerPack.setAndroidPlayStoreLink(androidPlayStoreLink);
            stickerPack.setIosAppStoreLink(iosAppStoreLink);
        }
        return stickerPackList;
    }

    @NonNull
    private static StickerPack readStickerPack(@NonNull JsonReader reader) throws IOException, IllegalStateException {
        reader.beginObject();
        String identifier = null;
        String name = null;
        String publisher = null;
        String trayImageFile = null;
        String publisherEmail = null;
        String publisherWebsite = null;
        String privacyPolicyWebsite = null;
        String licenseAgreementWebsite = null;
        String imageDataVersion = "";
        boolean avoidCache = false;
        List<Sticker> stickerList = null;
        while (reader.hasNext()) {
            String key = reader.nextName();
            switch (key) {
                case "identifier":
                    identifier = reader.nextString();
                    break;
                case "name":
                    name = reader.nextString();
                    break;
                case "publisher":
                    publisher = reader.nextString();
                    break;
                case "tray_image_file":
                    trayImageFile = reader.nextString();
                    break;
                case "publisher_email":
                    publisherEmail = reader.nextString();
                    break;
                case "publisher_website":
                    publisherWebsite = reader.nextString();
                    break;
                case "privacy_policy_website":
                    privacyPolicyWebsite = reader.nextString();
                    break;
                case "license_agreement_website":
                    licenseAgreementWebsite = reader.nextString();
                    break;
                case "stickers":
                    stickerList = readStickers(reader);
                    break;
                case "image_data_version":
                    imageDataVersion = reader.nextString();
                    break;
                case "avoid_cache":
                    avoidCache = reader.nextBoolean();
                    break;
                default:
                    reader.skipValue();
            }
        }
        if (TextUtils.isEmpty(identifier)) {
            throw new IllegalStateException("identificador não pode estar vazio");
        }
        if (TextUtils.isEmpty(name)) {
            throw new IllegalStateException("o nome não pode estar vazio");
        }
        if (TextUtils.isEmpty(publisher)) {
            throw new IllegalStateException("o editor não pode estar vazio");
        }
        if (TextUtils.isEmpty(trayImageFile)) {
            throw new IllegalStateException("tray_image_file não pode estar vazio");
        }
        if (stickerList == null || stickerList.size() == 0) {
            throw new IllegalStateException("lista de adesivos está vazia");
        }
        if (identifier.contains("..") || identifier.contains("/")) {
            throw new IllegalStateException("identificador não deve conter .. ou / para impedir a passagem do diretório");
        }
        if (TextUtils.isEmpty(imageDataVersion)) {
            throw new IllegalStateException("image_data_version não deve estar vazio");
        }
        reader.endObject();
        final StickerPack stickerPack = new StickerPack(identifier, name, publisher, trayImageFile, publisherEmail, publisherWebsite, privacyPolicyWebsite, licenseAgreementWebsite, imageDataVersion, avoidCache);
        stickerPack.setStickers(stickerList);
        return stickerPack;
    }

    @NonNull
    private static List<Sticker> readStickers(@NonNull JsonReader reader) throws IOException, IllegalStateException {
        reader.beginArray();
        List<Sticker> stickerList = new ArrayList<>();

        while (reader.hasNext()) {
            reader.beginObject();
            String imageFile = null;
            List<String> emojis = new ArrayList<>(LIMIT_EMOJI_COUNT);
            while (reader.hasNext()) {
                final String key = reader.nextName();
                if ("image_file".equals(key)) {
                    imageFile = reader.nextString();
                } else if ("emojis".equals(key)) {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        String emoji = reader.nextString();
                        emojis.add(emoji);
                    }
                    reader.endArray();
                } else {
                    throw new IllegalStateException("campo desconhecido em json: " + key);
                }
            }
            reader.endObject();
            if (TextUtils.isEmpty(imageFile)) {
                throw new IllegalStateException("adesivo image_file não pode estar vazio");
            }
            if (!imageFile.endsWith(".webp")) {
                throw new IllegalStateException("arquivo de imagem para adesivos deve ser arquivos webp, arquivo de imagem é: " + imageFile);
            }
            if (imageFile.contains("..") || imageFile.contains("/")) {
                throw new IllegalStateException("o nome do arquivo não deve conter .. ou / para impedir a travessia do diretório, o arquivo de imagem é: " + imageFile);
            }
            stickerList.add(new Sticker(imageFile, emojis));
        }
        reader.endArray();
        return stickerList;
    }
}
