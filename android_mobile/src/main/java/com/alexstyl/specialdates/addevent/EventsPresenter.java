package com.alexstyl.specialdates.addevent;

import com.alexstyl.specialdates.Optional;
import com.alexstyl.specialdates.contact.Contact;
import com.alexstyl.specialdates.date.Date;
import com.alexstyl.specialdates.events.Event;
import com.alexstyl.specialdates.events.peopleevents.EventType;

import java.util.Collection;
import java.util.List;

public final class EventsPresenter {

    private final ContactEventsFetcher contactEventsFetcher;
    private final ContactDetailsAdapter adapter;
    private final AddEventContactEventViewModelFactory factory;
    private final AddEventViewModelFactory addEventFactory;
    private SelectedEvents events = new SelectedEvents();

    EventsPresenter(ContactEventsFetcher contactEventsFetcher,
                    ContactDetailsAdapter adapter,
                    AddEventContactEventViewModelFactory factory,
                    AddEventViewModelFactory addEventFactory) {
        this.contactEventsFetcher = contactEventsFetcher;
        this.adapter = adapter;
        this.factory = factory;
        this.addEventFactory = addEventFactory;
    }

    void startPresenting() {
        contactEventsFetcher.loadEmptyEvents(onNewContactLoadedCallback);
    }

    void onContactSelected(Contact contact) {
        contactEventsFetcher.load(contact, onNewContactLoadedCallback);
    }

    private final ContactEventsFetcher.OnDataFetchedCallback onNewContactLoadedCallback = new ContactEventsFetcher.OnDataFetchedCallback() {
        @Override
        public void onDataFetched(List<AddEventContactEventViewModel> data) {
            adapter.replace(data);
            updateEventsWith(data);
        }

        private void updateEventsWith(List<AddEventContactEventViewModel> data) {
            events = new SelectedEvents();
            for (AddEventContactEventViewModel viewModel : data) {
                Optional<Date> date = viewModel.getDate();
                if (date.isPresent()) {
                    events.replaceDate(viewModel.getEventType(), date.get());
                }
            }
        }
    };

    void onEventDatePicked(EventType eventType, Date date) {
        AddEventContactEventViewModel viewModels = factory.createViewModelWith(eventType, date);
        adapter.replace(viewModels);
        events.replaceDate(eventType, date);
    }

    void removeEvent(EventType eventType) {
        AddEventContactEventViewModel viewModels = addEventFactory.createAddEventViewModelsFor(eventType);
        adapter.replace(viewModels);
        events.remove(eventType);
    }

    public Collection<Event> getEvents() {
        return events.getEvents();
    }

}
