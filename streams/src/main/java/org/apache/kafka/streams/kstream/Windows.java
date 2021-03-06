/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kafka.streams.kstream;

import java.util.Map;

/**
 * The window specification interface that can be extended for windowing operation in joins and aggregations.
 *
 * @param <W>   type of the window instance
 */
public abstract class Windows<W extends Window> {

    private static final int DEFAULT_NUM_SEGMENTS = 3;

    private static final long DEFAULT_MAINTAIN_DURATION = 24 * 60 * 60 * 1000L;   // one day

    private long maintainDurationMs;

    public int segments;

    protected Windows() {
        this.segments = DEFAULT_NUM_SEGMENTS;
        this.maintainDurationMs = DEFAULT_MAINTAIN_DURATION;
    }

    /**
     * Set the window maintain duration in milliseconds of streams time.
     * This retention time is a guaranteed <i>lower bound</i> for how long a window will be maintained.
     *
     * @return  itself
     */
    public Windows<W> until(long durationMs) {
        this.maintainDurationMs = durationMs;

        return this;
    }

    /**
     * Specify the number of segments to be used for rolling the window store,
     * this function is not exposed to users but can be called by developers that extend this JoinWindows specs.
     *
     * @return  itself
     */
    protected Windows<W> segments(int segments) {
        this.segments = segments;

        return this;
    }

    /**
     * Return the window maintain duration in milliseconds of streams time.
     *
     * @return the window maintain duration in milliseconds of streams time
     */
    public long maintainMs() {
        return this.maintainDurationMs;
    }

    /**
     * Creates all windows that contain the provided timestamp, indexed by non-negative window start timestamps.
     *
     * @param timestamp  the timestamp window should get created for
     * @return  a map of {@code windowStartTimestamp -> Window} entries
     */
    public abstract Map<Long, W> windowsFor(long timestamp);

    public abstract long size();
}
