/*
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
 *
 *
 * Code from the Stack Overflow question:
 * http://stackoverflow.com/questions/31739347/android-eu-cookie-law
 *
 * Question by Mr.Betatester:
 * http://stackoverflow.com/users/2235837/mr-betatester
 *
 * Answer by Ruyo:
 * http://stackoverflow.com/users/5240952/ruyo
 */

package com.github.carlosph.cookiesconsent;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.TimeZone;

public class EuropeanUserChecker {

    private static String TAG = "EU checker";

    public static boolean isEU(Context context) {
        boolean error = false;

/* is eu sim */

        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) {
                simCountry = simCountry.toUpperCase();

                if (EUCountry.contains(simCountry)) {
                    Log.v(TAG, "is EU User (sim)");
                    return true;
                }
            }
        } catch (Exception e) {
            error = true;
        }


/* is eu network */
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE) {
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) {
                    networkCountry = networkCountry.toUpperCase();

                    if (EUCountry.contains(networkCountry)) {
                        Log.v(TAG, "is EU User (network)");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            error = true;
        }

/* is eu time zone id */
        try {
            String tz = TimeZone.getDefault().getID().toLowerCase();
            if (tz.length() < 10) {
                error = true;
            } else if (tz.contains("euro")) {
                Log.v(TAG, "is EU User (time)");
                return true;
            }
        } catch (Exception e) {
            error = true;
        }


        if (error == true) {
            Log.v(TAG, "is EU User (err)");
            return true;
        }

        return false;
    }

    ;

    private enum EUCountry {
        AT, BE, BG, HR, CY, CZ, DK, EE, FI, FR, DE, GR, HU, IE, IT, LV, LT, LU, MT, NL, PL, PT, RO, SK, SI, ES, SE, GB, //28 member states
        GF, PF, TF, //French territories French Guiana,Polynesia,Southern Territories
        EL, UK,  //alternative EU names for GR and GB
        ME, IS, AL, RS, TR, MK; //candidate countries

        public static boolean contains(String s) {
            for (EUCountry eucountry : values())
                if (eucountry.name().equalsIgnoreCase(s))
                    return true;
            return false;
        }

    }
}
