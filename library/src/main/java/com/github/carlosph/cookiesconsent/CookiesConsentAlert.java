/*
 * Copyright (C) 2015 Carlos Piñar Hafner.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.carlosph.cookiesconsent;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.Html;
import android.text.Spanned;

public abstract class CookiesConsentAlert {

    protected Activity activity;
    protected String policyUrl;
    protected CookiesConsentListener listener;

    public static boolean isConsentNeeded(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("CookiesConsent", Context.MODE_PRIVATE);
        return prefs.getBoolean("isFirstRun", true) && EuropeanUserChecker.isEU(context);
    }

    public void showIfApplies() {
        if (isConsentNeeded(activity)) {
            show();
        } else {
            notifyCookiesAllowed();
        }
    }

    protected abstract void show();

    protected void notifyCookiesAllowed() {
        if (listener != null)
            listener.onCookiesAllowed();
    }

    protected void policyAccepted() {
        SharedPreferences prefs = activity.getSharedPreferences("CookiesConsent", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("isFirstRun", false).apply();
        notifyCookiesAllowed();
    }

    protected Spanned getFormattedMessage() {
        Resources res = activity.getResources();
        String policyText = res.getString(R.string.privacy_policy);
        if (policyUrl != null) {
            policyText = res.getString(R.string.html_link, policyUrl, policyText);
        }
        return Html.fromHtml(res.getString(R.string.dialog_text, policyText));
    }

    public interface CookiesConsentListener {
        public void onCookiesAllowed();
    }
}
