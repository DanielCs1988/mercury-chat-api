package com.danielcs.mercurychat.middleware;

import com.danielcs.webserver.socket.AuthGuard;
import com.danielcs.webserver.socket.SocketContext;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;

import java.util.Date;

public class Authenticator implements AuthGuard {

    private HttpsJwksVerificationKeyResolver resolver;

    public Authenticator(HttpsJwks httpsJwks) {
        this.resolver = new HttpsJwksVerificationKeyResolver(httpsJwks);
    }

    @Override
    public boolean authorize(SocketContext ctx, String token) {
        try {
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime()
                    .setExpectedIssuer(System.getenv("ISSUER"))
                    .setExpectedAudience(System.getenv("AUDIENCE"))
                    .setVerificationKeyResolver(resolver)
                    .setJweAlgorithmConstraints(
                            new AlgorithmConstraints(
                                    AlgorithmConstraints.ConstraintType.WHITELIST,
                                    AlgorithmIdentifiers.RSA_USING_SHA256
                            )
                    )
                    .build();
            JwtClaims claims = jwtConsumer.processToClaims(token);
            if (claims.getExpirationTime().getValueInMillis() < new Date().getTime()) {
                System.out.println("Token has already expired!");
                return false;
            }
            ctx.setProperty("userId", claims.getSubject());
        } catch (InvalidJwtException | MalformedClaimException e) {
            System.out.println("Invalid token!\n" + e);
            return false;
        }
        return true;
    }
}
