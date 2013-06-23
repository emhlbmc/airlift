/*
 * Copyright 2010 Proofpoint, Inc.
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
package com.proofpoint.http.client;

import com.google.common.annotations.Beta;
import com.proofpoint.stats.CounterStat;
import com.proofpoint.stats.DistributionStat;
import com.proofpoint.stats.TimerStat;
import com.proofpoint.units.Duration;
import org.weakref.jmx.Flatten;
import org.weakref.jmx.Managed;
import org.weakref.jmx.Nested;

import javax.inject.Inject;

@Beta
public class RequestStats
{
    private final CounterStat request;
    private final TimerStat requestTime;
    private final TimerStat responseTime;
    private final DistributionStat readBytes;
    private final DistributionStat writtenBytes;

    @Inject
    public RequestStats()
    {
        request = new CounterStat();
        requestTime = new TimerStat();
        responseTime = new TimerStat();
        readBytes = new DistributionStat();
        writtenBytes = new DistributionStat();
    }

    public void record(String method,
            int responseCode,
            long requestSizeInBytes,
            long responseSizeInBytes,
            Duration requestProcessingTime,
            Duration responseProcessingTime)
    {
        request.update(1);
        if (requestProcessingTime != null) {
            requestTime.addValue(requestProcessingTime);
        }
        if (requestProcessingTime != null) {
            responseTime.addValue(responseProcessingTime);
        }
        readBytes.add(responseSizeInBytes);
        writtenBytes.add(requestSizeInBytes);
    }

    @Managed
    @Flatten
    public CounterStat getRequest()
    {
        return request;
    }

    @Managed
    @Nested
    public TimerStat getRequestTime()
    {
        return requestTime;
    }

    @Managed
    @Nested
    public TimerStat getResponseTime()
    {
        return responseTime;
    }

    @Managed
    @Nested
    public DistributionStat getReadBytes()
    {
        return readBytes;
    }

    @Managed
    @Nested
    public DistributionStat getWrittenBytes()
    {
        return writtenBytes;
    }
}
