/**
 * Copyright © 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay.entities;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BrowserInfo {
    @SerializedName("AcceptHeader")
    private final String acceptHeader;
    @SerializedName("JavaEnabled")
    private final boolean javaEnabled;
    @SerializedName("Language")
    private final String language;
    @SerializedName("ColorDepth")
    private final int colorDepth;
    @SerializedName("ScreenHeight")
    private final int screenHeight;
    @SerializedName("ScreenWidth")
    private final int screenWidth;
    @SerializedName("TimeZoneOffset")
    private final int timeZoneOffset;
    @SerializedName("UserAgent")
    private final String userAgent;
    @SerializedName("JavascriptEnabled")
    private final boolean javascriptEnabled;

    public BrowserInfo(final String acceptHeader, final boolean javaEnabled, final String language, final int colorDepth, final int screenHeight, final int screenWidth, final int timeZoneOffset, final String userAgent, final boolean javascriptEnabled) {
        this.acceptHeader = acceptHeader;
        this.javaEnabled = javaEnabled;
        this.language = language;
        this.colorDepth = colorDepth;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.timeZoneOffset = timeZoneOffset;
        this.userAgent = userAgent;
        this.javascriptEnabled = javascriptEnabled;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public String getAcceptHeader() {
        return acceptHeader;
    }

    public boolean isJavaEnabled() {
        return javaEnabled;
    }

    public String getLanguage() {
        return language;
    }

    public int getColorDepth() {
        return colorDepth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public boolean isJavascriptEnabled() {
        return javascriptEnabled;
    }
}
