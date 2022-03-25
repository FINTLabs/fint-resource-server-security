package no.vigoiks.resourceserver.security

import no.vigoiks.testutils.JwtFactory
import org.springframework.security.oauth2.jwt.Jwt
import spock.lang.Specification

class FintJwtEndUserPrincipalSpec extends Specification {

    def "Converting a Jwt token with all claims set should populate the principal object with all values"() {
        when:
        def principal = FintJwtEndUserPrincipal.from(JwtFactory.createEndUserJwt())

        then:
        principal.getMail() == "test@test.com"
        principal.getSurname() == "Testesen"
        principal.getGivenName() == "Test"
        principal.getStudentNumber() == "123456"
        principal.getEmployeeId() == "654321"
        principal.getOrganizationNumber() == "123456789"
        principal.getOrgId() == "test.com"
        principal.getRoles().size() == 3
        principal.getRoles().get(0) == "role1"
    }
}
