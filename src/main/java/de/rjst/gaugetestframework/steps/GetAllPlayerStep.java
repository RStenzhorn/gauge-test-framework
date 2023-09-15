package de.rjst.gaugetestframework.steps;

import com.thoughtworks.gauge.Step;
import de.rjst.gaugetestframework.logic.GtaBackendService;
import de.rjst.gaugetestframework.logic.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor
@Service
public class GetAllPlayerStep implements Consumer<Integer> {

    private final GtaBackendService gtaBackendService;

    @Override
    @Step("Get all players <playersAmount>")
    public void accept(final Integer playersAmount) {

        final List<Player> result = gtaBackendService.getPlayers();
        assertThat(result.size()).isEqualTo(playersAmount);
    }
}
