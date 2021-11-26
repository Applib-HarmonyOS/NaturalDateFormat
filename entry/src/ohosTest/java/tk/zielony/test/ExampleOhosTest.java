/*
 * Copyright (C) 2020-21 Application Library Engineering Group
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

package tk.zielony.test;

import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.app.Context;
import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import tk.zielony.naturaldateformat.AbsoluteDateFormat;

import static org.junit.Assert.assertEquals;

public class ExampleOhosTest {
    private AbsoluteDateFormat absoluteDateFormat;
    private Context context;
    private int format = 5;

    public void setUp()  {
        context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
    }

    public void tearDown() {
        //this is an empty method
    }

    @Test
    public void testisAbbreviated() {
        absoluteDateFormat = new AbsoluteDateFormat(context,format);
        absoluteDateFormat.setAbbreviated(true);
        assertEquals(true,absoluteDateFormat.isAbbreviated());
    }

    @Test
    public void testisTwelveHour() {
        absoluteDateFormat = new AbsoluteDateFormat(context, format);
        absoluteDateFormat.setTwelveHour(true);
        assertEquals(true, absoluteDateFormat.isTwelveHour());
    }

    @Test
    public void testgetYearFormat() {
        absoluteDateFormat = new AbsoluteDateFormat(context, format);
        absoluteDateFormat.setYearFormat(DateTimeFormat.fullTime());
        assertEquals(DateTimeFormat.fullTime(),absoluteDateFormat.getYearFormat());
    }

    @Test
    public void testgetWeekdayFormat() {
        absoluteDateFormat = new AbsoluteDateFormat(context, format);
        absoluteDateFormat.setWeekdayFormat(DateTimeFormat.fullTime());
        assertEquals(DateTimeFormat.fullTime(),absoluteDateFormat.getWeekdayFormat());
    }

    @Test
    public void testgetDateFormat() {
        absoluteDateFormat = new AbsoluteDateFormat(context, format);
        absoluteDateFormat.setDateFormat(DateTimeFormat.fullTime());
        assertEquals(DateTimeFormat.fullTime(),absoluteDateFormat.getDateFormat());
    }

    @Test
    public void testgetOmitTime() {
        absoluteDateFormat = new AbsoluteDateFormat(context, format);
        absoluteDateFormat.setOmitTime(DateTimeFieldType.monthOfYear());
        assertEquals(DateTimeFieldType.monthOfYear(), absoluteDateFormat.getOmitTime());
    }

    @Test
    public void testgetOmitWeekday() {
        absoluteDateFormat = new AbsoluteDateFormat(context, format);
        absoluteDateFormat.setOmitWeekday(DateTimeFieldType.weekOfWeekyear());
        assertEquals(DateTimeFieldType.weekOfWeekyear(), absoluteDateFormat.getOmitWeekday());
    }

    @Test
    public void testBundleName() {
        final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();
        assertEquals("tk.zielony.test", actualBundleName);
    }
}