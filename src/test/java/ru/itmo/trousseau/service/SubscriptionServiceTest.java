package ru.itmo.trousseau.service;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.trousseau.object.SubscriptionDescription;

import static org.assertj.core.api.Assertions.assertThat;

class SubscriptionServiceTest extends BaseDbTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @Test
    public void findAllOwnedBy_userHasNoSubs_returnEmptyList() {
        List<SubscriptionDescription> subscriptions = subscriptionService.findAllOwnedBy(1);

        assertThat(subscriptions).isEmpty();
    }

    @Test
    public void findAllOwnedBy_userAbsent_returnEmptyList() {
        List<SubscriptionDescription> subscriptions = subscriptionService.findAllOwnedBy(-1);

        assertThat(subscriptions).isEmpty();
    }

    @Test
    public void findAllOwnedBy_userHasSomeSubs_returnSubs() {
        List<SubscriptionDescription> subscriptions = subscriptionService.findAllOwnedBy(3);

        assertThat(subscriptions).hasSize(1);

        SubscriptionDescription subscription = subscriptions.get(0);

        assertThat(subscription.getName()).isEqualTo("Что-нибудь на замену волко-трусам");
        assertThat(subscription.getCategories()).hasSize(1);
        assertThat(subscription.getCreationDatetime()).isEqualTo(Timestamp.valueOf("2023-09-03 16:30:00"));
    }

}