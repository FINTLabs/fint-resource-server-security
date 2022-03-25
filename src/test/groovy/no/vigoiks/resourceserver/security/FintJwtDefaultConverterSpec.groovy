package no.vigoiks.resourceserver.security

import no.vigoiks.testutils.JwtFactory
import spock.lang.Specification

class FintJwtDefaultConverterSpec extends Specification {

    def "Adding a mapping to the converter should result in a authority for the mapping been created"() {
        given:
        def converter = new FintJwtDefaultConverter()
                .addMapping("organizationid", "ORGID_")
        when:
        def convert = converter.convert(JwtFactory.createEndUserJwt()).block()

        then:
        convert.getAuthorities().size() == 1
        convert.getAuthorities().asList().get(0).getAuthority() == "ORGID_test.com"
    }
}
