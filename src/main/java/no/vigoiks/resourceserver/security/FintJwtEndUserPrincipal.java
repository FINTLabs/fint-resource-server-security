package no.vigoiks.resourceserver.security;

import lombok.Data;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.List;

@Data
public class FintJwtEndUserPrincipal {

    private String surname;
    private String givenName;
    private String mail;
    private String studentNumber;
    private String employeeId;
    private String orgId;
    private String organizationNumber;
    private List<String> roles;

    public static FintJwtEndUserPrincipal from(Jwt jwt) {

        FintJwtEndUserPrincipal principal = new FintJwtEndUserPrincipal();

        principal.setSurname(jwt.getClaimAsString("surname"));
        principal.setGivenName(jwt.getClaimAsString("givenname"));
        principal.setMail(jwt.getClaimAsString("email"));
        principal.setStudentNumber(jwt.getClaimAsString("studentnumber"));
        principal.setEmployeeId(jwt.getClaimAsString("employeeId"));
        principal.setOrgId(jwt.getClaimAsString("organizationid"));
        principal.setOrganizationNumber(jwt.getClaimAsString("organizationnumber"));

        principal.setRoles(
                jwt.hasClaim("roles") ?
                        jwt.getClaimAsStringList("roles") :
                        Collections.emptyList()
        );


        return principal;
    }


}
