package com.alexstyl.specialdates.events;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;

import com.alexstyl.specialdates.EventsUpdateTrigger;

public final class ContactsObserver extends ContentObserver implements EventsUpdateTrigger {

    private static final Uri URI = ContactsContract.Contacts.CONTENT_URI;
    private final ContentResolver resolver;

    private Callback callback;

    public ContactsObserver(ContentResolver resolver) {
        super(new Handler());
        this.resolver = resolver;
    }

    @Override
    public void startObserving(Callback callback) {
        this.callback = callback;
        resolver.registerContentObserver(URI, false, this);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        callback.onMonitorTriggered();
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        callback.onMonitorTriggered();
    }

    @Override
    public void stopObserving() {
        resolver.unregisterContentObserver(this);
    }

}
