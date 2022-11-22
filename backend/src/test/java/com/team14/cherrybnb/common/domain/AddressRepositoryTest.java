package com.team14.cherrybnb.common.domain;

import com.team14.cherrybnb.common.config.WebConfig;
import com.team14.cherrybnb.common.util.GeometryUtils;
import com.team14.cherrybnb.openapi.dummy.Position;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@Import(WebConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AddressRepositoryTest {

    AddressRepository addressRepository;

    @Autowired
    AddressRepositoryTest(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Test
    @Transactional(readOnly = true)
    void findAddressWithinTest() {

        double x = 209407.745384399;
        double y = 449459.252995276;
        double radius = 5000;

        Geometry circle = GeometryUtils.createCircle(x, y, radius);
        List<Address> addresses = addressRepository.findAddressWithin(circle);

        assertThat(addresses.size()).isEqualTo(52);
    }
}
