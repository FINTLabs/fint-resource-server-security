package no.vigoiks.resourceserver.security

import no.vigoiks.testutils.JwtFactory
import spock.lang.Specification

class FintJwtUserConverterSpec extends Specification {

    def "Converting a FINT user JWT should result in 5 authorities"() {
        given:
        def converter = new FintJwtUserConverter()

        when:
        def authenticationToken = converter.convert(JwtFactory.createEndUserJwt()).block()

        then:
        authenticationToken.getAuthorities().size() == 5
    }
}
