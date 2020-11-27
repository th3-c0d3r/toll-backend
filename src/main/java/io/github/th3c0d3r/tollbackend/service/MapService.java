package io.github.th3c0d3r.tollbackend.service;

import io.github.th3c0d3r.tollbackend.dto.LatLongDto;
import io.github.th3c0d3r.tollbackend.dto.RouteCostDto;
import io.github.th3c0d3r.tollbackend.dto.TollPlazaDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class MapService {

    private static String INDIA_BOUNDING_BOX = "68.1766451354,7.96553477623,97.4025614766,35.4940095078";
    private static String INDIAN_PLACE_SEARCH_URL = "https://nominatim.openstreetmap.org/search?q=salem&format=json&viewbox=" + INDIA_BOUNDING_BOX;
    private static String ROUTE_URL = "http://router.project-osrm.org/route/v1/driving/";

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private TollPlazaService tollPlazaService;


    public RouteCostDto getRoute(Double startLat, Double startLong, Double stopLat, Double stopLong) {

        String routeJsonStr = webClientBuilder.build()
                .get()
                .uri(ROUTE_URL + startLong + "," + startLat + ";" + stopLong + "," + stopLat + "?overview=false&geometries=geojson&steps=true")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONArray steps = (new JSONObject(routeJsonStr)).getJSONArray("routes").getJSONObject(0).getJSONArray("legs")
                .getJSONObject(0).getJSONArray("steps");

        List<LatLongDto> latLongDtoList = new ArrayList<>();
        for (Object stepObj : steps) {
            JSONObject step = (JSONObject) stepObj;
            for (Object cordinateObj : step.getJSONObject("geometry").getJSONArray("coordinates")) {
                latLongDtoList.add(new LatLongDto(Double.parseDouble(((JSONArray) cordinateObj).get(1).toString()),
                        Double.parseDouble(((JSONArray) cordinateObj).get(0).toString())));
            }
        }
        return RouteCostDto.builder()
                .routeLatLongs(latLongDtoList)
                .build();
    }

    public RouteCostDto getCost(Double startLat, Double startLong, Double stopLat, Double stopLong) {
        List<LatLongDto> latLongDtos = getRoute(startLat, startLong, stopLat, stopLong).getRouteLatLongs();
        List<TollPlazaDto> tollPlazaDtoList = tollPlazaService.getByStateAndTollName(null, null);

        Set<TollPlazaDto> tollPlazasInARoute = new HashSet<>();
        latLongDtos.parallelStream().forEach(latLongDto -> {
            for (TollPlazaDto tollPlazaDto : tollPlazaDtoList) {
                double distance = (haversine(latLongDto.getLat(), latLongDto.getLng(), tollPlazaDto.getLatitude(), tollPlazaDto.getLongitude()) * 1000);
                if (distance <= 100) {
                    tollPlazasInARoute.add(tollPlazaDto);
                }
            }
        });
        log.info("{}",tollPlazasInARoute.size());

        double singleTripLMV = 0d, singleTripLCV = 0d, singleTripBusTruck = 0d, singleTrip3Axle = 0d, singleTrip4To6Axle = 0d, singleTripHCM = 0d, singleTrip7PlusAxle = 0d;
        double returnTripLMV = 0d, returnTripLCV = 0d, returnTripBusTruck = 0d, returnTrip3Axle = 0d, returnTrip4To6Axle = 0d, returnTripHCM = 0d, returnTrip7PlusAxle = 0d;
        for(TollPlazaDto tollPlazaDto : tollPlazasInARoute){
            if (tollPlazaDto.getLMVSingle() != null) {
                singleTripLMV += tollPlazaDto.getLMVSingle();
            }
            if (tollPlazaDto.getLCVSingle() != null) {
                singleTripLCV += tollPlazaDto.getLCVSingle();
            }
            if (tollPlazaDto.getBusTruckSingle() != null) {
                singleTripBusTruck += tollPlazaDto.getBusTruckSingle();
            }
            if (tollPlazaDto.getAxle3Single() != null) {
                singleTrip3Axle += tollPlazaDto.getAxle3Single();
            }
            if (tollPlazaDto.getAxle4To6Single() != null) {
                singleTrip4To6Axle += tollPlazaDto.getAxle4To6Single();
            }
            if (tollPlazaDto.getHCMSingle() != null) {
                singleTripHCM += tollPlazaDto.getHCMSingle();
            }
            if (tollPlazaDto.getAxle7PlusSingle() != null) {
                singleTrip7PlusAxle += tollPlazaDto.getAxle7PlusSingle();
            }
            if (tollPlazaDto.getLMVReturn() != null) {
                returnTripLMV += tollPlazaDto.getLMVReturn();
            }
            if (tollPlazaDto.getLCVReturn() != null) {
                returnTripLCV += tollPlazaDto.getLCVReturn();
            }
            if (tollPlazaDto.getBusTruckReturn() != null) {
                returnTripBusTruck += tollPlazaDto.getBusTruckReturn();
            }
            if (tollPlazaDto.getAxle3Return() != null) {
                returnTrip3Axle += tollPlazaDto.getAxle3Return();
            }
            if (tollPlazaDto.getAxle4To6Return() != null) {
                returnTrip4To6Axle += tollPlazaDto.getAxle4To6Return();
            }
            if (tollPlazaDto.getHCMReturn() != null) {
                returnTripHCM += tollPlazaDto.getHCMReturn();
            }
            if (tollPlazaDto.getAxle7PlusReturn() != null) {
                returnTrip7PlusAxle += tollPlazaDto.getAxle7PlusReturn();
            }
        }
        RouteCostDto routeCostDto = RouteCostDto.builder()
                .singleLMVCost(singleTripLMV)
                .singleLCVCost(singleTripLCV)
                .singleBusTruckCost(singleTripBusTruck)
                .single3AxleCost(singleTrip3Axle)
                .single4To6AxleCost(singleTrip4To6Axle)
                .singleHCMCost(singleTripHCM)
                .single7PlusAxleCost(singleTrip7PlusAxle)
                .returnLMVCost(returnTripLMV)
                .returnLCVCost(returnTripLCV)
                .returnBusTruckCost(returnTripBusTruck)
                .return3AxleCost(returnTrip3Axle)
                .return4To6AxleCost(returnTrip4To6Axle)
                .returnHCMCost(returnTripHCM)
                .return7PlusAxleCost(returnTrip7PlusAxle)
                .routeLatLongs(latLongDtos)
                .build();

        return routeCostDto;
    }

    static double haversine(double lat1, double lon1,
                            double lat2, double lon2) {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }
}
