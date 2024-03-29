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

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Duration;

public enum RateLimitInterval {
    _15_MINUTES(Duration.ofMinutes(15)),
    _30_MINUTES(Duration.ofMinutes(30)),
    _1_HOUR(Duration.ofHours(1)),
    _1_DAY(Duration.ofDays(1));

    private final Duration duration;

    RateLimitInterval(final Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm");
    }

    public Duration getDuration() {
        return this.duration;
    }
}
