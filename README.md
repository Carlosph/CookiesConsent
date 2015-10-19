# CookiesConsent for Android

Android library to inform EU users of the use of cookies. The alert can be shown via dialog or an overlay view.

![Screenshot of the dialog](screenshot.png)

![Screenshot of the overlay](overlay_screenshot.png)

## Setup

Just add the dependency to your *gradle.build*
```groovy
compile 'com.github.7graus:cookiesconsent:1.0.0'
```

## Usage

Simply create a new ```CookiesConsetDialog``` or ```CookiesConsetOverlay``` and call the method ```showIfApplies```.

To add a link to your privacy policy use the method ```setPolicyUrl(String policyUrl)```.

You only need to add one line to the **onCreate** method of your **MainActivity** to show the dialog or overlay to EU users when they open the app for the first time.

```java
new CookiesConsentDialog(this)
    .setPolicyUrl("https://github.com/Carlosph/CookiesConsent")
    .showIfApplies();
```

```java
new CookiesConsentOverlay(this)
    .setPolicyUrl("https://github.com/Carlosph/CookiesConsent")
    .showIfApplies();
```