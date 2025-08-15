package popcong.app.application.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.application.image.port.in.GeoDistanceUseCase;

@Service
@RequiredArgsConstructor
public class GeoDistanceService implements GeoDistanceUseCase {

    private static final double EARTH_RADIUS_M = 6371000.0; // 지구 반지름 (m)

    // (lat1, lng1)과 (lat2, lng2) 사이 거리 계산 후 반올림 정수 값 반환
    public Long getStraightDistance(Double lat1, Double lng1, Double lat2, Double lng2) {

        if (lat1 == null || lng1 == null || lat2 == null || lng2 == null) {
            return null;
        }

        double rLat1 = Math.toRadians(lat1);
        double rLat2 = Math.toRadians(lat2);
        double dLat  = Math.toRadians(lat2 - lat1);
        double dLon  = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(rLat1) * Math.cos(rLat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        Long distance = Math.round(EARTH_RADIUS_M * c);;

        return distance;
    }
}
