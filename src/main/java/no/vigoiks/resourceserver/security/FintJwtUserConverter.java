package no.vigoiks.resourceserver.security;

/**
 * Extracts attributes from the FINT JWT token and adds them as authorities.
 * <p>
 * This converter is specialized for use with end-user facing applications.
 */
public class FintJwtUserConverter extends FintJwtDefaultConverter {

    public FintJwtUserConverter() {
        addMapping("organizationid", "ORGID_");
        addMapping("organizationnumber", "ORGNR_");
        addMapping("roles", "ROLE_");
    }
}
