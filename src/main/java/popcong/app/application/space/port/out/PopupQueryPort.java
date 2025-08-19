package popcong.app.application.space.port.out;

import popcong.app.application.space.dto.response.NearbyPopupsDto;

public interface PopupQueryPort {
    NearbyPopupsDto findNearbyPopups(double latitude, double longitude, double radiusKm, int limit);
}
