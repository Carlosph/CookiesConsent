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
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class CookiesConsentDialog extends CookiesConsentAlert {

    private boolean cancelable = false;

    public CookiesConsentDialog(@NonNull final Activity activity) {
        this.activity = activity;
    }

    public CookiesConsentDialog setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public CookiesConsentDialog setPolicyUrl(@NonNull String policyUrl) {
        this.policyUrl = policyUrl;
        return this;
    }

    public CookiesConsentDialog setListener(CookiesConsentListener listener) {
        this.listener = listener;
        return this;
    }

    protected void show() {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setNegativeButton(R.string.exit_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                })
                .setPositiveButton(R.string.continue_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        policyAccepted();
                    }
                })
                .setTitle(R.string.dialog_title)
                .setMessage(getFormattedMessage())
                .setCancelable(cancelable)
                .show();

        ((TextView) dialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
    }
}
