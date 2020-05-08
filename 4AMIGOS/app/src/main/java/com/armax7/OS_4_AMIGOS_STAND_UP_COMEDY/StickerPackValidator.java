/*
 * Copyright (c) WhatsApp Inc. and its affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.armax7.OS_4_AMIGOS_STAND_UP_COMEDY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;

import com.facebook.animated.webp.WebPImage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

class StickerPackValidator {
    private static final int STICKER_FILE_SIZE_LIMIT_KB = 100;
    private static final int EMOJI_LIMIT = 3;
    private static final int IMAGE_HEIGHT = 512;
    private static final int IMAGE_WIDTH = 512;
    private static final int STICKER_SIZE_MIN = 3;
    private static final int STICKER_SIZE_MAX = 30;
    private static final int CHAR_COUNT_MAX = 128;
    private static final long ONE_KIBIBYTE = 8 * 1024;
    private static final int TRAY_IMAGE_FILE_SIZE_MAX_KB = 50;
    private static final int TRAY_IMAGE_DIMENSION_MIN = 24;
    private static final int TRAY_IMAGE_DIMENSION_MAX = 512;
    private static final String PLAY_STORE_DOMAIN = "play.google.com";
    private static final String APPLE_STORE_DOMAIN = "itunes.apple.com";


    /**
     * Checks whether a sticker pack contains valid data
     */
    static void verifyStickerPackValidity(@NonNull Context context, @NonNull StickerPack stickerPack) throws IllegalStateException {
        if (TextUtils.isEmpty(stickerPack.identifier)) {
            throw new IllegalStateException("o identificador do pacote de adesivos está vazio");
        }
        if (stickerPack.identifier.length() > CHAR_COUNT_MAX) {
            throw new IllegalStateException("o identificador do pacote de adesivos não pode exceder " + CHAR_COUNT_MAX + " personagens");
        }
        checkStringValidity(stickerPack.identifier);
        if (TextUtils.isEmpty(stickerPack.publisher)) {
            throw new IllegalStateException("o editor do pacote de adesivos está vazio, identificador do pacote de adesivos:" + stickerPack.identifier);
        }
        if (stickerPack.publisher.length() > CHAR_COUNT_MAX) {
            throw new IllegalStateException("o editor do pacote de adesivos não pode exceder " + CHAR_COUNT_MAX + " personagens, identificador do pacote de adesivos:" + stickerPack.identifier);
        }
        if (TextUtils.isEmpty(stickerPack.name)) {
            throw new IllegalStateException("o nome do pacote de adesivos está vazio, identificador do pacote de adesivos:" + stickerPack.identifier);
        }
        if (stickerPack.name.length() > CHAR_COUNT_MAX) {
            throw new IllegalStateException("o nome do pacote de adesivos não pode exceder " + CHAR_COUNT_MAX + " personagens, identificador do pacote de adesivos:" + stickerPack.identifier);
        }
        if (TextUtils.isEmpty(stickerPack.trayImageFile)) {
            throw new IllegalStateException("o ID da bandeja do pacote de adesivos está vazio, identificador do pacote de adesivos:" + stickerPack.identifier);
        }
        if (!TextUtils.isEmpty(stickerPack.androidPlayStoreLink) && !isValidWebsiteUrl(stickerPack.androidPlayStoreLink)) {
            throw new IllegalStateException("Certifique-se de incluir http ou https nos links de URL, o link da loja de jogos Android não é um URL válido: " + stickerPack.androidPlayStoreLink);
        }
        if (!TextUtils.isEmpty(stickerPack.androidPlayStoreLink) && !isURLInCorrectDomain(stickerPack.androidPlayStoreLink, PLAY_STORE_DOMAIN)) {
            throw new IllegalStateException("O link da loja de jogos Android deve usar o domínio da loja de jogos: " + PLAY_STORE_DOMAIN);
        }
        if (!TextUtils.isEmpty(stickerPack.iosAppStoreLink) && !isValidWebsiteUrl(stickerPack.iosAppStoreLink)) {
            throw new IllegalStateException("Certifique-se de incluir http ou https nos links de URL, o link da loja de aplicativos iOS não é um URL válido: " + stickerPack.iosAppStoreLink);
        }
        if (!TextUtils.isEmpty(stickerPack.iosAppStoreLink) && !isURLInCorrectDomain(stickerPack.iosAppStoreLink, APPLE_STORE_DOMAIN)) {
            throw new IllegalStateException("O link da loja de aplicativos para iOS deve usar o domínio da loja de aplicativos: " + APPLE_STORE_DOMAIN);
        }
        if (!TextUtils.isEmpty(stickerPack.licenseAgreementWebsite) && !isValidWebsiteUrl(stickerPack.licenseAgreementWebsite)) {
            throw new IllegalStateException("Certifique-se de incluir http ou https nos links de URL, O link do contrato de licença não é um URL válido: " + stickerPack.licenseAgreementWebsite);
        }
        if (!TextUtils.isEmpty(stickerPack.privacyPolicyWebsite) && !isValidWebsiteUrl(stickerPack.privacyPolicyWebsite)) {
            throw new IllegalStateException("Certifique-se de incluir http ou https nos links de URL, o link da política de privacidade não é um URL válido: " + stickerPack.privacyPolicyWebsite);
        }
        if (!TextUtils.isEmpty(stickerPack.publisherWebsite) && !isValidWebsiteUrl(stickerPack.publisherWebsite)) {
            throw new IllegalStateException("Certifique-se de incluir http ou https nos links de URL, o link do site do editor não é um URL válido: " + stickerPack.publisherWebsite);
        }
        if (!TextUtils.isEmpty(stickerPack.publisherEmail) && !Patterns.EMAIL_ADDRESS.matcher(stickerPack.publisherEmail).matches()) {
            throw new IllegalStateException("o email do editor não parece válido, o email é: " + stickerPack.publisherEmail);
        }
        try {
            final byte[] bytes = StickerPackLoader.fetchStickerAsset(stickerPack.identifier, stickerPack.trayImageFile, context.getContentResolver());
            if (bytes.length > TRAY_IMAGE_FILE_SIZE_MAX_KB * ONE_KIBIBYTE) {
                throw new IllegalStateException("a imagem da bandeja deve ser menor que " + TRAY_IMAGE_FILE_SIZE_MAX_KB + " KB, arquivo de imagem da bandeja: " + stickerPack.trayImageFile);
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            if (bitmap.getHeight() > TRAY_IMAGE_DIMENSION_MAX || bitmap.getHeight() < TRAY_IMAGE_DIMENSION_MIN) {
                throw new IllegalStateException("a altura da imagem da bandeja deve " + TRAY_IMAGE_DIMENSION_MIN + " e " + TRAY_IMAGE_DIMENSION_MAX + " pixels, a altura atual da imagem na bandeja é " + bitmap.getHeight() + ", arquivo de imagem da bandeja: " + stickerPack.trayImageFile);
            }
            if (bitmap.getWidth() > TRAY_IMAGE_DIMENSION_MAX || bitmap.getWidth() < TRAY_IMAGE_DIMENSION_MIN) {
                throw new IllegalStateException("a largura da imagem da bandeja deve " + TRAY_IMAGE_DIMENSION_MIN + " e " + TRAY_IMAGE_DIMENSION_MAX + " pixels, a largura atual da imagem na bandeja é " + bitmap.getWidth() + ", arquivo de imagem da bandeja: " + stickerPack.trayImageFile);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Não é possível abrir a imagem da bandeja, " + stickerPack.trayImageFile, e);
        }
        final List<Sticker> stickers = stickerPack.getStickers();
        if (stickers.size() < STICKER_SIZE_MIN || stickers.size() > STICKER_SIZE_MAX) {
            throw new IllegalStateException("O número de adesivos deve estar entre 3 e 30, inclusive, atualmente " + stickers.size() + ", identificador do pacote de adesivos:" + stickerPack.identifier);
        }
        for (final Sticker sticker : stickers) {
            validateSticker(context, stickerPack.identifier, sticker);
        }
    }

    private static void validateSticker(@NonNull Context context, @NonNull final String identifier, @NonNull final Sticker sticker) throws IllegalStateException {
        if (sticker.emojis.size() > EMOJI_LIMIT) {
            throw new IllegalStateException("a contagem de emoji excede o limite, identificador do pacote de adesivos:" + identifier + ", nome do arquivo:" + sticker.imageFileName);
        }
        if (TextUtils.isEmpty(sticker.imageFileName)) {
            throw new IllegalStateException("nenhum caminho de arquivo para adesivo, identificador de pacote de adesivos:" + identifier);
        }
        validateStickerFile(context, identifier, sticker.imageFileName);
    }

    private static void validateStickerFile(@NonNull Context context, @NonNull String identifier, @NonNull final String fileName) throws IllegalStateException {
        try {
            final byte[] bytes = StickerPackLoader.fetchStickerAsset(identifier, fileName, context.getContentResolver());
            if (bytes.length > STICKER_FILE_SIZE_LIMIT_KB * ONE_KIBIBYTE) {
                throw new IllegalStateException("adesivo deve ser menor que " + STICKER_FILE_SIZE_LIMIT_KB + "KB, identificador do pacote de adesivos:" + identifier + ", nome do arquivo:" + fileName);
            }
            try {
                final WebPImage webPImage = WebPImage.create(bytes);
                if (webPImage.getHeight() != IMAGE_HEIGHT) {
                    throw new IllegalStateException("a altura do adesivo deve ser " + IMAGE_HEIGHT + ", identificador do pacote de adesivos:" + identifier + ", nome do arquivo:" + fileName);
                }
                if (webPImage.getWidth() != IMAGE_WIDTH) {
                    throw new IllegalStateException("a largura do adesivo deve ser " + IMAGE_WIDTH + ", identificador do pacote de adesivos:" + identifier + ", nome do arquivo:" + fileName);
                }
                if (webPImage.getFrameCount() > 1) {
                    throw new IllegalStateException("adesivo deve ser uma imagem estática, sem suporte de adesivo animado no momento, identificador do pacote de adesivos:" + identifier + ", nome do arquivo:" + fileName);
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Erro ao analisar a imagem do webp, identificador do pacote de adesivos:" + identifier + ", nome do arquivo:" + fileName, e);
            }
        } catch (IOException e) {
            throw new IllegalStateException("não é possível abrir o arquivo de adesivo: identificador do pacote de adesivos:" + identifier + ", nome do arquivo:" + fileName, e);
        }
    }

    private static void checkStringValidity(@NonNull String string) {
        String pattern = "[\\w-.,'\\s]+"; // [a-zA-Z0-9_-.' ]
        if (!string.matches(pattern)) {
            throw new IllegalStateException(string + " contém caracteres inválidos, os caracteres permitidos são a a z, A a Z, _, '-. e caráter espacial");
        }
        if (string.contains("..")) {
            throw new IllegalStateException(string + " não pode conter ..");
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isValidWebsiteUrl(String websiteUrl) throws IllegalStateException {
        try {
            new URL(websiteUrl);
        } catch (MalformedURLException e) {
            Log.e("StickerPackValidator", "url: " + websiteUrl + " está mal formado");
            throw new IllegalStateException("url: " + websiteUrl + " está mal formado", e);
        }
        return URLUtil.isHttpUrl(websiteUrl) || URLUtil.isHttpsUrl(websiteUrl);

    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isURLInCorrectDomain(String urlString, String domain) throws IllegalStateException {
        try {
            URL url = new URL(urlString);
            if (domain.equals(url.getHost())) {
                return true;
            }
        } catch (MalformedURLException e) {
            Log.e("StickerPackValidator", "url: " + urlString + " está mal formado");
            throw new IllegalStateException("url: " + urlString + " está mal formado");
        }
        return false;
    }
}
