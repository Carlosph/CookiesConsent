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
import android.support.annotation.NonNull;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CookiesConsentOverlay extends CookiesConsentAlert {

    public CookiesConsentOverlay(@NonNull final Activity activity) {
        this.activity = activity;
    }

    public CookiesConsentOverlay setPolicyUrl(@NonNull String policyUrl) {
        this.policyUrl = policyUrl;
        return this;
    }

    public CookiesConsentOverlay setListener(CookiesConsentListener listener) {
        this.listener = listener;
        return this;
    }

    protected void show() {
        ViewGroup rootView = getRootView();
        if (rootView != null)
            inflateOverlayView(rootView);
    }

    private ViewGroup getRootView() {
        View decor = activity.getWindow().getDecorView();
        return (ViewGroup) decor.findViewById(android.R.id.content);
    }

    private View inflateOverlayView(ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.cookies_consent_overlay, parent, true);
        final View overlayView = view.findViewById(R.id.overlayLayout);

        View button = view.findViewById(R.id.overlayButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overlayView.setVisibility(View.GONE);
                policyAccepted();
            }
        });

        TextView text = (TextView) view.findViewById(R.id.overlayText);
        text.setText(getFormattedMessage());
        text.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }

}
