package com.alexstyl.specialdates.addevent

import com.alexstyl.specialdates.contact.Contact
import com.alexstyl.specialdates.contact.ContactsProvider
import com.alexstyl.specialdates.search.NameMatcher

class ContactsSearch(private val contactsProvider: ContactsProvider, private val nameMatcher: NameMatcher) {

    fun searchForContacts(searchQuery: String, numberOfResults: Int): List<Contact> {
        val matchedContacts = ArrayList<Contact>()
        if (numberOfResults == 0) {
            return matchedContacts
        }
        val allContacts = contactsProvider.allContacts
        if (allContacts.isEmpty()) {
            return matchedContacts
        }
        var count = 0
        var index = 0
        val contactSize = allContacts.size
        do {
            val contact = allContacts[index]
            val displayName = contact.displayName
            if (nameMatcher.match(displayName, searchQuery)) {
                matchedContacts.add(contact)
                count++
            }
            index++
        } while (count < numberOfResults && index < contactSize)

        return matchedContacts
    }

}
