package com.alexstyl.specialdates.search;

import android.content.Context;

import com.alexstyl.specialdates.date.Date;
import com.alexstyl.specialdates.events.namedays.NameCelebrations;
import com.alexstyl.specialdates.events.namedays.NamedayLocale;
import com.alexstyl.specialdates.events.namedays.NamedayUserSettings;
import com.alexstyl.specialdates.events.namedays.calendar.NamedayCalendar;
import com.alexstyl.specialdates.events.namedays.calendar.resource.NamedayCalendarProvider;
import com.alexstyl.specialdates.ui.loader.SimpleAsyncTaskLoader;

final class NamedaysLoader extends SimpleAsyncTaskLoader<NameCelebrations> {

    private final NamedayUserSettings namedayUserSettings;
    private final String searchQuery;
    private final int year;
    private final NamedayCalendarProvider calendarProvider;

    private NamedayCalendar namedayCalendar;

    public static NamedaysLoader newInstance(Context context,
                                             String searchQuery,
                                             NamedayUserSettings namedayPreferences,
                                             NamedayCalendarProvider calendarProvider) {
        int year = Date.Companion.today().getYear();
        return new NamedaysLoader(context, namedayPreferences, searchQuery, calendarProvider, year);
    }

    private NamedaysLoader(Context context, NamedayUserSettings namedayUserSettings, String searchQuery, NamedayCalendarProvider calendarProvider, int year) {
        super(context);
        this.namedayUserSettings = namedayUserSettings;
        this.searchQuery = searchQuery;
        this.calendarProvider = calendarProvider;
        this.year = year;
    }

    @Override
    public NameCelebrations loadInBackground() {
        return getNamedays(searchQuery);
    }

    private NameCelebrations getNamedays(String searchQuery) {
        if (namedayUserSettings.isEnabled()) {
            return getNamedayCalendar().getAllNamedays(searchQuery);
        }
        return new NameCelebrations(searchQuery);
    }

    private NamedayCalendar getNamedayCalendar() {
        if (namedayCalendar == null) {
            NamedayLocale locale = namedayUserSettings.getSelectedLanguage();
            namedayCalendar = calendarProvider.loadNamedayCalendarForLocale(locale, year);
        }
        return namedayCalendar;

    }
}
