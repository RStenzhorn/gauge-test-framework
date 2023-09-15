package de.rjst.gaugetestframework.logic;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "gtaBackend", url = "https://gta-backend-service.rjst.de")
public interface GtaBackendService {

    @GetMapping("/players")
    List<Player> getPlayers();

}
