package popcong.app.application.image.port.in;

public interface GeoDistanceUseCase {
    Long getStraightDistance(Double lat1, Double lng1, Double lat2, Double lng2);
}