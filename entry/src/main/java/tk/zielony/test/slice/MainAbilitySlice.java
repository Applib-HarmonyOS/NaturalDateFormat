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

package tk.zielony.test.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import tk.zielony.naturaldateformat.AbsoluteDateFormat;
import tk.zielony.naturaldateformat.NaturalDateFormat;
import tk.zielony.naturaldateformat.RelativeDateFormat;
import tk.zielony.test.ResourceTable;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.util.Date;

/**
 * MainAbilitySlice.
 */
public class MainAbilitySlice extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Text tv = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        tv.setMultipleLine(true);
        Date now = new Date();
        SecureRandom random = new SecureRandom();
        long year2 = 1000L * 60 * 60 * 24 * 365 * 2;
        long hour3 = 1000L * 60 * 60 * 3;
        long month = 1000L * 60 * 60 * 24 * 30;
        StringBuilder buffer = new StringBuilder();
        DateFormat format = DateFormat.getDateTimeInstance();
        long[] mod = new long[]{ year2, hour3, year2, month};

        NaturalDateFormat[] formats = new NaturalDateFormat[]{
            new RelativeDateFormat(this, NaturalDateFormat.DATE),
            new RelativeDateFormat(this, NaturalDateFormat.TIME),
            new AbsoluteDateFormat(this, NaturalDateFormat.WEEKDAY | NaturalDateFormat.DATE),
            new AbsoluteDateFormat(this, NaturalDateFormat.DATE
                        | NaturalDateFormat.HOURS | NaturalDateFormat.MINUTES)
        };

        ((AbsoluteDateFormat) formats[2]).setAbbreviated(true);
        ((AbsoluteDateFormat) formats[3]).setTwelveHour(true);

        for (int j = 0; j < formats.length; j++) {
            for (int i = 0; i < 5; i++) {
                Date date = new Date(now.getTime() + random.nextLong() % mod[j]);
                buffer.append(format.format(date))
                        .append("  -  ")
                        .append(formats[j].format(date.getTime()))
                        .append("\n");
            }
            buffer.append("\n");
        }
        tv.setText(buffer.toString());
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
