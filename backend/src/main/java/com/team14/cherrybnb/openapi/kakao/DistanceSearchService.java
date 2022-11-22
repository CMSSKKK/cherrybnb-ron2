package com.team14.cherrybnb.openapi.kakao;

import com.team14.cherrybnb.openapi.District;
import com.team14.cherrybnb.openapi.dummy.DistanceInfo;
import com.team14.cherrybnb.openapi.dummy.DistanceInfoResponse;
import com.team14.cherrybnb.openapi.dummy.Position;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class DistanceSearchService {

    private static final String REQUEST_PATH = "/directions";

    private WebClient webClient;

    public DistanceSearchService(@Qualifier("kakao-navi") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<DistanceInfoResponse> searchDistrictInfo(Position position) {
        District[] districts = District.values();
        Map<String, NaviResponse> naviResponses = new HashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(districts.length);
        for (District district : districts) {
            NaviRequest naviRequest = NaviRequest.of(position.getX(), position.getY(),
                    district.getLongitude(), district.getLatitude());

            Mono<NaviResponse> naviResponseMono = sendReqeust(naviRequest).log();

            naviResponseMono.subscribe(naviResponse -> {
                naviResponses.put(district.name(), naviResponse);
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        List<DistanceInfoResponse> distanceInfoResponses = new ArrayList<>();

        for (String districtName : naviResponses.keySet()) {
            NaviResponse naviResponse = naviResponses.get(districtName);
            DistanceInfo distanceInfo = getDistanceInfo(naviResponse);
            String distance = String.valueOf(distanceInfo.getDistance());
            String duration = String.valueOf(distanceInfo.getDuration());
            distanceInfoResponses.add(new DistanceInfoResponse(districtName, distance, duration));
        }

        return distanceInfoResponses;
    }

    private Mono<NaviResponse> sendReqeust(NaviRequest naviRequest) {

        return webClient.get().uri(uriBuilder -> uriBuilder.path(REQUEST_PATH)
                .queryParam("origin", naviRequest.getOrigin())
                        .queryParam("destination", naviRequest.getDestination())
                        .queryParam("summary", naviRequest.isSummary())
                        .build())
                .retrieve()
                .bodyToMono(NaviResponse.class);

    }

    private DistanceInfo getDistanceInfo(NaviResponse naviResponse) {
        NaviInfo info = naviResponse.getInfo();
        if (info.getCode() != 0) {
            return new DistanceInfo(-999,-999);
        }
        Summary summary = info.getSummary();
        return new DistanceInfo(summary.getDistance(), summary.getDuration());
    }


}
